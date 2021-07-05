package kakaoscsiga.view.panels;

import kakaoscsiga.control.GameControl;
import kakaoscsiga.view.drawables.Point;
import kakaoscsiga.view.drawables.ViewContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

//TODO commentek
public class GamePanel extends JPanel implements ActionListener {

    /**
     * a jatek vezerlesehez szukseges control
     */
    private GameControl control;

    /**
     * a jenlegi jatekos informaciojat tarolo JTextPane
     */
    private JTextPane sideNav;

    /**
     * Timer ami ahhoz kell, hogy a palyat idokozonkent kirajzoljuk
     */
    private Timer t;

    /**
     * az alabbi ki attributum a hatter kirajzolasahoz szukseges tombbok eltaroljak a hatter csillagainak helyet es meretet
     */
    private List<Point> stars;
    private List<Integer> sizes;

    /**
     * deffault konstruktor beallitja a gamepanelt, pl keylistenerjet, a hatter szinet es a panel meretet
     * beallitja ezek mellett azt is, hogy az oldalso, az aktualis jatekos informacioinak megjenitesere szolgalo, sav hol, es hogyan
     * jelenjen meg
     *
     * beallitja a hatter csillagait
     */
    public GamePanel(){
        control = GameControl.getInstance();
        addKeyListener(control);
        addMouseListener(control);
        setFocusable(true);

        //SideNav szelessege: 200 pixel
        setPreferredSize(new Dimension(GameFrame.WIDTH + 200,GameFrame.HEIGHT));
        setBackground(new Color(0,37,50));

        //SideNav megjeleniteshez kell
        setLayout(null);
        sideNav = new JTextPane();
        sideNav.setFont(sideNav.getFont().deriveFont(15.0f));
        sideNav.setForeground(Color.WHITE);
        sideNav.setOpaque(false);
        sideNav.setSize(200, 800);
        sideNav.setEditable(false);
        sideNav.setLocation(1200, 0);
        add(sideNav);

        /**
         * beallitjuk a timert, hogy ennek az osztalynak az actionPerformed fuggvenyet 100 milisekundumonkent meghivja,
         * azaz a mi esetunkben kirajzolja a palyat
         */
        t = new Timer(100, this);
        t.start();
        setVisible(true);


        stars = new ArrayList<Point>();
        sizes = new ArrayList<Integer>();

        //Generating random stars
        Random rnd = new Random();
        for (int i = 0; i < 500; i++) {
            int x = rnd.nextInt(GameFrame.WIDTH - 10);
            int y = rnd.nextInt(GameFrame.HEIGHT);
            int size = rnd.nextInt(5);
            stars.add(new Point(x,y));
            sizes.add(size);
        }
    }

    /**
     * kirajzolja a jatekhoz szukseges komponenseket, a hatteret, es a viewcontainer altal tarolt osszes rajzolhato
     * objektumot
     * @param g a kirajzolashoz szukseges valtozo
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        for (int i = 0; i < stars.size(); i++) {
            g.fillOval(stars.get(i).x, stars.get(i).y, sizes.get(i), sizes.get(i));
        }

        ViewContainer.getInstance().drawAllView(g);
    }

    /**
     * getter fuggveny visszaadja az oldalso savot
     * @return az oldalso sav JTextPane-je
     */
    public JTextPane getSideNav(){return sideNav;}

    /**
     * a timer hatasara kirajzoljuk a palyat
     * @param e event ami meghivta jelen esetunkben nem fontos
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
