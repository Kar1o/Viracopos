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
    private final String QUERY_ALUNO = "select ra, nome from " + DB + ".aluno";
    private final String QUERY_PERGUNTAS = "select pergunta_id, pergunta, resposta, round from " + DB + ".perguntas";

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

    public void selectQuestions() throws SQLException {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(QUERY_PERGUNTAS);
            while (resultSet.next()){
                int pergunta_id = resultSet.getInt("pergunta_id");
                String pergunta = resultSet.getString("pergunta");
                String resposta = resultSet.getString("resposta");
                int categoria = resultSet.getInt("round");
                System.out.println("id:" + pergunta_id + " pergunta:" + pergunta + " resposta:" + resposta +
                        " round:" +categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null){ statement.close(); }
        }
    }

    public void selectPlayers() throws SQLException {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(QUERY_ALUNO);
            while (resultSet.next()){
                int ra = resultSet.getInt("ra");
                String nome = resultSet.getString("nome");
                System.out.println("ra:" + ra + " nome:" + nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (statement != null){ statement.close(); }
        }

    }

}
