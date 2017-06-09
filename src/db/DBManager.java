package db;

import model.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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

    public ArrayList<Prodotti> getProdottiInMagazzino(int id_farmacia) throws SQLException {
        ArrayList<Prodotti> prodotti = new ArrayList<Prodotti>();
        Prodotti prodotto = new Prodotti();

        if(connection == null)
            this.connessione();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_prodotto,nome,categoria, costo,principio_attivo,ricetta from prodotti join rimanenze on prodotti.id = rimanenze.id_prodotto where id_farmacia=?");
        preparedStatement.setInt(1, id_farmacia);
        ResultSet risultato = preparedStatement.executeQuery();

        while (risultato.next()) {
            prodotto.setId(risultato.getInt("id_prodotto"));
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

    public String getCFByUser(String username) throws SQLException {
        if(connection == null)
            this.connessione();
        String out = "";
        PreparedStatement getCFByUser = connection.prepareStatement("SELECT cf from login WHERE utente=?");
        getCFByUser.setString(1, username);
        ResultSet risultato = getCFByUser.executeQuery();
        while (risultato.next())
            out = risultato.getString(1);
        return out;
    }

    public boolean setVenditaPerOB(int id_farmacia, String[] qtaVenduta, ArrayList<Prodotti> prodotti, String userCheEffettuaVendita) throws SQLException {
        if(connection == null)
            this.connessione();

        for(int j = 0;j<prodotti.size();j++)
            if(prodotti.get(j).isRicetta() == true)
                prodotti.remove(j);

        PreparedStatement aggiornaRimanenze = null;
        double totaleAcquisto = 0;
        for(int i = 0;i<prodotti.size();i++) {
            aggiornaRimanenze = connection.prepareStatement("UPDATE rimanenze SET qta = qta - ? WHERE id_prodotto = ? and id_farmacia = ?");
            if(qtaVenduta[i].equals(""))
                aggiornaRimanenze.setInt(1, 0);
            else {
                aggiornaRimanenze.setInt(1, Integer.parseInt(qtaVenduta[i].toString()));
                totaleAcquisto = totaleAcquisto + (prodotti.get(i).getCosto() * Integer.parseInt(qtaVenduta[i].toString()));
            }
            aggiornaRimanenze.setInt(2, prodotti.get(i).getId());
            aggiornaRimanenze.setInt(3, id_farmacia);
            if(aggiornaRimanenze.executeUpdate() > 0) {
                aggiornaRimanenze = null;
            }
        }
        PreparedStatement inserisciInOrdine = connection.prepareStatement("insert INTO ordine( utente, totale_ordine,data_ordine) VALUES (?,?,?)");
        inserisciInOrdine.setString(1,this.getCFByUser(userCheEffettuaVendita));
        inserisciInOrdine.setDouble(2, totaleAcquisto);
        Date data_locale = new Date(Calendar.getInstance().getTime().getTime());
        inserisciInOrdine.setDate(3,data_locale);
        if(inserisciInOrdine.executeUpdate() > 0)
            return true;
        return false;
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
        if(utente.equals("REG") && password.equals("Amministratore1")) {
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

        if(connection == null)
            this.connessione();

        PreparedStatement inserimentoFarmacia = connection.prepareStatement("INSERT into farmacia(nome, cap, citta, numero_telefono, provincia, via) VALUES (?,?,?,?,?,?)");
        inserimentoFarmacia.setString(1,farmacia.getNomeFarmacia());
        inserimentoFarmacia.setString(2,farmacia.getCap());
        inserimentoFarmacia.setString(3,farmacia.getCitta());
        inserimentoFarmacia.setString(4,farmacia.getNumeroTelefono());
        inserimentoFarmacia.setString(5,farmacia.getProvincia());
        inserimentoFarmacia.setString(6,farmacia.getVia());

        if(inserimentoFarmacia.executeUpdate() > 0) {
            PreparedStatement recuperoIdTramiteNomeFarmacia = connection.prepareStatement("SELECT id_farmacia FROM farmacia where nome=?");
            recuperoIdTramiteNomeFarmacia.setString(1, farmacia.getNomeFarmacia());
            ResultSet result = recuperoIdTramiteNomeFarmacia.executeQuery();

            while (result.next())
                id = result.getLong(1);

            String role = "tf";
            PreparedStatement inserimentoPersonale = connection.prepareStatement("INSERT into personale(nome, cognome, cf, data_nascita, ruolo, id_farmacia) VALUES (?,?,?,?,?,?)");
            inserimentoPersonale.setString(1, personale.getNomePersonale());
            inserimentoPersonale.setString(2, personale.getCognome());
            inserimentoPersonale.setString(3, personale.getCf());
            inserimentoPersonale.setDate(4, personale.getDataNascita());
            inserimentoPersonale.setString(5, role);
            inserimentoPersonale.setLong(6, id);

            if (inserimentoPersonale.executeUpdate() > 0) {

                String cf = "";
                PreparedStatement recuperaCf = connection.prepareStatement("SELECT cf from personale where cf = ?");
                recuperaCf.setString(1, personale.getCf());
                ResultSet result1 = recuperaCf.executeQuery();

                while (result1.next())
                    cf = result1.getString("cf");

                PreparedStatement inserimentoCredenziali = connection.prepareStatement("insert into login(utente, password, cf) VALUES (?,?,?)");
                inserimentoCredenziali.setString(1, login.getUser());
                inserimentoCredenziali.setString(2, login.getPassword());
                inserimentoCredenziali.setString(3, cf);

                if (inserimentoCredenziali.executeUpdate() > 0)
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
























