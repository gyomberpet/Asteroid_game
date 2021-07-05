package kakaoscsiga.model.entity;

import kakaoscsiga.model.RoundManager;
import kakaoscsiga.model.Steppable;
import kakaoscsiga.model.TeleportGatePair;
import kakaoscsiga.model.galaxy.Asteroid;
import kakaoscsiga.model.galaxy.Galaxy;
import kakaoscsiga.model.resource.Resource;
import kakaoscsiga.view.drawables.Drawable;
import kakaoscsiga.view.drawables.ViewContainer;

import java.util.List;

/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amely kepes valamilyen cselekvest vegrehajtani
 * @author kakaoscsiga
 */
public abstract class Entity implements Steppable {
    /**
     * A view-t tarolja
     */
    protected Drawable view;
    /**
     * Az Entity aktualis aszteroidajat tarolja.
     */
    protected Asteroid location;
    /**
     * Azt a agalaxys objektumot tarolja, amiben az Entity van
     */
    protected Galaxy galaxy;

    @Override
    public abstract void step();

    /**
     * Entity osztaly konstruktora beallitja az Entity jelenlegi aszteroidajat és a galaxist
     * @param a Asteroida amelyiken tartozkodik
     * @param g A jatek alltal letrehozott galaxis
     */
    public Entity(Asteroid a, Galaxy g){
        location = a;
        if (a != null)
            location.addEntity(this);
        galaxy = g;
        RoundManager.getInstance().addSteppable(this);
    }

    /**
     * A Entity mozgasat valositja meg.
     * @param a Aszteroida amelyikre szeretne mozogni a felhasznalo
     */
    public boolean move(Asteroid a) {
        if (a == null) return false;
        if(location == null) return false;
        location.removeEntity(this);
        a.addEntity(this);
        location = a;

        return true;
    }

    /**
     * Az aszteroida furasat valositja meg.
     * Azt az aszteroidat furja meg amelyiken eppen tartozkodik
     */
    public boolean drill() {
        if(location == null) return false;
        return location.drilled();
    }

    /**
     * Az entity halalat valósítja meg
     * Alapesetben kivonja magat egyes helyekrol, ezt a settler tovabbi fuggvenyhivasokkal boviti
     */
    public void die() {
        if(location == null) return;
        location.removeEntity(this);
        RoundManager.getInstance().removeSteppable(this);
        ViewContainer.getInstance().removeDrawable(view);
    }

    /**
     * A jatekos teleportalasat valositja meg.
     * @param t A teleport kapu amit szeretne hasznalni
     */
    public boolean teleport(TeleportGatePair t) {
        return t.teleport(this);
    }

    /**
     * Getter fuggvény, visszaadja azt az aszteroidat, amelyiken eppen tartozkodik
     * az entitas
     * @return Aszteroida amelyiken tartozkodik az entitas.
     */
    public Asteroid getLocation(){
        return location;
    }

    /**
     * Absztrakt fuggveny az entitas radioaktiv altali felrobbanasat
     * valositja meg
     */
    public abstract void explode();

    /**
     * Getter fuggveny, amely visszadaja az entitas nyersanyagjait
     * Alapesetben null, ezt felulírja a Settler
     * @return null
     */
    public List<Resource> getResources(){
        return null;
    }

    /**
     * Setter fuggveny, ami beallitja az Entity-nek a location attributumat
     * @param a Az atadott Asteroida objektum
     */
    public void setLocation(Asteroid a) {
        this.location = a;
    }

}
