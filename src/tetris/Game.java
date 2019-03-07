package tetris;

import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;

public class Game extends KeyAdapter implements ActionListener {

    public static final int STANDART_GLASS = 1;
    public static final int EXTENDED_GLASS = 2;

    public static final int STANDART_POLYMINO_SET = 3;
    public static final int EXTENDED_POLYMINO_SET = 4;

    private static final int MIN_SCORE = 0;
    private static final int MAX_SCORE = 9999;

    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 10;

    private int widthGlass;
    private int heightGlass;

    private Display displayObject;

    private boolean[][] glass;
    private int currentGlassType;
    private int currentPolyminoSet;
    private int score;
    private int speed;
    private PolyminoCreator polyminoCreator;
    private Polymino currentPolymino;
    private Polymino nextPolymino;
    private Timer timer;

    class PolyminoCreator {

        //Массив шаблонов для создания полимино
        private String[] standartPolyminoTemplates = {
                "0000 0xx0 0xx0 0000",
                "0000 xxXx 0000 0000",
                "00x0 00X0 00x0 00x0",
                "0000 00Xx 0xx0 0000",
                "00x0 00Xx 000x 0000",
                "0000 0xX0 00xx 0000",
                "000x 00Xx 00x0 0000",
                "0000 0xXx 0x00 0000",
                "00x0 00X0 00xx 0000",
                "000x 0xXx 0000 0000",
                "0xx0 00X0 00x0 0000",
                "0000 0xXx 000x 0000",
                "00xx 00X0 00x0 0000",
                "0x00 0xXx 0000 0000",
                "00x0 00X0 0xx0 0000",
                "0000 0xXx 00x0 0000",
                "00x0 00Xx 00x0 0000",
                "00x0 0xXx 0000 0000",
                "00x0 0xX0 00x0 0000"
        };

        private String[] extendedPolyminoTemplates = {
                "0000 0xx0 0xx0 0000",
                "0000 xxXx 0000 0000",
                "00x0 00X0 00x0 00x0",
                "0000 00Xx 0xx0 0000",
                "00x0 00Xx 000x 0000",
                "0000 0xX0 00xx 0000",
                "000x 00Xx 00x0 0000",
                "0000 0xXx 0x00 0000",
                "00x0 00X0 00xx 0000",
                "000x 0xXx 0000 0000",
                "0xx0 00X0 00x0 0000",
                "0000 0xXx 000x 0000",
                "00xx 00X0 00x0 0000",
                "0x00 0xXx 0000 0000",
                "00x0 00X0 0xx0 0000",
                "0000 0xXx 00x0 0000",
                "00x0 00Xx 00x0 0000",
                "00x0 0xXx 0000 0000",
                "00x0 0xX0 00x0 0000"
        };

