package db;

import model.Farmacia;
import model.Login;
import model.Personale;

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
            farmacia.setIdFarmacia(resultSet.getLong("id_farmacia"));
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
}
























