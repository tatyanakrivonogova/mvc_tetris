package controller;

import exceptions.factoryexceptions.FactoryException;
import model.Model;
import view.View;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.System.exit;

public class Controller extends JFrame implements KeyListener {
    private Model game;
    private final View view;
    final int LEFT = 37;
    final int UP = 38;
    final int RIGHT = 39;
    final int DOWN = 40;
    public Controller(Model _game, View _view) {
        game = _game;
        view = _view;
        view.addListener(this);
    }
    public void go() {
        try {
            game.go();
        } catch (FactoryException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        if (!game.isGameOver()) {
            if (e.getKeyCode() == DOWN) game.down();
            if (e.getKeyCode() == UP) game.up();
            if (e.getKeyCode() == LEFT) game.left();
            if (e.getKeyCode() == RIGHT) game.right();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public void clickButtonAbout() {
        game.pause();
        view.showAbout();
        game.resume();
    }

    public void clickButtonHighScores() {
        game.pause();
        view.showHighRecords();
        game.resume();
    }

    public void clickButtonNewGame() {
//        game.pause();
//        try {
//            game.restart();
//        } catch (FactoryException ex) {
//            System.out.println(ex.getMessage());
//            exit(0);
//        }
        game.finish();
        game = new Model();
        game.setView(view);
        view.setGame(game);
        try {
            game.go();
        } catch (FactoryException e) {
            System.out.println(e.getMessage());
        }
        view.showNewGame();
//        game.resume();
    }

    public void clickButtonExit() {
        game.finish();
        view.showExit();
    }
}
