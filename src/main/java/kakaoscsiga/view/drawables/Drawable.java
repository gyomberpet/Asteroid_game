package kakaoscsiga.view.drawables;

import java.awt.*;

//TODO commentek
public abstract class Drawable {

    /**
     * a megjelenitendo kep neve
     */
    protected String imageName;

    /**
     * Konstruktor, ami beallitja a parameterkent atadott kep nevet, emellett hozzaadja magat a az osszes
     * view elemet tarolo ViewContainerhez
     * @param imageName a megjenitendo kep neve
     */
    public Drawable(String imageName){
        this.imageName = imageName;
        ViewContainer.getInstance().addDrawable(this);
    }

    /**
     * a megjeniteshez szukseges absztrakt metodus
     * @param g a kirajzolashoz szukseges parameter
     */
    public abstract void draw(Graphics g);
}
