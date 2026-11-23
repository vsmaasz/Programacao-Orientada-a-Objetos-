package jogo;

/** Base de todos os inimigos. Rastreador e Sabotador entram depois, herdando desta classe. */
public abstract class Inimigo extends Entidade implements IDestrutivel {

    private boolean vivo = true;

    public Inimigo(int linha, int coluna) {
        super(linha, coluna);
    }

    public boolean isVivo() { return vivo; }

    @Override
    public void sofrerExplosao() { vivo = false; }
}
