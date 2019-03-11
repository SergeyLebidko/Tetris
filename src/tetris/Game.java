package tetris;

import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;

public class Game extends KeyAdapter implements ActionListener {

    private static final int GAME_STOP = 1;
    private static final int GAME_WORK = 2;

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
    private int state;

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
                "00x0 0xX0 00x0 0000",

                "00x00 00x00 00X00 00x00 00x00",
                "00000 00000 xxXxx 00000 00000",

                "00000 0xx00 00Xx0 00x00 00000",
                "00000 000x0 0xXx0 00x00 00000",
                "00000 00x00 0xX00 00xx0 00000",
                "00000 00x00 0xXx0 0x000 00000",

                "00000 00xx0 0xX00 00x00 00000",
                "00000 00x00 0xXx0 000x0 00000",
                "00000 00x00 00Xx0 0xx00 00000",
                "00000 0x000 0xXx0 00x00 00000",

                "0x000 0x000 0X000 0xx00 00000",
                "00000 00000 0xXxx 0x000 00000",
                "00xx0 000X0 000x0 000x0 00000",
                "00000 00000 0000x 0xxXx 00000",

                "00x00 00x00 00X00 0xx00 00000",
                "00000 00000 0x000 0xXxx 00000",
                "00xx0 00X00 00x00 00x00 00000",
                "00000 00000 0xxXx 0000x 00000",

                "00x00 00x00 00Xx0 000x0 00000",
                "00000 00000 00Xxx 0xx00 00000",
                "00x00 00xX0 000x0 000x0 00000",
                "00000 00000 000xx 0xxX0 00000",

                "000x0 000x0 00xX0 00x00 00000",
                "00000 00000 0xx00 00Xxx 00000",
                "000x0 00Xx0 00x00 00x00 00000",
                "00000 00000 0xxX0 000xx 00000",

                "00000 00xx0 00xX0 000x0 00000",
                "00000 00000 00xx0 0xXx0 00000",
                "00000 00x00 00Xx0 00xx0 00000",
                "00000 00000 0xXx0 0xx00 00000",

                "00000 00xx0 00Xx0 00x00 00000",
                "00000 00000 0xXx0 00xx0 00000",
                "00000 000x0 00xX0 00xx0 00000",
                "00000 00000 0xx00 0xXx0 00000",

                "00000 0xxx0 00X00 00x00 00000",
                "00000 000x0 0xXx0 000x0 00000",
                "00000 00x00 00X00 0xxx0 00000",
                "00000 0x000 0xXx0 0x000 00000",

                "00000 0x0x0 0xXx0 00000 00000",
                "00000 00xx0 00X00 00xx0 00000",
                "00000 00000 0xXx0 0x0x0 00000",
                "00000 0xx00 00X00 0xx00 00000",

                "00000 0xxX0 000x0 000x0 00000",
                "00000 000x0 000x0 0xxX0 00000",
                "00000 0x000 0x000 0Xxx0 00000",
                "00000 0Xxx0 0x000 0x000 00000",

                "00000 0xx00 00Xx0 000x0 00000",
                "00000 000x0 00Xx0 0xx00 00000",
                "00000 0x000 0xX00 00xx0 00000",
                "00000 00xx0 0xX00 0x000 00000",

                "00000 00x00 0xxx0 00x00 00000",

                "00x00 0xX00 00x00 00x00 00000",
                "00000 000x0 0xxXx 00000 00000",
                "00x00 00x00 00Xx0 00x00 00000",
                "00000 00000 0xXxx 00x00 00000",

                "00x00 00Xx0 00x00 00x00 00000",
                "00000 00000 0xxXx 000x0 00000",
                "000x0 000x0 00xX0 000x0 00000",
                "00000 00000 00x00 0xXxx 00000",

                "00000 0xx00 00X00 00xx0 00000",
                "00000 000x0 0xXx0 0x000 00000",

