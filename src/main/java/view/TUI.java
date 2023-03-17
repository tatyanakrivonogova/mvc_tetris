package view;

import controller.Controller;
import model.Figure;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class TUI extends JTextField implements View {
    final int BLOCK_SIZE = 25;
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int START_LOCATION = 180;
    final int FIELD_DX = 15;
    final int FIELD_DY = 37;
    String title;
    Model game;
    public TUI(Model _game) {
        game = _game;
        title = "TETRIS";

//        setTitle("TETRIS 0");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setBounds(START_LOCATION, START_LOCATION, FIELD_WIDTH * BLOCK_SIZE + FIELD_DX, FIELD_HEIGHT * BLOCK_SIZE + FIELD_DY);
//        setResizable(false);
//        canvas.setBackground(Color.black);
//        add(BorderLayout.CENTER, canvas);
//        setVisible(true);

        System.out.println("TETRIS 0 points");



//        setTitle("TETRIS 0");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setBounds(START_LOCATION, START_LOCATION, FIELD_WIDTH * BLOCK_SIZE + FIELD_DX, FIELD_HEIGHT * BLOCK_SIZE + FIELD_DY);
//        setResizable(false);
//        canvas.setBackground(Color.black);
//
//        add(BorderLayout.CENTER, canvas);
//        setVisible(true);
    }
    @Override
    public void update(int[][] field, boolean gameOver, Figure figure) {

    }

    @Override
    public void showAbout() {

    }

    @Override
    public void showHighRecords() {

    }

    @Override
    public void showNewGame() {

    }

    @Override
    public void showExit() {

    }

    @Override
    public void changeTitle(String _title) {
        title = _title;
    }

    @Override
    public void addListener(KeyListener l) {
        addKeyListener(l);
    }

    @Override
    public void setGame(Model g) {

    }

    @Override
    public void setController(Controller c) {

    }
    public void clear() {}
}
