package view;

import model.Figure;

import java.util.Properties;

public interface View {
    void update(int[][] field, boolean gameOver, Figure figure, int scores, boolean state);
    void showAbout();
    void showHighScores(Properties properties);
    boolean showNewGame();
    boolean showExit();
    String getName();
    void changeTitle(String title);
    void changeScores(int scores);
    void changeState(boolean state);
    void closeGame();
}
