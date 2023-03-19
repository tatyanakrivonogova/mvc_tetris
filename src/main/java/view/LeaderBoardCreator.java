package view;

import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class LeaderBoardCreator {
    public String createLeaderBoard(Properties properties) {
        Map<String, String> sortedMap = new TreeMap<>(Comparator.comparing(o -> (Integer.parseInt((properties.get(o)).toString()))).reversed());
        for (String name : properties.stringPropertyNames()) {
            sortedMap.put(name, String.valueOf(properties.get(name)));
        }
        StringBuilder message = new StringBuilder();
        int counter = 0;
        for (String key : sortedMap.keySet()) {
            message.append(key).append(" : ").append(sortedMap.get(key)).append('\n');
            ++counter;
            if (counter == 5) break;
        }
        return message.toString();
    }
}
