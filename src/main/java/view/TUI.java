package view;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import controller.Controller;
import model.Block;
import model.Figure;
import model.leaderboard.LeaderBoardCreator;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

import java.io.IOException;
import java.util.Properties;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class TUI implements View {
    final int NAME_LIMIT = 30;
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    Controller controller;
    int scores;
    boolean state;
    String title;
    private final KeyListenerTUI keyListener;
    private Screen screen;
    private Terminal terminal;
    TextGraphics tGraphics;
    int currentX = 0;
    int currentY = 0;

    public TUI(Controller _controller) {
        controller = _controller;
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(FIELD_HEIGHT*5, FIELD_WIDTH*5));
        try {
            terminal = defaultTerminalFactory.createTerminal();
            closeOnExitInCaseOfASwingTerminal(terminal);
            screen = new TerminalScreen(terminal);
            tGraphics = screen.newTextGraphics();
            screen.startScreen();
            screen.setCursorPosition(null);
        } catch (IOException e) {
            System.out.println("IOException");
        }
        keyListener = new KeyListenerTUI(this, controller);
    }
    @Override
    public void update(int[][] field, boolean gameOver, Figure figure, int scores, boolean state) {

        screen.clear();
        currentX = 0;
        currentY = 0;

        printString("TETRIS");
        if (state) {
            printString("RUN");
        }
        else {
            printString("PAUSE");
        }
        printString("SCORES" + scores);

        if (gameOver) {
            printString("GAME OVER!");
            return;
        }

        printChar(' ');
        for (int i = 0; i < FIELD_WIDTH; ++i) printChar('_');
        printChar('\n');
        for (int i = 0; i < FIELD_HEIGHT; ++i) {
            printChar('|');
            for (int j = 0; j < FIELD_WIDTH; ++j) {
                boolean fill = (field[i][j] != 0);
                for (Block block : figure.getFigure()) {
                    if (block.getX() == j && block.getY() == i) {
                        fill = true;
                        break;
                    }
                }
                printChar(fill ? '+' : ' ');
            }
            printChar('|');
            printChar('\n');
        }
        printChar('|');
        for (int i = 0; i < FIELD_WIDTH; ++i) printChar('_');
        printChar('|');
        updateScreen();
    }
    private void printChar(Character c) {
        if (c == '\n') {
            currentY += 1;
            currentX = 0;
            return;
        }
        screen.setCharacter(currentX, currentY, new TextCharacter(c));
        currentX += 1;
    }
    private void printString(String s) {
        tGraphics.putString(currentX, currentY, s);
        currentX = 0;
        currentY += 1;
    }
    private void updateScreen() {
        try {
            screen.refresh();
        } catch (IOException e) {
            System.out.println("IOException");
        }
        currentX = 0;
        currentY = 0;
    }
    public void changeScores(int _scores) {
        scores = _scores;
    }

    @Override
    public void changeState(boolean _state) {
        state = _state;
        keyListener.setRunning(_state);
    }

    public void closeGame() {
        try {
            terminal.close();
        } catch (IOException e) {
            System.out.println("ERROR DURING CLOSING");
            System.exit(-1);
        }
    }

    @Override
    public void showLeaderBoardError() {
        screen.clear();
        printString("Failed to add record!");
        printString("Enter any key to continue");
        updateScreen();
        keyListener.getAnyKey();
    }

    @Override
    public void changeTitle(String _title) {
        title = _title;
    }
    public void showAbout() {
        screen.clear();
        printString("W       rotate figure");
        printString("D       move figure to right side");
        printString("A       move figure to left side");
        printString("X       drop figure");
        printString("E       about");
        printString("R       high records");
        printString("SPACE   new game");
        printString("Q       exit");

        printString("Enter any key to continue");
        updateScreen();
        keyListener.getAnyKey();
    }

    public void showHighScores(Properties properties) {
        screen.clear();
        LeaderBoardCreator creator = new LeaderBoardCreator();
        printString(creator.createLeaderBoard(properties));
        printString("Enter any key to continue");
        updateScreen();
        keyListener.getAnyKey();
    }
    public boolean showNewGame() {
        screen.clear();
        String message = "Do you want to start new game? Enter Y or N";
        printString(message);
        updateScreen();
        return keyListener.getReply();
    }
    public boolean showExit() {
        keyListener.setRunning(false);
        screen.clear();
        printString("Do you want to exit? Enter Y or N");
        updateScreen();
        return keyListener.getReply();
    }

    @Override
    public String getName() {
        printString("Enter your name:");
        String name = "";
        name = (name.length() > NAME_LIMIT) ? name.substring(0, NAME_LIMIT) : name;
        return name;
    }

    private void closeOnExitInCaseOfASwingTerminal(Terminal terminal) {
        if(terminal instanceof SwingTerminalFrame) {
            ((SwingTerminalFrame) terminal).setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }
    public KeyStroke pollInput() throws IOException {
        return terminal.pollInput();
    }

    public KeyStroke readInput() throws IOException {
        return terminal.readInput();
    }
}

