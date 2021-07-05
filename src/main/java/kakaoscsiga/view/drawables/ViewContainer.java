package kakaoscsiga.view.drawables;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A rajzolhato objektumok kozos tarolasaert felelos osztaly
 */
public class ViewContainer {
    /**
     * A tarolt rajzolhato objektumok listaja
     */
    private List<Drawable> drawables = new ArrayList<>();

    /**
     * Az adott statikus viewContainer objektum, amit mindenki kozosen hasznalhat
     */
    private static ViewContainer viewContainer = new ViewContainer();

    /**
     * A szinkronizaciohoz szukseges lock objet
     */
    private final Object lock = new Object();

    /**
     * Ezzel kerdezheto le a konkret ViewContainer egyetlen peldanya
     * @return Az egyetlen ViewContainer
     */
    public static ViewContainer getInstance(){ return viewContainer; }

    /**
     * Hozzaadja a kapott rajzolhato objektumot a listjahoz
     * @param d A kapott rajzolhato objektum
     */
    public void addDrawable(Drawable d){
        synchronized (lock) {
            drawables.add(d);
        }
    }

    /**
     * Eltavolitja a kapott rajzolhato objektumot a listjabol
     * @param d A kapott rajzolhato objektum
     */
    public void removeDrawable(Drawable d){
        synchronized (lock) {
            drawables.remove(d);
        }
    }

    /**
     * Az osszes rajzolhato objektumot kirajzolja, azaz meghivja rajtuk a draw() metodust
     * @param g A rajzolashoz szukseges Graphics
     */
    public void drawAllView(Graphics g){
        synchronized (lock){
            for(Drawable d : drawables){
                d.draw(g);
            }
        }
    }

    /**
     * Kitorli a tarolt Drawable-oket
     */
    public void clear(){
        synchronized (lock) {
            drawables.clear();
        }
    }
}