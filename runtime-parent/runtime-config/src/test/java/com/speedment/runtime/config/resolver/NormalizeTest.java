package com.speedment.runtime.config.resolver;

import com.speedment.common.json.Json;
import com.speedment.runtime.config.internal.resolver.DocumentResolverImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static com.speedment.common.mapbuilder.MapBuilder.mapBuilder;
import static org.junit.Assert.assertEquals;

/**
 * @author Emil Forslund
 * @since  3.1.6
 */
public class NormalizeTest {

    @BeforeClass
    public static void setUp() {
        Json.PRETTY = false;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeEmpty() {

        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{}";
        final String expected = "{}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeSingleton() {

        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"arne\"}";
        final String expected = "{\"a\":\"arne\"}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeNull() {

        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":null}";
        final String expected = "{}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeExtendsInline() {

        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"extends\":{\"name\":\"b\"},\"name\":\"b\",\"id\":\"a\"}";
        final String expected = "{\"extends\":{\"name\":\"b\"},\"id\":\"a\"}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeExtendsFromFile() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/foo.json", (Map<String, Object>) Json.fromJson("{\"b\":\"bert\",\"c\":\"carl\"}"))
                .build();

        final DocumentResolver resolver = new DocumentResolverImpl(files::get);

        final String original = "{\"extends\":\"/foo.json\",\"b\":\"bert\",\"c\":\"carl\",\"a\":\"adam\"}";
        final String expected = "{\"extends\":\"/foo.json\",\"a\":\"adam\"}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeExtendsFromFileInFile() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/foo.json", (Map<String, Object>) Json.fromJson("{\"b\":\"bert\",\"c\":\"carl\",\"extends\":\"/bar.json\"}"))
                .entry("/bar.json", (Map<String, Object>) Json.fromJson("{\"b\":\"adam\",\"a\":\"dave\"}"))
                .build();

        final DocumentResolver resolver = new DocumentResolverImpl(files::get);

        final String original = "{\"extends\":\"/foo.json\",\"b\":\"bert\",\"a\":\"adam\",\"c\":\"carl\"}";
        final String expected = "{\"extends\":\"/foo.json\",\"a\":\"adam\"}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeEmptyList() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":{\"items\":[]}}";
        final String expected = "{\"a\":\"adam\",\"list\":[]}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeListWithEmptyObject() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":{\"items\":[{}]}}";
        final String expected = "{\"a\":\"adam\",\"list\":[{}]}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeListWithInnerList() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":{\"items\":[{\"inner\":{\"items\":[]}}]}}";
        final String expected = "{\"a\":\"adam\",\"list\":[{\"inner\":[]}]}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeListOfLists() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":{\"items\":[{\"items\":[]},{\"items\":[]},{\"items\":[]}]}}";
        final String expected = "{\"a\":\"adam\",\"list\":[[],[],[]]}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizeListWithPrototype() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"extends\":{\"list\":{\"prototype\":{\"a\":\"arne\"}}},\"list\":{\"prototype\":{\"a\":\"arne\"},\"items\":[{\"a\":\"arne\"}]},\"a\":\"adam\"}";
        final String expected = "{\"extends\":{\"list\":{\"prototype\":{\"a\":\"arne\"}}},\"list\":[{}],\"a\":\"adam\"}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizePopulatedListWithPrototype() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"extends\":{\"list\":{\"prototype\":{\"a\":\"arne\"}}},\"list\":{\"prototype\":{\"a\":\"arne\"},\"items\":[{\"a\":\"bert\",\"b\":\"carl\"}]},\"a\":\"adam\"}";
        final String expected = "{\"extends\":{\"list\":{\"prototype\":{\"a\":\"arne\"}}},\"list\":[{\"a\":\"bert\",\"b\":\"carl\"}],\"a\":\"adam\"}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testNormalizePrototypeFromFile() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/foo.json", (Map<String, Object>) Json.fromJson("{\"prototype\":{\"b\":\"bert\",\"c\":\"carl\",\"extends\":\"/bar.json\"}}"))
                .entry("/bar.json", (Map<String, Object>) Json.fromJson("{\"a\":\"curt\"}"))
                .build();

        final DocumentResolver resolver = new DocumentResolverImpl(files::get);



        final String original = "{\"a\":\"adam\",\"list\":{\"extends\":\"/foo.json\",\"prototype\":{\"extends\":\"/bar.json\",\"a\":\"curt\",\"b\":\"bert\",\"c\":\"carl\"},\"items\":[{\"extends\":\"/bar.json\",\"a\":\"curt\",\"b\":\"bert\",\"c\":\"carl\"}]}}";
        final String expected = "{\"a\":\"adam\",\"list\":{\"extends\":\"/foo.json\",\"items\":[{}]}}";
        final String actual = Json.toJson(resolver.normalize((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }
}
