package player;

import caracteristicas.Curavel;

public class Guerreiro extends Personagem implements Curavel{

    public Guerreiro(String nome, int nivel) {
        super(nome, nivel);
    }

    @Override
    public String atacar() {
        return getNome() + "Ataca com espada";
    }

    @Override
    public int calcularDano() {
        return getNivel() * 10;
    }

    @Override
    public void receberCura(int pontos) {
        System.out.println(getNome() + " recebeu 20 pontos de cura");
    }
    
}
