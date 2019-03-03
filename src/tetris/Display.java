package tetris;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {

    //Элементы интерфейса игры привязаны к узлам сетки
    //Следующие две константы определяют количество ячеек этой сетки по горизонтали и по вертикали
    private final int COUNT_X_CELLS = 15;
    private final int COUNT_Y_CELLS = 20;

    //Положение и размеры стакана
    private final int GLASS_X = 0;
    private final int GLASS_Y = 0;
    private final int GLASS_WIDTH = 10;
    private final int GLASS_HEIGHT = 20;

    //Положение и размеры панели очков
    private final int SCOREBOARD_X = 11;
    private final int SCOREBOARD_Y = 0;
    private final int SCOREBOARD_WIDTH = 4;
    private final int SCOREBOARD_HEIGHT = 2;

    //Пложение и размеры панели, отображающей следующий полимино
    private final int NEXT_POLY_BOARD_X = 11;
    private final int NEXT_POLY_BOARD_Y = 3;
    private final int NEXT_POLY_BOARD_WIDTH = 4;
    private final int NEXT_POLY_BOARD_HEIGHT = 4;

    //Положение и размеры панели, отображающей текущую скорость
    private final int SPEEDBOARD_X = 11;
    private final int SPEEDBOARD_Y = 8;
    private final int SPEEDBOARD_WIDTH = 4;
    private final int SPEEDBOARD_HEIGHT = 2;

    //Положение и размеры панели сообщения
    private final int MSG_X=1;
    private final int MSG_Y=5;
    private final int MSG_WIDTH=13;
    private final int MSG_HEIGHT=10;

    //Количество разрядов индикатора количества очков
    private final int COUNT_SCOREBOARD_DIGITS = 4;

    //Количество разрядов индикатора текущей скорости
    private final int COUNT_SPEEDBOARD_DIGITS = 4;

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
        glassPainter.paintGlass(g2d, gameObject.getGlass(), xStart, yStart, width, height);

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
        messagePainter.paintMessage(g2d, gameObject.getMessage(), backgroundColor, interfaceColor, xStart, yStart, width, height);
    }

}
