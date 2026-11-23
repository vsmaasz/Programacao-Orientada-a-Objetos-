package jogo;

/** Lançada quando o arquivo de mapa não existe, não é quadrado ou tem símbolos desconhecidos. */
public class MapaInvalidoException extends Exception {

    public MapaInvalidoException(String mensagem) {
        super(mensagem);
    }
}
