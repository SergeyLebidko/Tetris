package tetris;

import java.util.Random;

public class PolyminoCreator {

    //Массив шаблонов для создания полимино
    //0 - ячейка пуста и будет проигнорирована
    //x - в ячейке есть квадратик (мономино), но она не является центром полимино (ячейкой, вокруг которой происходит вращение полимино)
    //X - в ячейке есть квадратик и она является центром полимино
    //Координаты других ячеек отсчитываются относительно её центра
    private static String[] polyminoTemplates = {
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

    public Polymino getNewPolymino() {

        //Получаем случайный шаблон из набора шаблонов
        Random rnd = new Random();
        String polyminoTemplate = polyminoTemplates[rnd.nextInt(polyminoTemplates.length)];

        //Параметры будущего полимино
        int xCenter, yCenter;
        int[] dx, dy;
        boolean[][] matr;

        //Получаем количество мономино в будущем полимино и создаем массивы смещений
        int lenghtDeltaArrs = 0;
        for (char c : polyminoTemplate.toCharArray()) if (c != '0' & c != ' ') lenghtDeltaArrs++;
        dx = new int[lenghtDeltaArrs];
        dy = new int[lenghtDeltaArrs];

        //Создаем матрицу, которая будет необходима для корректного отображения полимино в поле предпросмотра
        String[] lines = polyminoTemplate.split(" ");
        int rowsCount = lines.length;
        int colsCount = lines[0].length();
        matr = new boolean[rowsCount][colsCount];

        //Зполняем массивы смещений и матрицу предпросмотра
        int p;
        char c;
        boolean isRotate = false;
        p = 0;
        xCenter = 0;
        yCenter = 0;
        for (int i = 0; i < lines.length; i++)
            for (int j = 0; j < lines[i].length(); j++) {
                matr[i][j] = false;
                c = lines[i].charAt(j);
                if (c != '0') {
                    matr[i][j] = true;
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
        for (int i = 0; i < lenghtDeltaArrs; i++) {
            dx[i] -= xCenter;
            dy[i] -= yCenter;
        }

        return new Polymino(lenghtDeltaArrs, dx, dy, matr, isRotate);
    }

}
