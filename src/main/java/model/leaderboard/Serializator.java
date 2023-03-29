package model.leaderboard;

import java.io.*;
import java.util.Properties;

public class Serializator {
    String fileName = "src/main/resources/leaderboard.properties";
    public void serialize(Properties properties) throws InvalidObjectException {

        File file = new File(fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            if (fos != null) {
                oos.writeObject(properties);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidObjectException("Serialization error");
        }
    }
    public Properties deserialize() throws InvalidObjectException {
        File file = new File (fileName);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
            return (Properties) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new InvalidObjectException("Deserialization error");
        } catch (EOFException e) {
            return new Properties();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserialization error");
        }
        throw new InvalidObjectException("Deserialization error");
    }
}
