package jogo;

public class BlocoIndestrutivel extends Bloco {

    public BlocoIndestrutivel(int linha, int coluna) {
        super(linha, coluna);
    }

    @Override
    public char getSimbolo() { return '#'; }
}
