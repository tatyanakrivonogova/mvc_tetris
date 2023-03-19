package model;

import exceptions.factoryexceptions.FactoryException;
import model.factory.Factory;
import view.LeaderBoardAdder;
import view.View;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Model {
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int LEFT = 37;
    final int RIGHT = 39;
    private int gameScore = 0;
    final int SHOW_DELAY = 400;
    private final int[][] field = new int[FIELD_HEIGHT + 1][FIELD_WIDTH];
    private Figure currentFigure;
    private boolean gameOver;
    private boolean gameState;
    private boolean exit = false;
    final int[] SCORES = {100, 300, 700, 1500};
    LeaderBoard leaderBoard;
    LeaderBoardAdder leaderBoardAdder = new LeaderBoardAdder();
    View view;
    Timer timer = new Timer();
    public Model(View _view) {
        view = _view;
        leaderBoard = new LeaderBoard();
        try {
            Factory.getInstance();
            Arrays.fill(field[FIELD_HEIGHT], 1); //the invisible floor is full
            createNewFigure();
            gameOver = false;
            view.update(field, gameOver, currentFigure, gameScore, gameState);
        } catch (FactoryException e) {
            System.out.println(e.getMessage());
        }
        gameState = true;
        gameOver = false;

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (exit) System.exit(0);

                try {
                    Thread.sleep(SHOW_DELAY);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!gameState) return;
                view.update(field, gameOver, currentFigure, gameScore, gameState);
                if (gameOver) return;
                checkFilling();
                if (currentFigure.isTouchGround()) {
                    currentFigure.leaveOnTheGround();
                    try {
                        createNewFigure();
                    } catch (FactoryException e) {
                        System.out.println(e.getMessage());
                        System.exit(-1);
                    }
                    gameOver = currentFigure.isCrossGround();
                    if (gameOver) {
                        view.update(field, gameOver, currentFigure, gameScore, gameState);
                        addScoresToLeaderBoard();
                    }
                } else {
                    currentFigure.stepDown();
                }
            }
        };
        timer.schedule(timerTask, 0L, SHOW_DELAY);
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public void createNewFigure() throws FactoryException {
        currentFigure = new Figure(field);
    }

    void checkFilling() {
        int currentRow = FIELD_HEIGHT - 1; //bottom line
        int countFillRows = 0;
        while (currentRow > 0) {
            int filled = 1;
            for (int currentCol = 0; currentCol < FIELD_WIDTH; ++currentCol) {
                if (field[currentRow][currentCol] == 0) {
                    filled = 0;
                    break;
                }
            }
            if (filled > 0) {
                ++countFillRows;
                for (int i = currentRow; i > 0; --i) {
                    System.arraycopy(field[i - 1], 0, field[i], 0, FIELD_WIDTH);
                }
            } else
                --currentRow;
        }
        if (countFillRows > 0) {
            gameScore += SCORES[countFillRows - 1];
            view.changeTitle("TETRIS" + " : " + gameScore);
            view.changeScores(gameScore);
        }
    }
    public void down() {
        if (isGameOver()) return;
        currentFigure.drop();
        view.update(field, gameOver, currentFigure, gameScore, gameState);
    }
    public void up() {
        if (isGameOver()) return;
        currentFigure.rotate();
        view.update(field, gameOver, currentFigure, gameScore, gameState);
    }
    public void left() {
        if (isGameOver()) return;
        currentFigure.move(LEFT);
        view.update(field, gameOver, currentFigure, gameScore, gameState);
    }
    public void right() {
        if (isGameOver()) return;
        currentFigure.move(RIGHT);
        view.update(field, gameOver, currentFigure, gameScore, gameState);
    }
    public void pause() {
        gameState = false;
        view.changeState(gameState);
    }
    public void resume() {
        gameState = true;
        view.changeState(gameState);
    }
    public void restart() throws FactoryException {
        gameScore = 0;
        Arrays.fill(field[FIELD_HEIGHT], 1); //the invisible floor is full
        for (int i = 0; i < FIELD_HEIGHT; ++i) {
            Arrays.fill(field[i], 0);
        }
        createNewFigure();
        gameOver = false;
    }
    public void finish() {
        exit = true;
    }
    public void addScoresToLeaderBoard() {
        String name = view.getName();
        leaderBoardAdder.addToLeaderBoard(leaderBoard, gameScore, name);
    }
    public void about() {
        pause();
        view.showAbout();
        resume();
    }
    public void highScores() {
        pause();
        view.showHighScores(leaderBoard.getProperties());
        resume();
    }
    public void newGame() {
        pause();
        if (!view.showNewGame()) {
            resume();
            return;
        }
        try {
            restart();
        } catch (FactoryException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        view.changeScores(0);
        resume();
    }
    public void exitGame() {
        pause();
        if (!view.showExit()) {
            resume();
            return;
        }
        finish();
        view.closeGame();
        System.exit(0);
    }
}
