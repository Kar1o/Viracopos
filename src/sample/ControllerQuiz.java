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
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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

    private int scoreValue1, scoreValue2, scoreValue3, scoreValue4;

    final ToggleGroup group = new ToggleGroup();

    private String[][] questions = {{"Pergunta1", "Resposta1-1", "Resposta1-2", "Resposta1-3", "Resposta1-4"},
            {"Pergunta2", "Resposta2-1", "Resposta2-2", "Resposta2-3", "Resposta2-4"},
            {"Pergunta3", "Resposta3-1", "Resposta3-2", "Resposta3-3", "Resposta3-4"},
            {"Pergunta4", "Resposta4-1", "Resposta4-2", "Resposta4-3", "Resposta4-4"}};

    private List<String> answers = new ArrayList<String>();

    int currentQuestion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //elimina jogadores e placares nao existentes e ajusta posicao dos existentes
        if (ControllerJogador.parameters.equals("2")){
            name3.setVisible(false);
            name4.setVisible(false);
            score3.setVisible(false);
            score4.setVisible(false);
            name1.setLayoutX(220);
            name2.setLayoutX(520);
            score1.setLayoutX(220);
            score2.setLayoutX(520);
        }
        else if (ControllerJogador.parameters.equals("3")){
            name4.setVisible(false);
            score4.setVisible(false);
            name1.setLayoutX(200);
            name2.setLayoutX(400);
            name3.setLayoutX(600);
            score1.setLayoutX(200);
            score2.setLayoutX(400);
            score3.setLayoutX(600);
        }

        //starta pontos para cada jogador
        scoreValue1 = 0;
        scoreValue2 = 0;
        scoreValue3 = 0;
        scoreValue4 = 0;

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

        changeScore();

        currentQuestion = 0;
        changeQuestion();


        //evento para botao confirm
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //confere se uma resposta foi escolhida
                if (option1.isSelected() || option2.isSelected() || option3.isSelected() || option4.isSelected()){

                    //atribui ponto para jogador se resposta for certa
                    /*if (group.getSelectedToggle().getProperties().toString().equals(questions[currentQuestion][1])){
                        System.out.println("Resposta certa");
                    }*/
                    //escolhe nova pergunta aleatoria
                    if (currentQuestion < questions.length-1 ) {
                        currentQuestion += 1;
                        changeQuestion();

                        //changeScore();

                        //desmarca a opcao selecionada
                        group.getSelectedToggle().setSelected(false);
                    }
                    else {
                        Parent root;
                        try {
                            root = FXMLLoader.load(getClass().getClassLoader().getResource("sample/report.fxml"));
                            Stage report = new Stage();
                            report.setTitle("Resultados");
                            report.setScene(new Scene(root, 800, 600));
                            report.setResizable(false);
                            report.show();

                            //esconder janela atual
                            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    //abrir janela avisando que nenhuma resposta foi escolhida
                    final Stage dialog = new Stage();
                    //faz a janela nova bloquear a anterior
                    dialog.initModality(Modality.WINDOW_MODAL);
                    dialog.initOwner(((Node) (actionEvent.getSource())).getScene().getWindow());

                    dialog.initStyle(StageStyle.UTILITY);
                    dialog.setTitle("Aviso");
                    Text text = new Text(30, 30, "Uma opcao deve estar selecionada");
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
        });

    }

    public void changeQuestion(){
        //joga pergunta e cada resposta em um RadioButton
        question.setText(questions[currentQuestion][0]);
        answers.clear();
        for (int i = 1; i <= 4 ; i++) {
            answers.add(questions[currentQuestion][i]);
        }

        Collections.shuffle(answers);
        option1.setText(answers.get(0));
        option2.setText(answers.get(1));
        option3.setText(answers.get(2));
        option4.setText(answers.get(3));
    }

    public void changeScore(){
        score1.setText(String.valueOf(scoreValue1));
        score2.setText(String.valueOf(scoreValue2));
        score3.setText(String.valueOf(scoreValue3));
        score4.setText(String.valueOf(scoreValue4));

    }
}