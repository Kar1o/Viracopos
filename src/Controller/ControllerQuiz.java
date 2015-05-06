package Controller;

import Model.ConnectData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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

    @FXML
    private ImageView pictureRound;

    private final ToggleGroup GROUP = new ToggleGroup();

    private List<String> answers = new ArrayList<String>();

    private String[][][] questions = {
            {{"Round1"},
                    {"Pergunta1", "Resposta1-1", "Resposta1-2", "Resposta1-3", "Resposta1-4"},
                    {"Pergunta2", "Resposta2-1", "Resposta2-2", "Resposta2-3", "Resposta2-4"},
                    {"Pergunta3", "Resposta3-1", "Resposta3-2", "Resposta3-3", "Resposta3-4"},
                    {"Pergunta4", "Resposta4-1", "Resposta4-2", "Resposta4-3", "Resposta4-4"}
            },
            {{"Round2"},
                    {"Pergunta1 round2", "Resposta1-1", "Resposta1-2", "Resposta1-3", "Resposta1-4"},
                    {"Pergunta2 round2", "Resposta2-1", "Resposta2-2", "Resposta2-3", "Resposta2-4"},
                    {"Pergunta3 round2", "Resposta3-1", "Resposta3-2", "Resposta3-3", "Resposta3-4"},
                    {"Pergunta4 round2", "Resposta4-1", "Resposta4-2", "Resposta4-3", "Resposta4-4"}
            }
    };

    private int scoreValue1 = 0, scoreValue2 = 0, scoreValue3 = 0, scoreValue4 = 0;

    private int currentQuestion = 1, currentRound = 0, currentPlayer = 1;

    private final int totalRound = questions.length, totalPlayer = Integer.parseInt(ControllerJogador.parameters);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConnectData connectData = new ConnectData();
        connectData.open();

        //elimina labels nao existentes e ajusta posicao dos existentes
        if (totalPlayer == 2){
            name3.setVisible(false);
            name4.setVisible(false);
            score3.setVisible(false);
            score4.setVisible(false);
            name1.setLayoutX(220);
            name2.setLayoutX(520);
            score1.setLayoutX(220);
            score2.setLayoutX(520);
        }
        else if (totalPlayer == 3){
            name4.setVisible(false);
            score4.setVisible(false);
            name1.setLayoutX(200);
            name2.setLayoutX(400);
            name3.setLayoutX(600);
            score1.setLayoutX(200);
            score2.setLayoutX(400);
            score3.setLayoutX(600);
        }

        //seta o nome dos jogadores
        name1.setText(ControllerNome.parameters.get(0));
        name2.setText(ControllerNome.parameters.get(1));
        name3.setText(ControllerNome.parameters.get(2));
        name4.setText(ControllerNome.parameters.get(3));

        //seta os 4 RadioButton para o mesmo grupo
        option1.setToggleGroup(GROUP);
        option2.setToggleGroup(GROUP);
        option3.setToggleGroup(GROUP);
        option4.setToggleGroup(GROUP);



        changeScore();
        changeQuestion();


        //evento para botao confirm
        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buttonAction(actionEvent);
            }
        });

    }

    /**
     * acao para botao de resposta para as perguntas
     * @param actionEvent utilizado para identificar janela atual
     */
    private void buttonAction(ActionEvent actionEvent){
        //confere se uma resposta foi escolhida
        if (option1.isSelected() || option2.isSelected() || option3.isSelected() || option4.isSelected()){

            checkAnswer();

            //muda para a proxima pergunta
            if (currentPlayer < totalPlayer) {
                currentPlayer += 1;
                currentQuestion += 1;
                changeScore();
                changeQuestion();

                //desmarca a opcao selecionada
                GROUP.getSelectedToggle().setSelected(false);
            }
            else if(currentPlayer == totalPlayer && currentRound + 1 < totalRound){
                currentRound += 1;
                currentPlayer = 1;
                currentQuestion = 1;
                changeScore();
                changeQuestion();

                //desmarca a opcao selecionada
                GROUP.getSelectedToggle().setSelected(false);
            }
            else {
                ControllerNome.warningMessage("Fim da partida");

                try {
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/report.fxml"));
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
            ControllerNome.warningMessage("Uma opção deve estar selecionada");
        }
    }

    /**
     * verifica se resposta selecionada eh a correta
     */
    private void checkAnswer(){
        //recebe o nome do RadioButton selecionado
        String selectedRadio = GROUP.getSelectedToggle().toString().substring(15, 22);

        if (selectedRadio.equals("option1")) {
            if (option1.getText().equals(questions[currentRound][currentQuestion][1])){
                assignScoreValue();
            }

        }else if (selectedRadio.equals("option2")){
            if (option2.getText().equals(questions[currentRound][currentQuestion][1])){
                assignScoreValue();
            }

        }else if (selectedRadio.equals("option3")){
            if (option3.getText().equals(questions[currentRound][currentQuestion][1])){
                assignScoreValue();
            }

        }else if (selectedRadio.equals("option4")){
            if (option4.getText().equals(questions[currentRound][currentQuestion][1])){
                assignScoreValue();
            }

        }
    }

    /**
     * verifica o jogador atual e adiciona ponto
     */
    private void assignScoreValue(){
        if (currentPlayer == 1){
            scoreValue1 += 1;
        }
        else if (currentPlayer == 2){
            scoreValue2 += 1;
        }
        else if (currentPlayer == 3){
            scoreValue3 += 1;
        }
        else if (currentPlayer == 4){
            scoreValue4 += 1;
        }
    }

    /**
     * joga pergunta e cada resposta em um RadioButton
     */
    private void changeQuestion(){
        //seta texto da pergunta
        question.setText(questions[currentRound][currentQuestion][0]);
        answers.clear();
        for (int i = 1; i <= 4 ; i++) {
            answers.add(questions[currentRound][currentQuestion][i]);
        }
        //embaralha a ordem das respostas
        Collections.shuffle(answers);
        //seta texto das respostas
        option1.setText(answers.get(0));
        option2.setText(answers.get(1));
        option3.setText(answers.get(2));
        option4.setText(answers.get(3));
    }

    /**
     * atualiza o placar de todos os jogadores
     */
    private void changeScore(){
        score1.setText(String.valueOf(scoreValue1));
        score2.setText(String.valueOf(scoreValue2));
        score3.setText(String.valueOf(scoreValue3));
        score4.setText(String.valueOf(scoreValue4));
    }
}