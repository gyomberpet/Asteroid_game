package kakaoscsiga.model.galaxy;

import kakaoscsiga.model.Game;
import kakaoscsiga.model.resource.*;
import kakaoscsiga.view.drawables.Point;

import java.util.*;

/**
 * Az Aszteroidabanyaszat nevu jatekban a galaxyst reprezentalja, amiben minden objektum van
 * @author kakaoscsiga
 */
public class Galaxy {
    /**
     * Galaxy-ban talalhato aszteroidak listaja
     */
    private List<Asteroid> asteroids;
    /**
     * Galaxy-ban talalhato nap
     */
    private Sun sun;
    /**
     * Telepesek szama a Galaxy-ban, jatekban
     */
    private int settlerCount;
    /**
     * Megadja azt, hogy egy adott elem epitesehez milyen nyersanyagok szuksegesek
     */
    private HashMap<String, BillOfResource> bills;
    
    /**
     * Letrehozza a galaxist amiben a jatek zajlani fog
     * Beallitja a galaxisban levo telepesek szamat
     * Beallitja az epiteshez szukseges BillOfResource-okat (Robot, Teleport, Base)
     * Letrehozza az aszteroidakat, és beallítja a szomszedsagukat
     * Letrehozza a Napot ami a galaxisban van
     * @param settlerCount A galaxisban levo telepesek szama
     */
    public Galaxy(int settlerCount){
        this.settlerCount = settlerCount;
        bills = new HashMap<>();

        createBill("Robot");
        createBill("Teleport");
        createBill("Base");

        asteroids = new ArrayList<Asteroid>();
        sun = new Sun(this);
        init();
        /**
         * Kiszamolja, hogy melyik asteroida melyiknek a szomszedja
         */
        for(Asteroid a: asteroids){
            for(Asteroid b: asteroids){
                if (a != b && !a.getNeighbours().contains(b))
                    a.calcNeighbour(b);
            }
            a.setDistanceFrom(sun);
        }
    }

    /**
     * Hozzaad egy uj elemet a bills-hez
     * @param s A kulcs, amihez a receptet keressuk
     * @param resources A kulcshoz tartozo BillOfResource (recept)
     */
    private void addRecipeToBills(String s, List<Resource> resources){
        bills.put(s, new BillOfResource(resources));
    }

    public void init(){
        Random rand = new Random();
        for (int i = 50; i<1100; i+=120 ){
            for(int j=80; j<700; j+=120){
                Resource res;
                int resType = rand.nextInt(5);
                switch(resType){
                    case 0: res = new Iron();
                        break;
                    case 1: res = null;
                        break;
                    case 2: res = new Uranium();
                        break;
                    case 3: res = new Waterice();
                        break;
                    default:
                        res = new Coal();
                        break;
                }
                int plusz = new Random().nextInt(2);
                int r = new Random().nextInt(30);
                Point p;
                switch(plusz) {
                    case 0: p = new Point(i+r,j+r);
                    break;
                    case 1: p = new Point(i-r, j-r);
                    break;
                    default: p = new Point(i-r,j+r);
                }

                Asteroid a = new Asteroid(rand.nextInt(5)+1, res, false, this, p);
                asteroids.add(a);
            }
        }
    }

    /**
     * Torli az adott aszteroidat a galaxisbol
     * @param a A torlendo aszteroida
     */
    public void removeAsteroid(Asteroid a){
        asteroids.remove(a);
    }
    /**
     * Hozzaadja az adott aszteroidat a galaxishoz
     * @param a A hozzaadando aszteroida
     */
    public void addAsteroid(Asteroid a){
        asteroids.add(a);   
    }
    
    /**
     * Csokkenti a settler-ek szamat
     * Ha az aszteroidak szama 2-nel kisebbre csökken -> endgame()
     */
    public void settlerDied() {
        settlerCount--;
        if(settlerCount <= 1){
            Game.getInstance().endGame("Lose!");
        }
    }
    
    /**
     * Ertesiti az osszes aszteroidat, hogy mozgott a Nap
     */
    public void sunMoved(){

        for (int i = 0; i<asteroids.size(); i++){
            if (asteroids.get(i) != null)
                asteroids.get(i).setDistanceFrom(sun);
        }
    }

    /**
     * Visszaadja a galaxisban levo aszteroidakat
     * @return Az aszteroidak listaja
     */
    public List<Asteroid> getAsteroids(){
        return asteroids;
    }

    /**
     * Visszaadja azt az aszteroidat, ahonnan a settler-ek indulnak
     * @return Kezdo aszteroida
     */
    public Asteroid getStarterAsteroid(){
         if(asteroids.size()>0)
            return asteroids.get(0);
        return null;
    }

    /**
     * Visszaadja az adott nevhez tartozo BiLLOfResource-ot (pl.: Robot epites szukseges nyersanyagok listaja)
     * @param recipe A kivant recept kulcsa
     * @return A kulcshoz tartozo BillOfResource
     */
    public BillOfResource getBillOfResources(String recipe){
        return bills.get(recipe);
    }

    public int getSettlerCount(){return  settlerCount;}

    /**
     * A napkozeli aszteroidakat gyujti ki az aszteroida listabol,
     * amikor a nap mozog akkor meghivodik, hogy az aszteroidakat ellenorizze kozel vannak-e a naphoz
     * @return Napkolezi aszteroidak listaja
     */
    public List<Asteroid> getCloseAsteroids(){
        List<Asteroid> closeAsteroids = new ArrayList<Asteroid>();
        for (int i = 0; i < asteroids.size(); ++i) {
            if(asteroids.get(i).getCloseToSun()){
                closeAsteroids.add(asteroids.get(i));
            }
        }
        return closeAsteroids;
    }

    /**
     * Hozzaad az epitheto elemek listajahoz es ahhoz milyen "recept" tartozik
     * @param type amit hozza kell az epitheto elemek listajahoz
     */
    private void createBill(String type){
        List<Resource> billResources;
        switch(type){
            case("Robot"):
                billResources = Arrays.asList(new Iron(), new Coal(), new Uranium());
                break;
            case("Teleport"):
                billResources = Arrays.asList(new Iron(), new Iron(), new Waterice(), new Uranium());
                break;
            case("Base"):
                billResources = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    billResources.add(new Iron());
                    billResources.add(new Coal());
                    billResources.add(new Waterice());
                    billResources.add(new Uranium());
                }
                break;
            default:
                billResources = null;
                break;
        }
        if(billResources != null)
            addRecipeToBills(type, billResources);
    }
}
