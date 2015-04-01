package com.speedment.util;

import java.nio.file.Path;

/**
 *
 * @author Emil Forslund
 */
public class Paths {
    public static String toString(Path path) {
        return path.toString().replace("\\", "/");
    }
}
