package controller;

import exceptions.factoryexceptions.FactoryException;
import model.Model;

import javax.swing.*;

public class Controller extends JFrame {
    private Model game;
    public void setGame(Model _game) {
        game = _game;
    }
    public void go() {
        try {
            game.go();
        } catch (FactoryException e) {
            System.out.println(e.getMessage());
        }
    }

    public void up() {
        game.up();
    }
    public void down() {
        game.down();
    }
    public void left() {
        game.left();
    }
    public void right() {
        game.right();
    }
    public void clickButtonAbout() {
        game.about();
    }

    public void clickButtonHighScores() {
        game.highScores();
    }

    public void clickButtonNewGame() {
        game.newGame();
    }

    public void clickButtonExit() {
        game.exitGame();
    }
}
