package kakaoscsiga.view.drawables;

import kakaoscsiga.model.entity.UFO;
import kakaoscsiga.view.panels.LoadImages;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Az UFO megjeleniteseert felelos osztaly
 */
public class UFOView extends Drawable{
    /**
     * Az UFO, amihez ez a view tartozok
     */
    private UFO ufo;

    /**
     * Az UFO konstruktora
     * @param ufo Az az UFO, amihez ez a view tartozik
     */
    public UFOView(UFO ufo) {
        super("Ufo");
        this.ufo = ufo;
    }


    /**
     * Kirajzolja az UFO-t ahoz az aszteroidahoz viszonyitva, amelyiken az UFO van
     * @param g A rajzolashoz szukseges Graphics
     */
    @Override
    public void draw(Graphics g) {
        int asteroidX = ufo.getLocation().getAsteroidView().position.x;
        int asteroidY = ufo.getLocation().getAsteroidView().position.y;
        BufferedImage image = LoadImages.images.get(imageName);
        g.drawImage(image, asteroidX + 4, asteroidY - 1, null);
    }
}
