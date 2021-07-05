package kakaoscsiga.model.entity;

import kakaoscsiga.model.galaxy.Asteroid;
import kakaoscsiga.model.galaxy.Galaxy;
import kakaoscsiga.view.drawables.RobotView;

import java.util.List;
import java.util.Random;

/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amely arra kepes, mint egy robot
 * Tud mozogni, furni, teleportalni is az objektum
 * @author kakaoscsiga
 */
public class Robot extends Entity {
    /**
     * A Robot osztaly konstruktora
     * @param a Beallitja a robot location-jet erre az aszteroidara.
     * @param g Beallitja a Robotnak a galaxys attributumat erre az atadott ertekre
     */
    public Robot(Asteroid a, Galaxy g){
        super(a, g);
        view = new RobotView(this);
    }


    @Override
    public void step() {
        if(location == null) return;
        if(drill())
            return;
        Random r = new Random();
        int size = location.getNeighbours().size();
        int teleportSize = location.getTeleport().size();
        if(teleportSize != 0){
            teleport(location.getTeleport().get(r.nextInt(teleportSize)));
            return;
        }
        if(size != 0)
            move(location.getNeighbours().get(r.nextInt(size)));
    }

    /**
     * Ha egy aszteroid felrobban és azon egy robot tartozkodik, akkor a felrobbant aszteroid a szomszedai kozul lesz egy valasztva
     * a random generator segitsegevel, amire at lesz helyezve a robot.
     */
    @Override
    public void explode() {
        if(location == null) return;
        List<Asteroid> asteroids = location.getNeighbours();
        Random rand = new Random();
        if (asteroids.size() > 0) {
            int number = rand.nextInt(asteroids.size());
            move(asteroids.get(number));
        }
    }

    @Override
    public String toString() {
        return "Robot";
    }
}
