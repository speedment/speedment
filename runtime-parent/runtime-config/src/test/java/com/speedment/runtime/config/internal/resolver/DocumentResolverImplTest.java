package com.speedment.runtime.config.internal.resolver;

import com.speedment.common.json.Json;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static com.speedment.common.mapbuilder.MapBuilder.mapBuilder;
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

/**
 * Tests specific methods in {@link DocumentResolverImpl}.
 *
 * @author Emil Forslund
 * @since  3.1.6
 */
public class DocumentResolverImplTest {

    @BeforeClass
    public static void setUp() {
        Json.PRETTY = false;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void resolvePrototype() {

        final Map<String, Map<String, Object>> files =
            mapBuilder("/a.json", (Map<String, Object>) Json.fromJson("{\"a\":\"adam\"}"))
                .build();

        final DocumentResolverImpl resolver = new DocumentResolverImpl(res -> {
            System.out.println("Loading " + res);
            return files.get(res);
        });

        final String expected = "{\"a\":\"adam\"}";
        final String actual = Json.toJson(resolver.resolvePrototype("/a.json"));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void resolvePrototype2() {

        final Map<String, Map<String, Object>> files =
            mapBuilder("/a.json", (Map<String, Object>) Json.fromJson("{\"a\":\"adam\"}"))
                .entry("/b.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/a.json\",\"a\":\"bert\"}"))
                .build();

        final DocumentResolverImpl resolver = new DocumentResolverImpl(res -> {
            System.out.println("Loading " + res);
            return files.get(res);
        });

        final String expected = "{\"extends\":\"/a.json\",\"a\":\"bert\"}";
        final String actual = Json.toJson(resolver.resolvePrototype("/b.json"));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void resolvePrototype3() {

        final Map<String, Map<String, Object>> files =
            mapBuilder("/a.json", (Map<String, Object>) Json.fromJson("{\"a\":\"adam\"}"))
                .entry("/b.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/a.json\",\"a\":\"bert\"}"))
                .entry("/c.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/b.json\",\"a\":\"carl\"}"))
                .build();

        final DocumentResolverImpl resolver = new DocumentResolverImpl(res -> {
            System.out.println("Loading " + res);
            return files.get(res);
        });

        final String expected = "{\"extends\":\"/b.json\",\"a\":\"carl\"}";
        final String actual = Json.toJson(resolver.resolvePrototype("/c.json"));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void resolvePrototype4() {

        final Map<String, Map<String, Object>> files =
            mapBuilder("/a.json", (Map<String, Object>) Json.fromJson("{\"a\":{\"prototype\":\"/b.json\",\"items\":[{}]}}"))
                .entry("/b.json", (Map<String, Object>) Json.fromJson("{\"b\":{\"prototype\":\"/c.json\",\"items\":[{}]}}"))
                .entry("/c.json", (Map<String, Object>) Json.fromJson("{\"c\":\"foo\"}"))
                .build();

        final DocumentResolverImpl resolver = new DocumentResolverImpl(res -> {
            System.out.println("Loading " + res);
            return files.get(res);
        });

        final String expected = "{\"a\":{\"prototype\":\"/b.json\",\"items\":[{\"b\":{\"prototype\":\"/c.json\",\"items\":[{\"c\":\"foo\"}]}}]}}";
        final String actual = Json.toJson(resolver.resolvePrototype("/a.json"));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveExtendsOnly() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/base.json", (Map<String, Object>) Json.fromJson("{\"base\":true,\"a\":\"adam\"}"))
                .entry("/level1.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\",\"base\":false,\"a\":\"bert\",\"level2s\":{\"prototype:\":\"/level2.json\",\"items\":[{}]}}"))
                .entry("/level2.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\",\"base\":false,\"a\":\"carl\",\"level3s\":{\"prototype:\":\"/level3.json\",\"items\":[{}]}}"))
                .entry("/level3.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\",\"base\":false,\"a\":\"dave\"}"))
                .build();

        final DocumentResolverImpl resolver = new DocumentResolverImpl(res -> {
            System.out.println("Loading " + res);
            return files.get(res);
        });

        final String original = "{\"extends\":\"/base.json\",\"level1s\":{\"prototype\":\"/level1.json\",\"items\":[{}]}}";
        final String expected = "{\"extends\":\"/base.json\",\"base\":true,\"a\":\"adam\",\"level1s\":{\"prototype\":\"/level1.json\",\"items\":[{}]}}";
        final String actual = Json.toJson(resolver.resolveExtends((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void resolvePrototype5() {

        final Map<String, Map<String, Object>> files =
            mapBuilder("/base.json", (Map<String, Object>) Json.fromJson("{\"base\":true,\"a\":\"adam\"}"))
                .entry("/level1.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\",\"base\":false,\"a\":\"bert\",\"level2s\":{\"prototype\":\"/level2.json\",\"items\":[{}]}}"))
                .entry("/level2.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\",\"base\":false,\"a\":\"dave\"}"))
                .build();

        final DocumentResolverImpl resolver = new DocumentResolverImpl(res -> {
            System.out.println("Loading " + res);
            return requireNonNull(files.get(res), "Could not find file " + res);
        });

        final String expected = "{\"extends\":\"/base.json\",\"base\":false,\"a\":\"bert\",\"level2s\":{\"prototype\":\"/level2.json\",\"items\":[{\"extends\":\"/base.json\",\"base\":false,\"a\":\"dave\"}]}}";
        final String actual = Json.toJson(resolver.resolvePrototype("/level1.json"));
        assertEquals(expected, actual);
    }
}