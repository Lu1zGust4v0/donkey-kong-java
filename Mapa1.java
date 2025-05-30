import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.lang.Thread;

public class Mapa1 extends JFrame {
   Mapa1(){ 
    JFrame frame = new JFrame(); 
    frame.setSize(560,480);    
    frame.add(new Imagens());
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
}
    public static void main(String[] args){
        new Mapa1();
}
}
    class Imagens extends JPanel implements Runnable{
        BufferedImage map1;
        BufferedImage princess;
        BufferedImage mario;
        BufferedImage kong;
        int i = 0, j = 0;
        int dx = 148, dy = 105;
        
        Image[] sprites = new Image[5];
        Image[] barril = new Image[2];
        Imagens(){
            try{
                map1 = javax.imageio.ImageIO.read(new java.io.File("sprites/spritesm1.png"));
                princess = javax.imageio.ImageIO.read(new java.io.File("sprites/princess.png"));
                mario = javax.imageio.ImageIO.read(new java.io.File("sprites/m1.png"));
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
            barril[0] = ImageIO.read(new File("sprites/b1__2_-removebg-preview.png"));
            barril[1] = ImageIO.read(new File("sprites/b2-removebg-preview.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
    protected void paintComponent(Graphics g){
        super.paintComponents(g);
        g.drawImage(map1, 0, 0, 550, 450,null);
        g.drawImage(princess, 240,32, 50,50, null);
        g.drawImage(mario, 90, 400, 30, 30, null);
        if(i!=3)
            g.drawImage(sprites[i], 50, 35,100,100,null);
        else
            g.drawImage(sprites[i], 50, 50,100,100,null);
        g.drawImage(barril[j], dx, dy, 25, 25, null);
    }
    public void run() {
                while(true){
                    i+=1;
                    j+=1;
                    dx+=5;
                    dy+=1;
                    repaint();
                    if(i==5)
                        i=0;
                    if(j==2)
                        j = 0;
                    try{
                        Thread.sleep(1000);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
    }
