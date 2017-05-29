package db;

import model.Farmacia;

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

    public boolean validate(String utente, String password) throws SQLException {
        boolean status;
        if(connection == null)
            this.connessione();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from login join personale on login.cf = personale.cf where utente=? and password=? and ruolo='adm'");
        preparedStatement.setString(1,utente);
        preparedStatement.setString(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        status = resultSet.next();
        connection.close();
        return status;
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
            farmacia.setNome(resultSet.getString("nome"));
            farmacia.setNumeroTelefono(resultSet.getString("numero_telefono"));
            farmacia.setProvincia(resultSet.getString("provincia"));
            farmacia.setVia(resultSet.getString("via"));
            listaFarmacie.add(farmacia);
        }
        return listaFarmacie;
    }

    public boolean attivaFarmaciaAndTF(Farmacia farmacia) throws SQLException {

        if(connection == null)
            this.connessione();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into farmacia(nome, cap, citta, numero_telefono, provincia, via) VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(1,farmacia.getNome());
        preparedStatement.setString(2,farmacia.getCap());
        preparedStatement.setString(3,farmacia.getCitta());
        preparedStatement.setString(4,farmacia.getNumeroTelefono());
        preparedStatement.setString(5,farmacia.getProvincia());
        preparedStatement.setString(6,farmacia.getVia());
        if(preparedStatement.executeUpdate() > 0)
            return true;
        return false;
    }
}
























