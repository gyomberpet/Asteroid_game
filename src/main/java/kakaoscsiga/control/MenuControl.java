package kakaoscsiga.control;

import kakaoscsiga.model.Game;
import kakaoscsiga.view.panels.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A menuben torteno felhasznaloi interakciokat dolgozza fel és kezeli azokat
 * @author kakaoscsiga
 */
public class MenuControl implements ActionListener {
    /**
     * Kezdeti menu panelt tarolja
     */
    private Menu panel;
    /**
     * Konstruktor
     * @param panel A kezdeti menu panel, amit tarol
     */
    public MenuControl(Menu panel){
        this.panel = panel;
    }

    /**
     * Kezeli a felhasznalo altal megnyomott menu gombjait
     * startot nyom, elinditja a jatekot
     * csak akkor fog elindulni, ha 0-nal nagyobb szamot adott meg a felhasznalo
     * exitet nyom, kilep az alkalmazasbol
     * @param e Az adott esemenyt tarolja
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("bStart")) {
            if(Integer.parseInt(panel.getNumberInput().getText()) > 1) {
                panel.getFrame().initGamePanel();
                Game.getInstance().startGame(Integer.parseInt(panel.getNumberInput().getText()));
            }
        }
        if(e.getActionCommand().equals("bExit")) {
            System.exit(0);
        }
    }
}
