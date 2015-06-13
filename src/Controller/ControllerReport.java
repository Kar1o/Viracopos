package Controller;

import Model.ConnectData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by k on 5/11/15.
 */
public class ControllerReport implements Initializable {

    @FXML
    private ListView<String> listPlayer;

    @FXML
    private Button btnStart;

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

        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                btnStart(actionEvent);
            }
        });
    }

    private void btnStart(ActionEvent actionEvent){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/qtd_jogador.fxml"));
            Stage report = new Stage();
            report.setTitle("Viracopos");
            Scene scene = new Scene(root, 420, 380);
            scene.getStylesheets().add("View/style.css");
            report.setScene(scene);
            report.setResizable(false);
            report.show();

            //esconder janela atual
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
