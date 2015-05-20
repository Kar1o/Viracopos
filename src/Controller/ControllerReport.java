package Controller;

import Model.ConnectData;
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
    private Label title;

    @FXML
    private ListView<String> listPlayer;

    private ObservableList data = FXCollections.observableArrayList();

    private ConnectData connectData = new ConnectData();


    @Override
    public void initialize(URL url, final ResourceBundle resourceBundle) {
        title.setText("Lista de Jogadores");

        try {
            connectData.open();
            int totalPlayer = connectData.selectTotalPlayer();
            for (int i = 1; i <= totalPlayer; i++) {

                String player = connectData.selectPlayers(i);
                data.add(player);

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