        Polymino getNewPolymino(int typePolyminoSet) {

            //Получаем случайный шаблон из набора шаблонов
            Random rnd = new Random();
            String polyminoTemplate=null;
            if (typePolyminoSet==STANDART_POLYMINO_SET){
                polyminoTemplate = standartPolyminoTemplates[rnd.nextInt(standartPolyminoTemplates.length)];
            }
            if (typePolyminoSet==EXTENDED_POLYMINO_SET){
                polyminoTemplate = extendedPolyminoTemplates[rnd.nextInt(extendedPolyminoTemplates.length)];
            }

            //Параметры будущего полимино
            int xCenter, yCenter;
            int[] dx, dy;
            boolean[][] matrForDisplay;

            //Получаем количество мономино в будущем полимино и создаем массивы смещений
            int lenghtDeltaArrs = 0;
            for (char c : polyminoTemplate.toCharArray()) if (c != '0' & c != ' ') lenghtDeltaArrs++;
            dx = new int[lenghtDeltaArrs];
            dy = new int[lenghtDeltaArrs];

            //Создаем матрицу, которая будет необходима для корректного отображения полимино в поле предпросмотра
            String[] lines = polyminoTemplate.split(" ");
            int rowsCount = lines.length;
            int colsCount = lines[0].length();
            matrForDisplay = new boolean[rowsCount][colsCount];

            //Зполняем массивы смещений и матрицу предпросмотра
            int p;
            char c;
            boolean isRotate = false;
            p = 0;
            xCenter = 0;
            yCenter = 0;
            for (int i = 0; i < lines.length; i++)
                for (int j = 0; j < lines[i].length(); j++) {
                    matrForDisplay[i][j] = false;
                    c = lines[i].charAt(j);
                    if (c != '0') {
                        matrForDisplay[i][j] = true;
                        dx[p] = j;
                        dy[p] = i;
                        p++;
                    }
                    if (c == 'X') {
                        xCenter = j;
                        yCenter = i;
                        isRotate = true;
                    }
                }
            if (!isRotate) {
                xCenter = dx[0];
                yCenter = dy[0];
            }
            for (int i = 0; i < lenghtDeltaArrs; i++) {
                dx[i] -= xCenter;
                dy[i] -= yCenter;
            }

            return new Polymino(lenghtDeltaArrs, dx, dy, matrForDisplay, isRotate);
        }

    }

    public Game() {
        timer = new Timer(getTimerMiils(), this);
        polyminoCreator = new PolyminoCreator();
    }

    public void setDisplayObject(Display displayObject) {
        this.displayObject = displayObject;
    }

    public void startNewGame(int glassType, int polyminoSet) {
        if (timer.isRunning())timer.stop();

        //Сохраняем параметры новой игры
        currentGlassType=glassType;
        currentPolyminoSet=polyminoSet;

        //Создаем новый стакан
        if (glassType==STANDART_GLASS){
            widthGlass=10;
            heightGlass=20;
        }
        if (glassType==EXTENDED_GLASS){
            widthGlass=20;
            heightGlass=40;
        }
        glass=new boolean[heightGlass][widthGlass];
        for (int i = 0; i < heightGlass; i++)
            for (int j = 0; j < widthGlass; j++) {
                glass[i][j] = false;
            }

        //Сбрасываем показатель скорости и количество очков
        score = MIN_SCORE;
        speed = MIN_SPEED;

        //Получаем текущий и следующий полимино
        currentPolymino = polyminoCreator.getNewPolymino(currentPolyminoSet);
        nextPolymino = polyminoCreator.getNewPolymino(currentPolyminoSet);

        //Обновляем дисплей
        displayObject.refreshDisplay();

        //Устанавливаем интервал для таймера сдвига фигурок и запускаем таймер
        timer.setDelay(getTimerMiils());
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Тестовый код
        System.out.println("Таймер");
    }

    @Override
    public void keyPressed(KeyEvent e) {

        //Тестовый код
        System.out.println("Кнопка");
        if (e.getKeyCode()==KeyEvent.VK_SPACE)displayObject.showPauseDialog();
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE)displayObject.showFinalGameDialog();
    }

    public int getScore() {
        return score;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean[][] getGlass() {
        return glass;
    }

    public Polymino getCurrentPolymino() {
        return currentPolymino;
    }

    public Polymino getNextPolymino() {
        return nextPolymino;
    }

    public int getCurrentGlassType(){
        return currentGlassType;
    }

    public int getCurrentPolyminoSet(){
        return currentPolyminoSet;
    }

    private int getTimerMiils() {
        switch (speed) {
            case 1:
                return 1000;
            case 2:
                return 900;
            case 3:
                return 800;
            case 4:
                return 700;
            case 5:
                return 600;
            case 6:
                return 500;
            case 7:
                return 400;
            case 8:
                return 300;
            case 9:
                return 200;
            case 10:
                return 100;
            default:
                return 1500;
        }
    }

    private int getScoreForLines(int linesCount) {
        switch (linesCount) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 7;
            case 4:
                return 15;
            default:
                return 35;
        }
    }

}
