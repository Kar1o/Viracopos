package Model;

import java.sql.*;

/**
 * Created by k on 5/3/15.
 */
public class ConnectData {
    public Connection connection;

    //insert
    public Statement statement;
    //query
    public ResultSet resultSet;

    private final String DRIVER = "org.mariadb.jdbc.Driver";
    private final String DB = "Viracopos";
    private final String URL = "jdbc:mariadb://23.239.18.68:3306/";
    private final String USER = "admin";
    private final String PASSWD = "m1IgIOUY4ekY";

    public boolean open(){
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWD);
            System.out.println("Conectado com sucesso");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean close(){
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void selectQuestions(){
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectPlayers(){
        try {
            statement=connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
