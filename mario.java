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

    Ponto p;
    boolean pulando = false, escada = false;
    int direcao, frame, chao = 410, nivel = 0;
    int alturaPulo = 28, gravidade = 7;
    BufferedImage[][] sprites;
    static final int VELOCIDADE = 2;

    boolean[] teclas = new boolean[256];

    Mario(int x, int y) throws IOException {
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

        // Carrega os sprites
        sprites = new BufferedImage[3][2];
        sprites[0][0] = ImageIO.read(new File("sprites/m1.png"));
        sprites[0][1] = ImageIO.read(new File("sprites/m2.png"));
        sprites[1][0] = ImageIO.read(new File("sprites/m3.png"));
        sprites[1][1] = ImageIO.read(new File("sprites/m4.png"));
        sprites[2][0] = ImageIO.read(new File("sprites/m5.png"));
        sprites[2][1] = ImageIO.read(new File("sprites/m6.png"));

        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprites[direcao][frame], 0, 0, 60, 60, null);
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
        } else if (nivel == 5) {
            desniveis.add(276);
            desniveis.add(388);

            escadasBoas.add(308);
        }
    }

    public void movimentos() {
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

        // Atualiza nível baseado no eixo Y
        if (p.x2 >= 357) {
            nivel = 0;
            addArray(pontosDesnivel);
        } else if (p.x2 >= 297) {
            nivel = 1;
            addArray(pontosDesnivel);
        } else if (p.x2 >= 235) {
            nivel = 2;
            addArray(pontosDesnivel);
        } else if (p.x2 >= 175) {
            nivel = 3;
            addArray(pontosDesnivel);
        } else if (p.x2 >= 115) {
            nivel = 4;
            addArray(pontosDesnivel);
        } else if (p.x2 >= 61) {
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

        // Limites da tela
        if (p.x1 < 0) p.x1 = 0;
        if (p.x1 > 520) p.x1 = 520;
        if (p.x2 < 0) p.x2 = 0;
        if (p.x2 > 410) p.x2 = 410;

        setBounds(p.x1, p.x2, 20, 20);
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
