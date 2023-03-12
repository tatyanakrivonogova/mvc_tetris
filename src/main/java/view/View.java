package view;

import model.Figure;

import java.awt.event.KeyListener;

public interface View {
    void update(int[][] field, boolean gameOver, Figure figure);
    void changeTitle(String title);
    void addListener(KeyListener l);
}
