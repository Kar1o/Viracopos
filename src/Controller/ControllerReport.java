package Controller;

import Model.ConnectData;
import Model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by k on 5/11/15.
 */
public class ControllerReport implements Initializable {


    @FXML
    Label title;

    @FXML
    ListView<String> listPlayer;

    private ObservableList data = FXCollections.observableArrayList();

    ConnectData connectData = new ConnectData();

    Player player = new Player();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText("Lista de jogadores");

        try {
            connectData.open();
            int totalPlayer = connectData.selectTotalPlayer();
            for (int i = 1; i <= totalPlayer; i++) {

                player = connectData.selectPlayers(i);
                String info = player.getId() + "   -  Nome: " + player.getNome() + "  Pontos: " + player.getPontos();
                data.add(info);

            }
            listPlayer.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connectData.close();
        }


    }
}