//import com.googlecode.lanterna.TerminalSize;
//import com.googlecode.lanterna.input.KeyStroke;
//import com.googlecode.lanterna.screen.Screen;
//import com.googlecode.lanterna.screen.TerminalScreen;
//import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
//import com.googlecode.lanterna.terminal.Terminal;
//import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
//import model.Figure;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Properties;
//
//import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
//
//public class TUI implements View {
//    private Screen screen;
//    private List<DrawPoints> toDraw;
//    private Terminal terminal;
//
//    public TUI() throws IOException {
//        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
//        defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(10, 20));
//
//        terminal = defaultTerminalFactory.createTerminal();
//        closeOnExitInCaseOfASwingTerminal(terminal);
//        screen = new TerminalScreen(terminal);
//        screen.startScreen();
//        screen.setCursorPosition(null);
//
//        toDraw = new ArrayList<>();
//    }
//
//    public void add(Collection<DrawPoints> points) {
//        toDraw.addAll(points);
//    }
//
//    public void add(DrawPoints point) {
//        toDraw.add(point);
//    }
//
//    public void draw() throws IOException {
//        screen.clear();
//        for(DrawPoints dp : toDraw) {
//            for(Point p : dp.points) {
//                screen.setCharacter(p.x, p.y, dp.character);
//            }
//        }
//        screen.refresh();
//        toDraw.clear();
//    }
//
//    public KeyStroke pollInput() throws IOException {
//        return terminal.pollInput();
//    }
//
//    public KeyStroke readInput() throws IOException {
//        return terminal.readInput();
//    }
//
//    private void closeOnExitInCaseOfASwingTerminal(Terminal terminal) {
//        if(terminal instanceof SwingTerminalFrame) {
//            ((SwingTerminalFrame) terminal).setDefaultCloseOperation(EXIT_ON_CLOSE);
//        }
//    }
//
//    @Override
//    public void update(int[][] field, boolean gameOver, Figure figure, int scores, boolean state) {
//
//    }
//
//    @Override
//    public void showAbout() {
//
//    }
//
//    @Override
//    public void showHighScores(Properties properties) {
//
//    }
//
//    @Override
//    public boolean showNewGame() {
//        return false;
//    }
//
//    @Override
//    public boolean showExit() {
//        return false;
//    }
//
//    @Override
//    public String getName() {
//        return null;
//    }
//
//    @Override
//    public void changeTitle(String title) {
//
//    }
//
//    @Override
//    public void changeScores(int scores) {
//
//    }
//
//    @Override
//    public void changeState(boolean state) {
//
//    }
//
//    @Override
//    public void closeGame() {
//
//    }
//
//    @Override
//    public void showLeaderBoardError() {
//
//    }
//}