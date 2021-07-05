package kakaoscsiga.view.panels;

import kakaoscsiga.model.RoundManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Az Aszteroidabanyaszat nevu jatekban ez az osztaly valositja meg a jatek frame szerepet
 * Ez maga az ablak, amin megjelenik a jatek
 * Ezen lesz elhelyezve kezdetben a Menu, majd a game panel
 * @author kakaoscsiga
 */
public class GameFrame extends JFrame{
    /**
     * Az ablak szelessege
     */
    public static final int WIDTH = 1200;
    /**
     * Az ablak magassaga
     */
    public static final int HEIGHT = 800;
    /**
     * Az ablak cime
     */
    private String title = "Asteroid Game";
    /**
     * A GamePanel objektum, ami a jatek megjeleniteseert felelos
     */
    private GamePanel gamePanel;
    /**
     * A Menu objektum, ami a menu megjeleniteseert felelos
     */
    private Menu menu;

    /**
     * Az osztaly konstruktora, letrehozza az ablakot a megfelelo magassag-szelesseg parameterekkel
     * Es felhelyezi eloszor a menu-t az ablakra
     */
    public GameFrame() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setTitle(title);
        setResizable(false);
        setIconImage(LoadImages.images.get("Settler"));

        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent windowEvent){
                RoundManager.getInstance().setIsRunning(false);

                System.exit(0);
            }
        });
        initMenu();
    }

    /**
     * A GamePanel-t rakja fel a ablakra
     */
    public void initGamePanel(){
        remove(menu);
        gamePanel = new GamePanel();
        add(gamePanel);
        gamePanel.grabFocus();
        pack();
    }
    /**
     * A Menu-t rakja fel a ablakra
     */
    public void initMenu(){
        if(gamePanel!=null) {
            remove(gamePanel);
        }
        if(menu==null) {
            menu = new Menu(this);
        }
        else  {
            menu.init();
        }
        add(menu);
        menu.grabFocus();
        pack();
    }

    public void run() {
        setVisible(true);
    }

    public GamePanel getGamePanel(){
        return gamePanel;
    }

    public Menu getMenu(){
        return menu;
    }

}