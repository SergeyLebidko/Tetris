package tetris.painters;

import tetris.Polymino;

import java.awt.*;

public class GlassPainter {

    private static final int BORDER_SIZE = 1;

    public void paintGlass(Graphics2D g2d, Color glassColor, Color monominoColor, boolean[][] glass, Polymino polymino, int xStart, int yStart, int width, int height) {
        g2d.setColor(glassColor);
        g2d.fillRect(xStart,yStart,width,height);
        g2d.setColor(monominoColor);

        double widthCell, heightCell;      //Ширина и высота ячейки стакана
        int xRect, yRect;                  //Координаты отрисовываемого внутри ячейки прямоугольника
        int widthRect, heightRect;         //Ширина и высота отрисовываемого внутри ячейки прямоугольника

        widthCell = (double) width / glass[0].length;
        heightCell = (double) height / glass.length;
        int rowsCount = glass.length;
        int colsCount = glass[0].length;

        //Размещаем полимино в стакане. При этом запоминаем ячейки занимаемые полимино
        int[] xCoordsPolymino = polymino.getXCoords();
        int[] yCoordsPolymino = polymino.getYCoords();
        boolean[] flag = new boolean[polymino.getLenghtPolymino()];
        int xTmp, yTmp;
        for (int i = 0; i < polymino.getLenghtPolymino(); i++) {
            xTmp = xCoordsPolymino[i];
            yTmp = yCoordsPolymino[i];
            flag[i] = false;
            if (xTmp >= 0 && xTmp < colsCount && yTmp >= 0 && yTmp < rowsCount && !glass[yTmp][xTmp]) {
                glass[yTmp][xTmp] = true;
                flag[i] = true;
            }
        }

        //Отрисовываем содержимое стакана, вместе с размещенным в нем мономино
        for (int i = 0; i < rowsCount; i++)
            for (int j = 0; j < colsCount; j++) {
                if (glass[i][j]) {
                    xRect = (int) (xStart + widthCell * j + BORDER_SIZE);
                    yRect = (int) (yStart + heightCell * i + BORDER_SIZE);
                    widthRect = (int) (widthCell - BORDER_SIZE * 2);
                    heightRect = (int) (heightCell - BORDER_SIZE * 2);
                    g2d.fillRect(xRect, yRect, widthRect, heightRect);
                }
            }

        //Убираем полимино из стакана
        for (int i = 0; i < polymino.getLenghtPolymino(); i++) {
            xTmp = xCoordsPolymino[i];
            yTmp = yCoordsPolymino[i];
            if (xTmp >= 0 && xTmp < colsCount && yTmp >= 0 && yTmp < rowsCount && flag[i]) {
                glass[yTmp][xTmp] = false;
            }
        }
    }

}
