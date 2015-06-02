package Controller;

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

    private int totalPlayer = Integer.parseInt(ControllerJogador.parameters);

    public static List<String> parameters = new ArrayList<String>();


    @Override
    public void initialize(URL url, final ResourceBundle resourceBundle) {

        //elimina jogadores nao existentes e ajusta posicao dos existentes
        if (totalPlayer <= 3) {
            lblPlayer4.setVisible(false);
            player4.setVisible(false);
            if (totalPlayer == 2){
                lblPlayer3.setVisible(false);
                player3.setVisible(false);
            }
        }

        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buttonAction(actionEvent);
            }
        });

    }

    /**
     * acao para botao de confirmar nomes
     * @param actionEvent utilizado para identificar janela atual
     */
    private void buttonAction(ActionEvent actionEvent){

        if ((player1.getText().equals("") || player2.getText().equals(""))
                && totalPlayer == 2) {
            warningMessage("Insira nome em todos os campos!");
        }
        else if ((player1.getText().equals("") || player2.getText().equals("") || player3.getText().equals(""))
                && totalPlayer == 3) {
            warningMessage("Insira nome em todos os campos!");
        }
        else if ((player1.getText().equals("") || player2.getText().equals("") || player3.getText().equals("")
                || player4.getText().equals("")) && totalPlayer == 4) {
            warningMessage("Insira nome em todos os campos!");
        }
        else {
            //popular array com valores de nomes
            parameters.add(player1.getText());
            parameters.add(player2.getText());
            parameters.add(player3.getText());
            parameters.add(player4.getText());

            try {
                Parent root = FXMLLoader.load(getClass().getResource("../View/quiz.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Viracopos");
                Scene scene = new Scene(root, 1024, 720);
                scene.getStylesheets().add("View/style.css");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();

                //esconder janela atual
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * abrir janela avisando que nenhuma resposta foi escolhida
     * @param message texto de aviso para a janela
     */
    public static void warningMessage(String message){

        final Stage dialog = new Stage();

        //faz a janela nova bloquear a anterior
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Aviso");

        //cria elementos da janela
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

        Scene scene = new Scene(new Group(text, button), 290, 110);
        scene.getStylesheets().add("View/style.css");

        dialog.setScene(scene);
        dialog.setResizable(false);
        dialog.show();
    }
}
