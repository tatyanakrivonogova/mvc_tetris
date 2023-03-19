package view;

import model.Figure;

public interface View {
    void update(int[][] field, boolean gameOver, Figure figure);
    void showAbout();
    void showHighRecords();
    void showNewGame();
    void changeTitle(String title);
    void changeScores(int scores);
    void closeGame();
}
