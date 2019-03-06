package tetris;

import tetris.painters.GlassPainter;
import tetris.painters.MessagePainter;
import tetris.painters.NumberPainter;
import tetris.painters.PolyminoPainter;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {

    //Элементы интерфейса игры привязаны к узлам сетки
    //Следующие две константы определяют количество ячеек этой сетки по горизонтали и по вертикали
    private static final int COUNT_X_CELLS = 16;
    private static final int COUNT_Y_CELLS = 20;

    //Положение и размеры стакана
    private static final int GLASS_X = 0;
    private static final int GLASS_Y = 0;
    private static final int GLASS_WIDTH = 10;
    private static final int GLASS_HEIGHT = 20;

    //Положение и размеры панели очков
    private static final int SCOREBOARD_X = 11;
    private static final int SCOREBOARD_Y = 0;
    private static final int SCOREBOARD_WIDTH = 4;
    private static final int SCOREBOARD_HEIGHT = 2;

    //Пложение и размеры панели, отображающей следующий полимино
    private static final int NEXT_POLY_BOARD_X = 11;
    private static final int NEXT_POLY_BOARD_Y = 3;
    private static final int NEXT_POLY_BOARD_WIDTH = 4;
    private static final int NEXT_POLY_BOARD_HEIGHT = 4;

    //Положение и размеры панели, отображающей текущую скорость
    private static final int SPEEDBOARD_X = 11;
    private static final int SPEEDBOARD_Y = 8;
    private static final int SPEEDBOARD_WIDTH = 4;
    private static final int SPEEDBOARD_HEIGHT = 2;

    //Положение и размеры панели сообщения
    private static final int MSG_X=2;
    private static final int MSG_Y=7;
    private static final int MSG_WIDTH=11;
    private static final int MSG_HEIGHT=6;

    //Количество разрядов индикатора количества очков
    private static final int COUNT_SCOREBOARD_DIGITS = 4;

    //Количество разрядов индикатора текущей скорости
    private static final int COUNT_SPEEDBOARD_DIGITS = 4;

    private final Color backgroundColor = new Color(180, 180, 180);
    private final Color interfaceColor = new Color(60, 60, 60);

    //Объект, необходим для отрисовки стакана
    private final GlassPainter glassPainter = new GlassPainter();

    //Объект необходим для отрисовки индикатора количества очков
    private final NumberPainter scoreboardPainter = new NumberPainter(COUNT_SCOREBOARD_DIGITS);

    //Объект необходим для отрисовки индикатора скорости
    private final NumberPainter speedboardPainter = new NumberPainter(COUNT_SPEEDBOARD_DIGITS);

    //Объект необходим для отрисовки полимино, который выпадет в стакан после текущего
    private final PolyminoPainter polyminoPainter = new PolyminoPainter();

    //Объект, необходимый для отрисовки выводимых сообщений
    private final MessagePainter messagePainter = new MessagePainter();

    private Game gameObject;

    public Display() {
        super(null);
        setBackground(backgroundColor);
    }

    public void setGameObject(Game gameObject) {
        this.gameObject = gameObject;
        addKeyListener(this.gameObject);
    }

    public void refreshDisplay() {
        paintComponent(getGraphics());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(interfaceColor);
        double deltaX, deltaY;
        deltaX = (double) getWidth() / COUNT_X_CELLS;
        deltaY = (double) getHeight() / COUNT_Y_CELLS;
        int xStart, yStart;
        int width, height;

        //Отрисовка стакана
        xStart = (int) (GLASS_X * deltaX);
        yStart = (int) (GLASS_Y * deltaY);
        width = (int) (GLASS_WIDTH * deltaX);
        height = (int) (GLASS_HEIGHT * deltaY);
        glassPainter.paintGlass(g2d, gameObject.getGlass(), gameObject.getCurrentPolymino(), xStart, yStart, width, height);

        //Отрисовка табло набранных очков
        xStart = (int) (SCOREBOARD_X * deltaX);
        yStart = (int) (SCOREBOARD_Y * deltaY);
        width = (int) (SCOREBOARD_WIDTH * deltaX);
        height = (int) (SCOREBOARD_HEIGHT * deltaY);
        scoreboardPainter.paintNumber(g2d, gameObject.getScore(), xStart, yStart, width, height);

        //Отрисовка следующего полимино
        xStart = (int) (NEXT_POLY_BOARD_X * deltaX);
        yStart = (int) (NEXT_POLY_BOARD_Y * deltaY);
        width = (int) (NEXT_POLY_BOARD_WIDTH * deltaX);
        height = (int) (NEXT_POLY_BOARD_HEIGHT * deltaY);
        polyminoPainter.paintPolymino(g2d, gameObject.getNextPolymino(), xStart, yStart, width, height);

        //Отрисовка табло скорости
        xStart = (int) (SPEEDBOARD_X * deltaX);
        yStart = (int) (SPEEDBOARD_Y * deltaY);
        width = (int) (SPEEDBOARD_WIDTH * deltaX);
        height = (int) (SPEEDBOARD_HEIGHT * deltaY);
        speedboardPainter.paintNumber(g2d, gameObject.getSpeed(), xStart, yStart, width, height);

        //Отрисовка сообщения
        xStart = (int) (MSG_X * deltaX);
        yStart = (int) (MSG_Y* deltaY);
        width = (int) (MSG_WIDTH* deltaX);
        height = (int) (MSG_HEIGHT * deltaY);
        messagePainter.paintMessage(g2d, gameObject.getState(), backgroundColor, interfaceColor, xStart, yStart, width, height);
    }

}
