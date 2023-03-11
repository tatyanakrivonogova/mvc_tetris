package controller;

import exceptions.factoryexceptions.FactoryException;
import model.Model;
import view.View;

public class Controller {
    private final Model game;
    private final View view;
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int[] SCORES = {100, 300, 700, 1500};
    //boolean gameOver = false;
    final int SHOW_DELAY = 400;
    public Controller(Model _game, View _view) {
        game = _game;
        view = _view;
    }
    public void go() throws FactoryException {
        while (!game.isGameOver()) {
            try {
                Thread.sleep(SHOW_DELAY);
            } catch (Exception e) { e.printStackTrace(); }
            view.update(game.getField(), game.isGameOver(), game.getCurrentFigure());
            checkFilling();
            if (game.getCurrentFigure().isTouchGround()) {
                game.getCurrentFigure().leaveOnTheGround();
                game.createNewFigure();
                //gameOver = game.getCurrentFigure().isCrossGround();
                game.setGameOver(game.getCurrentFigure().isCrossGround());
            } else
                game.getCurrentFigure().stepDown();
        }
    }
    void checkFilling() {
        int row = FIELD_HEIGHT - 1;
        int countFillRows = 0;
        while (row > 0) {
            int filled = 1;
            for (int col = 0; col < FIELD_WIDTH; col++)
                filled *= Integer.signum(game.getField()[row][col]);
            if (filled > 0) {
                countFillRows++;
                for (int i = row; i > 0; i--)
                    System.arraycopy(game.getField()[i - 1], 0, game.getField()[i], 0, FIELD_WIDTH);
            } else
                row--;
        }
        if (countFillRows > 0) {
            game.setGameScore(game.getGameScore() + SCORES[countFillRows - 1]);
            //setTitle(TITLE_OF_PROGRAM + " : " + gameScore);
        }
    }
}
