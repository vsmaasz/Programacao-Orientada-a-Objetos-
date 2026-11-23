package jogo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Janela do jogo (só desenha e lê teclado). As regras ficam em Tabuleiro, Personagem etc.
 */
public class Jogo extends JFrame {

    static final int TAM = 56;            // tamanho de cada célula em pixels
    static final int TOTAL_SPRITES = 5;   // quantidade de frames da animação do herói
    static final Color COR_CHAO = new Color(236, 232, 214);

    private final String caminhoMapa;
    private final Tabuleiro tabuleiro = new Tabuleiro();
    private Personagem heroi;

    private JLabel[][] celulas;
    private final JLabel lblAtributos = new JLabel();
    private final JLabel lblMensagem = new JLabel(" ");

    private final ImageIcon[] spritesDir = new ImageIcon[TOTAL_SPRITES];
    private final ImageIcon[] spritesEsq = new ImageIcon[TOTAL_SPRITES];
    private int indiceSprite = 0;
    private boolean olhandoEsquerda = false;

    private final ImageIcon iconeParede = Icones.parede(TAM);
    private final ImageIcon iconeCaixote = Icones.caixote(TAM);
    private final ImageIcon iconeInimigo = Icones.inimigo(TAM);

    public static ImageIcon carregarSprite(String caminho) {
        ImageIcon iconeOriginal = new ImageIcon(Jogo.class.getResource(caminho));
        Image imgAjustada = iconeOriginal.getImage().getScaledInstance(TAM, TAM, Image.SCALE_SMOOTH);
        return new ImageIcon(imgAjustada);
    }

    public static ImageIcon espelharHorizontal(ImageIcon icone) {
        int largura = icone.getIconWidth();
        int altura = icone.getIconHeight();

        BufferedImage imagemEspelhada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagemEspelhada.createGraphics();
        g2d.drawImage(icone.getImage(), largura, 0, 0, altura, 0, 0, largura, altura, null);
        g2d.dispose();

        return new ImageIcon(imagemEspelhada);
    }

    public Jogo(String caminhoMapa) throws MapaInvalidoException {
        super("Mapa Grid");
        this.caminhoMapa = caminhoMapa;

        for (int i = 0; i < TOTAL_SPRITES; i++) {
            spritesDir[i] = carregarSprite("/mapa/Sprite" + (i + 1) + ".png");
            spritesEsq[i] = espelharHorizontal(spritesDir[i]);
        }

        tabuleiro.carregarDeArquivo(caminhoMapa);
        heroi = new Personagem(tabuleiro.getLinhaInicial(), tabuleiro.getColunaInicial());

        montarTela();
        desenhar();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                tratarTecla(e.getKeyCode());
            }
        });
        setFocusable(true);
    }

    private void montarTela() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topo = new JPanel(new GridLayout(2, 1));
        lblAtributos.setHorizontalAlignment(SwingConstants.CENTER);
        lblMensagem.setHorizontalAlignment(SwingConstants.CENTER);
        topo.add(lblAtributos);
        topo.add(lblMensagem);
        add(topo, BorderLayout.NORTH);

        int n = tabuleiro.getTamanho();
        JPanel painelGrid = new JPanel(new GridLayout(n, n));
        celulas = new JLabel[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                JLabel c = new JLabel();
                c.setHorizontalAlignment(SwingConstants.CENTER);
                c.setPreferredSize(new java.awt.Dimension(TAM, TAM));
                c.setOpaque(true);
                c.setBackground(COR_CHAO);
                c.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                celulas[i][j] = c;
                painelGrid.add(c);
            }
        }
        add(painelGrid, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // ---------- entrada ----------

    private void tratarTecla(int tecla) {
        int dLinha = 0;
        int dColuna = 0;

        if (tecla == KeyEvent.VK_W) dLinha = -1;
        else if (tecla == KeyEvent.VK_S) dLinha = 1;
        else if (tecla == KeyEvent.VK_A) dColuna = -1;
        else if (tecla == KeyEvent.VK_D) dColuna = 1;
        else return; // outras teclas são ignoradas

        try {
            Entidade alvo = tabuleiro.moverPersonagem(heroi, dLinha, dColuna);

            if (dColuna < 0) olhandoEsquerda = true;
            else if (dColuna > 0) olhandoEsquerda = false;
            indiceSprite = (indiceSprite + 1) % TOTAL_SPRITES;

            mensagem(" ");
            tratarEncontro(alvo);
        } catch (MovimentoInvalidoException ex) {
            mensagem(ex.getMessage());
        }
        desenhar();
    }

    // ---------- regras de encontro ----------

    private void tratarEncontro(Entidade alvo) {
        if (alvo instanceof Inimigo) {
            combate();
        }
    }

    private void combate() {
        heroi.perderVida();
        desenhar(); // mostra o herói em cima do inimigo antes do aviso

        if (heroi.isVivo()) {
            mensagem("COMBATE! Você encostou em um inimigo e perdeu 1 vida.");
            JOptionPane.showMessageDialog(this,
                    "COMBATE!\nVocê encostou em um inimigo e perdeu 1 vida.\nVidas restantes: "
                    + heroi.getVidas() + "\nA fase será reiniciada.",
                    "Combate", JOptionPane.WARNING_MESSAGE);
            reiniciarFase();
        } else {
            JOptionPane.showMessageDialog(this,
                    "COMBATE!\nVocê perdeu a última vida.\nFim de jogo!",
                    "Fim de jogo", JOptionPane.ERROR_MESSAGE);
            novoJogo();
        }
    }

    private void reiniciarFase() {
        try {
            tabuleiro.carregarDeArquivo(caminhoMapa);
            heroi.reiniciarFase(tabuleiro.getLinhaInicial(), tabuleiro.getColunaInicial());
        } catch (MapaInvalidoException ex) {
            erroFatal(ex);
        }
    }

    private void novoJogo() {
        try {
            tabuleiro.carregarDeArquivo(caminhoMapa);
            heroi = new Personagem(tabuleiro.getLinhaInicial(), tabuleiro.getColunaInicial());
            mensagem("Novo jogo iniciado.");
        } catch (MapaInvalidoException ex) {
            erroFatal(ex);
        }
    }

    private void erroFatal(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    // ---------- desenho ----------

    private void mensagem(String texto) {
        lblMensagem.setText(texto);
    }

    private void desenhar() {
        int n = tabuleiro.getTamanho();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                celulas[i][j].setIcon(iconePara(tabuleiro.getEntidade(i, j)));
            }
        }
        ImageIcon sprite = olhandoEsquerda ? spritesEsq[indiceSprite] : spritesDir[indiceSprite];
        celulas[heroi.getLinha()][heroi.getColuna()].setIcon(sprite);

        lblAtributos.setText("Vidas: " + heroi.getVidas());
    }

    private ImageIcon iconePara(Entidade e) {
        if (e == null) return null;
        switch (e.getSimbolo()) {
            case '#': return iconeParede;
            case 'X': return iconeCaixote;
            case 'E': return iconeInimigo;
            default: return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Jogo("mapas/facil.txt").setVisible(true);
            } catch (MapaInvalidoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
