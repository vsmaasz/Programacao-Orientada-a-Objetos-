package jogo;

public class InimigoComum extends Inimigo {

    public InimigoComum(int linha, int coluna) {
        super(linha, coluna);
    }

    @Override
    public char getSimbolo() { return 'E'; }
}
