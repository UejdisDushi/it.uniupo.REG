package db;

import model.*;

import java.sql.*;
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
        ArrayList<Prodotti> prodotti = new ArrayList<>();
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

    /**
     * Ottiene l'elenco di ID prodotti che stanno nel magazzino della farmacia in input
     */
    public ArrayList<Integer> getIdProdottiFarmacia(int idFarmacia) throws SQLException{
        ArrayList<Integer> lista = new ArrayList<>();
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

    /**
     * Ottiene quantità e id dei prodotti in magazzino per la farmacia in input
     */
    public ArrayList<Rimanenze> getRimanenzeByIdFarmacia(int id_farmacia) throws SQLException {
        if(connection == null)
            this.connessione();

        ArrayList<Rimanenze> rimanenze = new ArrayList<>();
        Rimanenze rimanenza = new Rimanenze();

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

    /**
     * Ottiene anagrafica dei prodotti in magazzino per la farmacia in input
     */
    public ArrayList<Prodotti> getProdottiInMagazzino(int id_farmacia) throws SQLException {
        ArrayList<Prodotti> prodotti = new ArrayList<>();
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

    /**
     * Ottiene il CF partendo dallo username
     */
    public String getCFByUsername(String username) throws SQLException {
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

        //se controllo per ricetta = true significa che sto impostando una vendita per OB, quindi
        //rimuovo tutti i prodotti con obbligo di ricetta
        if(controlloPerRicetta) {
            for (int j = 0; j < prodotti.size(); j++) {
                if (prodotti.get(j).isRicetta() == true) {
                    prodotti.remove(j);
                }
            }
        }

        PreparedStatement aggiornaRimanenze;
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
            aggiornaRimanenze.executeUpdate();
        }
        PreparedStatement inserisciInOrdine = connection.prepareStatement("insert INTO ordine( utente, totale_ordine,data_ordine) VALUES (?,?,?) RETURNING numero_ordine", PreparedStatement.RETURN_GENERATED_KEYS);
        inserisciInOrdine.setString(1,this.getCFByUsername(userCheEffettuaVendita));
        inserisciInOrdine.setDouble(2, totaleAcquisto);
        Date data_locale = new Date(Calendar.getInstance().getTime().getTime());
        inserisciInOrdine.setDate(3,data_locale);
        inserisciInOrdine.executeUpdate();

        //recupero l'id generato dall'inserimento dell'ordine
        ResultSet resultSet = inserisciInOrdine.getGeneratedKeys();
        resultSet.next();
        int numeroOrdine = resultSet.getInt("numero_ordine");

        //ora inserisco tutto nella tabella contiene
        inserisciInOrdine(numeroOrdine,prodotti,qtaVenduta);

        return numeroOrdine;
    }

    private void inserisciInOrdine (int numeroOrdine, List<Prodotti> elencoProdotti, String[] qtaVenduta) throws SQLException {
        for(int i = 0;i<elencoProdotti.size();i++) {
            if(!qtaVenduta[i].equals("")) {
                PreparedStatement inserisci = connection.prepareStatement("insert INTO contiene(qta, numero_ordine, id_prodotto) VALUES (?,?,?)");
                inserisci.setInt(1, Integer.parseInt(qtaVenduta[i]));
                inserisci.setInt(2, numeroOrdine);
                inserisci.setInt(3, elencoProdotti.get(i).getId());
                inserisci.executeUpdate();
            }
        }
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

    public ArrayList<Farmacia> elencoFarmacie() throws SQLException {
        if(connection == null)
            this.connessione();

        ArrayList<Farmacia> listaFarmacie = new ArrayList<>();
        Farmacia farmacia;
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
        boolean status = false;

        if(connection == null)
            this.connessione();

        try {
            PreparedStatement inserimentoFarmacia = connection.prepareStatement("INSERT into farmacia(nome, cap, citta, numero_telefono, provincia, via) VALUES (?,?,?,?,?,?) returning id_farmacia", PreparedStatement.RETURN_GENERATED_KEYS);
            inserimentoFarmacia.setString(1, farmacia.getNomeFarmacia());
            inserimentoFarmacia.setString(2, farmacia.getCap());
            inserimentoFarmacia.setString(3, farmacia.getCitta());
            inserimentoFarmacia.setString(4, farmacia.getNumeroTelefono());
            inserimentoFarmacia.setString(5, farmacia.getProvincia());
            inserimentoFarmacia.setString(6, farmacia.getVia());

            if (inserimentoFarmacia.executeUpdate() > 0) {
                //recupero l'id generato con la creazione della farmacia
                ResultSet resultSet = inserimentoFarmacia.getGeneratedKeys();
                resultSet.next();
                id = resultSet.getInt("id_farmacia");
                String role = "tf";
                PreparedStatement inserimentoPersonale = connection.prepareStatement("INSERT into personale(nome, cognome, cf, data_nascita, ruolo, id_farmacia) VALUES (?,?,?,?,?,?)");
                inserimentoPersonale.setString(1, personale.getNomePersonale());
                inserimentoPersonale.setString(2, personale.getCognome());
                inserimentoPersonale.setString(3, personale.getCf());
                inserimentoPersonale.setDate(4, personale.getDataNascita());
                inserimentoPersonale.setString(5, role);
                inserimentoPersonale.setLong(6, id);

                if (inserimentoPersonale.executeUpdate() > 0) {

                    PreparedStatement inserimentoCredenziali = connection.prepareStatement("insert into login(utente, password, cf) VALUES (?,?,?)");
                    inserimentoCredenziali.setString(1, login.getUser());
                    inserimentoCredenziali.setString(2, login.getPassword());
                    inserimentoCredenziali.setString(3, personale.getCf());

                    if (inserimentoCredenziali.executeUpdate() > 0)
                        status = true;

                }
            }
        } catch (SQLException e) {
            status = false;
        }
        return status;
    }

    public boolean attivaCollaboratore(Farmacia personaleDaInserire, Login login, long id_farmacia) throws SQLException {
        if(connection == null)
            this.connessione();
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO personale(nome, cognome, cf, data_nascita, ruolo, id_farmacia) VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(1, personaleDaInserire.getNomePersonale());
        preparedStatement.setString(2, personaleDaInserire.getCognome());
        preparedStatement.setString(3, personaleDaInserire.getCf());
        preparedStatement.setDate(4, personaleDaInserire.getDataNascita());
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
                if (aggiornaMagazzino.executeUpdate() > 0) status = true;
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

    public int getQTAInMagazzino(int idFarmacia,int idProdotto) throws SQLException {
        if(connection == null)
            this.connessione();

        PreparedStatement query = connection.prepareStatement("SELECT qta FROM rimanenze WHERE id_farmacia=? and id_prodotto=?");
        query.setInt(1, idFarmacia);
        query.setInt(2, idProdotto);
        ResultSet risultato = query.executeQuery();
        risultato.next();
        return risultato.getInt(1);
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

    public ArrayList<String> getElencoPazientiPerMessaggi(String ilTuoRuolo, int idFarmacia, String CFDiChiSpedisce) throws SQLException{
        if(connection == null)
            this.connessione();

        ArrayList<String> elenco = new ArrayList<>();

        //se ruolo uguale a vuoto, allora è reg, essendo reg può mandare ad una singola farmacia, oppure a tutte le farmacie
        if(ilTuoRuolo.equals("")) {
            PreparedStatement elencoFarmacie = connection.prepareStatement("SELECT DISTINCT farmacia.nome,farmacia.id_farmacia from farmacia JOIN personale ON farmacia.id_farmacia = personale.id_farmacia");
            ResultSet risultato = elencoFarmacie.executeQuery();
            while (risultato.next())
                elenco.add(risultato.getInt(2) + " - " + risultato.getString(1));
            elenco.add("Tutte le farmacie");
            return elenco;
        }

        if(ilTuoRuolo.equals("df") || ilTuoRuolo.equals("ob") || ilTuoRuolo.equals("tf")) {
            if(ilTuoRuolo.equals("tf"))
                elenco.add("REG");
            PreparedStatement elencoPersone = connection.prepareStatement("SELECT cf from personale WHERE id_farmacia=? and cf<>?");
            String utente = "";
            elencoPersone.setInt(1,idFarmacia);
            elencoPersone.setString(2, CFDiChiSpedisce);
            ResultSet risultato = elencoPersone.executeQuery();
            while (risultato.next()) {
                utente = this.getUserByCF(risultato.getString(1));
                if(!utente.equals(""))
                    elenco.add(utente);
            }
            elenco.add("Tutti i collaboratori");
            return elenco;
        }
        return elenco;
    }

    public String getUserByCF(String cf) throws SQLException{
        PreparedStatement ottieniUser = connection.prepareStatement("select utente from login WHERE cf=?");
        ottieniUser.setString(1,cf);
        ResultSet risultato = ottieniUser.executeQuery();
        risultato.next();
        return risultato.getString(1);
    }

    public String getNomeECognomeByCf(String cf) throws SQLException {
        if(connection == null)
            this.connessione();

        PreparedStatement query = connection.prepareStatement("SELECT nome,cognome from personale WHERE cf=?");
        query.setString(1, cf);
        ResultSet risultato = query.executeQuery();
        risultato.next();
        return risultato.getString("nome") + " " + risultato.getString("cognome");
    }

    public boolean nuovoMessaggio(String mittente, String destinatario, String corpo, int idFarmacia) throws SQLException {
        if(connection == null)
            this.connessione();

        boolean mandato = false;

        try {
            if (destinatario.startsWith("Tutti")) {
                ArrayList<String> elenco = new ArrayList<>();
                PreparedStatement elencoUtenti = connection.prepareStatement("SELECT utente FROM login JOIN personale on login.cf = personale.cf WHERE id_farmacia=? and utente<>?");
                elencoUtenti.setInt(1, idFarmacia);
                elencoUtenti.setString(2, mittente);
                ResultSet risultato = elencoUtenti.executeQuery();
                while (risultato.next())
                    elenco.add(risultato.getString(1));
                for (String s : elenco) {
                    PreparedStatement mandaATutti = connection.prepareStatement("INSERT INTO messaggi(mittente, ricevente, corpo, data, visualizzato) VALUES (?,?,?,?,?)");
                    mandaATutti.setString(1, mittente);
                    mandaATutti.setString(2, s);
                    mandaATutti.setString(3, corpo);
                    Date data_locale = new Date(Calendar.getInstance().getTime().getTime());
                    mandaATutti.setDate(4, data_locale);
                    mandaATutti.setBoolean(5, false);
                    if (mandaATutti.executeUpdate() > 0) mandato = true;
                }
            }

            if (destinatario.startsWith("Tutte")) {
                ArrayList<String> elenco = new ArrayList<>();
                PreparedStatement elencoUtenti = connection.prepareStatement("SELECT cf FROM personale WHERE ruolo='tf'");
                ResultSet risultato = elencoUtenti.executeQuery();
                while (risultato.next())
                    elenco.add(risultato.getString(1));
                for (String s : elenco) {
                    PreparedStatement mandaATutti = connection.prepareStatement("INSERT INTO messaggi(mittente, ricevente, corpo, data,visualizzato) VALUES (?,?,?,?,?)");
                    mandaATutti.setString(1, mittente);
                    mandaATutti.setString(2, this.getUserByCF(s));
                    mandaATutti.setString(3, corpo);
                    Date data_locale = new Date(Calendar.getInstance().getTime().getTime());
                    mandaATutti.setDate(4, data_locale);
                    mandaATutti.setBoolean(5, false);
                    if (mandaATutti.executeUpdate() > 0) mandato = true;
                }

            }

            int idFarmaciaPerTF;
            String temp = destinatario;
            temp = temp.replaceAll("\\s+", "");

            for (int i = 0; i < temp.length(); i++) {
                if (temp.charAt(i) == '-') {
                    String CFdiTF = "";
                    idFarmaciaPerTF = Integer.parseInt(destinatario.substring(0, i));
                    PreparedStatement trovaTfDiFarmacia = connection.prepareStatement("SELECT cf from personale WHERE ruolo='tf' and id_farmacia=?");
                    trovaTfDiFarmacia.setInt(1, idFarmaciaPerTF);
                    ResultSet risultato = trovaTfDiFarmacia.executeQuery();
                    while (risultato.next())
                        CFdiTF = risultato.getString(1);

                    PreparedStatement mandaATF = connection.prepareStatement("INSERT INTO messaggi(mittente, ricevente, corpo, data,visualizzato) VALUES (?,?,?,?,?)");
                    mandaATF.setString(1, mittente);
                    mandaATF.setString(2, this.getUserByCF(CFdiTF));
                    mandaATF.setString(3, corpo);
                    Date data_locale = new Date(Calendar.getInstance().getTime().getTime());
                    mandaATF.setDate(4, data_locale);
                    mandaATF.setBoolean(5, false);
                    if (mandaATF.executeUpdate() > 0) mandato = true;
                }
            }

            if (!mandato) {
                PreparedStatement manda = connection.prepareStatement("INSERT INTO messaggi(mittente, ricevente, corpo, data,visualizzato) VALUES (?,?,?,?,?)");
                manda.setString(1, mittente);
                manda.setString(2, destinatario);
                manda.setString(3, corpo);
                Date data_locale = new Date(Calendar.getInstance().getTime().getTime());
                manda.setDate(4, data_locale);
                manda.setBoolean(5, false);
                if (manda.executeUpdate() > 0) mandato = true;
            }
        } catch (SQLException e) {
            mandato = false;
        }
        return mandato;
    }

    public ArrayList<Messaggio> getMessaggiDaLeggere(String username, boolean conVisualizzato) throws SQLException {
        if(connection == null)
            this.connessione();

        ArrayList<Messaggio> daLeggere = new ArrayList<>();
        Messaggio messaggio;
        PreparedStatement query = connection.prepareStatement("SELECT mittente,ricevente, corpo, data, visualizzato from messaggi where ricevente=?");
        query.setString(1,username);
        ResultSet resultSet = query.executeQuery();

        while(resultSet.next()) {
            messaggio = new Messaggio();
            messaggio.setMittente(resultSet.getString(1));
            messaggio.setDestinatario(resultSet.getString(2));
            messaggio.setCorpo(resultSet.getString(3));
            messaggio.setData(resultSet.getDate(4));
            messaggio.setVisualizzato(resultSet.getBoolean(5));
            daLeggere.add(messaggio);
        }

        if(conVisualizzato) {
            PreparedStatement update = connection.prepareStatement("UPDATE messaggi SET visualizzato = true WHERE ricevente=?");
            update.setString(1, username);
            update.executeUpdate();
        }

        return daLeggere;
    }

    public double getTotaleByIdOrdine(int idOrdine) throws SQLException {
        if(connection == null)
            this.connessione();
        PreparedStatement totale = connection.prepareStatement("SELECT totale_ordine from ordine WHERE numero_ordine=?");
        totale.setInt(1, idOrdine);
        ResultSet risultato = totale.executeQuery();
        risultato.next();
        return risultato.getDouble(1);
    }
}























