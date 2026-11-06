package player;

public class Curandeiro extends Personagem{

    public Curandeiro(String nome, int nivel) {
        super(nome, nivel);
    }

    @Override
    public String atacar() {
        return getNome() + "Ataca com uma onda de energia";
    }

    @Override
    public int calcularDano() {
        return getNivel() * 5;
    }
    
}
