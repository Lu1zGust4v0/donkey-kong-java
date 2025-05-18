import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;
public class Mapa1 extends JFrame{
    ImageIcon telaa1 = new ImageIcon(getClass().getResource("spritesm1.png"));
    Image recorte = telaa1.getImage().getScaledInstance(560, 440, Image.SCALE_SMOOTH);
    ImageIcon tela1 = new ImageIcon(recorte);
    JLabel label = new JLabel(tela1);   
   Mapa1(){
    setSize(572,470);
    add(label);
    setLocationRelativeTo(this);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
   }
    public static void main(String[] args){
        new Mapa1();
}
}
