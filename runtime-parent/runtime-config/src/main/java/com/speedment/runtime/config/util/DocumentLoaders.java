package com.speedment.runtime.config.util;

import com.speedment.common.json.Json;
import com.speedment.runtime.config.resolver.ResolverException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;

import static java.lang.String.format;

/**
 * Utility class with some common loaders used when resolving documents.
 *
 * @author Emil Forslund
 * @since  3.1.6
 */
public final class DocumentLoaders {

    /**
     * Returns a loader that parses any resources as JSON. The returned loader
     * will look for the resource on the classpath if the name starts with a
     * forward slash, otherwise from the disk.
     *
     * @return  the json loader
     */
    public static Function<String, Map<String, Object>> jsonLoader() {
        return res -> {
            if (res.startsWith("/")) {
                try (final InputStream in = DocumentLoaders.class.getResourceAsStream(res)) {
                    return loadAsJson(res, in);
                } catch (final IOException ex) {
                    throw new ResolverException(format(
                        "Error reading resource '%s' from classpath.",
                        res), ex);
                }
            } else {
                final Path path = Paths.get(res);
                if (Files.exists(path)) {
                    try (final InputStream in = Files.newInputStream(path)) {
                        return loadAsJson(res, in);
                    } catch (final IOException ex) {
                        throw new ResolverException(format(
                            "Error reading resource '%s' from file.",
                            res), ex);
                    }
                } else {
                    throw new ResolverException(format(
                        "Can't find resource '%s'.", res
                    ));
                }
            }
        };
    }

    private static Map<String, Object> loadAsJson(String res, InputStream in) throws IOException {
        try {
            @SuppressWarnings("unchecked")
            final Map<String, Object> loaded = (Map<String, Object>) Json.fromJson(in);
            return loaded;
        } catch (final ClassCastException ex) {
            throw new ResolverException(format(
                "Loaded resource '%s' does not appear to be a Map.",
                res
            ), ex);
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private DocumentLoaders() {}
}
