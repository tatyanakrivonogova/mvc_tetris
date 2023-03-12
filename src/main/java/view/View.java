package view;

import model.Figure;

public interface View {
    void update(int[][] field, boolean gameOver, Figure figure);
    void changeTitle(String title);
}
