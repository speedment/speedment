package com.speedment.runtime.config.resolver;

import com.speedment.common.json.Json;
import com.speedment.runtime.config.internal.resolver.NewDocumentResolverImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static com.speedment.common.mapbuilder.MapBuilder.mapBuilder;
import static org.junit.Assert.assertEquals;

/**
 * @author Emil Forslund
 * @since  3.1.6
 */
public class NewResolveTest {

    @BeforeClass
    public static void setUp() {
        Json.PRETTY = false;
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveExtendsInline() {

        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

        final String original = "{\"id\":\"a\",\"extends\":{\"name\":\"b\"}}";
        final String expected = "{\"extends\":{\"name\":\"b\"},\"name\":\"b\",\"id\":\"a\"}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveNull() {

        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

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

        final DocumentResolver resolver = new NewDocumentResolverImpl(files::get);

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

        final DocumentResolver resolver = new NewDocumentResolverImpl(files::get);

        final String original = "{\"a\":\"adam\",\"extends\":\"/foo.json\"}";
        final String expected = "{\"extends\":\"/foo.json\",\"b\":\"bert\",\"a\":\"adam\",\"c\":\"carl\"}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveOnlyEmptyList() {
        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

        final String original = "{\"list\":[]}";
        final String expected = "{\"list\":[]}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveOnlyListWithEmptyObject() {
        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

        final String original = "{\"list\":[{}]}";
        final String expected = "{\"list\":[{}]}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveEmptyList() {
        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[]}";
        final String expected = "{\"a\":\"adam\",\"list\":[]}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveListWithEmptyObject() {
        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[{}]}";
        final String expected = "{\"a\":\"adam\",\"list\":[{}]}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveListWithInnerList() {
        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[{\"inner\":[]}]}";
        final String expected = "{\"a\":\"adam\",\"list\":[{\"inner\":[]}]}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveListOfLists() {
        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[[],[],[]]}";
        final String expected = "{\"a\":\"adam\",\"list\":[[],[],[]]}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveListWithPrototype() {
        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

        final String original = "{\"a\":\"adam\",\"list\":[{}],\"extends\":{\"list\":{\"prototype\":{\"a\":\"arne\"}}}}";
        final String expected = "{\"extends\":{\"list\":{\"prototype\":{\"a\":\"arne\"}}},\"list\":{\"prototype\":{\"a\":\"arne\"},\"items\":[{\"a\":\"arne\"}]},\"a\":\"adam\"}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolvePopulatedListWithPrototype() {
        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

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

        final DocumentResolver resolver = new NewDocumentResolverImpl(files::get);

        final String original = "{\"a\":\"adam\",\"list\":{\"items\":[{}],\"extends\":\"/foo.json\"}}";
        final String expected = "{\"a\":\"adam\",\"list\":{\"extends\":\"/foo.json\",\"prototype\":{\"extends\":\"/bar.json\",\"b\":\"bert\",\"c\":\"carl\",\"a\":\"curt\"},\"items\":[{\"extends\":\"/bar.json\",\"b\":\"bert\",\"c\":\"carl\",\"a\":\"curt\"}]}}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveNullOrder() {

        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> null);

