package cams.util;

import java.io.Serializable;

public interface Loader {
    Serializable loadObject(String fileName);
}
