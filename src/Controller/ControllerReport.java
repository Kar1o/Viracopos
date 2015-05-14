package Controller;

import Model.ConnectData;
import Model.Player;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by k on 5/11/15.
 */
public class ControllerReport implements Initializable {


    @FXML
    Label title;

    @FXML
    ListView<String> listPlayer;

    ObservableList<String> data;

    ConnectData connectData = new ConnectData();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText("Lista de jogadores");

        //data.add(connectData.selectPlayers());
        //listPlayer.setItems();


    }
}
