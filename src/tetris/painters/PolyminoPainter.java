package tetris.painters;

import tetris.Polymino;

import java.awt.*;

public class PolyminoPainter {

    public void paintPolymino(Graphics2D g2d, Polymino polymino, int xStart, int yStart, int width, int height) {
        double widthCell, heightCell;      //Ширина и высота ячейкиа
        double xRect, yRect;               //Координаты отрисовываемого прямоугольника

        boolean[][] matr = polymino.getMatr();

        widthCell = (double) width / matr[0].length;
        heightCell = (double) height / matr.length;

        for (int i = 0; i < matr.length; i++)
            for (int j = 0; j < matr[i].length; j++) {
                if (matr[i][j]) {
                    xRect = xStart + widthCell * j;
                    yRect = yStart + heightCell * i;
                    g2d.fillRect((int) xRect, (int) yRect, (int) widthCell, (int) heightCell);
                }
            }
    }

}
