package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by k on 4/18/15.
 */
public class ControllerNome implements Initializable{

    @FXML
    private TextArea player1, player2, player3, player4;

    @FXML
    private Label lblPlayer3, lblPlayer4;

    @FXML
    private Button btnStart;

    public static List<String> parameters = new ArrayList<String>();


    @Override
    public void initialize(URL url, final ResourceBundle resourceBundle) {

        //elimina jogadores nao existentes e ajusta posicao dos existentes
        if (ControllerJogador.parameters.equals("2")) {
            lblPlayer3.setVisible(false);
            player3.setVisible(false);
            lblPlayer4.setVisible(false);
            player4.setVisible(false);
        } else if (ControllerJogador.parameters.equals("3")) {
            lblPlayer4.setVisible(false);
            player4.setVisible(false);
        }

        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String message = "Insira nome em todos os campos!";

                if ((player1.getText().equals("") || player2.getText().equals(""))
                        && ControllerJogador.parameters.equals("2")) {
                    warningMessage(actionEvent, message);
                } else if ((player1.getText().equals("") || player2.getText().equals("") || player3.getText().equals(""))
                        && ControllerJogador.parameters.equals("3")) {
                    warningMessage(actionEvent, message);
                } else if ((player1.getText().equals("") || player2.getText().equals("") || player3.getText().equals("")
                        || player4.getText().equals("")) && ControllerJogador.parameters.equals("4")) {
                    warningMessage(actionEvent, message);
                } else {


                    //popular array com valores de nomes
                    parameters.add(player1.getText());
                    parameters.add(player2.getText());
                    parameters.add(player3.getText());
                    parameters.add(player4.getText());

                    Parent root;
                    try {
                        root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/quiz.fxml"), resourceBundle);
                        Stage stage = new Stage();
                        stage.setTitle("Viracopos");
                        stage.setScene(new Scene(root, 800, 600));
                        stage.setResizable(false);
                        stage.show();

                        //esconder janela atual
                        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    public static void warningMessage(ActionEvent actionEvent, String message){
        //abrir janela avisando que nenhuma resposta foi escolhida
        final Stage dialog = new Stage();
        //faz a janela nova bloquear a anterior
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(((Node) (actionEvent.getSource())).getScene().getWindow());

        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Aviso");
        Text text = new Text(30, 30, message);
        Button button = new Button("OK");
        button.setLayoutY(65);
        button.setLayoutX(130);
        //seta botao para fechar a janela
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dialog.close();
            }
        });

        dialog.setScene(new Scene(new Group(text, button), 290, 110));
        dialog.show();
    }
}
