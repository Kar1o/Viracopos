package Model;

/**
 * Created by k on 5/2/15.
 */
public class Player {
    private int id;
    private String nome;
    private int pontos;

    public Player(String nome) {
        this.nome = nome;
        this.pontos = 0;
    }

    public Player(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos += pontos;
    }
}
