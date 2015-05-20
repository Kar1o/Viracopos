package Controller;

import Model.ConnectData;
import Model.Player;
import Model.Round;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    private final ToggleGroup group = new ToggleGroup();

    private List<String> answers = new ArrayList<String>();

    private Player jogador1 = new Player(ControllerNome.parameters.get(0));
    private Player jogador2 = new Player(ControllerNome.parameters.get(1));
    private Player jogador3 = new Player(ControllerNome.parameters.get(2));
    private Player jogador4 = new Player(ControllerNome.parameters.get(3));

    private ConnectData connectData = new ConnectData();

    private Round round = new Round();

    private int currentQuestion = 1, currentRound = 1, currentPlayer = 1, totalRound = 0;

    private final int totalPlayer = Integer.parseInt(ControllerJogador.parameters);

    private final Image image1 = new Image("View/Images/round-1.jpg");
    private final Image image2 = new Image("View/Images/round-2.jpg");
    private final Image image3 = new Image("View/Images/round-3.jpg");
    private final Image image4 = new Image("View/Images/round-4.jpg");


    @Override
    public void initialize(URL url, final ResourceBundle resourceBundle) {

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
            name1.setText(jogador1.getNome());
            name2.setText(jogador2.getNome());
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
            name1.setText(jogador1.getNome());
            name2.setText(jogador2.getNome());
            name3.setText(jogador3.getNome());
        }
        else {
            name1.setText(jogador1.getNome());
            name2.setText(jogador2.getNome());
            name3.setText(jogador3.getNome());
            name4.setText(jogador4.getNome());
        }

        //seta os 4 RadioButton para o mesmo grupo
        option1.setToggleGroup(group);
        option2.setToggleGroup(group);
        option3.setToggleGroup(group);
        option4.setToggleGroup(group);

        changeScore();
        changeQuestion();
        changePlayer();
        changeImage();


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
                changePlayer();

                //desmarca a opcao selecionada
                group.getSelectedToggle().setSelected(false);
            }
            //muda para o proximo round
            else if(currentPlayer == totalPlayer && currentRound < totalRound){
                currentRound += 1;
                currentPlayer = 1;
                currentQuestion = 1;
                changeScore();
                changeQuestion();
                changePlayer();
                changeImage();

                //desmarca a opcao selecionada
                group.getSelectedToggle().setSelected(false);
            }
            else {
                changeScore();
                insertJogadorData();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fim!");
                alert.setHeaderText("Obrigado por jogar");
                alert.setContentText("Aperte OK para ver lista de jogadores");
                alert.showAndWait();

                try {
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/report.fxml"));
                    Stage report = new Stage();
                    report.setTitle("Resultados");
                    Scene scene = new Scene(root, 600, 400);
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
        } else {
            ControllerNome.warningMessage("Uma opção deve estar selecionada");
        }
    }

    /**
     * insere no banco o nome e nota dos jogadores
     */
    private void insertJogadorData(){
        if (totalPlayer == 2) {
            try {
                connectData.open();
                connectData.insertPlayers(jogador1.getNome(), jogador1.getPontos());
                connectData.insertPlayers(jogador2.getNome(), jogador2.getPontos());

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectData.close();
            }
        }

        else if (totalPlayer == 3) {
            try {
                connectData.open();
                connectData.insertPlayers(jogador1.getNome(), jogador1.getPontos());
                connectData.insertPlayers(jogador2.getNome(), jogador2.getPontos());
                connectData.insertPlayers(jogador3.getNome(), jogador3.getPontos());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectData.close();
            }
        }
        else{
            try {
                connectData.open();
                connectData.insertPlayers(jogador1.getNome(), jogador1.getPontos());
                connectData.insertPlayers(jogador2.getNome(), jogador2.getPontos());
                connectData.insertPlayers(jogador3.getNome(), jogador3.getPontos());
                connectData.insertPlayers(jogador4.getNome(), jogador4.getPontos());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectData.close();
            }


        }
    }

    /**
     * verifica se resposta selecionada eh a correta
     */
    private void checkAnswer(){
        //recebe o nome do RadioButton selecionado
        String selectedRadio = group.getSelectedToggle().toString().substring(15, 22);

        if (selectedRadio.equals("option1")) {
            if (option1.getText().equals(round.getAnswer1())){
                assignScoreValue();
            }

        }else if (selectedRadio.equals("option2")){
            if (option2.getText().equals(round.getAnswer1())){
                assignScoreValue();
            }

        }else if (selectedRadio.equals("option3")){
            if (option3.getText().equals(round.getAnswer1())){
                assignScoreValue();
            }

        }else if (selectedRadio.equals("option4")){
            if (option4.getText().equals(round.getAnswer1())){
                assignScoreValue();
            }

        }
    }

    /**
     * verifica o jogador atual e adiciona ponto
     */
    private void assignScoreValue(){
        if (currentPlayer == 1){
            jogador1.setPontos(1);
        }
        else if (currentPlayer == 2){
            jogador2.setPontos(1);
        }
        else if (currentPlayer == 3){
            jogador3.setPontos(1);
        }
        else if (currentPlayer == 4){
            jogador4.setPontos(1);
        }
    }

    /**
     * joga pergunta e cada resposta em um RadioButton
     */
    private void changeQuestion() {
        try {
            connectData.open();
            if (totalRound == 0){
                totalRound = connectData.selectTotalRound();
            }
            round = connectData.selectQuestions(currentRound, currentQuestion);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectData.close();
        }

        //seta texto da pergunta
        question.setText(round.getQuestion());

        //embaralha a ordem das respostas
        answers.clear();
        answers.add(round.getAnswer1());
        answers.add(round.getAnswer2());
        answers.add(round.getAnswer3());
        answers.add(round.getAnswer4());
        Collections.shuffle(answers);

        option1.setText(answers.get(0));
        option2.setText(answers.get(1));
        option3.setText(answers.get(2));
        option4.setText(answers.get(3));
    }

    /**
     * atualiza o placar de todos os jogadores
     */
    private void changeScore(){
        score1.setText(String.valueOf(jogador1.getPontos()));
        score2.setText(String.valueOf(jogador2.getPontos()));
        score3.setText(String.valueOf(jogador3.getPontos()));
        score4.setText(String.valueOf(jogador4.getPontos()));
    }

    /**
     * muda a imagem de acordo com o round
     */
    private void changeImage(){
        switch (currentRound){
            case 1: pictureRound.setImage(image1);
                break;
            case 2: pictureRound.setImage(image2);
                break;
            case 3: pictureRound.setImage(image3);
                break;
            case 4: pictureRound.setImage(image4);
                break;
        }
    }

    /**
     * altera a cor do label nome do jogador atual
     */
    private void changePlayer(){
        switch (currentPlayer){
            case 1: name1.setTextFill(Color.YELLOW);
                name2.setTextFill(Color.BLACK);
                name3.setTextFill(Color.BLACK);
                name4.setTextFill(Color.BLACK);
                break;
            case 2: name2.setTextFill(Color.YELLOW);
                name1.setTextFill(Color.BLACK);
                name3.setTextFill(Color.BLACK);
                name4.setTextFill(Color.BLACK);
                break;
            case 3: name3.setTextFill(Color.YELLOW);
                name1.setTextFill(Color.BLACK);
                name2.setTextFill(Color.BLACK);
                name4.setTextFill(Color.BLACK);
                break;
            case 4: name4.setTextFill(Color.YELLOW);
                name1.setTextFill(Color.BLACK);
                name2.setTextFill(Color.BLACK);
                name3.setTextFill(Color.BLACK);
                break;
        }
    }
}