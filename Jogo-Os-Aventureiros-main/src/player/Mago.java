package player;

import caracteristicas.Curavel;

public class Mago extends Personagem implements Curavel{

    public Mago(String nome, int nivel) {
        super(nome, nivel);
    }

    @Override
    public String atacar() {
        return getNome() + "lanca bola de fogo";
    }

    @Override
    public int calcularDano() {
        return getNivel() * 15;
    }

    @Override
    public void receberCura(int pontos) {
        System.out.println(getNome() + " recebeu 20 pontos de cura");
    }
}
