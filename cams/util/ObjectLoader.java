package cams.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class ObjectLoader implements Loader{
    public Serializable loadObject(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Serializable) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
