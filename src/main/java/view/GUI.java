package view;

import model.Figure;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI extends JFrame implements View {
    final String TITLE_OF_PROGRAM = "Tetris";
    final int BLOCK_SIZE = 25;
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int START_LOCATION = 180;
    final int FIELD_DX = 15;
    final int FIELD_DY = 37;
    final int LEFT = 37;
    final int UP = 38;
    final int RIGHT = 39;
    final int DOWN = 40;
    private final Canvas canvas;
    Model game;
    public GUI(Model _game) {
        game = _game;
        canvas = new Canvas(game);

        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, FIELD_WIDTH * BLOCK_SIZE + FIELD_DX, FIELD_HEIGHT * BLOCK_SIZE + FIELD_DY);
        setResizable(false);
        canvas.setBackground(Color.black); // define the background color
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!game.isGameOver()) {
                    if (e.getKeyCode() == DOWN) game.getCurrentFigure().drop();
                    if (e.getKeyCode() == UP) game.getCurrentFigure().rotate();
                    if (e.getKeyCode() == LEFT || e.getKeyCode() == RIGHT) game.getCurrentFigure().move(e.getKeyCode());
                }
                canvas.repaint();
            }
        });
        add(BorderLayout.CENTER, canvas);
        setVisible(true);
    }
    @Override
    public void update(int[][] field, boolean gameOver, Figure figure) {
        canvas.repaint();
    }

//    @Override
//    public void update(int[][] field, boolean isGameOver) {
//        canvas.paint();
//    }
}
