package kakaoscsiga.view.drawables;

import kakaoscsiga.model.TeleportGatePair;
import kakaoscsiga.view.panels.LoadImages;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TeleportView extends Drawable{
    private TeleportGatePair teleport;

    /**
     * A teleportView konstruktora, amelyben megadjuk a kirajzoláshoz szükséges kép nevét, majd az átadott
     *  paramétert beállítjuk TeleportView teleportjának.
     * @param teleport a lerakott teleportkapu
     */
    public TeleportView(TeleportGatePair teleport){
        super("TeleportGatePair");
        this.teleport = teleport;
    }

    /**
     * A Draweble osztály függvényét felülírjuk és kirajzoljuk a teleportkaput
     * @param g egy grafikus objektum
     */
    @Override
    public void draw(Graphics g) {
        if(teleport.getDestination()!=null) {
            int teleportX = teleport.getDestination().getAsteroidView().position.x;
            int teleportY = teleport.getDestination().getAsteroidView().position.y;
            BufferedImage image = LoadImages.images.get(imageName);
            g.drawImage(image, teleportX + 40, teleportY + 40, null);
        }
    }
}