        final String original = "{\"c\":\"carl\",\"b\":\"bert\",\"a\":\"adam\",\"extends\":{\"a\":null,\"b\":null}}";
        final String expected = "{\"extends\":{\"a\":null,\"b\":null},\"a\":\"adam\",\"b\":\"bert\",\"c\":\"carl\"}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveMultipleLevels() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/base.json", (Map<String, Object>) Json.fromJson("{\"base\":true,\"a\":\"adam\"}"))
                .entry("/level1.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\",\"base\":false,\"a\":\"bert\",\"level2s\":{\"prototype\":\"/level2.json\",\"items\":[{}]}}"))
                .entry("/level2.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\",\"base\":false,\"a\":\"carl\",\"level3s\":{\"prototype\":\"/level3.json\",\"items\":[{}]}}"))
                .entry("/level3.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\",\"base\":false,\"a\":\"dave\"}"))
                .build();

        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> {
            System.out.println("Loading " + res);
            return files.get(res);
        });

        final String original = "{\"extends\":\"/base.json\",\"level1s\":{\"prototype\":\"/level1.json\",\"items\":[{}]}}";
        final String expected = "{\"extends\":\"/base.json\",\"base\":true,\"a\":\"adam\",\"level1s\":{\"prototype\":\"/level1.json\",\"items\":[{\"extends\":\"/base.json\",\"base\":false,\"a\":\"bert\",\"level2s\":{\"prototype\":\"/level2.json\",\"items\":[{\"extends\":\"/base.json\",\"base\":false,\"a\":\"carl\",\"level3s\":{\"prototype\":\"/level3.json\",\"items\":[{\"extends\":\"/base.json\",\"base\":false,\"a\":\"dave\"}]}}]}}]}}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveMultipleEmptyLevels() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/base.json", (Map<String, Object>) Json.fromJson("{}"))
                .entry("/level1.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\",\"level2s\":{\"prototype\":\"/level2.json\",\"items\":[{}]}}"))
                .entry("/level2.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\",\"level3s\":{\"prototype\":\"/level3.json\",\"items\":[{}]}}"))
                .entry("/level3.json", (Map<String, Object>) Json.fromJson("{\"extends\":\"/base.json\"}"))
                .build();

        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> {
            System.out.println("Loading " + res);
            return files.get(res);
        });

        final String original = "{\"extends\":\"/base.json\",\"level1s\":{\"prototype\":\"/level1.json\",\"items\":[{}]}}";
        final String expected = "{\"extends\":\"/base.json\",\"level1s\":{\"prototype\":\"/level1.json\",\"items\":[{\"extends\":\"/base.json\",\"level2s\":{\"prototype\":\"/level2.json\",\"items\":[{\"extends\":\"/base.json\",\"level3s\":{\"prototype\":\"/level3.json\",\"items\":[{\"extends\":\"/base.json\"}]}}]}}]}}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveMultipleEmptyLevelsWithoutBase() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/level1.json", (Map<String, Object>) Json.fromJson("{\"level2s\":{\"prototype\":\"/level2.json\",\"items\":[{}]}}"))
                .entry("/level2.json", (Map<String, Object>) Json.fromJson("{\"level3s\":{\"prototype\":\"/level3.json\",\"items\":[{}]}}"))
                .entry("/level3.json", (Map<String, Object>) Json.fromJson("{}"))
                .build();

        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> {
            System.out.println("Loading " + res);
            return files.get(res);
        });

        final String original = "{\"level1s\":{\"prototype\":\"/level1.json\",\"items\":[{}]}}";
        final String expected = "{\"level1s\":{\"prototype\":\"/level1.json\",\"items\":[{\"level2s\":{\"prototype\":\"/level2.json\",\"items\":[{\"level3s\":{\"prototype\":\"/level3.json\",\"items\":[{}]}}]}}]}}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testResolveTwoEmptyLevelsWithoutBase() {
        final Map<String, Map<String, Object>> files =
            mapBuilder("/level1.json", (Map<String, Object>) Json.fromJson("{\"level2s\":{\"prototype\":\"/level2.json\",\"items\":[{}]}}"))
                .entry("/level2.json", (Map<String, Object>) Json.fromJson("{}"))
                .build();

        final DocumentResolver resolver = new NewDocumentResolverImpl(res -> {
            System.out.println("Loading " + res);
            return files.get(res);
        });

        final String original = "{\"level1s\":{\"prototype\":\"/level1.json\",\"items\":[{}]}}";
        final String expected = "{\"level1s\":{\"prototype\":\"/level1.json\",\"items\":[{\"level2s\":{\"prototype\":\"/level2.json\",\"items\":[{}]}}]}}";
        final String actual = Json.toJson(resolver.resolve((Map<String, Object>) Json.fromJson(original)));
        assertEquals(expected, actual);
    }
}