package kakaoscsiga.view.drawables;

import kakaoscsiga.model.Game;
import kakaoscsiga.model.RoundManager;
import kakaoscsiga.model.entity.Settler;
import kakaoscsiga.model.resource.Uranium;
import kakaoscsiga.view.panels.LoadImages;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Maganak a telepesnek az adott pozicióba torteno megjeleniteset,
 * kirajzolasat teszi lehetove.
 */
public class SettlerView extends Drawable {
    /**
     * Az Settler, amihez ez a view tartozik
     */
    private Settler settler;

    /**
     * Kontruktor
     * @param settler Az a Settler, amihez ez a view tartozik
     */
    public SettlerView(Settler settler) {
        super("Settler");
        this.settler = settler;
    }

    /**
     * Az adott telepes poziciojanak megfeleloen kirajzol egy telepest
     * @param g A rajzolashoz szukseges Graphics
     */
    @Override
    public void draw(Graphics g) {
        int asteroidX = settler.getLocation().getAsteroidView().position.x;
        int asteroidY = settler.getLocation().getAsteroidView().position.y;
        if(settler.getActive()){
            BufferedImage marker = LoadImages.images.get("Marker");
            g.drawImage(marker, asteroidX + 12, asteroidY - 56, null);
            writeSideNav();
        }
        BufferedImage image = LoadImages.images.get(imageName);
        g.drawImage(image, asteroidX + 19, asteroidY - 32, null);

    }

    /**
     * Kiirja a GamePanel oldal savjaba a settler informacioit
     * Illetve megjelenit egy listat a parancsok hasznalatarol
     * Az egyes epiteshez szukseges nyersanyagokat is kiirja mintegy segitsegnek
     * a felhasznalo szamara
     */
    public void writeSideNav(){
        /**
         * Aktualis telepes
         */
        String text = "\tSettler " + settler.getId() + "\n\n";

        /**
         * Aktualis kornek a szama a jatekban
         */
        text+="Round: "+ RoundManager.getInstance().getActualRound();

        /**
         * Adott telepesnel levo dolgok
         */
        text+= "\nInventory:\n";
        for(int i =0; i < settler.getResources().size(); i++){
            if(settler.getResources().get(i).equals(new Uranium()))
                text+="\t" + settler.getResources().get(i).toString() +" (time: "+ ((Uranium)settler.getResources().get(i)).getTimeToExplode() + ")";
            else
                text+="\t" + settler.getResources().get(i).toString();
            text +="\n";
        }
        for(int i= 0; i< settler.getTeleportGate().size(); i++) {
            text+="\tTeleportGate";
            text +="\n";
        }

        /**
         * Asteroida adatai
         */
        text+="\nAsteroid:\n";
        text+="\tLayer: " + settler.getLocation().getLayer() + "\n";
        if(settler.getLocation().getResource() != null)
            text+="\tRes: " + settler.getLocation().getResource().toString() + "\n";
        else
            text+="\tRes: null\n";
        text+="\tClose: " + settler.getLocation().getCloseToSun();

        /**
         * Segitseg a muveletek elvegzesehez
         */
        text+="\n\nPossible Commands:\n";
        text+="Move:\t\tClick\n";
        text+="Teleport:\t\tClick\n";
        text+="Drill:\t\td\n";
        text+="Mine:\t\tm\n";
        text+="Put resource:\t0-9\n";
        text+="Put teleport:\to\n";
        text+="Build Robot:\tr\n";
        text+="Build Teleport:\tt\n";
        text+="Build Base:\tb\n";

        /**
         * Receptek
         */
        text+="\n";
        text+="Teleport:";
        text+="\t2 Iron\n";
        text+="\t1 Waterice\n";
        text+="\t1 Uranium\n";

        text+="Robot:";
        text+="\t1 Iron\n";
        text+="\t1 Coal\n";
        text+="\t1 Uranium\n";

        text+="Base:";
        text+="\tEverything!\n";

        Game.getInstance().getGameFrame().getGamePanel().getSideNav().setText(text);
    }

}
