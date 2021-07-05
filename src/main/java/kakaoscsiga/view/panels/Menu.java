package kakaoscsiga.view.panels;

import kakaoscsiga.control.MenuControl;

import javax.swing.*;
import java.awt.*;


@SuppressWarnings("serial")
public class Menu extends JPanel {

    /**
     * Start és Exit gomb,
     * A settlerek szamat tartalmazo JTextField,
     * Egy JLabel, amiben a jatek vegen, mikor a menube visszaugrik,
     * akkor abban Lose! vagy Win! felirat jelenik meg
     */
    private JButton bStart;
    private JButton bExit;
    private JTextField numberInput;
    private JLabel winLose;

    private GameFrame frame;

    /**
     * Tarolja azt az osztalyt, amely kezeli a Menu panel esemenyeit
     */
    private MenuControl control;

    /**
     * Konstruktor, ami beallitja a menu kezdeti nézetét
     * 2 gomb található a menun, egy start és egy exit gomb
     * ezen kivul a settlerek szama is a menuben adhato meg
     */
    public Menu(GameFrame frame){
        this.frame = frame;
        setPreferredSize(new Dimension(660,600));
        setFocusable(true);
        setBackground(new Color(0,37,50));
        setLayout(null);

        control = new MenuControl(this);
        numberInput = new JTextField(5);
        init();
    }

    void init() {
        /**
         * A jatek neve es annak beallítasai
         */
        JLabel gameName = new JLabel("AsteroidMining");
        gameName.setFont(gameName.getFont().deriveFont(70.0f));
        gameName.setForeground(Color.CYAN);
        gameName.setSize(600,100);
        gameName.setLocation(70,50);
        add(gameName);

        /**
         * A jatek vegen ez a felirat fog valtozni, kezdetben üres
         */
        winLose = new JLabel();
        winLose.setFont(winLose.getFont().deriveFont(30.0f));
        winLose.setForeground(Color.CYAN);
        winLose.setSize(200,50);
        winLose.setLocation(270,150);
        add(winLose);

        /**
         * "Number of settlers:" szoveg és annak beallitasai
         */
        JLabel settlerNumber = new JLabel("Number of settlers:");
        settlerNumber.setFont(settlerNumber.getFont().deriveFont(25.0f));
        settlerNumber.setForeground(Color.CYAN);
        settlerNumber.setSize(300, 50);
        settlerNumber.setLocation(170,230);
        add(settlerNumber);

        /**
         * Telepesek szama
         */
        numberInput.setText("2");
        numberInput.setFont(numberInput.getFont().deriveFont(20.0f));
        numberInput.setHorizontalAlignment(JTextField.CENTER);
        numberInput.setSize(50,50);
        numberInput.setLocation(400,230);
        numberInput.setBackground(new Color(0,37,50));
        numberInput.setForeground(Color.CYAN);
        add(numberInput);

        /**
         * Start gomb és annak beallitasai
         */
        bStart = new JButton("New Game");
        bStart.setActionCommand("bStart");
        bStart.setFont(bStart.getFont().deriveFont(20.0f));
        bStart.setSize(280,50);
        bStart.setLocation(170,290);
        bStart.setBackground(new Color(0,37,50));
        bStart.setForeground(Color.CYAN);
        bStart.setBorder(BorderFactory.createBevelBorder(0));
        add(bStart);
        bStart.addActionListener(control);

        /**
         * Exit gomb es annak beallitasai
         */
        bExit = new JButton("Exit");
        bExit.setActionCommand("bExit");
        bExit.setFont(bExit.getFont().deriveFont(20.0f));
        bExit.setSize(280,50);
        bExit.setLocation(170,350);
        bExit.setBackground(new Color(0,37,50));
        bExit.setForeground(Color.CYAN);
        bExit.setBorder(BorderFactory.createBevelBorder(0));
        add(bExit);
        bExit.addActionListener(control);
    }

    /**
     * Az osztaly attributumainak getter-ei
     */
    public JTextField getNumberInput() {
        return numberInput;
    }

    public JLabel getWinLose() { return winLose; }

    public GameFrame getFrame() { return frame; }
}
