package model;

import exceptions.factoryexceptions.FactoryException;
import model.factory.Factory;
import view.View;

import java.util.Arrays;

public class Model {
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int LEFT = 37;
    final int RIGHT = 39;
    private int gameScore = 0;
    final int SHOW_DELAY = 400;
    private int[][] field = new int[FIELD_HEIGHT + 1][FIELD_WIDTH];
    private Figure currentFigure;
    private boolean gameOver = false;
    private boolean gameState;
    private boolean loopOver = false;
    final int[] SCORES = {100, 300, 700, 1500};
    View view;
    public Model() throws FactoryException {
        //view = _view;
        Factory.getInstance();
        Arrays.fill(field[FIELD_HEIGHT], 1); //the invisible floor is full
        createNewFigure();
    }
    public void setView(View _view) {
        view = _view;
    }
    public int[][] getField() {
        return field;
    }
    public Figure getCurrentFigure() {
        return currentFigure;
    }
    public int getGameScore() { return gameScore; }
    public void setGameScore(int score) {
        gameScore = score;
    }
    public void setGameOver(boolean value) {
        gameOver = value;
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public void createNewFigure() throws FactoryException {
        currentFigure = new Figure(field);
    }
    public void go() throws FactoryException {
        gameState = true;
        while (!gameOver) {
            try {
                Thread.sleep(SHOW_DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!gameState) continue;
            view.update(field, gameOver, currentFigure);
            checkFilling();
            if (currentFigure.isTouchGround()) {
                currentFigure.leaveOnTheGround();
                createNewFigure();
                gameOver = currentFigure.isCrossGround();
            } else {
                currentFigure.stepDown();
            }
        }
        loopOver = true;
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
        }
    }
    public void down() {
        currentFigure.drop();
        view.update(field, gameOver, currentFigure);
    }
    public void up() {
        currentFigure.rotate();
        view.update(field, gameOver, currentFigure);
    }
    public void left() {
        currentFigure.move(LEFT);
        view.update(field, gameOver, currentFigure);
    }
    public void right() {
        currentFigure.move(RIGHT);
        view.update(field, gameOver, currentFigure);
    }
    public void pause() {
        gameState = false;
    }
    public void resume() {
        gameState = true;
    }
    public void restart() throws FactoryException {
        //gameOver = true;
        Arrays.fill(field[FIELD_HEIGHT], 1); //the invisible floor is full
        for (int i = 0; i < FIELD_HEIGHT; ++i) {
            Arrays.fill(field[i], 0);
        }
        createNewFigure();
        if (loopOver) {
            loopOver = false;
            go();
        }
    }
    public void finish() {
        gameOver = true;
    }
}
