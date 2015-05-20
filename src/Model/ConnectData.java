package Model;


import java.sql.*;
/**
 * Created by k on 5/3/15.
 */
public class ConnectData {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private final String DRIVER = "org.mariadb.jdbc.Driver";
    private final String DB = "Viracopos";
    private final String URL = "jdbc:mariadb://23.239.18.68:3306/";
    private final String USER = "admin";
    private final String PASSWD = "m1IgIOUY4ekY";

    public Round selectQuestions(int round, int index) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select pergunta, resposta1, resposta2, resposta3, resposta4 from " + DB +
                ".respostas, " + DB + ".perguntas, " + DB + ".round where perguntas.pergunta_id = respostas.pergunta_id" +
                " && perguntas.round_id = round.round_id && round.round_id = " + round + " && perguntas.indice = " + index);
        resultSet.next();

        Round roundNew = new Round();
        roundNew.setQuestion(resultSet.getString("pergunta"));
        roundNew.setAnswer1(resultSet.getString("resposta1"));
        roundNew.setAnswer2(resultSet.getString("resposta2"));
        roundNew.setAnswer3(resultSet.getString("resposta3"));
        roundNew.setAnswer4(resultSet.getString("resposta4"));

        return roundNew;
    }

    public String selectPlayers(int player) throws SQLException {

        statement = connection.createStatement();
        resultSet = statement.executeQuery("select jogador_id, nome, score from " + DB + ".jogador where jogador_id = " + player);
        resultSet.next();

        String playerNew = resultSet.getInt("jogador_id") + "   -  Nome: " +  resultSet.getString("nome") + "  Pontos: " +  resultSet.getInt("score");
        return playerNew;
    }

    public int selectTotalPlayer() throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select count(jogador_id) as total from " + DB + ".jogador");
        resultSet.next();

        int max = resultSet.getInt("total");
        return max;
    }

    public int selectTotalRound() throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select max(round_id) as total from " + DB + ".round");
        resultSet.next();

        int max = resultSet.getInt("total");
        return max;
    }

    public void insertPlayers(String name, int score) throws SQLException {
        statement = connection.createStatement();
        statement.executeUpdate("insert into " + DB + ".jogador (nome, score) values(\"" + name + "\"," + score + ")");
    }

    public boolean open(){
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWD);
            //System.out.println("Conectado com sucesso");
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
            if (connection != null){
                connection.close();
            }
            if (statement != null){
                statement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
            //System.out.println("Desconectado com sucesso");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
