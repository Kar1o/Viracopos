package Model;

/**
 * Created by k on 5/2/15.
 */
public class Player {
    private String nome;
    private int pontos = 0;

    public Player(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos() {
        this.pontos += 1;
    }
}
