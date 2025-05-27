import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class Mapa1 extends JFrame{
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
    class Imagens extends JPanel{
        BufferedImage map1;
        BufferedImage princess;
        BufferedImage mario;
        Imagens(){
            try{
                map1 = javax.imageio.ImageIO.read(new java.io.File("sprites/spritesm1.png"));
                princess = javax.imageio.ImageIO.read(new java.io.File("sprites/princess.png"));
                mario = javax.imageio.ImageIO.read(new java.io.File("sprites/m1.png"));
            }catch(java.io.IOException e){
                e.printStackTrace();
            }    
    }
    protected void paintComponent(Graphics g){
        super.paintComponents(g);
        g.drawImage(map1, 0, 0, 550, 450, null);
        g.drawImage(princess, 240,32, 50,50, null);
        g.drawImage(mario, 90, 400, 30, 30, null);
    }
}
