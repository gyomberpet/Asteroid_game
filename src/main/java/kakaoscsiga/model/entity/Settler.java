package kakaoscsiga.model.entity;

import kakaoscsiga.control.GameControl;
import kakaoscsiga.model.Build;
import kakaoscsiga.model.Game;
import kakaoscsiga.model.TeleportGatePair;
import kakaoscsiga.model.galaxy.Asteroid;
import kakaoscsiga.model.galaxy.BillOfResource;
import kakaoscsiga.model.galaxy.Galaxy;
import kakaoscsiga.model.resource.*;
import kakaoscsiga.view.drawables.SettlerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amely arra kepes, mint egy telepes
 * Tud mozogni, furni, teleportalni, epiteni, banyaszni is az objektum
 * @author kakaoscsiga
 */
public class Settler extends Entity implements Build {
    private int id;
    /**
     * A settler-nel van-e soron
     */
    private boolean active = false;
    /**
     * A settler-nel levo nyersanyagok listaja
     */
    private List<Resource> resources = new ArrayList<>();
    /**
     * A settler-nel levo teleportkapuk listaja
     */
    private List<TeleportGatePair> teleportGate = new ArrayList<>();
    /**
     * A settler taskajanak merete, azaz mennyi nyersanyag lehet nala
     */
    private int inventorySize = 10;

    /**
     * Settler konstruktorja beallitja a kezdo aszteroidat, es a galaxist amelyben tartozkodik
     * Meghivja az ososztaly konstruktorjat.
     * @param a Aszteroida amelyen kezdeti allapotban tartozkodik
     * @param g A jatek altal letrehozott galaxis
     */
    public Settler(Asteroid a, Galaxy g, int id) {
        super(a, g);
        this.id = id;
        view = new SettlerView(this);
    }

    /**
     * Feluldefinialt metodus.
     * A jatekos egy korben vegrehajthato cselekveseit valasitja meg
     * Ez csak kesobbi fazisokban lenyeges.
     */
    @Override
    public void step() {
        active = true;
        GameControl.getInstance().control(this);
        active = false;
    }

    /**
     * A settler banyaszasi muveletet valositja meg
     * A settler csak azon az aszteroidan tud banyaszni amelyiken eppen tartozkodik.
     * A settler csak abban az esetben tud banyaszni, ha az aszteroida belseje nem ures, és a retegeinek szama nulla (le van furva)
     */
    public boolean mine() {
        if(location == null) return false;
        Resource res = location.getResource();
        if (res != null && resources.size() < inventorySize){
            if (location.getLayer() == 0){
                location.removeResource();
                resources.add(res);
                return true;
            }
        }
        return false;
    }

    /**
     * A settler a radioaktiv aszteroida altali robbanasat reprezentalja/valositja meg
     */
    @Override
    public void explode() {
        die();
    }

    /**
     * Visszaadja, hogy a settler aktiv-e
     */
    public boolean getActive() {
        return active;
    }

    /**
     * A settler halalat valositja meg
     * Az entity die fuggvenyehez tovabbi fuggvenyhivast ad hozza
     */
    @Override
    public void die(){
        super.die();
        while(teleportGate.size() != 0){
            teleportGate.get(0).destroy();
            teleportGate.remove(teleportGate.get(0));
        }

        galaxy.settlerDied();
    }

    /**
     * A settler teleportkapu lerakasat valositja meg
     * Csak abban az esetben tud a jatekos lerakni egy teleportkaput, ha az illetonel van
     * aktivalhato teleportkapu
     * Ha le tudott helyezni egyet, akkor annak az egyik szabad teleportalhato aszteroidajat beallitja
     * a telepes jelenlegi aszteroidajara.
     */
    public boolean teleportGatePut() {
        if(location == null) return false;
        if(!teleportGate.isEmpty()) {
            TeleportGatePair t = teleportGate.get(0);
            t.setDestination(location);
            location.addTeleportGate(t);
            teleportGate.remove(t);
            return true;
        }
        return false;
    }

