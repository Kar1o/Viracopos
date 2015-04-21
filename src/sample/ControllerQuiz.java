package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ControllerQuiz implements Initializable{

    @FXML
    private TextArea question;

    @FXML
    private Button confirm;

    @FXML
    private RadioButton option1, option2, option3, option4;

    @FXML
    private Label name1, name2, name3, name4;

    @FXML
    private Label score1, score2, score3, score4;

    final ToggleGroup group = new ToggleGroup();

    private String[] questions = {"111111111", "2222222222", "3333333333", "4444444444"};

    int randomQuestion;

    Random random = new Random();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        if (ControllerJogador.parameters.equals("2")){
            name3.setVisible(false);
            name4.setVisible(false);
            score3.setVisible(false);
            score4.setVisible(false);
        }
        else if (ControllerJogador.parameters.equals("3")){
            name4.setVisible(false);
            score4.setVisible(false);
        }

        //seta o nome dos jogadores
        name1.setText(ControllerNome.parameters.get(0));
        name2.setText(ControllerNome.parameters.get(1));
        name3.setText(ControllerNome.parameters.get(2));
        name4.setText(ControllerNome.parameters.get(3));

        //seta os 4 RadioButton para o mesmo grupo
        option1.setToggleGroup(group);
        option2.setToggleGroup(group);
        option3.setToggleGroup(group);
        option4.setToggleGroup(group);

        //escolhe pergunta aleatoria e joga no TextArea
        //random = new Random();
        randomQuestion = random.nextInt(questions.length);
        question.setText(questions[randomQuestion]);

        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //confere se uma resposta foi escolhida
                if (option1.isSelected() || option2.isSelected() || option3.isSelected() || option4.isSelected()){
                    //escolhe nova pergunta aleatoria
                    randomQuestion = random.nextInt(4);
                    question.setText(questions[randomQuestion]);
                    //desmarca a opcao selecionada
                    group.getSelectedToggle().setSelected(false);
                } else {
                    //abrir janela avisando que nenhuma resposta foi escolhida
                    final Stage dialog = new Stage();
                    dialog.initStyle(StageStyle.UTILITY);
                    dialog.setTitle("Aviso");
                    Text text = new Text(30, 30, "Uma opcao deve estar selecionada");
                    Button button = new Button("OK");
                    button.setLayoutY(65);
                    button.setLayoutX(130);
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
        });

    }
}