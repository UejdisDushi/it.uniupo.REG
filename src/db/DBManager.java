package db;

import java.sql.*;

public class DBManager {

    private String url = "jdbc:postgresql://localhost:5432/REG";
    private String driverName = "org.postgresql.Driver";
    private String username = "postgres";
    private String password = "admin";
    private Connection connection = null;


    public void connessione() {
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
        boolean status = false;
        if(connection == null)
            this.connessione();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from login where utente=? and password=?");
        preparedStatement.setString(1,utente);
        preparedStatement.setString(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        status = resultSet.next();
        return status;
    }
}