    /**
     * A settler robotepitesi metodusat valositja meg
     * Robotot csak akkor tud epiteni, ha a telepesnek rendelkezesere all minden
     * a robot epitesehez szukseges nyersanyag. Ezt a Galaxy billOfResource-ja tarolja, es a checkResources
     * fuggveny ellenorzni a szukseges nyersanyagok megletet.
     *
     * Ha megvan, akkor ezeket a nyersanyagokat levonja.
     */
    @Override
    public Robot buildRobot() {
        if(location == null) return null;
        BillOfResource bill = galaxy.getBillOfResources("Robot");
        List<Resource> resourceNeeded = bill.checkResources(resources);

        if(resourceNeeded != null) {
            resources.retainAll(resourceNeeded);
            Robot robot = new Robot(location, galaxy);
            return robot;
        }
        return null;
    }

    /**
     * A settler teleportkapu epitesi metodusat valositja meg.
     * A robot epiteshez hasonloan itt is a billOfResources tarolja a nyersanyagokat es a checkResources
     * ellenorzi annak megletet.
     * Ha megvan levonja a settler nyersanyagai kozul
     * Ha a settlernel mar van harom teleportkapu akkor nem tud letrehozni ujat.
     * @return A 2 teleportkapu amit megepitett, vagy null, ha nem tudott epiteni
     */
    @Override
    public List<TeleportGatePair> buildTeleportGate() {
        if (teleportGate.size() > 1)    return null;

        BillOfResource bill = galaxy.getBillOfResources("Teleport");
        List<Resource> resourceNeeded = bill.checkResources(resources);

        if (resourceNeeded != null){

            if(resources !=null){
                for(int i= 0; i < resourceNeeded.size(); i++){
                    for(int j=0; j < resources.size() ; j++) {
                        if(resources.get(j).equals(resourceNeeded.get(i))) {
                            resources.remove(j);
                            break;
                        }
                    }
                }
            }


            TeleportGatePair t1 = new TeleportGatePair();
            TeleportGatePair t2 = new TeleportGatePair();

            t1.setPair(t2);
            t2.setPair(t1);

            teleportGate.add(t1);
            teleportGate.add(t2);

            List<TeleportGatePair> tps = new ArrayList<>();
            tps.add(t1);
            tps.add(t2);
            return tps;
        }
        return null;
    }

    /**
     * A settler egyik nyersanyaganak magba vala visszahelyzeset valositja meg.
     * Ez csak akkor lehetseges, ha a kopenye 0 es nincs benne nyersanyag
     * @param r nyersanyag, amit vissza szeretne helyezni a jatekos
     */
    public boolean dropResource(Resource r) {
        if (location == null) return false;

        if (location.getLayer() == 0 && location.getResource() == null) {
            location.addResource(r);
            resources.remove(r);
            return true;
        }
        return false;
    }

    /**
     * Getter fuggveny. Visszaadja a Jatekos nyersanyagait.
     * @return A jatekos nyersanygait tarolo lista
     */
    @Override
    public List<Resource> getResources(){
        return resources;
    }

    /**
     * A settler bazis epitesi metodusat valositja meg
     * A robot epiteshez hasonloan itt is a billOfResources tarolja a nyersanyagokat es a checkResources ellenorzi
     * annak megletet.
     * Ha megvan meghivja az endGame-t es nyertek.
     */
    @Override
    public boolean buildBase() {
        if(location == null) return false;

        BillOfResource bill = galaxy.getBillOfResources("Base");
        List<Resource> allResources = new ArrayList<Resource>();
        for(Entity e : location.getEntities()) {
            if(e.getResources()!=null)
                allResources.addAll(e.getResources());
        }
        List<Resource> resourceNeeded = bill.checkResources(allResources);

        if(resourceNeeded != null) {
            Game.getInstance().endGame("Win!");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Settler";
    }

    /**
     * Az adott settler azonositoja
     * @return id-val ter vissza
     */
    public int getId(){
        return id;
    }

    public List<TeleportGatePair> getTeleportGate() {
        return teleportGate;
    }
}
