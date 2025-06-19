import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Mario extends JPanel implements KeyListener{
    // Atributos
    public static final int DIREITA = 0;
    public static final int ESQUERDA = 1;
    public static final int ESCADA = 2;
    public static final int MORRENDO = 4;
    
    ArrayList<Integer> pontosDesnivel = new ArrayList<Integer>();
    ArrayList<Integer> escadasBoas = new ArrayList<Integer>();
    ArrayList<Integer> proximoNivel = new ArrayList<Integer>();

    Ponto p;
    boolean pulando = false, escada = false;
    int direcao, frame, chao = 410, nivel = 0, alturaPulo = 28, gravidade = 7;
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

        proximoNivel.add(356);
        proximoNivel.add(296);
        proximoNivel.add(234);
        proximoNivel.add(174);
        proximoNivel.add(114);
        proximoNivel.add(60);
        

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

    public void addArray(ArrayList<Integer> desniveis){
        desniveis.clear();

        escadasBoas.clear();
        if (nivel == 0){
            desniveis.add(256);
            desniveis.add(312);
            desniveis.add(366);
            desniveis.add(422);
            desniveis.add(478);

            escadasBoas.add(446);
        }
        else if (nivel == 1 || nivel == 3){
            desniveis.add(444);
            desniveis.add(388);
            desniveis.add(332);
            desniveis.add(278);
            desniveis.add(222);
            desniveis.add(166);
            desniveis.add(112);
            desniveis.add(56);

            
            escadasBoas.add(88);
        }
        else if (nivel == 2 || nivel == 4){
            desniveis.add(90);
            desniveis.add(144);
            desniveis.add(200);
            desniveis.add(256);
            desniveis.add(310);
            desniveis.add(366);
            desniveis.add(424);
            desniveis.add(478);

            escadasBoas.add(446);
        }
        else if (nivel == 5){
            desniveis.add(276);
            desniveis.add(388);

            escadasBoas.add(308);
        }
    }
    public void movimentos(){
        if (teclas[KeyEvent.VK_LEFT] && !escada) {
            p.x1 -= VELOCIDADE;
            direcao = ESQUERDA;
            frame++;
            if (frame > 1) frame = 0;
        }
        if (teclas[KeyEvent.VK_RIGHT] && !escada) {
            p.x1 += VELOCIDADE;
            direcao = DIREITA;
            frame++;
            if (frame > 1) frame = 0;
        }

        //if (teclas[KeyEvent.VK_UP]){
        //    p.x2 -= VELOCIDADE;
        //}

        if (escadasBoas.contains(p.x1)){
            if (teclas[KeyEvent.VK_UP]){
                escada = true;
                direcao = ESCADA;
                p.x2 -= VELOCIDADE;
                frame++;
                if (frame > 1) frame = 0;
                if (proximoNivel.contains(p.x2)){
                    escada = false;
                    p.x1 += 2;
                    p.x2 -= 2;
                }
                System.out.println(nivel);
            }
        }
        
        if (p.x2 >= 357){
            nivel = 0;
            addArray(pontosDesnivel);
        }
        else if (p.x2 >= 297 && p.x2 < 357) {
            nivel = 1;
            addArray(pontosDesnivel);
        }
        else if (p.x2 >= 235 && p.x2 < 297) {
            nivel = 2;
            addArray(pontosDesnivel);
        }
        else if (p.x2 >= 175 && p.x2 < 235) {
            nivel = 3;
            addArray(pontosDesnivel);
        }
        else if (p.x2 >= 115 && p.x2 < 175) {
            nivel = 4;
            addArray(pontosDesnivel);
        }
        else if (p.x2 >= 61 && p.x2 < 115) {
            nivel = 5;
            addArray(pontosDesnivel);
        }
        else if (p.x2 < 61){
            //JOptionPane.showMessageDialog(null, "Voce venceu!", "Vitoria", JOptionPane.INFORMATION_MESSAGE);
        }
        

        if (pontosDesnivel.contains(p.x1) && !pulando){
            if (nivel % 2 == 0){
                if (direcao == DIREITA) p.x2 -= 2;
                else if (direcao == ESQUERDA) p.x2 += 2;
            }
            else{
                if (direcao == DIREITA) p.x2 += 2;
                else if (direcao == ESQUERDA) p.x2 -= 2;
            }
            chao = p.x2;
        }

        

        setBounds(p.x1, p.x2, getWidth(), getHeight());
        repaint();

    }

    public void pulo(){
        if (teclas[KeyEvent.VK_SPACE] && !escada){
            pulando = true;
            System.out.println("Apertou");
            SwingUtilities.invokeLater(() -> requestFocusInWindow());
            new Thread(()->{
                int i = 0;
                while (pulando) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    i += gravidade;
                    p.x2 -= gravidade;
                    if (i == 28) break;
                    movimentos();
                    setBounds(p.x1, p.x2, getWidth(), getHeight());
                    repaint();
                }

                while (p.x2 < chao) {
                    p.x2 += gravidade;
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    movimentos();
                    setBounds(p.x1, p.x2, getWidth(), getHeight());
                    repaint();
                }
            pulando = false;
            }).start();
        }
        
        
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
        teclas[e.getKeyCode()] = true;
        pulo();
        try {
            Thread.sleep(60);
        } catch (Exception error) {
        }
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

