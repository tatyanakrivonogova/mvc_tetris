package view;

import model.Figure;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class GUI extends JFrame implements View {
    final int BLOCK_SIZE = 25;
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int START_LOCATION = 180;
    final int FIELD_DX = 15;
    final int FIELD_DY = 37;
    private final Canvas canvas;
    Model game;
    public GUI(Model _game) {
        game = _game;
        canvas = new Canvas(game);

        setTitle("TETRIS 0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, FIELD_WIDTH * BLOCK_SIZE + FIELD_DX, FIELD_HEIGHT * BLOCK_SIZE + FIELD_DY);
        setResizable(false);
        canvas.setBackground(Color.black);
        add(BorderLayout.CENTER, canvas);
        setVisible(true);
    }
    public void addListener(KeyListener l) {
        addKeyListener(l);
    }
    @Override
    public void update(int[][] field, boolean gameOver, Figure figure) {
        canvas.repaint();
    }
    @Override
    public void changeTitle(String title) {
        setTitle(title);
    }
}
