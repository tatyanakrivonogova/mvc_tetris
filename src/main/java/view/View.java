package view;

import controller.Controller;
import model.Figure;

import java.awt.event.KeyListener;

public interface View {
    void update(int[][] field, boolean gameOver, Figure figure);
    void showAbout();
    void showHighRecords();
    void showNewGame();
    void changeTitle(String title);
    void addListener(KeyListener l);
    void setController(Controller c);
    void clear();
}
