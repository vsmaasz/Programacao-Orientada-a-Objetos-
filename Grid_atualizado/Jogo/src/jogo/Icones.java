package jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * Ícones desenhados por código, para os elementos que ainda não têm sprite.
 * Quando você tiver PNGs, troque o corpo de cada método por um carregarSprite(...).
 */
public class Icones {

    public static ImageIcon parede(int t) {
        BufferedImage img = novo(t);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(new Color(90, 90, 100));
        g.fillRect(0, 0, t, t);
        g.setColor(new Color(60, 60, 70));
        for (int y = 0; y < t; y += t / 4) {
            g.drawLine(0, y, t, y);
        }
        g.drawLine(t / 2, 0, t / 2, t / 4);
        g.drawLine(t / 4, t / 4, t / 4, t / 2);
        g.drawLine(3 * t / 4, t / 4, 3 * t / 4, t / 2);
        g.drawLine(t / 2, t / 2, t / 2, 3 * t / 4);
        g.dispose();
        return new ImageIcon(img);
    }

    public static ImageIcon caixote(int t) {
        BufferedImage img = novo(t);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(new Color(170, 110, 50));
        g.fillRect(3, 3, t - 6, t - 6);
        g.setColor(new Color(110, 70, 30));
        g.drawRect(3, 3, t - 7, t - 7);
        g.drawLine(3, 3, t - 4, t - 4);
        g.drawLine(t - 4, 3, 3, t - 4);
        g.dispose();
        return new ImageIcon(img);
    }

    public static ImageIcon inimigo(int t) {
        BufferedImage img = novo(t);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(200, 40, 40));
        g.fillOval(6, 6, t - 12, t - 12);
        g.setColor(Color.WHITE);
        g.fillOval(t / 3 - 4, t / 3, 10, 10);
        g.fillOval(2 * t / 3 - 6, t / 3, 10, 10);
        g.setColor(Color.BLACK);
        g.fillOval(t / 3, t / 3 + 3, 4, 4);
        g.fillOval(2 * t / 3 - 2, t / 3 + 3, 4, 4);
        g.dispose();
        return new ImageIcon(img);
    }

    public static ImageIcon powerUp(int t, char letra, Color cor) {
        BufferedImage img = novo(t);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(cor);
        g.fillOval(10, 10, t - 20, t - 20);
        g.setColor(Color.BLACK);
        g.drawOval(10, 10, t - 21, t - 21);
        g.setFont(new Font("SansSerif", Font.BOLD, t / 3));
        FontMetrics fm = g.getFontMetrics();
        String s = String.valueOf(letra);
        g.drawString(s, (t - fm.stringWidth(s)) / 2, (t + fm.getAscent()) / 2 - 3);
        g.dispose();
        return new ImageIcon(img);
    }

    private static BufferedImage novo(int t) {
        return new BufferedImage(t, t, BufferedImage.TYPE_INT_ARGB);
    }
}
