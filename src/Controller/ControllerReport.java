package Controller;

import Model.ConnectData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by k on 5/11/15.
 */
public class ControllerReport implements Initializable {

    @FXML
    private ListView<String> listPlayer;

    private ConnectData connectData = new ConnectData();

    @Override
    public void initialize(URL url, final ResourceBundle resourceBundle) {

        try {
            connectData.open();
            ObservableList<String> observableList = FXCollections.observableArrayList(connectData.selectPlayers());
            listPlayer.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connectData.close();
        }
    }
}
