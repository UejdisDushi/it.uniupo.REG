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

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id,nome,categoria, costo,principio_attivo, ricetta from prodotti");
        ResultSet risultato = preparedStatement.executeQuery();

        while (risultato.next()) {
            prodotto.setId(risultato.getInt("id"));
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

    public ArrayList<Integer> getIdProdottiFarmacia(int idFarmacia) throws SQLException{
        ArrayList<Integer> lista = new ArrayList<Integer>();
        int element;
        if(connection == null)
            this.connessione();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT id_prodotto from rimanenze WHERE id_farmacia=?");
        preparedStatement.setInt(1, idFarmacia);
        ResultSet risultato = preparedStatement.executeQuery();
        while (risultato.next()) {
            element = risultato.getInt(1);
            lista.add(element);
        }
        return lista;
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


    public int setVendita(int id_farmacia, String[] qtaVenduta, ArrayList<Prodotti> prodotti, String userCheEffettuaVendita, boolean controlloPerRicetta) throws SQLException {
        if(connection == null)
            this.connessione();

        if(controlloPerRicetta) {
            for (int j = 0; j < prodotti.size(); j++)
                if (prodotti.get(j).isRicetta() == true)
                    prodotti.remove(j);
        }

        int numeroOrdine = 0;
        PreparedStatement aggiornaRimanenze = null;
        PreparedStatement recuperaIdOrdine = null;
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
        if(inserisciInOrdine.executeUpdate() > 0) {
            recuperaIdOrdine = connection.prepareStatement("SELECT MAX(numero_ordine) FROM ordine");
            ResultSet resultSet = recuperaIdOrdine.executeQuery();
            while (resultSet.next())
                numeroOrdine = resultSet.getInt(1);
        }
        return numeroOrdine;
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

    public String getRuoloByCF(String CF) throws SQLException {
        if(connection == null)
            this.connessione();
        String ruolo = "";

        PreparedStatement ottieneRuolo = connection.prepareStatement("SELECT ruolo from personale where cf=?");
        ottieneRuolo.setString(1, CF);
        ResultSet risultato = ottieneRuolo.executeQuery();
        while (risultato.next()) {
            ruolo = risultato.getString(1);
        }
        return ruolo;

    }

    public boolean reintegra(String[] quantita, int idFarmacia, ArrayList<Prodotti> prodotti) throws SQLException {
        if (connection == null)
            this.connessione();
        boolean status = true;
        Date data_locale = new Date(Calendar.getInstance().getTime().getTime());

        //elenco dei prodotti già presenti in magazzino
        ArrayList<Integer> prodottiInMagazzino = getIdProdottiFarmacia(idFarmacia);

        PreparedStatement aggiornaMagazzino;
        for (int i = 0; i < prodotti.size(); i++) {

            //se il prodotto è già in magazzino allora reintegro con update
            if (prodottiInMagazzino.contains(prodotti.get(i).getId())) {
                aggiornaMagazzino = connection.prepareStatement("UPDATE rimanenze SET qta = qta + ?,data_reintegro=? WHERE id_prodotto = ? and id_farmacia = ?");
                if (quantita[i].equals(""))
                    aggiornaMagazzino.setInt(1, 0);
                else
                    aggiornaMagazzino.setInt(1, Integer.parseInt(quantita[i].toString()));

                aggiornaMagazzino.setDate(2, data_locale);
                aggiornaMagazzino.setInt(3, prodotti.get(i).getId());
                aggiornaMagazzino.setInt(4, idFarmacia);
                if (aggiornaMagazzino.executeUpdate() > 0)
                    aggiornaMagazzino = null;
                else status = false;
            }
            //se non presente in magazzino allora lo aggiungo
            else {
                if(quantita[i].equals("")) {}
                else if (inserisciInMagazzino(Integer.parseInt(quantita[i]), data_locale, idFarmacia, prodotti.get(i).getId()))
                    status = true;
                else status = false;
            }

        }
        return status;
}

    private boolean inserisciInMagazzino(int quantita, Date data, int idFarmacia,int idProdotto) throws SQLException {
        PreparedStatement inserisci = connection.prepareStatement("INSERT into rimanenze VALUES (?,?,?,?)");
        inserisci.setInt(1, quantita);
        inserisci.setDate(2, data);
        inserisci.setInt(3, idFarmacia);
        inserisci.setInt(4, idProdotto);
        if (inserisci.executeUpdate() > 0) return true;
        return false;
    }

    public ArrayList<Paziente> getPazienti() throws SQLException {
        if(connection == null)
            this.connessione();

        ArrayList<Paziente> elencoPazienti = new ArrayList<>();
        Paziente paziente = new Paziente();

        PreparedStatement query = connection.prepareStatement("SELECT * FROM paziente");
        ResultSet risultatoQuery = query.executeQuery();
        while (risultatoQuery.next()) {
            paziente.setCf(risultatoQuery.getString("cf"));
            paziente.setNome(risultatoQuery.getString("nome"));
            paziente.setCognome(risultatoQuery.getString("cognome"));
            paziente.setDataDiNAscita(risultatoQuery.getDate("data_nascita"));
            paziente.setPersonale(risultatoQuery.getString("utente"));
            elencoPazienti.add(paziente);
            paziente = new Paziente();
        }
        return elencoPazienti;
    }


    public int getQTAInMagazzino(int idFarmacia,int idProdotto) throws SQLException {
        if(connection == null)
            this.connessione();

        int quantita = 0;

        PreparedStatement query = connection.prepareStatement("SELECT qta FROM rimanenze WHERE id_farmacia=? and id_prodotto=?");
        query.setInt(1, idFarmacia);
        query.setInt(2, idProdotto);
        ResultSet risultato = query.executeQuery();
        while (risultato.next())
            quantita = risultato.getInt(1);

        return quantita;
    }

    public ArrayList<Medico> getMedici() throws SQLException {
        if(connection == null)
            this.connessione();

        ArrayList<Medico> elencoMedici = new ArrayList<>();
        Medico medico = new Medico();

        PreparedStatement query = connection.prepareStatement("SELECT * FROM medico");
        ResultSet risultatoQuery = query.executeQuery();
        while (risultatoQuery.next()) {
            medico.setCodiceRegionale(risultatoQuery.getInt("codice_regionale"));
            medico.setNome(risultatoQuery.getString("nome"));
            medico.setCognome(risultatoQuery.getString("cognome"));
            medico.setDataNascita(risultatoQuery.getDate("data_nascita"));
            medico.setCf(risultatoQuery.getString("cf"));
            elencoMedici.add(medico);
            medico = new Medico();
        }
        return elencoMedici;
    }

    public boolean setRicetta(int codiceRegionale, String cfPaziente, int idOrdine) throws SQLException {
        if(connection == null)
            this.connessione();

        Date data_locale = new Date(Calendar.getInstance().getTime().getTime());

        PreparedStatement inserisciRicetta = connection.prepareStatement("insert into ricetta (data, codice_regionale, cf, numero_ordine) values (?,?,?,?)");
        inserisciRicetta.setDate(1,data_locale);
        inserisciRicetta.setInt(2,codiceRegionale);
        inserisciRicetta.setString(3, cfPaziente);
        inserisciRicetta.setInt(4, idOrdine);
        if(inserisciRicetta.executeUpdate() > 0)
            return true;
        return false;
    }

    public boolean inserisciNuovoPaziente(String cf, String nome, String cognome, Date dataDiNAscita, String userCheRegistraPaziente) throws SQLException{
        if(connection == null)
            this.connessione();

        PreparedStatement nuovoPaziente = connection.prepareStatement("INSERT INTO paziente(cf, nome, cognome, data_nascita, utente) VALUES (?,?,?,?,?)");
        nuovoPaziente.setString(1,cf);
        nuovoPaziente.setString(2, nome);
        nuovoPaziente.setString(3,cognome);
        nuovoPaziente.setDate(4,dataDiNAscita);
        nuovoPaziente.setString(5,userCheRegistraPaziente);
        if(nuovoPaziente.executeUpdate() > 0)
            return true;
        return false;
    }
}























