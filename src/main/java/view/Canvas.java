package view;

import model.Model;

import javax.swing.*;
import java.awt.*;

class Canvas extends JPanel {
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int BLOCK_SIZE = 25;

    Model game;
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
    Canvas(Model _game) {
        game = _game;
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < FIELD_WIDTH; x++)
            for (int y = 0; y < FIELD_HEIGHT; y++) {
                if (x < FIELD_WIDTH - 1 && y < FIELD_HEIGHT - 1) {
                    g.setColor(Color.lightGray);
                    g.drawLine((x+1)*BLOCK_SIZE-2, (y+1)*BLOCK_SIZE, (x+1)*BLOCK_SIZE+2, (y+1)*BLOCK_SIZE);
                    g.drawLine((x+1)*BLOCK_SIZE, (y+1)*BLOCK_SIZE-2, (x+1)*BLOCK_SIZE, (y+1)*BLOCK_SIZE+2);
                }
                if (game.getField()[y][x] > 0) {
                    g.setColor(new Color(game.getField()[y][x]));
                    g.fill3DRect(x*BLOCK_SIZE+1, y*BLOCK_SIZE+1, BLOCK_SIZE-1, BLOCK_SIZE-1, true);
                }
            }
        if (game.isGameOver()) {
            g.setColor(Color.white);
            for (int y = 0; y < GAME_OVER_MSG.length; y++)
                for (int x = 0; x < GAME_OVER_MSG[y].length; x++)
                    if (GAME_OVER_MSG[y][x] == 1) g.fill3DRect(x*11+18, y*11+160, 10, 10, true);
        } else
            game.getCurrentFigure().paint(g);
    }
}