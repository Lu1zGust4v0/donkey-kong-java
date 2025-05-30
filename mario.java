import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Mario extends JPanel implements KeyListener{
    // Atributos
    public static final int ESQUERDA = 1;
    public static final int DIREITA = 2;
    public static final int SUBINDO = 3;
    public static final int DESCENDO = 4;
    
    int x, y;
    BufferedImage inicial;
    static final int VELOCIDADE = 5;

    boolean[] teclas = new boolean[256];

    Mario (int x, int y) throws IOException {
        setFocusable(true);
        setBackground(Color.BLACK);
        this.x = x;
        setSize(50, 50);
        this.y = y;
        try {
            inicial = javax.imageio.ImageIO.read(new File("sprites/m1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addKeyListener(this);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(inicial, x, y, 60, 60, null);
    }

    public void movimentos(){
        if (teclas[KeyEvent.VK_LEFT]) x -= VELOCIDADE;
        if (teclas[KeyEvent.VK_RIGHT]) x += VELOCIDADE;
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

