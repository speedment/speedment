package com.speedment.runtime.internal.util.decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * A utility class that can parse strings into ordinary java objects.
 * 
 * @author Emil Forslund
 * @since  2.4.0
 */
public final class Json {
    
    /**
     * Parses the specified JSON string into a java object. The following
     * objects are supported:
     * <ul>
     *      <li>{@code Map<String, Object>}
     *      <li>{@code List<Object>}
     *      <li>{@code String}
     *      <li>{@code Double}
     *      <li>{@code Long}
     *      <li>{@code null}
     * </ul>
     * 
     * @param json  the json to parse
     * @return      the created object
     * 
     * @throws JsonSyntaxException  if the specified json is malformed  
     */
    public static Object parse(String json) throws JsonSyntaxException {
        try (final InputStream stream = 
            new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
            
            return parse(stream);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Parses the specified JSON stream of unicode characters into a java 
     * object. The following objects are supported:
     * <ul>
     *      <li>{@code Map<String, Object>}
     *      <li>{@code List<Object>}
     *      <li>{@code String}
     *      <li>{@code Double}
     *      <li>{@code Long}
     *      <li>{@code null}
     * </ul>
     * 
     * @param in  the json to parse
     * @return    the created object
     * 
     * @throws IOException          if the stream can not be read 
     * @throws JsonSyntaxException  if the specified json is malformed  
     */
    public static Object parse(InputStream in) throws IOException, JsonSyntaxException {
        try (final JsonParser parser = new JsonParser(in)) {
            return parser.get();
        }
    }

    private Json() {}
}
