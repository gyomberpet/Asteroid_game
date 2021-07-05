package kakaoscsiga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Az Aszteroidabanyaszat nevu jatekban a Steppable objektumok egymas utani lepteteseert felelos
 * SINGLETON
 * @author kakaoscsiga
 */
public class RoundManager implements Runnable{
    /**
     * RoundManager letrehozasa
     */
    private static RoundManager roundManager = new RoundManager();
    /**
     * Az aktualis kort tarolo attributum
     */
    private int actualRound;
    /**
     * Cselekvesre kepes elemek listaja
     */
    private List<Steppable> steppables;
    /**
     * A jatek fut-e vagy sem
     */
    private boolean isRunning = true;

    public int getActualRound(){return actualRound;}

    /**
     * A roundManager konstruktora.
     * Itt szamoljuk azt, hogy a jatek kezdete ota hany kor telt el, ez az elejen ertelemszeruen nullara van allitva,
     * valamint kap egy listat, ahol azokat a szereploket veheti fel, akkik kepesek a mozgasra
     */
    private RoundManager(){
        actualRound = 0;
        steppables = new ArrayList<>();
    }

    /**
     * Eltavolit egy mozgasra kepes szereplot a listajabol, ez lehet azert, mert meghalt az adott entity
     * @param s egy steppable objektum. Jatekban cselekvo kebes egyed.
     * @return sikeres torlesnel igazzal ter vissza, ellenkezo esetben hamissal.
     */
    public boolean removeSteppable(Steppable s){
        if (steppables.contains(s)){
            steppables.remove(s);
            return true;
        }
        return false;
    }

    /**
     * Hozzaad egy cselekvere kepes entity-t a listajahoz
     * @param s steppable objektum. Jatekban cselekvesre kepes egyed.
     */
    public void addSteppable(Steppable s){
        steppables.add(s);
    }

    /**
     * Ez az a fuggveny ami lehetoseget ad a jatekosnak cselekedni/lepni a jatekban ez korkorosen megy, akinel eppen
     * van az tud cselekedni.
     */
    @Override
    public void run(){
        int i = 0;
        actualRound = 0;
        while(isRunning){
            if (i == 0)
                actualRound++;

            if(steppables != null) {
                steppables.get(i).step();
                if(steppables.size() !=0)
                    i = (i + 1) % steppables.size();
                /*
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {}
                */
            }
        }
    }

    /**
     * Visszaad egy peldanyt a RoundManager-bol
     * @return A RoundManager
     */
    public static RoundManager getInstance(){
        return roundManager;
    }

    /**
     * A jatek ideje alatt igaz lesz, ez reprezentalja azt, hogy fut a jatek
     * @param b jatek futasat reprezentalja
     */
    public void setIsRunning(boolean b){
        isRunning = b;
    }

    public void setSteppables(List<Steppable> newSteppables){ steppables = newSteppables;}

    public void clear(){
        steppables.clear();
    }
}
