package kakaoscsiga.model.resource;

import kakaoscsiga.model.galaxy.Asteroid;
/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amelyet Szen nyersanyagnak hivunk
 * @author kakaoscsiga
 */
public class Coal implements Resource {

    /**
     * A napkozeli viselkedeset irja le a fuggveny, a Coal nem csinal ilyenkor semmit
     * @param a Aszteroida amely napkozelben van
     */
    @Override
    public void coreInDanger(Asteroid a) {}

    /**
     * Ha a kapott nyersanyag megegyzeik a Coal-val akkor igazzal ter vissza, ellenkezo esetben hamis lesz az allitas
     */
    @Override
    public boolean equals(Resource r) {
        return r instanceof Coal;
    }

    public String toString(){
        return "Coal";
    }
}
