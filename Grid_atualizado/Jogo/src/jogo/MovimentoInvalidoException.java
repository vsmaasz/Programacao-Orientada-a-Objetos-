package jogo;

/** Lançada ao tentar andar contra uma parede/caixote ou para fora do mapa. */
public class MovimentoInvalidoException extends Exception {

    public MovimentoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
