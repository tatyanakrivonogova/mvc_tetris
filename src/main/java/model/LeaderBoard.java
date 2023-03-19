package model;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LeaderBoard {
    private final Properties properties = new Properties();

    public LeaderBoard() {
        try {
            InputStream is = LeaderBoard.class.getClassLoader().getResourceAsStream("leaderboard.properties");
            properties.load(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }
}