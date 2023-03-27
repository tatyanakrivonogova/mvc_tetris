package model.leaderboard;

import java.io.InvalidObjectException;
import java.util.Properties;

public class LeaderBoardAdder {
    public void addToLeaderBoard(LeaderBoard leaderBoard, int score, String name) {
        try {
            Properties properties = leaderBoard.getProperties();
            if (badRecord(properties, name, score)) return;
            Serializator serializator = new Serializator();
            properties.setProperty(name, String.valueOf(score));
            if (!serializator.serialize(properties)) {
                System.out.println("Serialization error");
            }
        } catch (InvalidObjectException e) {
            System.out.println("Deserialization error");
        }
    }
    public boolean badRecord(Properties properties, String name, int score) {
        return name == null || name.equals("") || (properties.getProperty(name) != null && (Integer.parseInt(properties.getProperty(name)) >= score));
    }
}