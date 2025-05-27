import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Jogador {
    private int x, y;
    private final int velocidade = 5;

    private enum Direcao { FRENTE, COSTAS, ESQUERDA, DIREITA }
    private Direcao direcao = Direcao.FRENTE;

    private Image[][] sprites; // [direcao][frame]
    private int frameAtual = 1;
    private int contadorAnim = 0;
    private int size_mult = 3;

    public Jogador(int x, int y) throws IOException {
        this.x = x;
        this.y = y;
        sprites = new Image[4][3];

        // Carrega sprites por direção
        // Frente
        sprites[0][0] = ImageIO.read(new File("../sprites/players/players_04.png"));
        sprites[0][1] = ImageIO.read(new File("../sprites/players/players_05.png"));
        sprites[0][2] = ImageIO.read(new File("../sprites/players/players_06.png"));

        // Costas
        sprites[1][0] = ImageIO.read(new File("../sprites/players/players_13.png"));
        sprites[1][1] = ImageIO.read(new File("../sprites/players/players_14.png"));
        sprites[1][2] = ImageIO.read(new File("../sprites/players/players_15.png"));

        // Esquerda
        sprites[2][0] = ImageIO.read(new File("../sprites/players/players_07.png"));
        sprites[2][1] = ImageIO.read(new File("../sprites/players/players_08.png"));
        sprites[2][2] = ImageIO.read(new File("../sprites/players/players_09.png"));

        // Direita
        sprites[3][0] = ImageIO.read(new File("../sprites/players/players_10.png"));
        sprites[3][1] = ImageIO.read(new File("../sprites/players/players_11.png"));
        sprites[3][2] = ImageIO.read(new File("../sprites/players/players_12.png"));
    }

    public void mover(int dx, int dy, int larguraTela, int alturaTela) {
        if (dx == 0 && dy == 0) {
            frameAtual = 1; // quadro parado
            return;
        }

        // Define direção
        if (Math.abs(dx) > Math.abs(dy)) {
            direcao = dx > 0 ? Direcao.DIREITA : Direcao.ESQUERDA;
        } else {
            direcao = dy > 0 ? Direcao.FRENTE : Direcao.COSTAS;
        }

        // Movimento e limites da tela
        x += dx * velocidade;
        y += dy * velocidade;
        x = Math.max(0, Math.min(x, larguraTela - getLargura()));
        y = Math.max(0, Math.min(y, alturaTela - getAltura()));

        // Alterna frame de animação
        contadorAnim++;
        if (contadorAnim % 5 == 0) {
            frameAtual = (frameAtual + 1) % 3;
        }
    }

    public void desenhar(Graphics g, ImageObserver obs) {
        g.drawImage(sprites[direcao.ordinal()][frameAtual], x, y, getLargura(), getAltura(), obs);
    }

    public int getLargura() {
        return sprites[0][0].getWidth(null)*size_mult;
    }

    public int getAltura() {
        return sprites[0][0].getHeight(null)*size_mult;
    }
}
