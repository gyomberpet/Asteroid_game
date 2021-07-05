package kakaoscsiga.view.panels;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoadImages {
    public static final Map<String, BufferedImage> images = new HashMap<>();
    static{
        try {
            images.put("Coal", ImageIO.read(new File("images/coal.png")));
            images.put("Empty", ImageIO.read(new File("images/empty.png")));
            images.put("Iron", ImageIO.read(new File("images/iron.png")));
            images.put("WaterIce", ImageIO.read(new File("images/waterice.png")));
            images.put("Uranium", ImageIO.read(new File("images/uran.png")));
            images.put("Settler", ImageIO.read(new File("images/settler.png")));
            images.put("Ufo", ImageIO.read(new File("images/ufo.png")));
            images.put("Robot", ImageIO.read(new File("images/robot.png")));
            images.put("Sun", ImageIO.read(new File("images/sun.png")));
            images.put("AngrySun", ImageIO.read(new File("images/angrysun.png")));
            images.put("ReallyAngrySun", ImageIO.read(new File("images/angrysun2.png")));
            images.put("TeleportGatePair", ImageIO.read(new File("images/teleport.png")));
            images.put("Marker", ImageIO.read(new File("images/marker.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}