package model;

import exceptions.factoryexceptions.FactoryException;
import model.factory.Factory;

import java.util.Arrays;

public class Model {
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    private int gameScore = 0;
    private int[][] field = new int[FIELD_HEIGHT + 1][FIELD_WIDTH];
    private Figure currentFigure;
    boolean gameOver = false;
    public Model() throws FactoryException {
        Factory.getInstance();
        Arrays.fill(field[FIELD_HEIGHT], 1); //the invisible floor is full
        createNewFigure();
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
}
