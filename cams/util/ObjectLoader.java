package cams.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;


 /**
 * The class Object loader implements loader
 */ 
public class ObjectLoader implements Loader{

/** 
 *
 * Load object
 *
 * @param fileName  the file name. 
 * @return Serializable
 */
    public Serializable loadObject(String fileName) { 

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Serializable) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
