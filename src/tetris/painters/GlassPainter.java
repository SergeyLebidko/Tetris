package tetris.painters;

import tetris.Polymino;

import java.awt.*;

public class GlassPainter {

    private static final int BORDER_SIZE = 1;

    public void paintGlass(Graphics2D g2d, boolean[][] glass, Polymino polymino, int xStart, int yStart, int width, int height) {
        double widthCell, heightCell;      //Ширина и высота ячейки стакана

        int xRect, yRect;                  //Координаты отрисовываемого внутри ячейки прямоугольника
        int widthRect, heightRect;         //Ширина и высота отрисовываемого внутри ячейки прямоугольника

        widthCell = (double) width / glass[0].length;
        heightCell = (double) height / glass.length;
        int rowsCount=glass.length;
        int colsCount=glass[0].length;

        polyminoInGlass(glass,polymino,true);
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
        polyminoInGlass(glass,polymino,false);
    }

    //Метод размещает полимино в стакан, либо убирает его оттуда (в зависимости от значения параметра inGlass)
    private void polyminoInGlass(boolean[][] glass, Polymino polymino, boolean inGlass){
        int[] xCoordsPolymino = polymino.getXCoords();
        int[] yCoordsPolymino = polymino.getYCoords();
        int rowsCount=glass.length;
        int colsCount=glass[0].length;
        int xTmp, yTmp;
        for (int i = 0; i<polymino.getLenghtPolymino(); i++){
            xTmp=xCoordsPolymino[i];
            yTmp=yCoordsPolymino[i];
            if (isPointInRect(xTmp,yTmp,colsCount,rowsCount)){
                glass[yTmp][xTmp]=inGlass;
            }
        }
    }

    //Метод проверяет, попала ли точка [x,y] в прямоугольник [0..xSize)[0..ySize)
    private boolean isPointInRect(int x, int y, int xSize, int ySize){
        return (x>=0 & x<xSize & y>=0 & y<ySize);
    }

}
