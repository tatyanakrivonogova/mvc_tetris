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

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class TUI implements View {
    final int NAME_LIMIT = 30;
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    Controller controller;
    int scores;
    volatile boolean state;
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
            terminal.enterPrivateMode();
            setCloseOnExit(terminal);
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
        if (gameOver) {
            printString("GAME OVER!");
            printString("YOUR SCORES IS " + scores);
            return;
        }

        printString("TETRIS");
        if (state) {
            printString("RUN");
        }
        else {
            printString("PAUSE");
        }
        printString("SCORES " + scores);

        printChar(' ');
        for (int i = 0; i < FIELD_WIDTH*2; ++i) printChar('━');
        printChar('\n');
        for (int i = 0; i < FIELD_HEIGHT; ++i) {
            printChar('│');
            printChar(' ');
            for (int j = 0; j < FIELD_WIDTH; ++j) {
                boolean fill = (field[i][j] != 0);
                for (Block block : figure.getFigure()) {
                    if (block.getX() == j && block.getY() == i) {
                        fill = true;
                        break;
                    }
                }
                printChar(fill ? '■' : ' ');
                printChar(' ');
            }
            printChar('│');
            printChar('\n');
        }
        printChar(' ');
        for (int i = 0; i < FIELD_WIDTH*2; ++i) printChar('━');
        printChar(' ');
        updateScreen();
    }
    public void printChar(Character c) {
        if (c == '\n') {
            currentY += 1;
            currentX = 0;
            return;
        }
        try {
            screen.setCharacter(currentX, currentY, new TextCharacter(c));
        } catch (IllegalArgumentException e) {
            System.out.println("Impossible to print control character");
        }

        currentX += 1;
    }
    private void printString(String s) {
        String[] strings = s.split("\n");
        for (String string : strings) {
            tGraphics.putString(currentX, currentY, string);
            currentX = 0;
            currentY += 1;
        }
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
    public void addToScreen() {
        try {
            screen.refresh();
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
    public void changeScores(int _scores) {
        scores = _scores;
    }

    @Override
    public void changeState(boolean _state) {
        state = _state;
        keyListener.setRunning(_state);
        keyListener.listen();
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
        String message = """
            UP
                rotate figure
            RIGHT
                move figure to right side
            LEFT
                move figure to left side
            DOWN
                drop figure
            W       rotate figure
            D       move figure to right side
            A       move figure to left side
            X       drop figure
            E       about
            R       high records
            SPACE   new game
            Q       exit
        """;
        printString(message);

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
        boolean result = keyListener.getReply();
        if (!result) {
            screen.clear();
            printString("Game is over! Press ENTER to start new game...");
            updateScreen();
        }
        return result;
    }
    public boolean showExit() {
        keyListener.setRunning(false);
        screen.clear();
        printString("Do you want to exit? Enter Y or N");
        updateScreen();
        boolean reply = keyListener.getReply();
        if (reply) keyListener.setRunning(false);
        return reply;
    }

    @Override
    public String getName() {
        screen.clear();
        printString("Enter your name:");
        updateScreen();
        String name = keyListener.getName();
        name = (name.length() > NAME_LIMIT) ? name.substring(0, NAME_LIMIT) : name;
        return name;
    }

    private void setCloseOnExit(Terminal terminal) {
        if(terminal instanceof SwingTerminalFrame) {
            ((SwingTerminalFrame) terminal).setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }
    public KeyStroke readInput() throws IOException {
        return terminal.readInput();
    }
}