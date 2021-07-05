package kakaoscsiga.view.drawables;

/**
 * A pozicio jelolesehez szukseges segedosztaly
 * Egy poziciot reprezental x es y koordinatakkal
 */
public class Point {
    /**
     * Az x koordinata
     */
    public int x;
    /**
     * Az y koordinata
     */
    public int y;

    /**
     * A Point konstruktora
     * @param x
     * @param y
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Meghatarozza a kapott ponttol valo tavolsagat
     * @param p A kapott pont
     * @return A tavolsagot adja vissza
     */
    public double distance(Point p){
       return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
    }
}
