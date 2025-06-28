import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Mario extends JPanel implements KeyListener {
    // Constantes de direção
    public static final int DIREITA = 0;
    public static final int ESQUERDA = 1;
    public static final int ESCADA = 2;

    ArrayList<Integer> pontosDesnivel = new ArrayList<>();
    ArrayList<Integer> escadasBoas = new ArrayList<>();
    ArrayList<Integer> proximoNivel = new ArrayList<>();

    public Ponto p;
    public boolean pulando = false, escada = false;
    public int direcao, frame, chao = 390, nivel = 0; // chao ajustado para 390
    public int alturaPulo = 28, gravidade = 7;
    public BufferedImage[][] sprites;
    static final int VELOCIDADE = 2;
    int spritenum = 1;
    int spritecounter = 0;
    boolean[] teclas = new boolean[256];

    Mario(int x, int y) throws IOException {
        setFocusable(true);
        setBackground(null);
        p = new Ponto(x, y);
        setSize(30, 30);
        addArray(pontosDesnivel);

        // proximoNivel ajustado (-20)
        proximoNivel.add(336);
        proximoNivel.add(276);
        proximoNivel.add(214);
        proximoNivel.add(154);
        proximoNivel.add(94);
        proximoNivel.add(40);
        PlayerImage();
        frame = 0;
        addKeyListener(this);
        setOpaque(false); // permite que o fundo seja transparente
        setPreferredSize(new Dimension(64, 64)); // mesmo tamanho da imagem
        setSize(64,64);

    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        BufferedImage image = null;
        switch(direcao){
            case DIREITA:
            if(spritenum == 1)
                image = sprites[0][0];
            else
                image = sprites[0][1];
                break;
            case ESQUERDA:
            if(spritenum == 1)
                image = sprites[1][0];
            else
                image = sprites[1][1];
                break;
            case ESCADA:
            if(spritenum == 1)
                image = sprites[2][0];
            else
                image = sprites[2][1];
                break;
        }
        g.drawImage(image, 0, 0, getWidth(),getHeight(),null);
    }
    void PlayerImage() {
        try{
            sprites = new BufferedImage[3][2];
            sprites[0][0] = ImageIO.read(new File("sprites/m1.png"));
            sprites[0][1] = ImageIO.read(new File("sprites/m2.png"));
            sprites[1][0] = ImageIO.read(new File("sprites/m3.png"));
            sprites[1][1] = ImageIO.read(new File("sprites/m4.png"));
            sprites[2][0] = ImageIO.read(new File("sprites/m5.png"));
            sprites[2][1] = ImageIO.read(new File("sprites/m6.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addArray(ArrayList<Integer> desniveis) {
        desniveis.clear();
        escadasBoas.clear();

        if (nivel == 0) {
            desniveis.add(256);
            desniveis.add(312);
            desniveis.add(366);
            desniveis.add(422);
            desniveis.add(478);

            escadasBoas.add(446);
        } else if (nivel == 1 || nivel == 3) {
            desniveis.add(444);
            desniveis.add(388);
            desniveis.add(332);
            desniveis.add(278);
            desniveis.add(222);
            desniveis.add(166);
            desniveis.add(112);
            desniveis.add(56);

            escadasBoas.add(88);
            if(nivel == 1)
                escadasBoas.add(226);
            if(nivel ==3)
                escadasBoas.add(198);
        } else if (nivel == 2 || nivel == 4) {
            desniveis.add(90);
            desniveis.add(144);
            desniveis.add(200);
            desniveis.add(256);
            desniveis.add(310);
            desniveis.add(366);
            desniveis.add(424);
            desniveis.add(478);

            escadasBoas.add(446);
            if(nivel ==2)
                escadasBoas.add(254);
        } else if (nivel == 5) {
            desniveis.add(276);
            desniveis.add(388);

            escadasBoas.add(308);
        }
    }
    public boolean buraco(){
        if ((p.x1 >= 494) && (p.x2 == 334 || p.x2 == 92)) return true; // -20 nos valores de x2
        if ((p.x1 >= 36) && (p.x2 == 274 || p.x2 == 152)) return true;
        if ((p.x1 >= 330) && p.x2 == 38) return true;
        return false;
    }
    public void movimentos() {
        spritecounter++;
        if(spritecounter>10){
            if(spritenum==1)
                spritenum =2;
            else
                spritenum =1;
            spritecounter = 0;
        }
        if (teclas[KeyEvent.VK_LEFT] && !escada) {
            p.x1 -= VELOCIDADE;
            direcao = ESQUERDA;
            frame = (frame + 1) % 2;
        }
        if (teclas[KeyEvent.VK_RIGHT] && !escada) {
            p.x1 += VELOCIDADE;
            direcao = DIREITA;
            frame = (frame + 1) % 2;
        }
        // Subir escada
        if (escadasBoas.contains(p.x1)) {
            if (teclas[KeyEvent.VK_UP]) {
                escada = true;
                direcao = ESCADA;
                p.x2 -= VELOCIDADE;
                frame = (frame + 1) % 2;
                if (proximoNivel.contains(p.x2)) {
                    escada = false;
                    p.x1 += 2;
                    p.x2 -= 2;
                    chao = p.x2;
                }
            }
        }
        //descer escada
        if (escadasBoas.contains(p.x1)) {
            if (teclas[KeyEvent.VK_DOWN]) {
                escada = true;
                direcao = ESCADA;
                p.x2 += VELOCIDADE;
                frame = (frame + 1) % 2;
                if (proximoNivel.contains(p.x2)) {
                    escada = false;
                    p.x1 += 2;
                    p.x2 -= 2;
                    chao = p.x2;
                }
            }
        }
        // Atualiza nível baseado no eixo Y (ajustado -20)
        if (p.x2 >= 337) {
            nivel = 0;
            addArray(pontosDesnivel);
        } else if (p.x2 >= 277) {
            nivel = 1;
            addArray(pontosDesnivel);
        } else if (p.x2 >= 215) {
            nivel = 2;
            addArray(pontosDesnivel);
        } else if (p.x2 >= 155) {
            nivel = 3;
            addArray(pontosDesnivel);
        } else if (p.x2 >= 95) {
            nivel = 4;
            addArray(pontosDesnivel);
        } else if (p.x2 >= 41) {
            nivel = 5;
            addArray(pontosDesnivel);
        }

        // Desníveis (subidas/descidas)
        if (pontosDesnivel.contains(p.x1)) {
            if (nivel % 2 == 0) {
                if (direcao == DIREITA) chao -= 2;
                else if (direcao == ESQUERDA) chao += 2;
            } else {
                if (direcao == DIREITA) chao += 2;
                else if (direcao == ESQUERDA) chao -= 2;
            }
            if (!pulando) p.x2 = chao;
        }

        // Limites da tela (ajustado -20 no máximo de x2)
        if (p.x1 < 0) p.x1 = 0;
        if (p.x1 > 520) p.x1 = 520;
        if (p.x2 < 0) p.x2 = 0;
        if (p.x2 > 390) p.x2 = 390;

        setBounds(p.x1, p.x2, 48, 48);
        repaint();
    }

    public void pulo() {
        if (teclas[KeyEvent.VK_SPACE] && !escada) {
            pulando = true;
            new Thread(() -> {
                int i = 0;
                while (pulando) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    i += gravidade;
                    p.x2 -= gravidade;
                    if (i >= alturaPulo) break;
                    movimentos();
                }
                pulando = false;
                while (p.x2 < chao) {
                    p.x2 += gravidade;
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    movimentos();
                }
            }).start();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        teclas[e.getKeyCode()] = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !pulando && !escada) {
            pulo();
        }
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

    class Ponto {
        int x1, x2;
        Ponto(int x, int y) {
            x1 = x;
            x2 = y;
        }
    }
}
