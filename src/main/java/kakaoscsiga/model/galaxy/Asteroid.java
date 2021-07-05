package kakaoscsiga.model.galaxy;

import kakaoscsiga.model.TeleportGatePair;
import kakaoscsiga.model.entity.Entity;
import kakaoscsiga.model.resource.Resource;
import kakaoscsiga.view.drawables.AsteroidView;
import kakaoscsiga.view.drawables.Point;
import kakaoscsiga.view.drawables.ViewContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amely arra kepes mint egy aszteroida
 * @author kakaoscsiga
 */
public class Asteroid {
	/**
	 * Asteroid retegeinek a szamat tarolo attributum
	 */
	private int layer;
	/**
	 * Az aszteroida naphoz valo kozelseget adja meg igazzal vagy hamissal
	 */
	private boolean closeToSun;
	/**
	 * Aszteroidan tartozkodo entitasok listaja
	 */
	private List<Entity> entities = new ArrayList<>();
	/**
	 * Egy aszteroid a szomszedainak a listaja
	 */
	private List<Asteroid> neighbours = new ArrayList<>();
	/**
	 * Az aszteroidan talalhato teleportkapuk listaja
	 */
	private List<TeleportGatePair> teleport = new ArrayList<>();
	/**
	 * Az aszteroida magjaban talalhato nyersanyagot tarolo attributum
	 */
	private Resource resource;
	/**
	 * Az aszteroidat tartalmazo galaxy
	 */
	private Galaxy galaxy;
	/**
	 * Az aszteroida view-jat tartalmazo attributum
	 */
	private AsteroidView asteroidView;
	/**
	 * Constructor
	 * @param layer Az aszteroida kergenek a vastagsaga
	 * @param res	Az aszteroida nyersanyaga (null, ha ures)
	 * @param cTS	Jelzi, hogy az aszteroida napkozelben van-e
	 * @param galaxy	A galaxis, amiben az aszteroida van
	 */
	public Asteroid( int layer, Resource res, boolean cTS, Galaxy galaxy, Point p) {
		this.setLayer(layer);
		this.setCloseToSun(cTS);
		this.galaxy = galaxy;
		resource = res;
		asteroidView = new AsteroidView(p,this);
	}
	/**
	 * A retegek szamat 1-el csokkenti
	 * Ha a retegek szama 1-nel kisebb, nem csokkent
	 */
	public void removeOneLayer() {
		if(layer > 0)
			layer--;
	}
	/**
	 * Hozzaadja a parameterul kapott entitast az aszteroidahoz
	 * @param e	A hozzaadni kivant entitas
	 */
	public void addEntity(Entity e) {
		entities.add(e);
	}

	/**
	 * Eltavolitja a parameterul kapott entitast az aszteroidarol
	 * @param e	Az eltavolitando entitas
	 */
	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	/**
	 * Hozzaadja a parameterul kapott nyersanyagot az aszteroidahoz, amennyiben az ures
	 * Ha napkozelben van es a keregvastagsag 0, meghivja a coreInDanger()-t
	 * @param res A hozzaadni kivant nyersanyag
	 */
	public void addResource(Resource res) {
		if(this.resource != null)
			return;
		this.resource = res;
		asteroidView.resourceChanged(resource);
		if(closeToSun && layer == 0)
			resource.coreInDanger(this);
	}

	/**
	 * Eltavolitja a nyersanyagot a magbol
	 */
	public void removeResource() {
		this.resource = null;
		asteroidView.resourceChanged(resource);
	}

	/**
	 * A parameterul kapott aszteroidat beallitja szomszedosnak
	 * @param a Az aszteroida, amit szomszednak allit be
	 */
	public void addNeighbour(Asteroid a) {
		neighbours.add(a);
	}

	/**
	 * Eltavolitja a kapott aszteroidat a szomszedai kozul
	 * @param a Az eltavolitando aszteroida
	 */
	public void removeNeighbour(Asteroid a) {
		neighbours.remove(a);
	}

	/**
	 * Hozzaadja a kapott teleportkaput az aszteroidahoz
	 * @param t A hozzaadando teleportkapu
	 */
	public void addTeleportGate(TeleportGatePair t) {
		teleport.add(t);
	}
	/**
	 * Eltavolitja a kapott teleportkaput az aszteroidarol
	 * @param t Az eltavolitando teleportkapu
	 */
	public void removeTeleportGate(TeleportGatePair t){
		teleport.remove(t);
	}

	/**
	 *  Beallitja a naptol valo tavolsagot
	 * @param s A Nap, ami alapjan a tavolsag beallithato
	 */
	public void setDistanceFrom(Sun s) {

		if (layer == 0 && closeToSun && resource != null){

			resource.coreInDanger(this);
		}

		closeToSun = (s.getPosition().distance(this.getPosition()) <= 400);
	}

	/**
	 * Megnezi, hogy a kapott aszteroida szomszedosnak szamit-e (a koztuk levo tavolsag alapjan)
	 * Ha igen, beallitja szomszedosnak
	 * @param a kapott aszteroida
	 */
	public void calcNeighbour(Asteroid a) {
		if (a.getPosition().distance(this.getPosition()) <= 200){
			addNeighbour(a);
		}
	}

	/**
	 * Ha a mag nem ures, vaga a keregvastagság nem 0, megoli az osszes entitast az aszteroidan
	 */
	public void burn() {
		if(resource != null || layer != 0) {
			List<Entity> entitiesCopy= new ArrayList<>(entities);
			for (int i = 0; i< entitiesCopy.size(); i++)
				if (closeToSun)
					entitiesCopy.get(i).die();
		}
		for (TeleportGatePair t : teleport){
			t.setCrazy(true);
		}
	}

	/**
	 * Az aszteroida kerget 1-el csokkenti, ha a keregvastagsag nagyobb mint 0
	 * Ha a keregvastagsag pontosan 1 es asz aszteroida napkozelben van, akkor a coreInDanger() is meghivodik
	 */
	public boolean drilled(){
		if(layer == 0)
			return false;
		else if(layer > 1) {
			this.removeOneLayer();
		}
		else if(layer == 1) {
			this.removeOneLayer();
			if(this.closeToSun == true && resource!=null)
				resource.coreInDanger(this);
		}
		return true;
	}

	/**
	 * Minden entitasnak meghivja az explode()-jat
	 * Minden teleportkapunak meghivja adestroy()-aszteroidat
	 * Minden szomszedjatol eltavolitja magat
	 */
	public void explode(){
		List<Entity> entitiesCopy= new ArrayList<>(entities);
		for (int i = 0; i< entitiesCopy.size(); i++)
			entitiesCopy.get(i).explode();
		List<TeleportGatePair> teleportCopy= new ArrayList<>(teleport);
		for(int i = 0; i < teleportCopy.size(); i++)
			teleportCopy.get(i).destroy();
		for(int i = 0; i < neighbours.size(); i++)
			neighbours.get(i).removeNeighbour(this);

		ViewContainer.getInstance().removeDrawable(asteroidView);
		galaxy.removeAsteroid(this);
	}

	/**
	 * További getter-ek és setter-ek
	 */

	public int getLayer() { return layer; }
	public void setLayer(int layer) { this.layer = layer; }

	public boolean getCloseToSun() { return closeToSun; }
	public void setCloseToSun(boolean closeToSun) { this.closeToSun = closeToSun; }

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Asteroid> getNeighbours() {
		return neighbours;
	}

	public List<TeleportGatePair> getTeleport() {
		return teleport;
	}

	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Point getPosition(){
		return asteroidView.getPosition();
	}

	public AsteroidView getAsteroidView() {
		return asteroidView;
	}
}
