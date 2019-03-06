package tetris.painters;

import tetris.StateTypes;

import java.awt.*;

public class MessagePainter {

    private static final String[] pauseMsg = {
            "_______________________________________________________",
            "_______________________________________________________",
            "_______________________________________________________",
            "_______________________________________________________",
            "____XXXXXX_______X_____XX_____XX___XXXXX____XXXXXXXX___",
            "___XXXXXXXX_____XXX____XX_____XX__XXXXXXX__XXXXXXXX____",
            "___XX____XXX____XXX____XX_____XX_XX_____XX_XX__________",
            "___XX_____XX___XX_XX___XX_____XX_XX______X_XX__________",
            "___XX_____XX___XX_XX___XX_____XX_XX________XX__________",
            "___XX_____XX__XX___XX__XX_____XX_XX________XX__________",
            "___XX_____XX__XX___XX__XX_____XX_XX________XX__________",
            "___XX_____XX__XX___XX__XX_____XX_XX________XX__________",
            "___XX_____XX_XXX____XX_XX_____XX_XX________XX__________",
            "___XX____XXX_XX_____XX_XX_____XX__XXXXXX___XX__________",
            "___XXXXXXXX__XX_____XX_XX_____XX___XXXXXX__XXXXXXXX____",
            "___XXXXXXX___XXXXXXXXX_XX_____XX________XX_XX__________",
            "___XX________XXXXXXXXX_XX_____XX________XX_XX__________",
            "___XX________XX_____XX_XX_____XX________XX_XX__________",
            "___XX________XX_____XX_XX_____XX________XX_XX__________",
            "___XX________XX_____XX_XX_____XX________XX_XX__________",
            "___XX________XX_____XX_XX_____XX________XX_XX__________",
            "___XX________XX_____XX_XX_____XX________XX_XX__________",
            "___XX________XX_____XX_XX_____XX_X______XX_XX__________",
            "___XX________XX_____XX__XX___XXX_XX_____XX_XX__________",
            "___XX________XX_____XX___XXXXX____XXXXXXX__XXXXXXXX____",
            "___XX________XX_____XX____XXX______XXXXX____XXXXXXXX___",
            "_______________________________________________________",
            "_______________________________________________________",
            "_______________________________________________________",
            "_______________________________________________________",
    };

    private static final String[] finalMsg = {
            "_______________________________________________________",
            "_______________________________________________________",
            "__________XXXXX_______X________________________________",
            "_________XXXXXXX_____XXX____X_______X__XXXXXXX_________",
            "________XX_____XX____X_X____XX_____XX_XXXXXXXXX________",
            "________XX__________XX_XX___XXX___XXX_XX_______________",
            "________XX_________XX___XX__XXXX_XXXX_XX_______________",
            "________XX__XXXX___XX___XX__XX_XXX_XX_XXXXXXX__________",
            "________XX___XXXX__XXXXXXX__XX__X__XX_XXXXXXX__________",
            "________XX_____XX__XXXXXXX__XX_____XX_XX_______________",
            "________XX____XXX_XX_____XX_XX_____XX_XX_______________",
            "_________XXXXXXX__XX_____XX_XX_____XX_XXXXXXXXX________",
            "__________XXXXX___XX_____XX_XX_____XX__XXXXXXX_________",
            "_______________________________________________________",
            "_______________________________________________________",
            "__________XXXXX___XX_____XX__XXXXXXXX__XXXXXX__________",
            "_________XXX_XXX__XX_____XX_XXXXXXXX__XXXXXXXX_________",
            "________XXX___XXX_XX_____XX_XX________XX_____XX________",
            "________XX_____XX__XX___XX__XX________XX_____XX________",
            "________XX_____XX__XX___XX__XXXXXXX___XX_____XX________",
            "________XX_____XX__XX___XX__XXXXXXX___XXXXXXXX_________",
            "________XX_____XX___XX_XX___XX________XXXXXXX__________",
            "________XX_____XX___XX_XX___XX________XX_XX____________",
            "________XXX___XXX___XXXXX___XX________XX__XX___________",
            "_________XXX_XXX_____XXX____XXXXXXXX__XX___XXX_________",
            "__________XXXXX_______X______XXXXXXXX_XX____XXX________",
            "_______________________________________________________",
            "_______________________________________________________",
            "_______________________________________________________",
            "_______________________________________________________",
    };


    public void paintMessage(Graphics2D g2d, StateTypes message, Color backgroundColor, Color messageColor, int xStart, int yStart, int width, int height) {
        if (message == StateTypes.GAME) return;

        //Закрашиваем область сообщения и рисуем рамку вокруг неё
        g2d.setColor(backgroundColor);
        g2d.fillRect(xStart, yStart, width, height);
        g2d.setColor(messageColor);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(xStart, yStart, width, height);

        //Выводим само сообщение
        String[] currentTemplate = {};
        switch (message) {
            case PAUSE: {
                currentTemplate = pauseMsg;
                break;
            }
            case FINAL: {
                currentTemplate = finalMsg;
            }
        }

        double widthCell, heightCell;      //Ширина и высота ячейкиа
        double xRect, yRect;               //Координаты отрисовываемого прямоугольника

        widthCell = (double) width / currentTemplate[0].length();
        heightCell = (double) height / currentTemplate.length;

        int i, j;
        i = j = 0;
        for (String s : currentTemplate) {
            j = 1;
            for (char c : s.toCharArray()) {
                if (c == 'X') {
                    xRect = xStart + widthCell * j-1;
                    yRect = yStart + heightCell * i-1;
                    g2d.fillRect((int) xRect, (int) yRect, (int) widthCell+1, (int) heightCell+1);
                }
                j++;
            }
            i++;
        }
    }

}
