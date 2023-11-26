package cams.util;
import java.io.*;


 /**
 * The class Savable object implements savable. 
 */ 
public class SavableObject implements Savable {
    private static final String fileLocation = System.getProperty("user.dir") + File.separator + "resources" + File.separator;


/** 
 *
 * Save object
 *
 * @param object  the object. 
 * @param folderName  the folder name. 
 * @param fileName  the file name. 
 */
    public void saveObject(Serializable object, String folderName, String fileName) { 

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileLocation + folderName + File.separator + fileName))) {
            out.writeObject(object);
        }
        catch (FileNotFoundException e){
                System.out.println("File not Found:" + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }


/** 
 *
 * Delete file
 *
 * @param folderName  the folder name. 
 * @param fileName  the file name. 
 * @return boolean
 */
    public boolean deleteFile(String folderName, String fileName) { 

        try{
            File file = new File(fileLocation + folderName + File.separator + fileName);
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
}
