package tetris;

import java.awt.event.*;
import javax.swing.Timer;

public class Game extends KeyAdapter implements ActionListener {

    private static final int WIDTH_GLASS = 10;
    private static final int HEIGHT_GLASS = 20;

    private static final int MIN_SCORE=0;
    private static final int MAX_SCORE=9999;

    private static final int MIN_SPEED=1;
    private static final int MAX_SPEED=10;

    //Массив, сопоставляющий значение скорости и соответствующий ей интервал таймера
    private final int[][] speedAndMills = {
            {1, 1000},
            {2, 900},
            {3, 800},
            {4, 700},
            {5, 600},
            {6, 500},
            {7, 400},
            {8, 300},
            {9, 200},
            {10, 100}
    };

    //Массив, сопоставляющий количество очков, добавляемых игроку при построении одгной, двух и т.д. линий
    private final int[] lineScore = {10, 20, 40, 70};

    private Display displayObject;

    private boolean[][] glass;
    private int score;
    private int speed;
    private PolyminoCreator polyminoCreator;
    private Polymino currentPolymino;
    private Polymino nextPolymino;
    private Timer timer;
    private StateTypes state;

    public Game() {
        glass = new boolean[HEIGHT_GLASS][WIDTH_GLASS];
        polyminoCreator = new PolyminoCreator();
        score=MIN_SCORE;
        speed=MIN_SPEED;
        state = StateTypes.GAME;
        timer = new Timer(getTimerMiils(), this);
    }

    public void setDisplayObject(Display displayObject) {
        this.displayObject = displayObject;
    }

    public void startNewGame() {
        timer.stop();
        for (int i = 0; i < HEIGHT_GLASS; i++)
            for (int j = 0; j < WIDTH_GLASS; j++) {
                glass[i][j] = false;
            }
        score = MIN_SCORE;
        speed=MIN_SPEED;
        state = StateTypes.GAME;

        currentPolymino = polyminoCreator.getNewPolymino();
        nextPolymino = polyminoCreator.getNewPolymino();

        displayObject.refreshDisplay();
        timer.setDelay(getTimerMiils());
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    public int getScore(){
        return score;
    }

    public int getSpeed(){
        return speed;
    }

    public boolean[][] getGlass(){
        return glass;
    }

    public Polymino getCurrentPolymino(){
        return currentPolymino;
    }

    public Polymino getNextPolymino(){
        return nextPolymino;
    }

    public StateTypes getState(){
        return state;
    }

    private int getTimerMiils(){
        for (int[] p: speedAndMills)if (speed==p[0])return p[1];
        return 0;
    }

}
