package view;

import model.LeaderBoard;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;

public class LeaderBoardAdder {
    public void addToLeaderBoard(LeaderBoard leaderBoard, int score) {
        String name = JOptionPane.showInputDialog("Enter your name");
        if (leaderBoard.checkRecord(name, score)) return;
        String newRecord = name + "=" + score + "\n";
        writeFile("/D:/java/lab3/src/main/resources/leaderboard.properties", newRecord);
        leaderBoard.getProperties().setProperty(name, String.valueOf(score));
    }
    private void writeFile(String filename, String text) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filename, true);
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