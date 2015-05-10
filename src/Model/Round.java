package Model;

/**
 * Created by k on 5/4/15.
 */
public class Round {
    private int round;

    private String question1;
    private String question2;
    private String question3;
    private String question4;

    private String answers1[];
    private String answers2[];
    private String answers3[];
    private String answers4[];


    public Round(int round) {
        this.round = round;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String[] getAnswers1() {
        return answers1;
    }

    public void setAnswers1(String[] answers1) {
        this.answers1 = answers1;
    }

    public String[] getAnswers2() {
        return answers2;
    }

    public void setAnswers2(String[] answers2) {
        this.answers2 = answers2;
    }

    public String[] getAnswers3() {
        return answers3;
    }

    public void setAnswers3(String[] answers3) {
        this.answers3 = answers3;
    }

    public String[] getAnswers4() {
        return answers4;
    }

    public void setAnswers4(String[] answers4) {
        this.answers4 = answers4;
    }
}
