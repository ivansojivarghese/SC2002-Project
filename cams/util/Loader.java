package cams.util;

import java.io.Serializable;

/** 
 *
 * Loading the interface using the serialised object (or file)
 *
 */

public interface Loader {
    Serializable loadObject(String fileName);
}
