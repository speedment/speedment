package com.speedment.internal.util;

import com.speedment.exception.SpeedmentException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Emil Forslund
 */
public final class CopyFileUtil {
    
    public static void generate(String srcFolder, String destFolder, String className) {
        final String destFilename = destFolder + className;
        
        final Path writePath = Paths.get(destFilename);
        try {
            writePath.getParent().toFile().mkdirs();
            Files.copy(CopyFileUtil.class.getResourceAsStream(className), 
                writePath,
                StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException ex) {
            throw new SpeedmentException(
                "Could not copy file.", ex
            );
        }
    }
    
    private CopyFileUtil() {}
}