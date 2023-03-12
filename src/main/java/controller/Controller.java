package controller;

import exceptions.factoryexceptions.FactoryException;
import model.Model;
import view.View;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller extends JFrame implements KeyListener {
    private final Model game;
    private final View view;
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int LEFT = 37;
    final int UP = 38;
    final int RIGHT = 39;
    final int DOWN = 40;
    final int[] SCORES = {100, 300, 700, 1500};
    final int SHOW_DELAY = 400;
    public Controller(Model _game, View _view) {
        game = _game;
        view = _view;
        view.addListener(this);
    }
    public void go() throws FactoryException {
        while (!game.isGameOver()) {
            try {
                Thread.sleep(SHOW_DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            view.update(game.getField(), game.isGameOver(), game.getCurrentFigure());
            checkFilling();
            if (game.getCurrentFigure().isTouchGround()) {
                game.getCurrentFigure().leaveOnTheGround();
                game.createNewFigure();
                game.setGameOver(game.getCurrentFigure().isCrossGround());
            } else {
                game.getCurrentFigure().stepDown();
            }
        }
    }
    void checkFilling() {
        int currentRow = FIELD_HEIGHT - 1; //bottom line
        int countFillRows = 0;
        while (currentRow > 0) {
            int filled = 1;
            for (int currentCol = 0; currentCol < FIELD_WIDTH; ++currentCol) {
                if (game.getField()[currentRow][currentCol] == 0) {
                    filled = 0;
                    break;
                }
            }
            if (filled > 0) {
                ++countFillRows;
                for (int i = currentRow; i > 0; --i) {
                    System.arraycopy(game.getField()[i - 1], 0, game.getField()[i], 0, FIELD_WIDTH);
                }
            } else
                --currentRow;
        }
        if (countFillRows > 0) {
            game.setGameScore(game.getGameScore() + SCORES[countFillRows - 1]);
            view.changeTitle("TETRIS" + " : " + game.getGameScore());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if (!game.isGameOver()) {
            if (e.getKeyCode() == DOWN) game.getCurrentFigure().drop();
            if (e.getKeyCode() == UP) game.getCurrentFigure().rotate();
            if (e.getKeyCode() == LEFT || e.getKeyCode() == RIGHT) game.getCurrentFigure().move(e.getKeyCode());
        }
        view.update(game.getField(), game.isGameOver(), game.getCurrentFigure());
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}
