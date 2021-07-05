package kakaoscsiga.model;

import kakaoscsiga.model.galaxy.Asteroid;
import kakaoscsiga.model.galaxy.Galaxy;
import kakaoscsiga.model.entity.Settler;
import kakaoscsiga.model.entity.UFO;
import kakaoscsiga.view.panels.GameFrame;
import kakaoscsiga.view.drawables.ViewContainer;

import java.util.Random;

public class Game {
    /**
     * Uj jatek letrehozasa
     */
    private static Game game= new Game();
    /**
     * Jatekban letrehozz galaxy attributuma
     */
    private Galaxy galaxy;

    private GameFrame gameFrame;

    private Thread gameloop;

    /**
     * Privat konstruktor, hogy mas ne tudja peldanyositani
     */
    private Game(){
        gameFrame = new GameFrame();
        gameFrame.run();
    };

    /**
     * Visszaadja az egyetlen statikus Game peldanyt
     * @return A Game példánya
     */
    public static Game getInstance(){
        return game;
    }

    /**
     * Letrehoz egy uj galaxist, a megadott szamu telepessel
     * A telepeseket a kezdo szteroidara teszi
     * Elinditja a RoundManager-t (run)
     * @param n A settler-ek száma
     */
    public void startGame(int n){
        galaxy = new Galaxy(n);

        Asteroid starterAsteroid = galaxy.getStarterAsteroid();
        for(int i = 0; i < n; i++){
            Settler s = new Settler(starterAsteroid, galaxy,i+1);
        }

        int gSize = galaxy.getAsteroids().size();
        Random rnd = new Random();

        //ufok sun letrehozasa
        for(int i = 0; i < 2; i++){
            int index = rnd.nextInt(gSize);
            UFO u = new UFO(galaxy.getAsteroids().get(index), galaxy);
        }

        RoundManager.getInstance().setIsRunning(true);
        gameloop = new Thread(RoundManager.getInstance());
        gameloop.start();
    }

    /**
     * Ha jatek veget er, ez a fuggveny hivodik meg
     * Jelzi a RoundManager-nek hogy ne fussanak tovabb a korok
     */
    public void endGame(String condition){
        RoundManager.getInstance().setIsRunning(false);
        RoundManager.getInstance().clear();
        ViewContainer.getInstance().clear();
        gameloop.interrupt();
        gameFrame.initMenu();
        gameFrame.getMenu().getWinLose().setText(condition);
    }
    /**
     * getter fuggvenyek
     */
    public GameFrame getGameFrame(){return this.gameFrame;}
}
