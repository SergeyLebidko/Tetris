package tetris;

import java.awt.event.*;
import javax.swing.Timer;

public class Game extends KeyAdapter implements ActionListener {

    private final int WIDTH_GLASS = 10;
    private final int HEIGHT_GLASS = 20;

    //Массив, сопоставляющий набранные очки, скорость и соответствующий ей интервал таймера
    private final int[][] speedAndScoreParameters = {
            {0, 999, 1, 1000},
            {1000, 1999, 2, 900},
            {2000, 2999, 3, 800},
            {3000, 3999, 4, 700},
            {4000, 4999, 5, 600},
            {5000, 5999, 6, 500},
            {6000, 6999, 7, 400},
            {7000, 7999, 8, 300},
            {8000, 8999, 9, 200},
            {9000, 9999, 10, 100},
    };

    //Массив, сопоставляющий количество очков, добавляемых игроку при построении одгной, двух и т.д. линий
    private final int[] scoreIncrementValues = {10, 30, 60, 100};

    private Display displayObject;

    private boolean[][] glass;
    private int score;
    private PolyminoCreator polyminoCreator;
    private Polymino currentPolymino;
    private Polymino nextPolymino;
    private Timer timer;
    private String message;

    public Game() {
        glass = new boolean[HEIGHT_GLASS][WIDTH_GLASS];
        polyminoCreator = new PolyminoCreator();
        score=0;
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
        score = 0;
        currentPolymino = polyminoCreator.getNewPolymino();
        nextPolymino = polyminoCreator.getNewPolymino();
        message="";
        displayObject.refreshDisplay();
        timer.setDelay(getTimerMiils());
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("polyminoDown");
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    public int getScore(){
        return score;
    }

    public int getSpeed(){
        int result=0;
        for (int[] param: speedAndScoreParameters){
            if (score>=param[0] & score<=param[1]){
                result=param[2];
                break;
            }
        }
        return result;
    }

    public boolean[][] getGlass(){
        //Временный код-заглушка
        for (int i=0;i<HEIGHT_GLASS; i++)
            for (int j=0;j<WIDTH_GLASS;j++){
                glass[i][j]=true;
            }
        return glass;
    }

    public Polymino getNextPolymino(){
        return null;
    }

    public String getMessage(){
        return "";
    }

    private int getTimerMiils(){
        int result=0;
        for (int[] param: speedAndScoreParameters){
            if (score>=param[0] & score<=param[1]){
                result=param[3];
                break;
            }
        }
        return result;
    }

}
