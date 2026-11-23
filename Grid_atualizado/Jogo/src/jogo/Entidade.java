package jogo;

/** Qualquer coisa que ocupa uma célula do tabuleiro. */
public abstract class Entidade {

    protected int linha;
    protected int coluna;

    public Entidade(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() { return linha; }
    public int getColuna() { return coluna; }

    public void setPosicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    /** Caractere usado no arquivo de mapa e para escolher o ícone na tela. */
    public abstract char getSimbolo();
}
