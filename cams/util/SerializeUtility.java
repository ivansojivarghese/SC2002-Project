package cams.util;
import java.io.*;

public class SerializeUtility {
    public static void saveObject(Serializable object, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(object);
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static boolean deleteFile(String fileName) {
        try{
            String fileLocation = System.getProperty("user.dir") + File.separator + "cams" + File.separator + "util" + File.separator;
            File file = new File(fileLocation + fileName);
            if (!file.exists()) {
                System.out.println("File does not exist.");
                return false;
            }
            else {
                return file.delete(); //Returns boolean result of attempt to delete file
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    public Serializable loadObject(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Serializable) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
