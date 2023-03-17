package model;

import model.factory.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class HighScoresTable {
    Properties properties;
    public HighScoresTable() throws IOException {
        InputStream input = Configuration.class.getClassLoader().getResourceAsStream("high_scores.properties");
        properties = new Properties();
        properties.load(input);
    }
    private ArrayList<String> getHighScoresTable() {
        ArrayList<String> statistics = new ArrayList<>();
        for (String userName : properties.stringPropertyNames()) {
            String scores = properties.getProperty(userName);
            statistics.add(scores);
        }
        return statistics;
    }
    public void updateHighScoresTable(String name, int scores) {
        properties.setProperty(name, String.valueOf(scores));
    }
}
