package kakaoscsiga.model.resource;

import kakaoscsiga.model.galaxy.Asteroid;

/**
 * Az Aszteroidabanyaszat nevu jatekban minden olyan objektumot reprezental, amelyet Uran nyersanyagnak hivunk
 * @author kakaoscsiga
 */
public class Uranium extends Radioactive {
    /**
     * Az uran expozicios ideje, azaz, hogy mikor robban fel
     */
    private int timeToExplode;

    /**
     * Az osztaly konstruktora, beallitja az expozicios idejet 3-ra
     */
    public Uranium(){
        super();
        timeToExplode = 3;
    }

    /**
     * Ha a kapott nyersanyag megegyzeik a uraniummal, akkor igazzal tér vissza, ellenkezo esetben hamis lesz az allitas
     * @param r A nyersanyag, amivel osszehasonlitjuk
     * @return Igaz, ha ugyanaz a 2 nyersanyag
     */
    @Override
    public boolean equals(Resource r) {
        return r instanceof Uranium;
    }

    public String toString(){
        return "Uranium";
    }

    /**
    * Azt az esetet valositja meg, amikor a nyersanyag veszelybe kerul
    * (Pl.: amikor az aszteroidanak 1 vagy 0 retege van és napkozelben van).
     * Ebben az esetben felrobban, ha a timeToExplode eleri a 0-t, egyeb esetben csak csokkenti azt
     * @param a A napkozeli aszteroida
    */
    @Override
    public void coreInDanger(Asteroid a) {
        timeToExplode--;
        if (timeToExplode == 0)
            super.coreInDanger(a);
    }
    public int getTimeToExplode(){return timeToExplode;}
}
