package model.leaderboard;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.util.Properties;

public class LeaderBoard {
    //private final Properties properties = new Properties();

//    public LeaderBoard() {
////        try {
////            InputStream is = LeaderBoard.class.getClassLoader().getResourceAsStream("leaderboard.properties");
////            //properties.load(is);
////        }
////        catch (IOException e) {
////            e.printStackTrace();
////        }
//    }

    public Properties getProperties() throws InvalidObjectException {
        Serializator serializator = new Serializator();
        try {
            return serializator.deserialize();
        } catch(InvalidObjectException e) {
            throw new InvalidObjectException("Deserialization error");
        }
    }
}