package kakaoscsiga.model.resource;

import kakaoscsiga.model.galaxy.Asteroid;

/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amelyet a Radioaktiv nyersanyagok csoportjaba tartozik
 * @author kakaoscsiga
 */
public abstract class Radioactive implements Resource {
    /**
     * Ez a fuggveny robbantja fel a radiokativ maggal rendelkezo aszteroidat, ez abban az esetben valosul meg, ha az adott
     * aszteroida magjaig le van furva és napkozelben van.
     */
    @Override
    public void coreInDanger(Asteroid a){
        a.explode();
    }

    @Override
    public abstract boolean equals(Resource r);
}
