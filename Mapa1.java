import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.lang.Thread;
import java.net.URL;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mapa1 extends JFrame implements MouseListener {
    private JPanel inicio;
    private JPanel imagens;
    private Mario jumpMan;
    Sound sound = new Sound();
    Thread gameThread;
    public Mapa1() {
        setSize(560, 480);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centraliza a janela
        
        inicio = new Inicio(); // painel inicial com a imagem e botões
        inicio.addMouseListener(this);
        add(inicio);
        
        playMusic(0);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // Ajuste as coordenadas conforme a posição dos botões no painel Inicio
        Rectangle playArea = new Rectangle(213, 240, 120, 20);
        Rectangle exitArea = new Rectangle(213, 285, 120, 20);

        if (exitArea.contains(x, y)) {
            System.exit(0);
        } else if (playArea.contains(x, y)) {
            remove(inicio);
            stopMusic();
            imagens = new Imagens();
            add(imagens);
            playMusic(1);
            imagens.repaint();
            revalidate();
        }
    }
    // Métodos obrigatórios do MouseListener (vazios)
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new Mapa1();
        
    }

    
    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();   
    }

    public void stopMusic(){
        sound.stop();
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
class Inicio extends JPanel{
    BufferedImage inicio;

    Inicio(){
        try{
            inicio = javax.imageio.ImageIO.read(new java.io.File("sprites/inicial.png"));
        }catch(java.io.IOException e ){
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponents(g);
        g.drawImage(inicio, 0, 0, 550, 450,null);
    }
}

class Imagens extends JPanel implements Runnable{
    BufferedImage map1;
    BufferedImage princess;
    BufferedImage mario;
    BufferedImage kong;
    Mario jumpMan;
    
    int i = 0, j = 0, nivelPlataforma = 0, q = 0, a = 0;
    boolean direita = true, caindo = false, primeiravez = true, mostraBarrilAzul = false;
    double dx = 148.00, dy = 105.00, dy_azul = 120.00, dx_azul = 90.00;
    double[] alturasPlataformas = {157, 215, 277, 338, 395};
    
    Image[] sprites = new Image[5];
    Image[] barril = new Image[4];
    ArrayList<Barril> barris = new ArrayList<>();
    Image[] fogo = new Image[2];
    Image[] barrilAzul = new Image[2];

    Imagens(){
        try{
            map1 = javax.imageio.ImageIO.read(new java.io.File("sprites/mapa1.png"));
            princess = javax.imageio.ImageIO.read(new java.io.File("sprites/princess.png"));
            mario = javax.imageio.ImageIO.read(new java.io.File("sprites/m1.png"));
            
            jumpMan = new Mario(108, 410);
            setLayout(null);
            jumpMan.setBounds(jumpMan.p.x1, jumpMan.p.x2, 20,20);
            add(jumpMan);
            jumpMan.requestFocusInWindow();
            
            SwingUtilities.invokeLater(() -> jumpMan.requestFocusInWindow());
            new Thread(()->{
                while (true) {
                    jumpMan.movimentos();
                    jumpMan.pulo();
                    repaint();
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {}
                }
            }).start();

            new Thread(this).start();
        }catch(java.io.IOException e){
            e.printStackTrace();
        }
    try {
        sprites[0] = ImageIO.read(new File("sprites/d1.png"));
        sprites[1] = ImageIO.read(new File("sprites/d2.png"));
        sprites[2] = ImageIO.read(new File("sprites/d3.png"));
        sprites[3] = ImageIO.read(new File("sprites/d5.png"));
        sprites[4] = ImageIO.read(new File("sprites/d6.png"));
        barril[0] = ImageIO.read(new File("sprites/barril1.png"));
        barril[1] = ImageIO.read(new File("sprites/barril2.png"));
        barril[2] = ImageIO.read(new File("sprites/barril3.png"));
        barril[3] = ImageIO.read(new File("sprites/barril4.png"));
        fogo[0] = ImageIO.read(new File("sprites/fogo1_preview.png"));
        fogo[1] = ImageIO.read(new File("sprites/fogo2_preview.png"));
        barrilAzul[0] = ImageIO.read(new File("sprites/barril_azul_1.png"));
        barrilAzul[1] = ImageIO.read(new File("sprites/barril_azul_2.png"));
    } catch (IOException e) {
        e.printStackTrace();
    }   
    
    new Thread(() -> {
        while (true) {
            q += 1;
            repaint();
            if(q==2)
                q=0;
            try {
                Thread.sleep(80);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();

    Thread barril_Azul = new Thread(() -> {
        while (true) {
            a+=1;
            dy_azul+=5.00;
            repaint();
            if(dy_azul>410){
                mostraBarrilAzul = false;
                break;
            }
            if(a==2)
                a=0;
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    new Thread(() -> {
        while (true) {
            i += 1;
            repaint();
            if(primeiravez && i == 3){
                mostraBarrilAzul = true;
                barril_Azul.start();
                i = 0;
                primeiravez = false;
            }
            if(i==4)
                barris.add(new Barril(dx, dy, direita, alturasPlataformas, barril));
            if(i==5)
                i=0;
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
    
}

protected void paintComponent(Graphics g){
    super.paintComponents(g);
    g.drawImage(map1, 0, 0, 550, 450,null);
    g.drawImage(princess, 240,33, 50,50, null);

    g.setColor(Color.white);
    g.setFont(new Font("Century Gothic", Font.BOLD, 10));
    g.drawString("mario_x:"+jumpMan.p.x1, 0, 20);
    g.drawString("mario_y:"+jumpMan.p.x2, 0, 40);

    if(i!=3)
        g.drawImage(sprites[i], 50, 35,100,100,null);
    else
        g.drawImage(sprites[i], 50, 50,100,100,null);

    for (Barril b : barris) 
        b.desenhar(g);

    g.drawImage(fogo[q], 43, 368, 43, 30, null);
    if(mostraBarrilAzul)
        g.drawImage(barrilAzul[a], (int) dx_azul, (int) dy_azul, 25, 25, null);
}

public void run() {
            while(true){
                for (int ind = 0; ind < barris.size(); ind++) {
                    Barril b = barris.get(ind);
                    b.atualizar();

                    if(b.dx > 50 && b.dx < 60 && b.dy > 400){
                        barris.remove(ind);
                        ind--;
                    }
                        
                }
                repaint();
                try{
                    Thread.sleep(50);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
}

class Barril {
    double dx, dy;
    int j = 0, e = 0;
    boolean direita;
    boolean caindo;
    boolean descendoEscada;
    boolean decidiuDescerEscada = false;
    boolean vaiDescerEscada = false;
    int nivelPlataforma;
    double[] alturasPlataformas;
    Image[] imagens;
    static Map<Integer, double[]> escadasPorNivel = new HashMap<>();
    static {
        escadasPorNivel.put(0, new double[] {225, 444});
        escadasPorNivel.put(1, new double[] {388, 194, 84});
        escadasPorNivel.put(2, new double[] {169, 251, 444});
        escadasPorNivel.put(3, new double[] {224, 85});
        escadasPorNivel.put(4, new double[] {196, 444});
    }

    Barril(double dx, double dy, boolean direita, double[] alturasPlataformas, Image[] imagens) {
        this.dx = dx;
        this.dy = dy;
        this.direita = direita;
        this.caindo = false;
        this.descendoEscada = false;
        this.nivelPlataforma = 0;
        this.alturasPlataformas = alturasPlataformas;
        this.imagens = imagens;
    }

    void atualizar() {
        //if(!descerEscada())
            j = (j + 1) % 4;
        //else
            //e = (e + 1) % 2;

        if (descendoEscada) {
            dy += 4;
            if (nivelPlataforma < alturasPlataformas.length && dy >= alturasPlataformas[nivelPlataforma]) {
                descendoEscada = false;
                decidiuDescerEscada = false;
                vaiDescerEscada = false;

                if (nivelPlataforma % 2 == 0) {
                    direita = false;
                } else {
                    direita = true;
                }

                nivelPlataforma++;
            }
            return;
        }
        if (caindo) {
            dy += 5;
            if (nivelPlataforma % 2 == 0) {
                dx += 1;
            } else {
                dx -= 1;
            }
            if (dy >= alturasPlataformas[nivelPlataforma]) {
                caindo = false;
                nivelPlataforma++;
            }
        } else{
            if (direita) {
                dx += 5;
                dy += 0.15;
                if (dx > 495 && dx < 505) {
                    direita = false;
                    caindo = true;
                }
            } else {
                dx -= 5;
                dy += 0.15;
                if (dx > 30 && dx < 35) {
                    direita = true;
                    caindo = true;
                }
            }
        }

        if (descerEscada()) {
            if (!decidiuDescerEscada) {
                decidiuDescerEscada = true;
                vaiDescerEscada = Math.random() < 0.5;
            }
            if (vaiDescerEscada) {
                descendoEscada = true;
                return;
            }
        } else {
            decidiuDescerEscada = false;
        }
        
    }

    boolean descerEscada() {
        double[] escadas = escadasPorNivel.getOrDefault(nivelPlataforma, new double[0]);
        for (double escadaX : escadas) {
            if (Math.abs(dx - escadaX) < 3) {
                return true;
            }
        }
        return false;
    }

    void desenhar(Graphics g) {
        if(!descerEscada())
            g.drawImage(imagens[j], (int) dx, (int) dy, 25, 25, null);
        else
            g.drawImage(imagens[j], (int) dx, (int) dy, 25, 25, null);
    }
}
class Sound{
        Clip clip;
        URL soundURL[] = new URL[30];
        Sound(){
            soundURL[0] = getClass().getResource("/sound/menu.wav");
            soundURL[1] = getClass().getResource("/sound/mapa1.wav");
        }
        void setFile(int i){
            try{
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                clip = AudioSystem.getClip();
                clip.open(ais);
            }catch(Exception e){

            }
        }
        void play(){
            clip.start();
        }
        void loop(){
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        void stop(){
            clip.stop();
        }
}
