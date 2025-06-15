import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Mario extends JPanel implements KeyListener{
    // Atributos
    public static final int DIREITA = 0;
    public static final int ESQUERDA = 1;
    public static final int ESCADA = 2;
    public static final int MORRENDO = 4;
    
    ArrayList<Ponto> pontosDesnivel = new ArrayList<Ponto>();

    Ponto p;
    int direcao, frame, chao = 400;
    BufferedImage[][] sprites;
    BufferedImage[] morte;
    static final int VELOCIDADE = 2;

    boolean[] teclas = new boolean[256];

    Mario (int x, int y) throws IOException {
        setFocusable(true);
        setBackground(null);
        p = new Ponto(x, y);
        setSize(30, 30);
        addArray(pontosDesnivel);
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
        g.drawImage(sprites[direcao][frame], p.x1, p.x2, 60, 60, null);
    }

    public void addArray(ArrayList<Ponto> desniveis){
        desniveis.add(new Ponto(256, 410));
        desniveis.add(new Ponto(312, 408));
        desniveis.add(new Ponto(366, 406));
        desniveis.add(new Ponto(422, 404));
        desniveis.add(new Ponto(478, 402));
    }

    public void movimentos(){
        if (teclas[KeyEvent.VK_LEFT]) {
            p.x1 -= VELOCIDADE;
            direcao = ESQUERDA;
            frame++;
            if (frame > 1) frame = 0;
        }
        if (teclas[KeyEvent.VK_RIGHT]) {
            p.x1 += VELOCIDADE;
            direcao = DIREITA;
            frame++;
            if (frame > 1) frame = 0;
        }

        if (teclas[KeyEvent.VK_DOWN]){
            p.x2 += VELOCIDADE;
        }

        if (teclas[KeyEvent.VK_UP]){
            p.x2 -= VELOCIDADE;
        }

        if (teclas[KeyEvent.VK_SPACE]){

        }

        System.out.println(pontosDesnivel.contains(p));

        setBounds(p.x1, p.x2, getWidth(), getHeight());
        repaint();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        teclas[e.getKeyCode()] = true;
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
        janela.setSize(300,300);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mario bigodudo = new Mario(3, 20);
        janela.add(bigodudo);
        janela.setVisible(true);
    }

    class Ponto{
        int x1, x2;
        Ponto(int x, int y){
            x1 = x;
            x2 = y;
        }
    }
}

