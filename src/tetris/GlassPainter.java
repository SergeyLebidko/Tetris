package tetris;

import java.awt.*;

public class GlassPainter {

    //Каждая ячейка стакана разбита в свою очередь на сетку из меньших ячеек.
    //Следующие две константы определяют количество ячеек в этой сетке по горизонтали и вертикали
    private final int COUNT_SEGMENT_X_IN_CELL=50;
    private final int COUNT_SEGMENT_Y_IN_CELL=50;

    public void paintGlass(Graphics2D g2d, boolean[][] glass, int xStart, int yStart, int width, int height) {
        double widthCell, heightCell;      //Ширина и высота ячейки стакана
        double deltaX, deltaY;             //Шаг сетки внутри ячейки
        int xRect, yRect;                  //Координаты отрисовываемого внутри ячейки прямоугольника
        int widthRect, heightRect;         //Ширина и высота отрисовываемого внутри ячейки прямоугольника

        widthCell = (double) width / glass[0].length;
        heightCell = (double) height / glass.length;
        deltaX = widthCell / COUNT_SEGMENT_X_IN_CELL;
        deltaY = heightCell / COUNT_SEGMENT_Y_IN_CELL;

        for (int i=0;i<glass.length;i++)
            for (int j=0;j<glass[i].length;j++){
                if (glass[i][j]){
                    xRect=(int)(xStart+widthCell*j+deltaX);
                    yRect=(int)(yStart+heightCell*i+deltaY);
                    widthRect=(int)(widthCell-deltaX*2);
                    heightRect=(int)(heightCell-deltaY*2);
                    g2d.fillRect(xRect,yRect,widthRect,heightRect);
                }
            }
    }

}
