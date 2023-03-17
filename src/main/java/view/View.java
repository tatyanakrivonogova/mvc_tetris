package view;

import controller.Controller;
import model.Figure;
import model.Model;

import java.awt.event.KeyListener;

public interface View {
    void update(int[][] field, boolean gameOver, Figure figure);
    void showAbout();
    void showHighRecords();
    void showNewGame();
    void showExit();
    void changeTitle(String title);
    void addListener(KeyListener l);
    void setGame(Model g);
    void setController(Controller c);
    void clear();
}
