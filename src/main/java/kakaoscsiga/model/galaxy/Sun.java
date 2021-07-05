package kakaoscsiga.model.galaxy;

import kakaoscsiga.model.RoundManager;
import kakaoscsiga.model.Steppable;
import kakaoscsiga.view.panels.GameFrame;
import kakaoscsiga.view.drawables.Point;
import kakaoscsiga.view.drawables.SunView;

import java.util.List;
import java.util.Random;

/**
 * Az Aszteroidabanyaszat nevu jatekban a napot reprezentalja
 * @author kakaoscsiga
 */
public class Sun implements Steppable {
    /**
     * A Sun aktualis poziciojat meghatarozo ido
     */
    private int t;

    /**
     * Galaxy-t tarolo attributum
     */
    private Galaxy galaxy;
    /**
     * A napvihar bekovetkezesenek idejet hatarozza meg
     */
    private int timeToStorm;
    /**
     * A nap view-jat tarolo attributum
     */
    private SunView view;

    /**
     * A napot reprezentalja a jatekban, konstruktor
     * A timetoStrom attributumnak, ami azt hatarozza meg, hogy mikor legyen napkitores a galaxysban, egy random szamot adunk meg
     * amit 5 és 14 kozott sorsol ki a fuggveny, ez a korok szamat jeloli, amig cselekedhetnek a jateksok
     * Beallitja a kezdo poziciojat is
     * @param g A jatek alltal letrehozott galaxis
     */
    public Sun(Galaxy g) {
        t=1;
        galaxy = g;
        Random rand = new Random();
        timeToStorm = rand.nextInt(10)+5;

        RoundManager.getInstance().addSteppable(this);
        view = new SunView(this);
        int r = Math.min(GameFrame.WIDTH, GameFrame.HEIGHT) / 8;
        double alpha = t/12.0 * 2 * Math.PI;
        view.getPosition().x = (int)(GameFrame.WIDTH / 2 + r * Math.cos(alpha));
        view.getPosition().y = (int)(GameFrame.HEIGHT / 2 + r * Math.sin(alpha));
    }

    /**
     * Maga a napvihar, ami a galaxis osszes aszteroidajat erinti.
     * Minden aszteroidara meghivja a burn fuggvenyt, amit vagy tulel az entitas, aki az aszteroidan tartozkodik vagy nem
     * a feltetelek, ami alapjan ez megtortenhet ismertetve vannak a burn fuggvenyben
     */
    public void generateStrom(){
        List<Asteroid> listOfAsteroids = galaxy.getCloseAsteroids();
        if (listOfAsteroids != null){
            for (int i = 0; i < listOfAsteroids.size(); ++i) {
                listOfAsteroids.get(i).burn();
            }
        }
    }

    /**
     * Bizonyos idokozonkent napvihart general vagy mozog.
     * A nap mozog a galaxysban, ha mozgott a galaxysnak meguzenjuk ezt es ezzel o megvizsgalja a benne levo aszteroidaknak
     * closeToSun attributumat, ha ez esetleg valtozott akkor ezt a bool erteket megvaltoztatja
     * Ha a timeTostrom attributum erteke 0, akkor elinditja a galaxysona napvihar, majd egy ujabb random szamot sorsol ki 5 és 14 kozott.
     * Amennyiben ez az ertek nem nulla, akkor csokkenti a korok szamat (timeToStrom), meg ennyi koruk van a jatekosoknak cselekedni.
     */
    @Override
    public void step() {

        move();
        timeToStorm--;
        galaxy.sunMoved();
        if( timeToStorm == 0) {
            generateStrom();
            Random rand = new Random();
            timeToStorm = rand.nextInt(10)+5;
        }
    }

    /**
     * A nap konkret mozgasat valositja meg. Ez majd a grafikus megjelenitesnel lesz lenyeges
     * ezert itt meg nem fejtettuk ki
     */
    public void move(){
        int panelWidth = GameFrame.WIDTH;
        int panelHeight = GameFrame.HEIGHT;
        int origoX = panelWidth / 2;
        int origoY= panelHeight / 2;
        int r = Math.min(panelWidth, panelHeight) / 8;

        double alpha = t/12.0 * 2 * Math.PI;
        view.getPosition().x = (int)(origoX + r * Math.cos(alpha));
        view.getPosition().y = (int)(origoY + r * Math.sin(alpha));
        t++;
    }

    public int getTimeToStorm(){return timeToStorm;}
    public void setTimeToStorm(int time){
        this.timeToStorm = time;
    }

    public Point getPosition(){
        return view.getPosition();
    }
}
