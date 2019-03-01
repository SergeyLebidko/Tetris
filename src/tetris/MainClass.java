package tetris;

import javax.swing.*;
import java.awt.*;

public class MainClass {

    private static final int WIDTH_FRM = 600;
    private static final int HEIGHT_FRM = 800;

    private final JFrame frm;
    private final Display display;
    private final Game game;

    public MainClass() {
        frm = new JFrame("Тетрис");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setSize(WIDTH_FRM, HEIGHT_FRM);
        frm.setResizable(false);
        int xPos = Toolkit.getDefaultToolkit().getScreenSize().width / 2 - WIDTH_FRM / 2;
        int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 2 - HEIGHT_FRM / 2;
        frm.setLocation(xPos, yPos);

        display=new Display();
        game=new Game();
        display.setGameObject(game);
        game.setDisplayObject(display);

        frm.setContentPane(display);
        frm.setVisible(true);

        game.startNewGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainClass();
            }
        });
    }

}
