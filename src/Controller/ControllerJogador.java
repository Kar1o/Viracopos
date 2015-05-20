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
    private Button btnContinue;

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

    }

    private void buttonAction(ActionEvent actionEvent){
        parameters = choicePlayer.getValue().toString();

        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/nome_jogador.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Viracopos");
            Scene scene = new Scene(root, 600, 450);
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
}