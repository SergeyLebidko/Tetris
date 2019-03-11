package tetris.painters;

import tetris.Polymino;

import java.awt.*;

public class NextPolyminoPanePainter {

    public void paintNextPolyminoPane(Graphics2D g2d, Color polyminoColor, Polymino polymino, int xStart, int yStart, int width, int height) {
        if (polymino == null) return;
        g2d.setColor(polyminoColor);

        double widthCell, heightCell;      //Ширина и высота ячейкиа
        double xRect, yRect;               //Координаты отрисовываемого прямоугольника

        boolean[][] matr = polymino.getMatrForDisplay();

        widthCell = (double) width / matr[0].length;
        heightCell = (double) height / matr.length;

        for (int i = 0; i < matr.length; i++)
            for (int j = 0; j < matr[i].length; j++) {
                if (matr[i][j]) {
                    xRect = xStart + widthCell * j - 1;
                    yRect = yStart + heightCell * i - 1;
                    g2d.fillRect((int) xRect, (int) yRect, (int) widthCell + 1, (int) heightCell + 1);
                }
            }
    }

}