                "00000 00xx0 00X00 0xx00 00000",
                "00000 0x000 0xXx0 000x0 00000",
        };

        Polymino getNewPolymino(int typePolyminoSet) {
            //Получаем случайный шаблон из набора шаблонов
            Random rnd = new Random();
            String polyminoTemplate = null;
            if (typePolyminoSet == STANDART_POLYMINO_SET) {
                polyminoTemplate = standartPolyminoTemplates[rnd.nextInt(standartPolyminoTemplates.length)];
            }
            if (typePolyminoSet == EXTENDED_POLYMINO_SET) {
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
        speed = MIN_SPEED;
        state = GAME_WORK;
        timer = new Timer(getTimerMiils(speed), this);
        polyminoCreator = new PolyminoCreator();
    }

    public void setDisplayObject(Display displayObject) {
        this.displayObject = displayObject;
    }

    public void startNewGame(int glassType, int polyminoSet) {
        if (timer.isRunning()) timer.stop();

        //Сохраняем параметры новой игры
        currentGlassType = glassType;
        currentPolyminoSet = polyminoSet;

        //Создаем новый стакан
        if (glassType == STANDART_GLASS) {
            widthGlass = 10;
            heightGlass = 20;
        }
        if (glassType == EXTENDED_GLASS) {
            widthGlass = 20;
            heightGlass = 40;
        }
        glass = new boolean[heightGlass][widthGlass];
        for (int i = 0; i < heightGlass; i++)
            for (int j = 0; j < widthGlass; j++) {
                glass[i][j] = false;
            }

        //Сбрасываем показатель скорости и количество очков
        score = MIN_SCORE;
        speed = MIN_SPEED;

        //Получаем текущий и следующий полимино и устанавливаем текущий полимино в стартовую позицию
        currentPolymino = polyminoCreator.getNewPolymino(currentPolyminoSet);
        nextPolymino = polyminoCreator.getNewPolymino(currentPolyminoSet);
        currentPolymino = polyminoInitPlace(currentPolymino, glass);

        //Обновляем дисплей
        displayObject.refreshDisplay();

        //Устанавливаем интервал для таймера сдвига фигурок и запускаем таймер
        state = GAME_WORK;
        timer.setDelay(getTimerMiils(speed));
        timer.start();
    }

    private int getTimerMiils(int s) {
        switch (s) {
            case 1:
                return 550;
            case 2:
                return 500;
            case 3:
                return 450;
            case 4:
                return 400;
            case 5:
                return 350;
            case 6:
                return 300;
            case 7:
                return 250;
            case 8:
                return 200;
            case 9:
                return 150;
            case 10:
                return 100;
            default:
                return 1500;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (state == GAME_STOP) return;

        boolean successDown;
        successDown = polyminoToDown(currentPolymino, glass);
        if (successDown) {
            displayObject.refreshDisplay();
            return;
        }
        polyminoOnTheBottom();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (state == GAME_STOP) return;

        int key = e.getKeyCode();

        //Пауза
        if (key == KeyEvent.VK_ENTER) {
            pause();
            displayObject.showPauseDialog();
            resume();
            return;
        }

        //Увеличить скорость (нажата клавиша +)
        if (key == 107) {
            speed++;
            if (speed > MAX_SPEED) {
                speed = MAX_SPEED;
                return;
            }
            timer.setDelay(getTimerMiils(speed));
            displayObject.refreshDisplay();
            return;
        }

        //Уменьшить скорость (нажата клавиша -)
        if (key == 109) {
            speed--;
            if (speed < MIN_SPEED) {
                speed = MIN_SPEED;
                return;
            }
            timer.setDelay(getTimerMiils(speed));
            displayObject.refreshDisplay();
            return;
        }

        //Сдвиг влево
        if (key == 37) {
            boolean successShift;
            successShift = polyminoToLeft(currentPolymino, glass);
            if (successShift) {
                displayObject.refreshDisplay();
            }
            return;
        }

        //Сдвиг вправо
        if (key == 39) {
            boolean successShift;
            successShift = polyminoToRight(currentPolymino, glass);
            if (successShift) {
                displayObject.refreshDisplay();
            }
            return;
        }

        //Поворот
        if (key == KeyEvent.VK_SPACE) {
            boolean successRotate;
            successRotate = polyminoRotate(currentPolymino, glass);
            if (successRotate) {
                displayObject.refreshDisplay();
            }
            return;
        }

        //Сдвиг вниз на одну клетку
        if (key == 40) {
            polyminoToDown(currentPolymino, glass);
            displayObject.refreshDisplay();
            return;
        }

        //Сдвиг вниз на все возможные клетки
        if (key==38){
            while (polyminoToDown(currentPolymino, glass)) {}
            displayObject.refreshDisplay();
            polyminoOnTheBottom();
        }

    }

    //Метод вызывается, когда полимино уже не может двигаться вниз
    //Метод подсчитывает количество стертых линий, обновляет табло очков, создает новый полимино и размещает его,
    //а также проверяет условие окнчания игры
    private void polyminoOnTheBottom(){
        //Переносим полимино в стакан
        polyminoToGlass(currentPolymino, glass);

        //Получаем следующий полимино
        currentPolymino = nextPolymino;
        nextPolymino = polyminoCreator.getNewPolymino(currentPolyminoSet);

        //Подсчитываем количество собранных полных линий и удаляем их, обновляем количество набранных очков
        int linesCount;
        linesCount = getCountFullLine(glass);
        if (linesCount != 0) {
            score += getScoreForLines(linesCount);
            if (score > MAX_SCORE) score = MIN_SCORE;
            deleteFullLines(glass);
        }
        displayObject.refreshDisplay();

        //Размещаем новый полимино в стакане
        polyminoInitPlace(currentPolymino, glass);
        displayObject.refreshDisplay();

        //Проверяем условие окончания игры
        if (isPolyminoCrash(currentPolymino, glass)) {
            timer.stop();
            boolean isPlayStill;
            isPlayStill = displayObject.showFinalGameDialog();
            if (!isPlayStill) System.exit(0);
            startNewGame(currentGlassType, currentPolyminoSet);
            return;
        }
    }

    //Метод сдвигает полимино p влево в стакане g. Возвращает истина/или ложь в зависимости от успеха операции
    private boolean polyminoToLeft(Polymino p, boolean[][] g) {
        int xStart, yStart;
        xStart = p.getXCenterCoord();
        yStart = p.getYCenterCoord();
        p.leftShift();
        if (isLeftExit(p) || isPolyminoCrash(p, g)) {
            p.setXCenterCoord(xStart);
            p.setYCenterCoord(yStart);
            return false;
        }
        return true;
    }

    //Метод сдвигает полимино p вправо в стакане g. Возвращает истина/или ложь в зависимости от успеха операции
    private boolean polyminoToRight(Polymino p, boolean[][] g) {
        int xStart, yStart;
        xStart = p.getXCenterCoord();
        yStart = p.getYCenterCoord();
        p.rightShift();
        if (isRightExit(p, g[0].length) || isPolyminoCrash(p, g)) {
            p.setXCenterCoord(xStart);
            p.setYCenterCoord(yStart);
            return false;
        }
        return true;
    }

    //Метод сдвигает полимино p вниз в стакане g. Возвращает истина/или ложь в зависимости от успеха операции
    private boolean polyminoToDown(Polymino p, boolean[][] g) {
        int xStart, yStart;
        xStart = p.getXCenterCoord();
        yStart = p.getYCenterCoord();
        p.downShift();
        if (isDownExit(p, g.length) || isPolyminoCrash(p, g)) {
            p.setXCenterCoord(xStart);
            p.setYCenterCoord(yStart);
            return false;
        }
        return true;
    }

    //Метод вращает полимино p вправо в стакане g. Возвращает истина/или ложь в зависимости от успеха операции
    private boolean polyminoRotate(Polymino p, boolean[][] g) {
        int xStart = p.getXCenterCoord();
        int yStart = p.getYCenterCoord();
        int wg, hg;
        wg = g[0].length;
        hg = g.length;
        boolean success, isRE, isLE, isDE, isPC;
        p.rotateRight();

        //Если нет выхода за пределы стакана и нет пересечения с другими мономино в стакане, то вращение успешно
        isRE = isRightExit(p, wg);
        isLE = isLeftExit(p);
        isDE = isDownExit(p, hg);
        isPC = isPolyminoCrash(p, g);
        success = !(isDE || isLE || isRE || isPC);
        if (success) return true;

        //Если выход за нижнюю границу - вращаем полимино в обратную сторону и выходим
        if (isDownExit(p, hg)) {
            p.rotateLeft();
            return false;
        }

        //Если выход за правое поле, то пробуем сдвинуть полимино влево
        if (isRE) {
            while (isRightExit(p, wg)) {
                p.leftShift();
            }
        }

        //Если выход за левое поле, то пробуем сдвинуть полимино вправо
        if (isLE) {
            while (isLeftExit(p)) {
                p.rightShift();
            }
        }

        //Если сдвиги не решили проблему, то делаем откат
        isPC = isPolyminoCrash(p, g);
        if (isPC) {
            p.rotateLeft();
            p.setXCenterCoord(xStart);
            p.setYCenterCoord(yStart);
            return false;
        }

        return true;
    }

    //Метод выполняет первоначальное размещение полимино в стакане
    //Первоначальное размещение предполагает, что полимино будет иметь такие координаты, что:
    //  1) Будет располагаться как можно ближе к центральной оси стакана
    //  2) В стакане будет находиться только нижний ряд мономино, входящих в полимино
    //Метод возвращает полимино с требуемыми координатами
    private Polymino polyminoInitPlace(Polymino p, boolean[][] g) {
        double averageLine;
        double averageDistance;
        double minAverageDistance;
        int xPosMinDistance;
        int xStart, yStart;

        averageLine = (g[0].length - 1) / 2;
        xStart = 0;
        yStart = -p.getLenghtPolymino() - 1;

        //Располагаем полимино как можно ближе к оси стакана
        minAverageDistance = Double.MAX_VALUE;
        xPosMinDistance = 0;
        for (int i = 0; i < widthGlass; i++) {
            averageDistance = 0;
            p.setXCenterCoord(i);
            for (int j = 0; j < p.getLenghtPolymino(); j++) {
                averageDistance += (p.getXCoords()[j] - averageLine) * (p.getXCoords()[j] - averageLine);
            }
            averageDistance /= p.getLenghtPolymino();
            if (averageDistance < minAverageDistance) {
                minAverageDistance = averageDistance;
                xPosMinDistance = i;
            }
        }
        p.setXCenterCoord(xPosMinDistance);

        //Теперь сдвигаем полимино вниз до тех пор пока его нижние мономино не окажутся в стакане
        boolean endOfShift = false;
        while (!endOfShift) {
            yStart++;
            p.setYCenterCoord(yStart);
            for (int i = 0; i < p.getLenghtPolymino(); i++) {
                if (p.getYCoords()[i] >= 0) {
                    endOfShift = true;
                    break;
                }
            }
        }

        return p;
    }

    //Метод переносит полимино в стакан (то есть помечает соответствующие ячейки стакана как занятые мономино)
    private boolean[][] polyminoToGlass(Polymino p, boolean[][] g) {
        int wg, hg;
        int[] x;
        int[] y;
        x = p.getXCoords();
        y = p.getYCoords();
        wg = g[0].length;
        hg = g.length;
        for (int i = 0; i < p.getLenghtPolymino(); i++) {
            if (x[i] >= 0 & x[i] < wg & y[i] >= 0 & y[i] < hg) {
                g[y[i]][x[i]] = true;
            }
        }
        return g;
    }

    //Метод возвращает true, если полимино выходит за левый край стакана
    //Сам стакан в метод не передается, так как левый край стакана любого размера ограничен x-координатой 0
    private boolean isLeftExit(Polymino p) {
        int[] x;
        x = p.getXCoords();
        for (int i = 0; i < p.getLenghtPolymino(); i++) {
            if (x[i] < 0) return true;
        }
        return false;
    }

    //Метод возвращает true, если полимино выходит за правый край стакана
    //Параметр wGlass - ширина стакана
    private boolean isRightExit(Polymino p, int wGlass) {
        int[] x;
        x = p.getXCoords();
        for (int i = 0; i < p.getLenghtPolymino(); i++) {
            if (x[i] >= wGlass) return true;
        }
        return false;
    }

    //Метод возвращает true, если полимино выходит за нижний край стакана
    //Параметр hGlass - высота стакана
    private boolean isDownExit(Polymino p, int hGlass) {
        int[] y;
        y = p.getYCoords();
        for (int i = 0; i < p.getLenghtPolymino(); i++) {
            if (y[i] >= hGlass) return true;
        }
        return false;
    }

    //Метод возвращает true, если полимино пересекается хотя бы с одним мономино в стакане
    private boolean isPolyminoCrash(Polymino p, boolean[][] g) {
        int wg, hg;
        int[] x;
        int[] y;
        x = p.getXCoords();
        y = p.getYCoords();
        wg = g[0].length;
        hg = g.length;
        for (int i = 0; i < p.getLenghtPolymino(); i++) {
            if (x[i] >= 0 & x[i] < wg & y[i] >= 0 & y[i] < hg) {
                if (g[y[i]][x[i]]) return true;
            }
        }
        return false;
    }

    //Метод убирает все полные линии в стакане, которые находит
    private boolean[][] deleteFullLines(boolean[][] g) {
        int wg, hg;
        boolean isFullLine;
        wg = g[0].length;
        hg = g.length;
        for (int i = (hg - 1); i >= 0; i--) {
            isFullLine = true;
            while (isFullLine) {
                for (int j = 0; j < wg; j++) {
                    isFullLine = isFullLine & g[i][j];
                }
                if (isFullLine) {
                    for (int j = 0; j < wg; j++) {
                        for (int k = i; k >= 0; k--) {
                            g[k][j] = ((k > 0) ? g[k - 1][j] : false);
                        }
                    }
                }
            }
        }
        return g;
    }

    //Метод возвращает количество полных линий в стакане g
    private int getCountFullLine(boolean[][] g) {
        int wg, hg;
        int countFullLine = 0;
        boolean isFullLine;
        wg = g[0].length;
        hg = g.length;
        for (int i = 0; i < hg; i++) {
            isFullLine = true;
            for (int j = 0; j < wg; j++) {
                isFullLine = isFullLine & g[i][j];
            }
            if (isFullLine) countFullLine++;
        }
        return countFullLine;
    }

    //Метод возвращает количество очков, соответствующее количеству набранных линий
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

    //Блок открытых методов, используемых классом Display
    public void pause() {
        state = GAME_STOP;
    }

    public void resume() {
        state = GAME_WORK;
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

    public int getCurrentGlassType() {
        return currentGlassType;
    }

    public int getCurrentPolyminoSet() {
        return currentPolyminoSet;
    }

}
