package model.leaderboard;

import java.io.*;
import java.util.Properties;

public class Serializator {
    String fileName = "leaderboard.properties";
    public boolean serialize(Properties properties) {
        boolean flag = false;
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
                flag = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
    public Properties deserialize() throws InvalidObjectException {
        File file = new File (fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(fis)){
            if (fis != null) {
                return (Properties) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new InvalidObjectException("Deserialization error");
    }
}
