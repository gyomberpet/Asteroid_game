package kakaoscsiga.view.drawables;

import kakaoscsiga.model.galaxy.Asteroid;
import kakaoscsiga.model.resource.*;
import kakaoscsiga.view.panels.LoadImages;

import java.awt.*;
import java.awt.image.BufferedImage;

//TODO commentek
public class AsteroidView extends Drawable{
    private Asteroid asteroid;
    protected Point position;

    /**
     * Az aszteroidaView deffault konstruktora, beallitja a megjelenitendo aszteroidat betolti a kep nevet,
     * (a kep neve fugg attol, hogy az aszteroida milyen nyersanyagot tarol el, mivel ezekhez kulon kep tartozik)
     * es beallitja az aszteroida poziciojat.
     * @param p az aszteroida pozicioja
     * @param asteroid a megjelenitendo aszteroida
     */
    public AsteroidView(Point p, Asteroid asteroid){
        super("Empty");
        position = p;
        this.asteroid = asteroid;
        if(asteroid.getResource() != null)
            imageName = asteroid.getResource().toString();
    }

    /**
     * betolti az aszteroidahoz tartozo kepet, majd kirajzolja az aszteroidat az eltarolt poziciora merete 64*64 es
     * @param g grafikai megjelenitohoz tartozo parameter
     */
    @Override
    public void draw(Graphics g) {
        BufferedImage image = LoadImages.images.get(imageName);
        g.drawImage(image,position.x, position.y, 64,64, null);
    }

    /**
     * A nyersanyag megvaltozasat kezelo fuggveny, ha megvaltozott a nyersanyag (pl.: kibanyasztak),
     * akkor a kinezete is megvaltozik, amihez uj kepet kell betolteni kirajzolaskor
     * @param resource a nyersanyag amire valtozott az aszteroida belseje, ha null akkor ugy ertelmezzuk, hogy az aszteroida ures
     */
    public void resourceChanged(Resource resource){
        if(resource == null)
            imageName = "Empty";
        else
            imageName = resource.toString();
    }

    /**
     * getter metodus
     * @return visszaadja az aszteroida poziciojat
     */
    public Point getPosition(){
        return position;
    }
}
