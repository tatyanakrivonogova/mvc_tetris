package controller;

import model.Model;

public class Controller {
    private Model game;
    public void setGame(Model _game) {
        game = _game;
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
    public void clickAbout() {
        game.about();
    }

    public void clickHighScores() {
        game.highScores();
    }

    public void clickNewGame() {
        game.newGame();
    }

    public void clickExit() {
        game.exitGame();
    }
}
