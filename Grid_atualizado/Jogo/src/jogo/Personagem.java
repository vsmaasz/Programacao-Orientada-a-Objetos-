package jogo;

public class Personagem extends Entidade implements IDestrutivel {

    private int vidas = 3;
    private int maxBombas = 1;
    private int raio = 1;

    public Personagem(int linha, int coluna) {
        super(linha, coluna);
    }

    public int getVidas() { return vidas; }
    public int getMaxBombas() { return maxBombas; }
    public int getRaio() { return raio; }
    public boolean isVivo() { return vidas > 0; }

    public void perderVida() { vidas--; }
    public void aumentarRaio() { raio++; }
    public void aumentarMaxBombas() { maxBombas++; }

    /** Volta ao início da fase: mantém as vidas, zera os power-ups coletados. */
    public void reiniciarFase(int linha, int coluna) {
        setPosicao(linha, coluna);
        maxBombas = 1;
        raio = 1;
    }

    @Override
    public void sofrerExplosao() { perderVida(); }

    @Override
    public char getSimbolo() { return 'H'; }
}
