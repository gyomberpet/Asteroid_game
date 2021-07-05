package kakaoscsiga.model.resource;

import kakaoscsiga.model.galaxy.Asteroid;
/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amelyet Vas nyersanyagnak hivunk
 * @author kakaoscsiga
 */
public class Iron implements Resource {

    /**
     * Ha a kapott nyersanyag megegyzeik a ironnal akkor igazzal ter vissza, ellenkező esetben hamis lesz az allitas
     */
    @Override
    public boolean equals(Resource r) {
        return r instanceof Iron;
    }

    /**
     * A napkozeli viselkedeset irja le a fuggveny, a Coal nem csinal ilyenkor semmit
     * @param a Aszteroida amely napkozelben van
     */
    @Override
    public void coreInDanger(Asteroid a) {
    }

    public String toString(){
        return "Iron";
    }
}
