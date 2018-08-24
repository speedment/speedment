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
public class ResolveTest {

    @BeforeClass
    public static void setUp() {
        Json.PRETTY = false;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveExtendsInline() {

        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"id\":\"a\",\"extends\":{\"name\":\"b\"}}";
        final String expected = "{\"extends\":{\"name\":\"b\"},\"name\":\"b\",\"id\":\"a\"}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveNull() {

        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"name\":null}";
        final String expected = "{\"name\":null}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveExtendsFromFile() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/foo.json", (Map<String, Object>) Json.fromJson("{\"b\":\"bert\",\"c\":\"carl\"}"))
            .build();

        final DocumentResolver resolver = new DocumentResolverImpl(files::get);

        final String original = "{\"a\":\"adam\",\"extends\":\"/foo.json\"}";
        final String expected = "{\"extends\":\"/foo.json\",\"b\":\"bert\",\"c\":\"carl\",\"a\":\"adam\"}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveExtendsFromFileInFile() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/foo.json", (Map<String, Object>) Json.fromJson("{\"b\":\"bert\",\"c\":\"carl\",\"extends\":\"/bar.json\"}"))
                .entry("/bar.json", (Map<String, Object>) Json.fromJson("{\"b\":\"adam\",\"a\":\"dave\"}"))
                .build();

        final DocumentResolver resolver = new DocumentResolverImpl(files::get);

        final String original = "{\"a\":\"adam\",\"extends\":\"/foo.json\"}";
        final String expected = "{\"extends\":\"/foo.json\",\"b\":\"bert\",\"a\":\"adam\",\"c\":\"carl\"}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveEmptyList() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[]}";
        final String expected = "{\"a\":\"adam\",\"list\":{\"items\":[]}}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveListWithEmptyObject() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[{}]}";
        final String expected = "{\"a\":\"adam\",\"list\":{\"items\":[{}]}}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveListWithInnerList() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[{\"inner\":[]}]}";
        final String expected = "{\"a\":\"adam\",\"list\":{\"items\":[{\"inner\":{\"items\":[]}}]}}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveListOfLists() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[[],[],[]]}";
        final String expected = "{\"a\":\"adam\",\"list\":{\"items\":[{\"items\":[]},{\"items\":[]},{\"items\":[]}]}}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveListWithPrototype() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[{}],\"extends\":{\"list\":{\"prototype\":{\"a\":\"arne\"}}}}";
        final String expected = "{\"extends\":{\"list\":{\"prototype\":{\"a\":\"arne\"}}},\"list\":{\"prototype\":{\"a\":\"arne\"},\"items\":[{\"a\":\"arne\"}]},\"a\":\"adam\"}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolvePopulatedListWithPrototype() {
        final DocumentResolver resolver = new DocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[{\"a\":\"bert\",\"b\":\"carl\"}],\"extends\":{\"list\":{\"prototype\":{\"a\":\"arne\"}}}}";
        final String expected = "{\"extends\":{\"list\":{\"prototype\":{\"a\":\"arne\"}}},\"list\":{\"prototype\":{\"a\":\"arne\"},\"items\":[{\"a\":\"bert\",\"b\":\"carl\"}]},\"a\":\"adam\"}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolvePrototypeFromFile() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/foo.json", (Map<String, Object>) Json.fromJson("{\"prototype\":{\"b\":\"bert\",\"c\":\"carl\",\"extends\":\"/bar.json\"}}"))
                .entry("/bar.json", (Map<String, Object>) Json.fromJson("{\"a\":\"curt\"}"))
                .build();

        final DocumentResolver resolver = new DocumentResolverImpl(files::get);

        final String original = "{\"a\":\"adam\",\"list\":{\"items\":[{}],\"extends\":\"/foo.json\"}}";
        final String expected = "{\"a\":\"adam\",\"list\":{\"extends\":\"/foo.json\",\"prototype\":{\"extends\":\"/bar.json\",\"a\":\"curt\",\"b\":\"bert\",\"c\":\"carl\"},\"items\":[{\"extends\":\"/bar.json\",\"a\":\"curt\",\"b\":\"bert\",\"c\":\"carl\"}]}}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }
}