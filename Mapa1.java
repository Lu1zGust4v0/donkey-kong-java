import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class Mapa1 extends JFrame{
   Mapa1(){
    JFrame frame = new JFrame(); 
    frame.setSize(572,468);
    frame.add(new Imagens());
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
        Imagens(){
            try{
                map1 = javax.imageio.ImageIO.read(new java.io.File("sprites/spritesm1.png"));
                princess = javax.imageio.ImageIO.read(new java.io.File("sprites/princess.png"));
            }catch(java.io.IOException e){
                e.printStackTrace();
            }    
    }
    protected void paintComponent(Graphics g){
        super.paintComponents(g);
        g.drawImage(map1, 0, 0, 560, 440, null);
        g.drawImage(princess, 240,32, 50,50, null);
    }
}
