package com.speedment.maven.internal.util;

import org.apache.maven.plugin.logging.Log;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public final class ConfigUtil {

    private ConfigUtil() {}

    /**
     * Returns if the specified file is non-null, exists and is readable. If,
     * not, {@code false} is returned and an appropriate message is shown in the
     * console.
     *
     * @param file the config file to check
     * @param log the log for outputting messages
     * @return {@code true} if available, else {@code false}
     */
    public static boolean hasConfigFile(Path file, Log log) {
        requireNonNull(log);

        if (file == null) {
            final String msg = "The expected .json-file is null.";
            log.info(msg);
            return false;
        } else if (!file.toFile().exists()) {
            final String msg = "The expected .json-file '"
                + file + "' does not exist.";
            log.info(msg);
            return false;
        } else if (!Files.isReadable(file)) {
            final String err = "The expected .json-file '"
                + file + "' is not readable.";
            log.error(err);
            return false;
        } else {
            return true;
        }
    }
}
