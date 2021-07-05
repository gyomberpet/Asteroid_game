package kakaoscsiga.model.resource;

import kakaoscsiga.model.galaxy.Asteroid;

/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amelyet Vizjeg nyersanyagnak hivunk
 * @author kakaoscsiga
 */
public class Waterice implements Resource {
    /**
     * Az osztaly konstruktora
     */
    public Waterice(){
        super();
    }

    /**
     * Ha a kapott nyersanyag megegyzeik a urániummal, akkor igazzal ter vissza, ellenkezo esetben hamis lesz az allitas
     * @param r A nyersanyag, amivel osszehasonlitjuk
     * @return Igaz, ha ugyanaz a 2 nyersanyag
     */
    @Override
    public boolean equals(Resource r) {
        return r instanceof Waterice;
    }

    /**
     * Ha az aszteroidba, aminek a magjaban waterice van, napkozelben van és lefurtaka a magjahoz, akkor az elparolog
     * tehát kivesszuk a nyersanyagot a magbol.
     * @param a A napkozeli aszteroida
     */
    @Override
    public void coreInDanger(Asteroid a) {
        a.removeResource();
    }

    public String toString(){
        return "WaterIce";
    }

}