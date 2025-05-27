import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Jogo extends JPanel implements KeyListener {
    private Jogador jogador;
    private boolean[] teclas = new boolean[256];

    public Jogo() throws Exception {
        setPreferredSize(new Dimension(640, 480));
        setBackground(Color.BLUE);
        jogador = new Jogador(100, 100);
        addKeyListener(this);
        setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        jogador.desenhar(g, this);
    }

    public void game_loop() {
        int dx = 0, dy = 0;
        if (teclas[KeyEvent.VK_LEFT]) dx = -1;
        if (teclas[KeyEvent.VK_RIGHT]) dx = 1;
        if (teclas[KeyEvent.VK_UP]) dy = -1;
        if (teclas[KeyEvent.VK_DOWN]) dy = 1;

        jogador.mover(dx, dy, getWidth(), getHeight());
        repaint();

        try {
            Thread.sleep(33);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent e) {
        teclas[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        teclas[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Mini Jogo com Sprites");
        Jogo jogo = new Jogo();
        frame.add(jogo);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        while(true)
            jogo.game_loop();
    }
}
