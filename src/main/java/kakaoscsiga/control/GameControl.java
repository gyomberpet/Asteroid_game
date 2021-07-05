package kakaoscsiga.control;

import kakaoscsiga.model.galaxy.Asteroid;
import kakaoscsiga.model.TeleportGatePair;
import kakaoscsiga.model.entity.Settler;
import kakaoscsiga.view.drawables.Point;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Ez az osztaly felelos a jatek panelen tortent esemenyek kezeleseert.
 * Ehhez implementalja a megfelelo interfaceket.
 * Singleton osztaly
 * @author kakaoscsiga
 */
public class GameControl implements KeyListener, MouseListener {
    /**
     * Az eppen soron kovetkezo settlert tarolja
     */
    private Settler ActiveSettler;
    /**
     * Az az aszteroida, amire a jatekos kattintott
     */
    private Asteroid TargetAsteroid;
    /**
     * Az a teleportkapu, amire at akar menni a telepes
     */
    private TeleportGatePair TargetTeleport;
    /**
     * Az aktualisan lenyomott billentyu
     */
    private char  activeKey;
    /**
     * Annak a vizsgalatara kell, hogy a felhasznalo nyomott-e mar le valamit
     */
    private static boolean ready = false;

    private static GameControl gameControl = new GameControl();

    private GameControl(){}

    /**
     * Ebben a fuggvenyben varunk a settler inputjara.
     * A lenyomott key alapjan pedig az aktualis settler muveleteit hivjuk
     * @param s
     */
    public void control(Settler s) {
        ActiveSettler = s;
        activeKey = ' ';
        while(true){
            try { Thread.sleep(2); }
            catch (InterruptedException e) {}
            //Lenyomott key ellenorzes
            if (ready){
                switch (activeKey){
                    case 'm':
                        if(s.mine())
                            return;
                        break;
                    case 'd':
                        if(s.drill())
                            return;
                        break;
                    case 'o':
                        if(s.teleportGatePut())
                            return;
                        break;
                    case '0':
                        if (s.getResources().size() >= 1) {
                            if(s.dropResource(s.getResources().get(0)))
                                return;
                        }
                        break;
                    case '1':
                        if (s.getResources().size() >= 2) {
                            if(s.dropResource(s.getResources().get(1)))
                                return;
                        }
                        break;
                    case '2':
                        if (s.getResources().size() >= 3) {
                            if(s.dropResource(s.getResources().get(2)))
                                return;
                        }
                        break;
                    case '3':
                        if (s.getResources().size() >= 4) {
                            if(s.dropResource(s.getResources().get(3)))
                                return;
                        }
                        break;
                    case '4':
                        if (s.getResources().size() >= 5) {
                            if(s.dropResource(s.getResources().get(4)))
                                return;
                        }
                        break;
                    case '5':
                        if (s.getResources().size() >= 6) {
                            if(s.dropResource(s.getResources().get(5)))
                                return;
                        }
                        break;
                    case '6':
                        if (s.getResources().size() >= 7) {
                            if(s.dropResource(s.getResources().get(6)))
                                return;
                        }
                        break;
                    case '7':
                        if (s.getResources().size() >= 8) {
                            if(s.dropResource(s.getResources().get(7)))
                                return;
                        }
                        break;
                    case '8':
                        if (s.getResources().size() >= 9) {
                            if(s.dropResource(s.getResources().get(8)))
                                return;
                        }
                        break;
                    case '9':
                        if (s.getResources().size() >= 10) {
                            if(s.dropResource(s.getResources().get(9)))
                                return;
                        }
                        break;
                    case 'r':
                        if(s.buildRobot()!=null)
                            return;
                        break;
                    case 't':
                        if(s.buildTeleportGate()!=null)
                            return;
                        break;
                    case 'b':
                        if(s.buildBase())
                            return;
                        break;
                    case '!':
                        if (s.teleport(TargetTeleport))
                            return;
                        break;
                    case '\r':
                        if(s.move(TargetAsteroid))
                            return;
                        break;
                    case 'x':
                        return;
                }
                ready = false;
            }
        }
    }

    /**
     * Akkor hivodik meg, hogyha a jatekos lenyom egy billentyut
     * Es az beallitja az ActiveKey-t es a ready erteket is
     * @param e Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        activeKey = e.getKeyChar();
        ready = true;
    }

    /**
     * Kattintas hatasara hivodik meg
     * Ha szomszedos az aszteroida, akkor at tud oda mozogni
     * Ha nem szomszedosak, de van koztuk teleportkapu kapcsolat, akkor ugyis at tud menni
     * Kulonben kattintasra nem tortenik semmi
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();

        Asteroid actualAsteroid = ActiveSettler.getLocation();
        List<Asteroid> asteroids = actualAsteroid.getNeighbours();

        TargetAsteroid = null;

        /**
         * Megvizsgaljuk, hogy melyik aszteroidakra kattinthattunk valoszinuleg
         */

        for(Asteroid a: asteroids) {
            Point temp = new Point(a.getPosition().x + 32, a.getPosition().y + 32);
            boolean isClicked = temp.distance(new Point(clickX, clickY)) <= 32;

            if (isClicked) {
                activeKey = '\r';
                TargetAsteroid = a;
                ready = true;
                break;
            }
        }

        List<TeleportGatePair> teleports = actualAsteroid.getTeleport();
        for (TeleportGatePair teleport : teleports){
            if (teleport.getPairDestination() == null)
                continue;
            Point temp = new Point(teleport.getPairDestination().getPosition().x + 32, teleport.getPairDestination().getPosition().y + 32);
            boolean isClicked = temp.distance(new Point(clickX, clickY)) <= 32;
            System.out.println("X: " + clickX + "Y: " + clickY);
            System.out.println("TeleportX: " + temp.x + "TeleportY: " + temp.y);
            if (isClicked) {
                activeKey = '!';
                TargetTeleport = teleport;
                ready = true;
                break;
            }
        }
    }

    public static GameControl getInstance(){ return gameControl;}

    /**
     * Kotelezoen implementalando fuggvenyek, de ezeket mi nem hasznaljuk
     * @param e GombLenyomas
     */
    @Override
    public void keyTyped (KeyEvent e){}
    @Override
    public void keyReleased (KeyEvent e){}
    @Override
    public void mousePressed (MouseEvent e){}
    @Override
    public void mouseReleased (MouseEvent e){}
    @Override
    public void mouseEntered (MouseEvent e){}
    @Override
    public void mouseExited (MouseEvent e){}

}
