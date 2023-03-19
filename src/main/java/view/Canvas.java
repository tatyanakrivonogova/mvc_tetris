package view;

import model.Block;
import model.Figure;

import javax.swing.*;
import java.awt.*;

class Canvas extends JPanel {
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int BLOCK_SIZE = 25;
    final static int ROUNDING = 6;
    private int[][] field = new int[FIELD_HEIGHT + 1][FIELD_WIDTH];
    private Figure currentFigure;
    private boolean gameOver;

    //Model game;
    final int[][] GAME_OVER_MSG = {
            {0,1,1,0,0,0,1,1,0,0,0,1,0,1,0,0,0,1,1,0},
            {1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,1},
            {1,0,1,1,0,1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
            {1,0,0,1,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,1,0,1,0,1,0,0,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,1,1,0,0},
            {1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0},
            {1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,1,0,0},
            {1,0,0,1,0,1,1,0,0,0,1,0,0,0,0,1,0,0,1,0},
            {0,1,1,0,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0}};

    public void update(int[][] _field, Figure _currentFigure, boolean _gameOver) {
        field = _field;
        currentFigure = _currentFigure;
        gameOver = _gameOver;
        repaint();
    }
    @Override
    public void paint(Graphics g) {
        if (currentFigure == null) return;
        super.paint(g);
        g.setColor(Color.lightGray);
        g.drawLine(0, BLOCK_SIZE, (FIELD_WIDTH)*BLOCK_SIZE, BLOCK_SIZE);
        g.drawLine(0, BLOCK_SIZE, 0, (FIELD_HEIGHT)*BLOCK_SIZE);
        g.drawLine((FIELD_WIDTH)*BLOCK_SIZE, BLOCK_SIZE, (FIELD_WIDTH)*BLOCK_SIZE, (FIELD_HEIGHT)*BLOCK_SIZE);
        g.drawLine(0, (FIELD_HEIGHT)*BLOCK_SIZE, (FIELD_WIDTH)*BLOCK_SIZE, (FIELD_HEIGHT)*BLOCK_SIZE);

        for (int x = 0; x < FIELD_WIDTH; ++x)
            for (int y = 1; y < FIELD_HEIGHT; ++y) {
                if (x < FIELD_WIDTH - 1 && y < FIELD_HEIGHT - 1) {
                    g.setColor(Color.lightGray);
                    g.drawLine((x+1)*BLOCK_SIZE-2, (y+1)*BLOCK_SIZE, (x+1)*BLOCK_SIZE+2, (y+1)*BLOCK_SIZE);
                    g.drawLine((x+1)*BLOCK_SIZE, (y+1)*BLOCK_SIZE-2, (x+1)*BLOCK_SIZE, (y+1)*BLOCK_SIZE+2);
                }
                if (field[y][x] > 0) {
                    g.setColor(new Color(field[y][x]));
                    g.fill3DRect(x*BLOCK_SIZE+1, y*BLOCK_SIZE+1, BLOCK_SIZE-1, BLOCK_SIZE-1, true);
                }
            }
        if (gameOver) {
            g.setColor(Color.white);
            for (int y = 0; y < GAME_OVER_MSG.length; ++y)
                for (int x = 0; x < GAME_OVER_MSG[y].length; ++x)
                    if (GAME_OVER_MSG[y][x] == 1) g.fill3DRect(x*11+18, y*11+160, 10, 10, true);
        } else {
            g.setColor(new Color(currentFigure.getColor()));
            for (Block block : currentFigure.getFigure()) {
                g.drawRoundRect(block.getX() * BLOCK_SIZE + 1, block.getY() * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2, ROUNDING, ROUNDING);
            }
        }
    }
}