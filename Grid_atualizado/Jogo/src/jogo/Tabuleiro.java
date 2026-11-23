package jogo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Matriz do labirinto. Guarda blocos e inimigos.
 * O herói não fica na matriz: ele guarda a própria posição.
 *
 * Símbolos do arquivo de mapa:
 *  '#' bloco indestrutível   'X' caixote   'E' inimigo comum
 *  'H' início do herói   ' ' vazio
 */
public class Tabuleiro {

    private Entidade[][] grade;
    private int tamanho;
    private int linhaInicial;
    private int colunaInicial;

    public void carregarDeArquivo(String caminho) throws MapaInvalidoException {
        ArrayList<String> linhas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String l;
            while ((l = br.readLine()) != null) {
                linhas.add(l);
            }
        } catch (IOException e) {
            throw new MapaInvalidoException("Não foi possível ler o arquivo: " + caminho);
        }

        if (linhas.isEmpty()) {
            throw new MapaInvalidoException("O arquivo de mapa está vazio: " + caminho);
        }

        tamanho = linhas.size();
        grade = new Entidade[tamanho][tamanho];
        boolean achouHeroi = false;

        for (int i = 0; i < tamanho; i++) {
            String linha = linhas.get(i);
            if (linha.length() != tamanho) {
                throw new MapaInvalidoException("O mapa precisa ser quadrado (erro na linha " + (i + 1) + ")");
            }
            for (int j = 0; j < tamanho; j++) {
                switch (linha.charAt(j)) {
                    case '#': grade[i][j] = new BlocoIndestrutivel(i, j); break;
                    case 'X': grade[i][j] = new BlocoDestrutivel(i, j); break;
                    case 'E': grade[i][j] = new InimigoComum(i, j); break;
                    case 'H':
                        linhaInicial = i;
                        colunaInicial = j;
                        achouHeroi = true;
                        break;
                    case ' ': break;
                    default:
                        throw new MapaInvalidoException("Símbolo desconhecido '" + linha.charAt(j)
                                + "' na linha " + (i + 1) + ", coluna " + (j + 1));
                }
            }
        }

        if (!achouHeroi) {
            throw new MapaInvalidoException("O mapa não tem a posição inicial do herói ('H')");
        }
    }

    public int getTamanho() { return tamanho; }
    public int getLinhaInicial() { return linhaInicial; }
    public int getColunaInicial() { return colunaInicial; }

    public Entidade getEntidade(int linha, int coluna) {
        return grade[linha][coluna];
    }

    public void remover(int linha, int coluna) {
        grade[linha][coluna] = null;
    }

    /**
     * Move o personagem uma célula. Valida os limites e os blocos.
     * Devolve o que havia na célula de destino (inimigo ou null)
     * para quem chamou decidir o que fazer.
     */
    public Entidade moverPersonagem(Personagem p, int dLinha, int dColuna) throws MovimentoInvalidoException {
        int novaLinha = p.getLinha() + dLinha;
        int novaColuna = p.getColuna() + dColuna;

        if (novaLinha < 0 || novaLinha >= tamanho || novaColuna < 0 || novaColuna >= tamanho) {
            throw new MovimentoInvalidoException("Movimento inválido: fora do mapa.");
        }

        Entidade alvo = grade[novaLinha][novaColuna];
        if (alvo instanceof Bloco) {
            throw new MovimentoInvalidoException("Movimento inválido: há um bloco no caminho.");
        }

        p.setPosicao(novaLinha, novaColuna);
        return alvo;
    }
}
