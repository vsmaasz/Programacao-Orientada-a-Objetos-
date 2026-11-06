package player;

public class Arqueiro extends Personagem{

    public Arqueiro(String nome, int nivel) {
        super(nome, nivel);
    }

    @Override
    public String atacar() {
        return getNome() + "Atira flechas";
    }

    @Override
    public int calcularDano() {
        return getNivel() * 12;    }
    
}
