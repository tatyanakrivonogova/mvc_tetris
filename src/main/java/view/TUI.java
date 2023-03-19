package view;

import controller.Controller;
import model.Block;
import model.Figure;


import java.io.IOException;
import java.util.Properties;

public class TUI implements View {
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int LEFT = 37;
    final int UP = 38;
    final int RIGHT = 39;
    final int DOWN = 40;
    Controller controller;
    int scores;
    boolean state;
    String title;

    public TUI(Controller _controller) {
        controller = _controller;
    }
    @Override
    public void update(int[][] field, boolean gameOver, Figure figure, int scores, boolean state) {
        clear();
        System.out.println("TETRIS");
        if (state) {
            System.out.println("STATE: RUN");
        }
        else {
            System.out.println("STATE: PAUSE");
        }
        System.out.println("SCORES: " + scores);

        System.out.print(' ');
        for (int i = 0; i < FIELD_WIDTH*2; ++i) System.out.print('_');
        System.out.println();
        for (int i = 0; i < FIELD_HEIGHT; ++i) {
            System.out.print('|');
            for (int j = 0; j < FIELD_WIDTH; ++j) {
                boolean fill = (field[i][j] != 0);
                for (Block block : figure.getFigure()) {
                    if (block.getX() == j && block.getY() == i) {
                        fill = true;
                        break;
                    }
                }
                System.out.print(fill ? " +" : "  ");
            }
            System.out.print('|');
            System.out.println();
        }
        System.out.print('|');
        for (int i = 0; i < FIELD_WIDTH*2; ++i) System.out.print('_');
        System.out.print('|');
    }
    public void changeScores(int _scores) {
        scores = _scores;
    }

    @Override
    public void changeState(boolean _state) {
        state = _state;
    }

    public void closeGame() {
        System.exit(0);
    }
    @Override
    public void changeTitle(String _title) {
        title = _title;
    }
    public void showAbout() {
        clear();
        String message = """
            UP
                rotate figure
            RIGHT
                move figure to right side
            LEFT
                move figure to left side
            DOWN
                drop figure
        """;
        System.out.println(message);
    }
    public void showHighScores(Properties properties) {
        clear();
        LeaderBoardCreator creator = new LeaderBoardCreator();
        System.out.println(creator.createLeaderBoard(properties));
    }
    public void showNewGame() {
        clear();
        String message = "Let's start new game!";
        System.out.println(message);
    }
    private void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }
}
