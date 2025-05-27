import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class Mario{
    // Atributos
    public static final int ESQUERDA = 1;
    public static final int DIREITA = 2;
    public static final int SUBINDO = 3;
    public static final int DESCENDO = 4;
    
    boolean vivo;
    int fasesMorte = 0, x, y;
    Image[][] sprites;

    Mario (int x, int y) throws IOException {
        this.x = x;
        this.y = y;
        sprites = new Image[3][2];
        
        // Esquerda
        sprites[0][0] = ImageIO.read(new File("sprites/m1.png"));
        sprites[0][1] = ImageIO.read(new File("sprites/m2.png"));
        
        // Direita
        sprites[1][0] = ImageIO.read(new File("sprites/m4.png"));
        sprites[1][1] = ImageIO.read(new File("sprites/m5.png"));

        // Na escada
        sprites[2][0] = ImageIO.read(new File("sprites/m6.png"));
        sprites[2][1] = ImageIO.read(new File("sprites/m7.png"));
    }

    
}