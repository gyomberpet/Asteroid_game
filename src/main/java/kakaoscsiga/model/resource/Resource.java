package kakaoscsiga.model.resource;

import kakaoscsiga.model.galaxy.Asteroid;

/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amelyet Nyersanyagnak hivunk
 * @author kakaoscsiga
 */
public interface Resource {

    /**
     * absztrakt metodus, akkor hivodik meg ha az aszteroida napkozelben van
     * @param a Aszteroida amely napkozelben van
     */
    void coreInDanger(Asteroid a);

    /**
     * absztrakt metodus
     * ellenorzi, hogy az atadott nyersanyag megegyezik e az adott nyersanyaggal.
     * @param r az ellenorzesre varo nyersanyag
     * @return igaz v. hamis, attól fuggoen, hogy megegyezik e a 2 nyersanyag.
     */
    boolean equals(Resource r);
}
