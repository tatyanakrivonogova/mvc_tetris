package view;

import model.LeaderBoard;

import java.io.FileWriter;
import java.io.IOException;

public class LeaderBoardAdder {
    public void addToLeaderBoard(LeaderBoard leaderBoard, int score, String name) {
        if (leaderBoard.badRecord(name, score)) return;
        String newRecord = name + "=" + score + "\n";
        writeFile(newRecord);
        leaderBoard.getProperties().setProperty(name, String.valueOf(score));
    }
    private void writeFile(String text) {
        FileWriter writer = null;
        try {
            writer = new FileWriter("src/main/resources/leaderboard.properties", true);
            writer.write(text);
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}