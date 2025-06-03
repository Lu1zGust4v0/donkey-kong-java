import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Mario extends JPanel implements KeyListener{
    // Atributos
    public static final int DIREITA = 0;
    public static final int ESQUERDA = 1;
    public static final int ESCADA = 2;
    public static final int MORRENDO = 4;
    
    int x, y, direcao, frame;
    BufferedImage[][] sprites;
    BufferedImage[] morte;
    static final int VELOCIDADE = 7;

    boolean[] teclas = new boolean[256];

    Mario (int x, int y) throws IOException {
        setFocusable(true);
        setBackground(null);
        this.x = x;
        setSize(30, 30);
        this.y = y;
        frame = 0;
        try {
            //inicial = javax.imageio.ImageIO.read(new File("sprites/m1.png"));
            sprites = new BufferedImage[3][2];
            sprites[0][0] = javax.imageio.ImageIO.read(new File("sprites/m1.png"));
            sprites[0][1] = javax.imageio.ImageIO.read(new File("sprites/m2.png"));
            sprites[1][0] = javax.imageio.ImageIO.read(new File("sprites/m4.png"));
            sprites[1][1] = javax.imageio.ImageIO.read(new File("sprites/m5.png"));
            sprites[2][0] = javax.imageio.ImageIO.read(new File("sprites/m6.png"));
            sprites[2][1] = javax.imageio.ImageIO.read(new File("sprites/m7.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        addKeyListener(this);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(sprites[direcao][frame], x, y, 60, 60, null);
    }

    public void movimentos(){
        if (teclas[KeyEvent.VK_LEFT]) {
            x -= VELOCIDADE;
            direcao = ESQUERDA;
            frame++;
            if (frame > 1) frame = 0;
        }
        if (teclas[KeyEvent.VK_RIGHT]) {
            x += VELOCIDADE;
            direcao = DIREITA;
            frame++;
            if (frame > 1) frame = 0;
        }
        
        repaint();
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        teclas[e.getKeyCode()] = true;
        System.out.println(x);
        movimentos();   
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclas[e.getKeyCode()] = false;
        movimentos();
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) throws IOException{
        JFrame janela = new JFrame("Teste");
        janela.setSize(500,500);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mario bigodudo = new Mario(3, 20);
        janela.add(bigodudo);
        janela.setVisible(true);
    }
}

