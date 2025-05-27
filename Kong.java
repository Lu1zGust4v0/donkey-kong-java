import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Kong {
    int x, y;
    Kong(){
        Image[] sprites = new Image[5];
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
}
