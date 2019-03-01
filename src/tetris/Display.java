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

    //Количество разрядов индикатора количества очков
    private final int COUNT_SCOREBOARD_DIGITS = 4;

    //Количество разрядов индикатора текущей скорости
    private final int COUNT_SPEEDBOARD_DIGITS = 4;

    private final Color backgroundColor = new Color(180, 180, 180);
    private final Color interfaceColor = new Color(60, 60, 60);

    //Объект, необходим для отрисовки стакана
    private  GlassPainter glassPainter = new GlassPainter();

    //Объект необходим для отрисовки индикатора количества очков
    private  NumberPainter scoreboardPainter = new NumberPainter(COUNT_SCOREBOARD_DIGITS);

    //Объект необходим для отрисовки индикатора скорости
    private  NumberPainter speedboardPainter = new NumberPainter(COUNT_SPEEDBOARD_DIGITS);

    //Объект необходим для отрисовки полимино, который выпадет в стакан после текущего
    private  PolyminoPainter polyminoPainter = new PolyminoPainter();

    private Game gameObject;

    public Display() {
        super(null);
        setBackground(backgroundColor);
        glassPainter = new GlassPainter();
        scoreboardPainter = new NumberPainter(COUNT_SCOREBOARD_DIGITS);
        speedboardPainter = new NumberPainter(COUNT_SPEEDBOARD_DIGITS);
        polyminoPainter = new PolyminoPainter();
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
        /*
        Код отрисовки содержимого дисплея
        - отрисовка стакана
        - отрисовка табло очков
        - отрисовка следующего полимино
        - орисовка табло скорости
         */
    }

}
