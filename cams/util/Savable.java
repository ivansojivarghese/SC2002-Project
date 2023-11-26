package cams.util;

import java.io.Serializable;

/** 
 *
 * Program persistence interface
 *
 */
public interface Savable {
    void saveObject(Serializable object, String folderName, String fileName);
    boolean deleteFile(String folderName, String fileName);
}
