package kakaoscsiga.model.entity;

import kakaoscsiga.model.galaxy.Asteroid;
import kakaoscsiga.model.galaxy.Galaxy;
import kakaoscsiga.model.resource.Resource;
import kakaoscsiga.view.drawables.UFOView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amely arra kepes, mint egy foldonkivuli
 * Tud mozogni, teleportalni, banyaszni is az objektum
 * @author kakaoscsiga
 */
public class UFO extends Entity{
    /**
     * Az ufo-nal levo nyersanyagok listaja
     */
    private List<Resource> resources = new ArrayList<>();

    /**
     * UFO osztaly konstruktora beallitja az UFO jelenlegi aszteroidajat és a galaxist
     *
     * @param a Asteroida amelyiken tartozkodik
     * @param g A jatek alltal letrehozott galaxis
     */
    public UFO(Asteroid a, Galaxy g) {
        super(a, g);
        view = new UFOView(this);
    }

    /**
     * Ha el tudja lopni a nyersanyagot, akkor azt teszi
     * Kulonben egy szomszedos aszteroidara atmegy
     */
    @Override
    public void step() {
        if(location == null) return;
        if(location.getLayer() <= 0 && location.getResource() != null){
            mine();
        } else {
            Random r = new Random();
            int size = location.getNeighbours().size();
            if(size == 0) return;
            int next = r.nextInt(size);
            move(location.getNeighbours().get(next));
        }
    }

    /**
     * feluldefinialt metodus
     * Az UFO a radioaktív aszteroida altali robbanasat reprezentalja/valositja meg
     */
    @Override
    public void explode() {
        die();
    }

    /**
     * Az UFO banyaszasi muveletet valositja meg, azaz ellopja az aszteroida nyersanyagjat
     * Az UFO csak azon az aszteroidan tud banyaszni amelyiken eppen tartozkodik.
     * Az UFO csak abban az esetben tud banyaszni, ha az aszteroida belseje nem ures, es a retegeinek szama nulla (le van furva)
     */
    public void mine() {
        if(location == null) return;
        Resource res = location.getResource();
        if (res != null){
            if (location.getLayer() == 0){
                location.removeResource();
                resources.add(res);
            }
        }
    }

    /**
     * Az UFO nem kepes furni, felulirja az Entity furasat, igy ez ures
     */
    @Override
    public boolean drill() { return false;}
}
