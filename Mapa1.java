import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.lang.Thread;
import java.lang.reflect.InvocationTargetException;
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
        BufferedImage barril;
        int i =0;
        
        Image[] sprites = new Image[5];
        Imagens(){
            try{
                map1 = javax.imageio.ImageIO.read(new java.io.File("sprites/spritesm1.png"));
                princess = javax.imageio.ImageIO.read(new java.io.File("sprites/princess.png"));
                mario = javax.imageio.ImageIO.read(new java.io.File("sprites/m1.png"));
                barril = javax.imageio.ImageIO.read(new java.io.File("sprites/b1.png"));
                new Thread(this).start();
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
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
    protected void paintComponent(Graphics g){
        super.paintComponents(g);
        g.drawImage(map1, 0, 0, 550, 450,null);
        g.drawImage(princess, 240,32, 50,50, null);
        g.drawImage(mario, 90, 400, 30, 30, null);
        g.drawImage(barril, 9, 400, 30, 30, null);
        if(i!=3)
            g.drawImage(sprites[i], 50, 35,100,100,null);
        else
            g.drawImage(sprites[i], 50, 50,100,100,null);
    }
    public void run() {
                while(true){
                    i+=1;
                    repaint();
                    if(i==5)
                        i=0;
                    try{
                        Thread.sleep(1000);
                         }
                     catch(Exception e){
                         e.printStackTrace();
              }
            }
}
    }
