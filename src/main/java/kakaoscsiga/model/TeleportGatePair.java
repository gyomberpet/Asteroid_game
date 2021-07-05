package kakaoscsiga.model;

import kakaoscsiga.model.galaxy.Asteroid;
import kakaoscsiga.model.entity.Entity;
import kakaoscsiga.view.drawables.TeleportView;

import java.util.List;
import java.util.Random;
/**
 * Az Aszteroidabanyaszat nevu jatekban a teleport kapukat reprezentalja
 * @author kakaoscsiga
 */
public class TeleportGatePair implements Steppable {
	/**
	 * A teleportkapu helyet tarolo attributum
	 */
	private Asteroid destination = null;
	/**
	 * Ez az attributum tarolja, hogy megkergult-e a nap vagy sem
	 */
	private boolean crazy = false;
	/**
	 * A teleportkapu parjat tarolo attributum
	 */
	private TeleportGatePair pair;
	/**
	 * A TeleportGatePair view-jat tarolo attributum
	 */
	private TeleportView view;

	public TeleportGatePair(){
		RoundManager.getInstance().addSteppable(this);
		view = new TeleportView(this);
	}

	public void setPair(TeleportGatePair pair){
		this.pair = pair;
	}

	/**
	 * A teleportalast valositja meg a jatkeban, ha a teleport kapu parja le van teve
	 * egy masik aszteroidara, akkor lehetseges a teleportalas
	 * @param e a jatekban egy karakter
	 */
	public boolean teleport(Entity e){
		Asteroid dest = pair.destination;
		if (dest != null){
			e.move(dest);
			return true;
		}
		return false;
	}

	/**
	 * A teleportkapu felrobbanasat valositja meg.
	 */
	public void destroy() {
		if (RoundManager.getInstance().removeSteppable(this)){
			if (destination != null){
				destination.removeTeleportGate(this);
				destination = null;
			}
			pair.destroy();
		}
	}
	/**
	* A teleportkapu minden korben, ha meg van kergulve, atmozog egy masik, szomszedos aszteroidara
	*/
	@Override
	public void step() {
		Random rand = new Random();
		if (crazy && destination != null){
			List<Asteroid> neighbors = destination.getNeighbours();
			int newDestIdx = rand.nextInt(neighbors.size());

			destination.removeTeleportGate(this);
			neighbors.get(newDestIdx).addTeleportGate(this);
			destination = neighbors.get(newDestIdx);
		}
	}

	/**
	 * Beallitja az atadott aszteroidat a teleportalhato aszteroidak listajaba.
	 * @param a Aszteroida ahova leraktak
	 */
	public void setDestination(Asteroid a) {
		if (destination != null) {
			return;
		}
		destination = a;
	}

	public Asteroid getDestination () {
			return destination;
	}
	public Asteroid getPairDestination(){ return pair.destination; }
	public void setCrazy ( boolean crazy) {
			this.crazy = crazy;
	}
}