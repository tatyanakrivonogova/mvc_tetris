package view;

import controller.Controller;
import model.Block;
import model.Figure;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class TUI implements View {
    final int NAME_LIMIT = 30;
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    Controller controller;
    Scanner scan;
    int scores;
    boolean state;
    String title;

    public TUI(Controller _controller) {
        controller = _controller;
        scan = new Scanner(System.in);
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

        if (gameOver) {
            System.out.println("Game over!");
            return;
        }

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
//        if (scan.hasNext()) {
        if (!scan.hasNext()) return;
        String input = scan.nextLine();
        //if (Objects.equals(input, null)) return;
        if (Objects.equals(input, "x")) controller.down();
        if (Objects.equals(input, "w")) controller.up();
        if (Objects.equals(input, "a")) controller.left();
        if (Objects.equals(input, "d")) controller.right();
        if (Objects.equals(input, "q")) controller.clickExit();
        //}
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
    public boolean showNewGame() {
        clear();
        String message = "Do you want to start new game? Enter Y or N";
        System.out.println(message);
        String result = scan.nextLine();
        return result.equals("Y") || result.equals("y");
    }
    public boolean showExit() {
        clear();
        String message = "Do you want to exit? Enter Y or N";
        System.out.println(message);
        String result = scan.nextLine();
        return result.equals("Y") || result.equals("y");
    }

    @Override
    public String getName() {
        System.out.println("Enter your name:");
        String name = scan.nextLine();
        name = (name.length() > NAME_LIMIT) ? name.substring(0, NAME_LIMIT) : name;
        return name;
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
