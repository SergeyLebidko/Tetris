package tetris;

import tetris.painters.GlassPainter;
import tetris.painters.NumberPainter;
import tetris.painters.NextPolyminoPanePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Display extends JPanel {

    //Элементы интерфейса игры привязаны к узлам сетки
    //Следующие две константы определяют количество ячеек этой сетки по горизонтали и по вертикали
    private static final int COUNT_X_CELLS = 16;
    private static final int COUNT_Y_CELLS = 20;

    //Положение и размеры стакана
    private static final int GLASS_X = 0;
    private static final int GLASS_Y = 0;
    private static final int GLASS_WIDTH = 10;
    private static final int GLASS_HEIGHT = 20;

    //Положение и размеры панели очков
    private static final int SCOREBOARD_X = 11;
    private static final int SCOREBOARD_Y = 0;
    private static final int SCOREBOARD_WIDTH = 4;
    private static final int SCOREBOARD_HEIGHT = 2;

    //Пложение и размеры панели, отображающей следующий полимино
    private static final int NEXT_POLY_BOARD_X = 11;
    private static final int NEXT_POLY_BOARD_Y = 3;
    private static final int NEXT_POLY_BOARD_WIDTH = 4;
    private static final int NEXT_POLY_BOARD_HEIGHT = 4;

    //Положение и размеры панели, отображающей текущую скорость
    private static final int SPEEDBOARD_X = 11;
    private static final int SPEEDBOARD_Y = 8;
    private static final int SPEEDBOARD_WIDTH = 4;
    private static final int SPEEDBOARD_HEIGHT = 2;

    //Группа вспомогательных переменных, используемых в методах отрисовки
    private int xStart, yStart;
    private int width, height;

    //Количество разрядов индикатора количества очков
    private static final int COUNT_SCOREBOARD_DIGITS = 4;

    //Количество разрядов индикатора текущей скорости
    private static final int COUNT_SPEEDBOARD_DIGITS = 4;

    private final Color backgroundDisplayColor = new Color(180, 180, 180);
    private final Color backgroundGlassColor = new Color(210, 210, 210);
    private final Color interfaceColor = new Color(60, 60, 60);

    //Объект, необходим для отрисовки стакана
    private final GlassPainter glassPainter = new GlassPainter();

    //Объект необходим для отрисовки индикатора количества очков
    private final NumberPainter scoreboardPainter = new NumberPainter(COUNT_SCOREBOARD_DIGITS);

    //Объект необходим для отрисовки индикатора скорости
    private final NumberPainter speedboardPainter = new NumberPainter(COUNT_SPEEDBOARD_DIGITS);

    //Объект необходим для отрисовки полимино, который выпадет в стакан после текущего
    private final NextPolyminoPanePainter nextPolyminoPanePainter = new NextPolyminoPanePainter();

    //Набор полей, необходимых для реализации всплывающего меню
    private final String NEW_GAME = "NEW_GAME";
    private final String STANDART_GLASS_COMMAND = "STANDART_GLASS";
    private final String EXTENDED_GLASS_COMMAND = "EXTENDED_GLASS";
    private final String STANDART_POLY_SET_COMMAND = "STANDART_POLYMINO_SET";
    private final String EXTENDED_POLY_SET_COMMAND = "EXTENDED_POLYMINO_SET";
    private final String HELP_COMMAND = "HELP";

    private JPopupMenu popupMenu = new JPopupMenu();

    private JMenuItem startNewGame = new JMenuItem("Новая игра с текущими параметрами");

    private JRadioButtonMenuItem standartGlassMenu = new JRadioButtonMenuItem("Обычный стакан", true);
    private JRadioButtonMenuItem extendedGlassMenu = new JRadioButtonMenuItem("Большой стакан");

    private JRadioButtonMenuItem standartPolyminoSet = new JRadioButtonMenuItem("Обычный набор фигурок", true);
    private JRadioButtonMenuItem extendedPolyminoSet = new JRadioButtonMenuItem("Расширенный набор фигурок");

    private ButtonGroup glassButtonGroup = new ButtonGroup();
    private ButtonGroup polyminoButtonGroup = new ButtonGroup();

    private JMenuItem helpMenu = new JMenuItem("Помощь");

    private MouseAdapter ma = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
                gameObject.pause();
                return;
            }
            gameObject.resume();
        }
    };

    private ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            switch (actionCommand) {
                case NEW_GAME: {
                    gameObject.startNewGame(gameObject.getCurrentGlassType(), gameObject.getCurrentPolyminoSet());
                    return;
                }
                case STANDART_GLASS_COMMAND: {
                    if (gameObject.getCurrentGlassType() == Game.STANDART_GLASS) return;
                    gameObject.startNewGame(Game.STANDART_GLASS, gameObject.getCurrentPolyminoSet());
                    return;
                }
                case EXTENDED_GLASS_COMMAND: {
                    if (gameObject.getCurrentGlassType() == Game.EXTENDED_GLASS) return;
                    gameObject.startNewGame(Game.EXTENDED_GLASS, gameObject.getCurrentPolyminoSet());
                    return;
                }
                case STANDART_POLY_SET_COMMAND: {
                    if (gameObject.getCurrentPolyminoSet() == Game.STANDART_POLYMINO_SET) return;
                    gameObject.startNewGame(gameObject.getCurrentGlassType(), Game.STANDART_POLYMINO_SET);
                    return;
                }
                case EXTENDED_POLY_SET_COMMAND: {
                    if (gameObject.getCurrentPolyminoSet() == Game.EXTENDED_POLYMINO_SET) return;
                    gameObject.startNewGame(gameObject.getCurrentGlassType(), Game.EXTENDED_POLYMINO_SET);
                    return;
                }
                case HELP_COMMAND: {
                    showHelpDialog();
                }
            }
        }
    };

    //Объект, реализующий игровую логику
    private Game gameObject;

    public Display() {
        super(null);
        setFocusable(true);
        setBackground(backgroundDisplayColor);
        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");

        startNewGame.setActionCommand(NEW_GAME);
        standartGlassMenu.setActionCommand(STANDART_GLASS_COMMAND);
        extendedGlassMenu.setActionCommand(EXTENDED_GLASS_COMMAND);
        standartPolyminoSet.setActionCommand(STANDART_POLY_SET_COMMAND);
        extendedPolyminoSet.setActionCommand(EXTENDED_POLY_SET_COMMAND);
        helpMenu.setActionCommand(HELP_COMMAND);

        popupMenu.add(startNewGame);
        popupMenu.addSeparator();
        popupMenu.add(standartGlassMenu);
        popupMenu.add(extendedGlassMenu);
        glassButtonGroup.add(standartGlassMenu);
        glassButtonGroup.add(extendedGlassMenu);
        popupMenu.addSeparator();
        popupMenu.add(standartPolyminoSet);
        popupMenu.add(extendedPolyminoSet);
        polyminoButtonGroup.add(standartPolyminoSet);
        polyminoButtonGroup.add(extendedPolyminoSet);
        popupMenu.addSeparator();
        popupMenu.add(helpMenu);

        addMouseListener(ma);

        startNewGame.addActionListener(al);
        standartGlassMenu.addActionListener(al);
        extendedGlassMenu.addActionListener(al);
        standartPolyminoSet.addActionListener(al);
        extendedPolyminoSet.addActionListener(al);
        helpMenu.addActionListener(al);
    }

    public boolean showFinalGameDialog() {
        int answer;
        JPanel msgPane = new JPanel();
        msgPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel msg = new JLabel("Игра окончена. Хотите сыграть ещё?");
        msg.setFont(new Font("Arial", Font.PLAIN, 20));
        msgPane.add(msg);
        answer = JOptionPane.showConfirmDialog(this, msgPane, "", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        return (answer == JOptionPane.YES_OPTION);
    }

    public void showPauseDialog() {
        JPanel msgPane = new JPanel();
        msgPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel msg = new JLabel("Пауза");
        msg.setFont(new Font("Arial", Font.PLAIN, 20));
        msgPane.add(msg);
        JOptionPane.showConfirmDialog(this, msgPane, "", (-1), JOptionPane.PLAIN_MESSAGE);
    }

    private void showHelpDialog(){
        String helpMsg = "<html>" +
                "Стелки влево, вправо, вниз - движение фигурки<br>" +
                "Стрелка вверх - опустить полимино на дно стакана<br>"+
                "Пробел - поворнуть фигурку<br>" +
                "Enter - пауза<br>" +
                "[+] и [-] - увеличение и уменьшение скорости игры<br>" +
                "ESC - начать новую игру";
        JPanel msgPane = new JPanel();
        msgPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel msg = new JLabel(helpMsg);
        msg.setFont(new Font("Arial", Font.PLAIN, 20));
        msgPane.add(msg);
        JOptionPane.showConfirmDialog(Display.this, msgPane, "Подсказка", (-1), JOptionPane.PLAIN_MESSAGE);
        gameObject.resume();
    }

    public void setGameObject(Game gameObject) {
        this.gameObject = gameObject;
        addKeyListener(this.gameObject);
    }

    public void refreshDisplay() {
        this.paintComponent(getGraphics());
        crutch();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        refreshGlass();
        refreshScoreBoard();
        refreshNextPolyminoPane();
        refreshSpeedBoard();
    }

    private void refreshGlass() {
        Graphics2D g2d = (Graphics2D) getGraphics();
        xStart = (int) (GLASS_X * getDeltaX());
        yStart = (int) (GLASS_Y * getDeltaY());
        width = (int) (GLASS_WIDTH * getDeltaX());
        height = (int) (GLASS_HEIGHT * getDeltaY());
        glassPainter.paintGlass(g2d, backgroundGlassColor, interfaceColor, gameObject.getGlass(), gameObject.getCurrentPolymino(), xStart, yStart, width, height);
    }

    private void refreshScoreBoard() {
        Graphics2D g2d = (Graphics2D) getGraphics();
        xStart = (int) (SCOREBOARD_X * getDeltaX());
        yStart = (int) (SCOREBOARD_Y * getDeltaY());
        width = (int) (SCOREBOARD_WIDTH * getDeltaX());
        height = (int) (SCOREBOARD_HEIGHT * getDeltaY());
        scoreboardPainter.paintNumber(g2d, interfaceColor, gameObject.getScore(), xStart, yStart, width, height);
    }

    private void refreshSpeedBoard() {
        Graphics2D g2d = (Graphics2D) getGraphics();
        xStart = (int) (SPEEDBOARD_X * getDeltaX());
        yStart = (int) (SPEEDBOARD_Y * getDeltaY());
        width = (int) (SPEEDBOARD_WIDTH * getDeltaX());
        height = (int) (SPEEDBOARD_HEIGHT * getDeltaY());
        speedboardPainter.paintNumber(g2d, interfaceColor, gameObject.getSpeed(), xStart, yStart, width, height);
    }

    private void refreshNextPolyminoPane() {
        Graphics2D g2d = (Graphics2D) getGraphics();
        xStart = (int) (NEXT_POLY_BOARD_X * getDeltaX());
        yStart = (int) (NEXT_POLY_BOARD_Y * getDeltaY());
        width = (int) (NEXT_POLY_BOARD_WIDTH * getDeltaX());
        height = (int) (NEXT_POLY_BOARD_HEIGHT * getDeltaY());
        nextPolyminoPanePainter.paintNextPolyminoPane(g2d, interfaceColor, gameObject.getNextPolymino(), xStart, yStart, width, height);
    }

    private void crutch() {
        Graphics2D g2d = (Graphics2D) getGraphics();
        xStart = (int) ((COUNT_X_CELLS - 1) * getDeltaX());
        yStart = (int) ((COUNT_Y_CELLS - 1) * getDeltaY());
        width = (int) getDeltaX();
        height = (int) getDeltaY();
        g2d.setColor(backgroundDisplayColor);
        g2d.fillRect(xStart, yStart, width, height);
    }

    private double getDeltaX() {
        return (double) getWidth() / COUNT_X_CELLS;
    }

    private double getDeltaY() {
        return (double) getHeight() / COUNT_Y_CELLS;
    }

}
