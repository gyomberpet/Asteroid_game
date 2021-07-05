package kakaoscsiga.model.galaxy;

import kakaoscsiga.model.resource.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Az Aszteroidabanyaszat nevu jatekban a megepitheto dolgok receptjet irja le
 * @author kakaoscsiga
 */
public class BillOfResource {
    /**
     * A nyersanyagok listaja
     */
    private List<Resource> resources;

    /**
     * Constructor
     * @param resources Az epiteshez szukseges nyersanyagok listaja
     */
    public BillOfResource(List<Resource> resources){
        this.resources = resources;
    }

    /**
     * Leellenori, hogy a kapott nyersanyag lista tartalmazza-e a megepiteshez szukseges nyersanyagokat
     * Ha igen, visszaadja az epiteshez szukseges nyersanyagok listajat
     * Ha nem, null-t ad vissza
     * @param have Az epiteshez rendelkezesre allo nyersanyagok
     * @return Az epiteshez szukseges nyersanyagok listaja, vagy null
     */
    public List<Resource> checkResources(List<Resource> have) {
        List<Resource> removeFrom = new ArrayList<>(have);
        int resCount = 0;
        for(Resource need : resources){
            for(Resource r : removeFrom){
                if(need.equals(r)){
                    removeFrom.remove(r);
                    resCount++;
                    break;
                }
            }
        }
        if (resCount == resources.size())
            return  resources;
        return null;
    }
}