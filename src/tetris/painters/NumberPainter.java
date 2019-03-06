package tetris.painters;

import java.awt.*;

public class NumberPainter {

    //Координаты точек сегментов, из которых строятся отдельные цифры привязаны к сетке.
    //Следующие две константы определяют количество ячеек этой сетки по горизонтали и по вертикали
    private static final int COUNT_X_CELLS=11;
    private static final int COUNT_Y_CELLS=24;

    //Максимальное количество цифровых разрядов, которое будет отрисовано методом paintNumber
    private int maxPaintDigits;

    //Массив с координатами отдельных сегментов цифрового табло
    private static final double[][] coordsSegment = {
            {2, 2, 3, 1, 8, 1, 9, 2, 8, 3, 3, 3},
            {2, 2.5, 3, 3.5, 3, 10.5, 2, 11.5, 1, 10.5, 1, 3.5},
            {9, 2.5, 10, 3.5, 10, 10.5, 9, 11.5, 8, 10.5, 8, 3.5},
            {2, 12, 3, 11, 8, 11, 9, 12, 8, 13, 3, 13},
            {2, 12.5, 3, 13.5, 3, 20.5, 2, 21.5, 1, 20.5, 1, 13.5},
            {9, 12.5, 10, 13.5, 10, 20.5, 9, 21.5, 8, 20.5, 8, 13.5},
            {3, 21, 8, 21, 9, 22, 8, 23, 3, 23, 2, 22}
    };

    //Перечни сегментов, входящих в каждую цифру, которую может отобразить табло
    private static final int[][] numbersSegment = {
            {0, 2, 5, 6, 4, 1},
            {2, 5},
            {0, 2, 3, 4, 6},
            {0, 2, 3, 5, 6},
            {1, 3, 2, 5},
            {0, 1, 3, 5, 6},
            {0, 1, 3, 4, 5, 6},
            {0, 2, 5},
            {0, 1, 2, 3, 4, 5, 6},
            {0, 1, 2, 3, 5, 6}
    };

    public NumberPainter(int maxPaintDigits) {
        this.maxPaintDigits = maxPaintDigits;
    }

    public void paintNumber(Graphics2D g2d, int value, int xStart, int yStart, int width, int height) {
        String numberStr = "" + value;
        int currentNumber;                             //Текущая выводимая цифра
        int currentDigit = 0;                          //Номер выводимого разряда
        double xStartDigit, yStartDigit;               //Координаты верхнего левого угла выводимой цифры
        double widthDigit, heightDigit;                //Высота и ширина выводимой цифры

        int countPointsInSegment;                      //Количество точек в текущем выводимом на экран сегменте цифры
        double deltaX, deltaY;                         //Шаг сетки (по горизонтали и вертикали), к которому привязаны сегменты
        double xTmp, yTmp;


        widthDigit=(double)width/maxPaintDigits;
        heightDigit=height;
        deltaX=widthDigit/COUNT_X_CELLS;
        deltaY=heightDigit/COUNT_Y_CELLS;

        widthDigit = (double) width / maxPaintDigits;
        while (true) {

            //Получаем текущую выводимую цифру и координаты её левого верхнего угла
            currentNumber = numberStr.charAt(numberStr.length() - (currentDigit + 1)) - 48;
            xStartDigit=(xStart+width)-(widthDigit*(currentDigit+1));
            yStartDigit=yStart;

            //Перебираем сегменты, входящие в текущую выводимую цифру
            for (int p : numbersSegment[currentNumber]) {
                countPointsInSegment = coordsSegment[p].length / 2;
                int[] x = new int[countPointsInSegment];
                int[] y = new int[countPointsInSegment];

                //Во внутреннем цикле формируем координаты точек, входящих в текущий сегмент (выбранный во внешнем цикле)
                for (int i = 0; i < countPointsInSegment * 2; i += 2) {
                    xTmp = coordsSegment[p][i];
                    yTmp = coordsSegment[p][i + 1];
                    x[i / 2] = (int) (xStartDigit + xTmp * deltaX);
                    y[i / 2] = (int) (yStartDigit + yTmp * deltaY);
                }

                //Каждая строка массива result - это координаты очередного сегмента
                g2d.fillPolygon(x,y,countPointsInSegment);
            }

            //Переходим к следующему разряду
            currentDigit++;
            if (currentDigit==numberStr.length() || currentDigit==maxPaintDigits)break;
        }
    }

}
