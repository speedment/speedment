package com.speedment.generator.internal.util;

import com.speedment.runtime.exception.SpeedmentException;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.joining;

/**
 * A utility method for creating and comparing hashes of files.
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class HashUtil {
    
    private final static String ALGORITHM = "SHA-1";
    private final static Charset CHARSET = StandardCharsets.UTF_8;
    
    public static boolean compare(Path path, Path sha1) {
        final String expected = sha1(path);
        final String actual   = load(sha1).get(0);
        return expected.equals(actual);
    }
    
    public static boolean compare(Path path, String sha1) {
        final String expected = sha1(path);
        return expected.equals(sha1);
    }
    
    public static boolean compare(String content, String sha1) {
        final String expected = sha1(content);
        return expected.equals(sha1);
    }
    
    public static String sha1(Path path) {
        return sha1(load(path));
    }
    
    public static String sha1(String content) {
        return sha1(Arrays.asList(content.split("\\s+")));
    }
    
    private static String sha1(List<String> rows) {
        return sha1(rows.stream()
            .map(String::trim)
            .flatMap(s -> Arrays.asList(s.split("\\s+")).stream())
            .collect(joining())
            .getBytes(CHARSET)
        );
    }
    
    private static String sha1(byte[] bytes) {
        final MessageDigest md;
        
        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch(final NoSuchAlgorithmException ex) {
            throw new SpeedmentException(
                "Could not find hashing algorithm '" + ALGORITHM + "'.", ex
            );
        }
        
        return bytesToHex(md.digest(bytes));
    }
    
    private static String bytesToHex(byte[] bytes) {
        final StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < bytes.length; i++) {
            result.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return result.toString();
    }
    
    private static List<String> load(Path path) {
        try {
            return Files.readAllLines(path, CHARSET);
        } catch (final IOException ex) {
            throw new SpeedmentException(
                "Error reading file '" + path + "' for hashing.", ex
            );
        }
    }
    
    /**
     * Utility classes should never be instantiated.
     */
    private HashUtil() {
        instanceNotAllowed(getClass());
    }
}
