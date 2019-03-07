package tetris;

public class Polymino {

    private static final int LEFT_ROTATE = -1;
    private static final int RIGHT_ROTATE = 1;

    //Длина полимино (количество мономино)
    private int lenghtPolymino;

    //Смещения квадратиков полимино относительно центра полимино
    private int[] dx;
    private int[] dy;

    //Координаты центра полимино
    private int xCenter;
    private int yCenter;

    //Равен true для полимино, коотрые допускают вращение
    private boolean isRotate;

    //Матрица, необходимая для корректного отображения полимино в областях предпросмотра
    private boolean[][] matrForDisplay;

    public Polymino(int lenghtPolymino, int[] dx, int[] dy, boolean[][] matrForDisplay, boolean isRotate) {
        this.lenghtPolymino = lenghtPolymino;
        this.dx = dx;
        this.dy = dy;
        xCenter = 0;
        yCenter = 0;
        this.matrForDisplay = matrForDisplay;
        this.isRotate = isRotate;
    }

    public void setCoords(int x, int y) {
        xCenter = x;
        yCenter = y;
    }

    public int getLenghtPolymino() {
        return lenghtPolymino;
    }

    public int[] getXCoords() {
        int[] result;
        result = new int[dx.length];
        for (int i = 0; i < dx.length; i++) result[i] = xCenter + dx[i];
        return result;
    }

    public int[] getYCoords() {
        int[] result;
        result = new int[dy.length];
        for (int i = 0; i < dy.length; i++) result[i] = yCenter + dy[i];
        return result;
    }

    public boolean[][] getMatrForDisplay() {
        return matrForDisplay;
    }

    public void rightShift() {
        xCenter++;
    }

    public void leftShift() {
        xCenter--;
    }

    public void upShift() {
        yCenter--;
    }

    public void downShift() {
        yCenter++;
    }

    public void rotateRight() {
        rotate(RIGHT_ROTATE);
    }

    public void rotateLeft() {
        rotate(LEFT_ROTATE);
    }

    private void rotate(int direction) {
        if (!isRotate)return;
        int sinAlpha;
        int xNext, yNext;
        sinAlpha = 0;
        if (direction == LEFT_ROTATE) {
            sinAlpha = -1;
        }
        if (direction == RIGHT_ROTATE) {
            sinAlpha = 1;
        }
        for (int i = 0; i < lenghtPolymino; i++) {
            xNext = -dy[i] * sinAlpha;
            yNext = dx[i] * sinAlpha;
            dx[i] = xNext;
            dy[i] = yNext;
        }
    }

}
