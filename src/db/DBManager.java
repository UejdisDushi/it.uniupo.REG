package db;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private String url = "jdbc:postgresql://localhost:5432/REG";
    private String driverName = "org.postgresql.Driver";
    private String username = "postgres";
    private String password = "admin";
    private Connection connection = null;


    private void connessione() {
        try {
            Class.forName(driverName);
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found.");
        }
    }

    public ArrayList<Prodotti> getTuttiProdotti() throws SQLException {
        ArrayList<Prodotti> prodotti = new ArrayList<Prodotti>();
        Prodotti prodotto = new Prodotti();

        if(connection == null)
            this.connessione();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT nome,categoria, costo,principio_attivo, ricetta from prodotti");
        ResultSet risultato = preparedStatement.executeQuery();

        while (risultato.next()) {
            prodotto.setNome(risultato.getString("nome"));
            prodotto.setCategoria(risultato.getString("categoria"));
            prodotto.setCosto(risultato.getDouble("costo"));
            prodotto.setPrincipioAttivo(risultato.getString("principio_attivo"));
            prodotto.setRicetta(risultato.getBoolean("ricetta"));
            prodotti.add(prodotto);
            prodotto = new Prodotti();
        }
        return prodotti;
    }

    public ArrayList<Rimanenze> getRimanenzeByIdFarmacia(int id_farmacia) throws SQLException {
        ArrayList<Rimanenze> rimanenze = new ArrayList<Rimanenze>();
        Rimanenze rimanenza = new Rimanenze();

        if(connection == null)
            this.connessione();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT qta, id_prodotto from rimanenze WHERE id_farmacia=?");
        preparedStatement.setInt(1, id_farmacia);
        ResultSet risultato = preparedStatement.executeQuery();

        while (risultato.next()) {
            rimanenza.setQuantita(risultato.getInt(1));
            rimanenza.setIdProdotto(risultato.getInt(2));
            rimanenze.add(rimanenza);
            rimanenza = new Rimanenze();
        }
        return rimanenze;
    }

    public ArrayList<Prodotti> getOBMagazzino(int id_farmacia) throws SQLException {
        ArrayList<Prodotti> prodotti = new ArrayList<Prodotti>();
        Prodotti prodotto = new Prodotti();

        if(connection == null)
            this.connessione();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_prodotto,nome,categoria, costo,principio_attivo from prodotti join rimanenze on prodotti.id = rimanenze.id_prodotto where ricetta='false' and id_farmacia=?");
        preparedStatement.setInt(1, id_farmacia);
        ResultSet risultato = preparedStatement.executeQuery();

        while (risultato.next()) {
            prodotto.setId(risultato.getInt("id_prodotto"));
            prodotto.setNome(risultato.getString("nome"));
            prodotto.setCategoria(risultato.getString("categoria"));
            prodotto.setCosto(risultato.getDouble("costo"));
            prodotto.setPrincipioAttivo(risultato.getString("principio_attivo"));
            prodotti.add(prodotto);
            prodotto = new Prodotti();
        }
        return prodotti;
    }

    public boolean setVendita(int id_farmacia, String[] qtaVenduta, ArrayList<Prodotti> prodotti) throws SQLException {
        if(connection == null)
            this.connessione();

        PreparedStatement preparedStatement = null;
        System.out.print(qtaVenduta[0]);
        System.out.print(qtaVenduta[1]);
        for(int i = 0;i<prodotti.size();i++) {
            preparedStatement = connection.prepareStatement("UPDATE rimanenze SET qta = qta - ? WHERE id_prodotto = ? and id_farmacia = ?");
            if(qtaVenduta[i].equals(""))
                preparedStatement.setInt(1, 0);
            else {
                preparedStatement.setInt(1, Integer.parseInt(qtaVenduta[i].toString()));
            }
            preparedStatement.setInt(2, prodotti.get(i).getId());
            preparedStatement.setInt(3, id_farmacia);
            if(preparedStatement.executeUpdate() > 0)
                preparedStatement = null;
            else return false;
        }
        return true;
    }

    public int getIdFarmacia(String cf) throws SQLException {
        int id_farmacia = 0;
        if(connection == null)
            this.connessione();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_farmacia from personale where cf = ?");
        preparedStatement.setString(1, cf);
        ResultSet risultato = preparedStatement.executeQuery();
        while (risultato.next())
            id_farmacia = risultato.getInt("id_farmacia");
        return id_farmacia;
    }

    public String validate(String utente, String password) throws SQLException {
        String ruolo = "";
        if(connection == null)
            this.connessione();
        if(utente.equals("REG") && password.equals("amministratore")) {
            ruolo = "adm";
            return ruolo;
        }

        PreparedStatement preparedStatement = connection.prepareStatement(
                "select ruolo from login join personale on login.cf = personale.cf where utente=? and password=?");
        preparedStatement.setString(1,utente);
        preparedStatement.setString(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next())
            ruolo = resultSet.getString("ruolo");

        return ruolo;
    }

    public List<Farmacia> elencoFarmacie() throws SQLException {
        List<Farmacia> listaFarmacie = new ArrayList<>();
        Farmacia farmacia;
        if(connection == null)
            this.connessione();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from farmacia");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            farmacia = new Farmacia();
            farmacia.setCap(resultSet.getString("cap"));
            farmacia.setCitta(resultSet.getString("citta"));
            farmacia.setIdFarmacia(resultSet.getInt("id_farmacia"));
            farmacia.setNomeFarmacia(resultSet.getString("nome"));
            farmacia.setNumeroTelefono(resultSet.getString("numero_telefono"));
            farmacia.setProvincia(resultSet.getString("provincia"));
            farmacia.setVia(resultSet.getString("via"));
            listaFarmacie.add(farmacia);
        }
        return listaFarmacie;
    }

    public boolean attivaFarmaciaAndTF(Farmacia farmacia, Personale personale, Login login) throws SQLException {
        long id = 0;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;
        if(connection == null)
            this.connessione();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into farmacia(nome, cap, citta, numero_telefono, provincia, via) VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(1,farmacia.getNomeFarmacia());
        preparedStatement.setString(2,farmacia.getCap());
        preparedStatement.setString(3,farmacia.getCitta());
        preparedStatement.setString(4,farmacia.getNumeroTelefono());
        preparedStatement.setString(5,farmacia.getProvincia());
        preparedStatement.setString(6,farmacia.getVia());

        if(preparedStatement.executeUpdate() > 0) {

            preparedStatement1 = connection.prepareStatement("SELECT id_farmacia FROM farmacia where nome=?");
            preparedStatement1.setString(1, farmacia.getNomeFarmacia());
            ResultSet result = preparedStatement1.executeQuery();
            while (result.next())
                id = result.getLong(1);

            String role = "tf";
            preparedStatement2 = connection.prepareStatement("INSERT into personale(nome, cognome, cf, data_nascita, ruolo, id_farmacia) VALUES (?,?,?,?,?,?)");
            preparedStatement2.setString(1, personale.getNomePersonale());
            preparedStatement2.setString(2, personale.getCognome());
            preparedStatement2.setString(3, personale.getCf());
            preparedStatement2.setDate(4, personale.getDataNascita());
            preparedStatement2.setString(5, role);
            preparedStatement2.setLong(6, id);

            if (preparedStatement2.executeUpdate() > 0) {

                String cf = "";
                preparedStatement3 = connection.prepareStatement("SELECT cf from personale where cf = ?");
                preparedStatement3.setString(1, personale.getCf());
                ResultSet result1 = preparedStatement3.executeQuery();

                while (result1.next())
                    cf = result1.getString("cf");

                preparedStatement4 = connection.prepareStatement("insert into login(utente, password, cf) VALUES (?,?,?)");
                preparedStatement4.setString(1, login.getUser());
                preparedStatement4.setString(2, login.getPassword());
                preparedStatement4.setString(3, cf);

                if (preparedStatement4.executeUpdate() > 0)
                    return true;


            }
        }
        return false;
    }

    public boolean attivaCollaboratore(Farmacia personaleDaInserire, Login login, long id_farmacia) throws SQLException {
        if(connection == null)
            this.connessione();
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO personale(nome, cognome, cf, data_nascita, ruolo, id_farmacia) VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(1, personaleDaInserire.getNomePersonale());
        preparedStatement.setString(2, personaleDaInserire.getCognome());
        preparedStatement.setString(3, personaleDaInserire.getCf());
        preparedStatement.setDate(4, (java.sql.Date) personaleDaInserire.getDataNascita());
        preparedStatement.setString(5, personaleDaInserire.getRuolo());
        preparedStatement.setLong(6, id_farmacia);
        if (preparedStatement.executeUpdate() > 0) {
            preparedStatement1 = connection.prepareStatement("INSERT INTO login(utente, password, cf) VALUES (?,?,?)");
            preparedStatement1.setString(1, login.getUser());
            preparedStatement1.setString(2, login.getPassword());
            preparedStatement1.setString(3, personaleDaInserire.getCf());
            if (preparedStatement1.executeUpdate() > 0)
                return true;
        }
        return false;
    }

    public String getCF(String utente) throws SQLException {
        String cf = "";
        if(connection == null)
            this.connessione();
        PreparedStatement preparedStatement = connection.prepareStatement("select cf from login where utente = ?");
        preparedStatement.setString(1, utente);
        ResultSet risultato = preparedStatement.executeQuery();
        while (risultato.next())
            cf = risultato.getString("cf");
        return cf;
    }

    public boolean reintegra(int quantita, int idFarmacia, ArrayList<Prodotti> prodotti) throws SQLException {
        if(connection == null)
            this.connessione();
        PreparedStatement preparedStatement = null;
        for(int i = 0;i<prodotti.size();i++) {
            preparedStatement = connection.prepareStatement("UPDATE rimanenze SET qta = qta + ? WHERE id_prodotto = ? and id_farmacia = ?");
            preparedStatement.setInt(1, quantita);
            preparedStatement.setInt(2, prodotti.get(i).getId());
            preparedStatement.setInt(3, idFarmacia);
            if(preparedStatement.executeUpdate() > 0)
                preparedStatement = null;
            else return false;
        }
        return true;
    }
}
























