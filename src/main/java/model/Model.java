package model;

import exceptions.factoryexceptions.FactoryException;
import model.factory.Factory;
import model.leaderboard.LeaderBoard;
import model.leaderboard.LeaderBoardAdder;
import view.View;

import java.io.InvalidObjectException;
import java.util.Arrays;

public class Model extends Thread {
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int LEFT = 37;
    final int RIGHT = 39;
    private int gameScore = 0;
    int currentDelay = 400;
    private final int[][] field = new int[FIELD_HEIGHT + 1][FIELD_WIDTH];
    private Figure currentFigure;
    volatile private boolean gameOver;
    volatile private boolean gameState;
    final int[] SCORES = {100, 300, 700, 1500};
    final double[] BOOST = {1.05, 1.1, 1.15, 1.2};
    LeaderBoard leaderBoard = new LeaderBoard();
    LeaderBoardAdder leaderBoardAdder = new LeaderBoardAdder();
    View view;
    public Model(View _view) {
        view = _view;
        try {
            Factory.getInstance();
            Arrays.fill(field[FIELD_HEIGHT], 1); //the invisible floor is full
            createNewFigure();
            view.update(field, gameOver, currentFigure, gameScore, gameState);
        }catch (FactoryException e) {
            System.exit(-1);
        }
        gameState = true;
        gameOver = false;

        this.start();
    }

    public boolean isGameOver() {
        return gameOver;
    }
    private void createNewFigure() throws FactoryException {
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
            currentDelay = (int) (currentDelay / BOOST[countFillRows - 1]);
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
    public void resumeGame() {
        gameState = true;
        //gameOver = false;
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
    public void addScoresToLeaderBoard() throws InvalidObjectException {
        String name = view.getName();
        leaderBoardAdder.addToLeaderBoard(leaderBoard, gameScore, name);
        newGame();
    }
    public void about() {
        pause();
        view.showAbout();
        resumeGame();
    }
    public void highScores() {
        pause();
        try {
            view.showHighScores(leaderBoard.getProperties());
        } catch (InvalidObjectException e) {
            System.out.println("Impossible to load high scores");
        }
        resumeGame();
    }
    public void newGame() {
        pause();
        if (!view.showNewGame()) {
            resumeGame();
            return;
        }
        try {
            restart();
        } catch (FactoryException ex) {
            System.out.println(ex.getMessage());
            System.exit(0);
        }
        view.changeScores(0);
        resumeGame();
    }
    public void exitGame() {
        pause();
        if (!view.showExit()) {
            resumeGame();
            return;
        }

        view.closeGame();
        this.interrupt();
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sleep(currentDelay);
            } catch (InterruptedException e) {
                interrupt();
            }
            if (!gameState) continue;
            view.update(field, gameOver, currentFigure, gameScore, gameState);
            if (gameOver) continue;
            checkFilling();
            if (currentFigure.isTouchGround()) {
                currentFigure.leaveOnTheGround();
                try {
                    createNewFigure();
                } catch (FactoryException e) {
                    System.err.println(e.getMessage());
                    exitGame();
                }
                gameOver = currentFigure.isCrossGround();
                if (gameOver) {
                    view.update(field, gameOver, currentFigure, gameScore, gameState);
                    try {
                        addScoresToLeaderBoard();
                    } catch (InvalidObjectException e) {
                        view.showLeaderBoardError();
                    }
                }
            } else {
                currentFigure.stepDown();
            }
        }
    }
}