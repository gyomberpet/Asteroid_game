package kakaoscsiga.view.drawables;

import kakaoscsiga.model.galaxy.Sun;
import kakaoscsiga.view.panels.LoadImages;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SunView extends Drawable{
    private Sun sun;
    private Point position;

    /**
     * A sunView konstruktora, amelyben megadjuk a kirajzolashoz szukseges kep nevet, majd az atadott
     * parametert beallitjuk sunView napjanak.
     * @param sun a galaxis napja
     */
    public SunView(Sun sun){
        super("Sun");
        position = new Point(100, 100);
        this.sun = sun;
    }

    /**
     * A Draweble osztaly fuggvenyet felulirjuk és kirajzoljuk a napot
     * @param g egy grafikus objektum
     */
    @Override
    public void draw(Graphics g) {
        int sunX = position.x;
        int sunY = position.y;
        BufferedImage image;
        if(sun.getTimeToStorm()>3)
            image = LoadImages.images.get(imageName);
        else if(sun.getTimeToStorm()>1)
            image = LoadImages.images.get("AngrySun");
        else
            image = LoadImages.images.get("ReallyAngrySun");
        g.drawImage(image, sunX - 128, sunY - 128, 256, 256,null);
    }
    public Point getPosition(){
        return this.position;
    }
}
