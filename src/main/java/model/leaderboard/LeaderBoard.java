package model.leaderboard;
import java.io.InvalidObjectException;
import java.util.Properties;

public class LeaderBoard {
    public Properties getProperties() throws InvalidObjectException {
        Serializator serializator = new Serializator();
        try {
            return serializator.deserialize();
        } catch(InvalidObjectException e) {
            throw new InvalidObjectException("Deserialization error");
        }
    }
}