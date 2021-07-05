package kakaoscsiga.view.drawables;


import kakaoscsiga.model.entity.Robot;
import kakaoscsiga.view.panels.LoadImages;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Maganak a robotnak az adott pozicióba torténő megjeleniteset,
 * kirajzolasat teszi lehetove.
 */
public class RobotView extends Drawable{
    /**
     * A Robot, amihez ez a view tartozik
     */
    private Robot robot;

    /**
     * Konstruktor
     * @param robot Az a Robot, amihez ez a view tartozik
     */
    public RobotView(Robot robot) {
        super("Robot");
        this.robot = robot;
    }

    /**
     * Az adott robot poziciojanak megfeleloen kirajzol egy robotot
     * @param g A rajzolashoz szukseges Graphics
     */
    @Override
    public void draw(Graphics g) {
        int asteroidX = robot.getLocation().getAsteroidView().position.x;
        int asteroidY = robot.getLocation().getAsteroidView().position.y;
        BufferedImage image = LoadImages.images.get(imageName);
        g.drawImage(image, asteroidX + 38, asteroidY - 1, null);
    }
}
