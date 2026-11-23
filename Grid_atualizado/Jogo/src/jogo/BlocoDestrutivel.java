package jogo;

public class BlocoDestrutivel extends Bloco implements IDestrutivel {

    private boolean destruido = false;

    public BlocoDestrutivel(int linha, int coluna) {
        super(linha, coluna);
    }

    public boolean isDestruido() { return destruido; }

    @Override
    public void sofrerExplosao() { destruido = true; }

    @Override
    public char getSimbolo() { return 'X'; }
}
