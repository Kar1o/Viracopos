package Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerJogador implements Initializable{

    @FXML
    private Button btnContinue, btnRank;

    @FXML
    private ChoiceBox choicePlayer;

    public static String parameters;


    @Override
    public void initialize(URL url, final ResourceBundle resourceBundle) {

        //setar os valores do ChoiceBox
        choicePlayer.setItems(FXCollections.observableArrayList(
                "2", "3", "4"
        ));

        //definir valor default
        choicePlayer.getSelectionModel().selectFirst();
        choicePlayer.setTooltip(new Tooltip(choicePlayer.getValue().toString() + "jogadores"));

        btnContinue.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buttonAction(actionEvent);
            }
        });

        btnRank.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buttonRank(actionEvent);
            }
        });

    }

    private void buttonAction(ActionEvent actionEvent){
        parameters = choicePlayer.getValue().toString();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/nome_jogador.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Viracopos");
            Scene scene = new Scene(root, 640, 480);
            scene.getStylesheets().add("View/style.css");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            //esconder janela atual
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buttonRank(ActionEvent actionEvent){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../View/report.fxml"));
            Stage report = new Stage();
            report.setTitle("Resultados");
            Scene scene = new Scene(root, 640, 480);
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