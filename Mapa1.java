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
public class Mapa1 extends JFrame implements MouseListener {
    private JPanel inicio;
    public JPanel imagens;
    private Mario jumpMan;
    private JPanel perdeu;
    private JPanel win;
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
    public void troca(){
        perdeu = new Again();
        stopMusic();
        remove(imagens);
        add(perdeu);
        perdeu.addMouseListener(this);
        revalidate();
        repaint();
    }
    public void fim(){
        stopMusic();
        win = new Win();
        remove(imagens);
        add(win);
        win.addMouseListener(this);
        revalidate();
        repaint();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if(e.getSource() ==inicio){
            System.out.println("x:"+e.getX()+"y:"+e.getY());
        // Ajuste as coordenadas conforme a posição dos botões no painel Inicio

            Rectangle playArea = new Rectangle(220, 235, 120, 20);
            Rectangle exitArea = new Rectangle(220, 285, 120, 20);

            if (exitArea.contains(x, y)) {
                System.exit(0);
            } else if (playArea.contains(x, y)) {
                remove(inicio);
                stopMusic();
                imagens = new Imagens(this);
                add(imagens);
                playMusic(1);
                imagens.repaint();
                revalidate();
            }
        }
        else if(e.getSource() == perdeu){
            Rectangle playArea = new Rectangle(270, 230, 230, 22);
            Rectangle exitArea = new Rectangle(326, 310, 120, 30);

            if(exitArea.contains(x, y)){
                System.exit(0);
            }
            else if(playArea.contains(x,y)){
                remove(perdeu);
                imagens = new Imagens(this);
                add(imagens);
                playMusic(1);
                imagens.repaint();
                revalidate();
            }
        }
        else if(e.getSource() == win){
            Rectangle playArea = new Rectangle(418, 251, 80, 60);
            Rectangle exitArea = new Rectangle(418, 338, 70, 20);
            if(exitArea.contains(x,y))
                System.exit(0);
            else if(playArea.contains(x,y)){
                remove(win);
                imagens = new Imagens(this);
                add(imagens);
                playMusic(1);
                imagens.repaint();
                revalidate();
            }
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
        super.paintComponent(g);
        g.drawImage(inicio, 0, 0, 550, 450,null);
    }
}
class Win extends JPanel{
    BufferedImage fim;
    Win(){
        try{
            fim = javax.imageio.ImageIO.read(new java.io.File("sprites/venceu.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(fim, 0, 0, 550, 450,null);
}
}

class Imagens extends JPanel implements Runnable{
    Sound sound1;
    Mapa1 Mapa1;
    BufferedImage map1;
    BufferedImage princess;
    BufferedImage mario;
    BufferedImage kong;
    Mario jumpMan;
    Again perdeu = new Again();
    
    int i = 0, j = 0, nivelPlataforma = 0, q = 0, a = 0, f = 0;
    boolean direita = true, caindo = false, primeiravez = true, mostraBarrilAzul = false, fg = false;
    double dx = 148.00, dy = 108.00, dy_azul = 120.00, dx_azul = 90.00, fdx = 46.00, fdy = 395.00;
    double[] alturasPlataformas = {158, 217, 278, 338, 399};
    
    Image[] sprites = new Image[5];
    Image[] barril = new Image[4];
    Image[] barrilFrente = new Image[2];
    ArrayList<Barril> barris = new ArrayList<>();
    Image[] fogo = new Image[2];
    Image[] barrilAzul = new Image[2];
    Image[] foguinhoImagem = new Image[2];
    Image[] foguinhoImagemEsq = new Image[2];
    Foguinho foguinho = new Foguinho(foguinhoImagem, f, fdx, fdy, foguinhoImagemEsq);

    Imagens(Mapa1 Mapa1){
        this.Mapa1 = Mapa1;
        sound1 = new Sound();
        try{
            map1 = javax.imageio.ImageIO.read(new java.io.File("sprites/mapa1.png"));
            princess = javax.imageio.ImageIO.read(new java.io.File("sprites/princess.png"));
            mario = javax.imageio.ImageIO.read(new java.io.File("sprites/m1.png"));
            
            jumpMan = new Mario(92, 387);
            setLayout(null);
            jumpMan.setBounds(jumpMan.p.x1, jumpMan.p.x2, 20,20);
            add(jumpMan);
            jumpMan.requestFocusInWindow();
            
            SwingUtilities.invokeLater(() -> jumpMan.requestFocusInWindow());
            new Thread(()->{
                while (true) {
                    if (jumpMan.buraco() && !jumpMan.caindo){
                                jumpMan.cair();
                                new Thread(() -> {
                                    while (jumpMan.caindo) {
                                        try {
                                            Thread.sleep(50);
                                        } catch (Exception e) {}
                                    }/* 
                                    SwingUtilities.invokeLater(() -> {
                                        System.out.println("passou");
                                        sound1.setFile(2);
                                        sound1.play();
                                        Mapa1.troca();
                                        
                                    });*/
                                }).start();
                            }
                    jumpMan.movimentos();
                    repaint();
                    try {
                        Thread.sleep(16);
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
        barrilFrente[0] = ImageIO.read(new File("sprites/barril_frente_1.png"));
        barrilFrente[1] = ImageIO.read(new File("sprites/barril_frente_2.png"));
        fogo[0] = ImageIO.read(new File("sprites/fogo1_preview.png"));
        fogo[1] = ImageIO.read(new File("sprites/fogo2_preview.png"));
        barrilAzul[0] = ImageIO.read(new File("sprites/barril_azul_1.png"));
        barrilAzul[1] = ImageIO.read(new File("sprites/barril_azul_2.png"));
        foguinhoImagem[0] = ImageIO.read(new File("sprites/foguinho1-d.png"));
        foguinhoImagem[1] = ImageIO.read(new File("sprites/foguinho2-d.png"));
        foguinhoImagemEsq[0] = ImageIO.read(new File("sprites/foguinho1-e.png"));
        foguinhoImagemEsq[1] = ImageIO.read(new File("sprites/foguinho2-e.png"));
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

    Thread foguinhoThread = new Thread(() -> {
        while (true) {
            foguinho.atualizarF();
            repaint();
            try {
                Thread.sleep(60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    Thread barril_Azul = new Thread(() -> {
        while (true) {
            a+=1;
            dy_azul+=5.00;
            repaint();
            if(dy_azul>410){
                mostraBarrilAzul = false;
                foguinhoThread.start();
                fg = true;
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
                barris.add(new Barril(dx, dy, direita, alturasPlataformas, barril, barrilFrente));
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
    super.paintComponent(g);
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

    for (Barril b : barris){
        b.desenhar(g);
        Rectangle brect = new Rectangle((int)b.dx+10, (int)b.dy+10, 10, 10);
        Rectangle marioRectangle = new Rectangle(jumpMan.p.x1, jumpMan.p.x2, 35, 35);
        Rectangle fire = new Rectangle((int)foguinho.dx+10, (int)foguinho.dy+10, 10,10);
        Rectangle blue = new Rectangle((int)dx_azul+10, (int)dy_azul+10, 25, 25);
        Rectangle prin = new Rectangle(240, 33, 50, 50);
 
        if (marioRectangle.intersects(brect)){
            System.out.println("O barril encostou no mario");
            sound1.setFile(2);
            sound1.play();
            Mapa1.troca();
            return;
        }
        
        if (marioRectangle.intersects(blue)){
            System.out.println("O barril blueeee encostou no mario");
            sound1.setFile(2);
            sound1.play();
            Mapa1.troca();
            return;
        }
        if(marioRectangle.intersects(fire)){
            System.out.println("encontou no fogoooo");
            sound1.setFile(2);
            sound1.play();
            Mapa1.troca();
            return;
        }
    if(marioRectangle.intersects(prin)){
        //sound1.setFile(3);
        //sound1.play();
        Mapa1.fim();
        return;
    }
}
    g.drawImage(fogo[q], 43, 368, 43, 30, null);
    if(mostraBarrilAzul)
        g.drawImage(barrilAzul[a], (int) dx_azul, (int) dy_azul, 25, 25, null);

    if(fg)
        foguinho.desenharF(g);
}
public void run() {
            while(true){
                for (int ind = 0; ind < barris.size(); ind++) {
                    Barril b = barris.get(ind);
                    b.atualizar();

                    if(b.dx > 50 && b.dx < 60 && b.dy > 380){
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

class Sound{
        Clip clip;
        URL soundURL[] = new URL[30];
        Sound(){
            soundURL[0] = getClass().getResource("/sound/menu.wav");
            soundURL[1] = getClass().getResource("/sound/mapa1.wav");
            soundURL[2] = getClass().getResource("/sound/morreu.wav");
            soundURL[3] = getClass().getResource("/sound/venceu.wav");
            soundURL[4] = getClass().getResource("/sound/final.wav");
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

class Again extends JPanel{
    BufferedImage again;

    Again(){
        try{
            again = javax.imageio.ImageIO.read(new java.io.File("sprites/perdeu.png"));
        }catch(java.io.IOException e ){
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(again, 0, 0, 550, 450,null);
    }
}