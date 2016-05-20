package com.speedment.runtime.internal.util.json;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Emil
 */
public class JsonTest {
    
    @Test
    public void testParse_String() {
        final String json = "{\"message\":\"Hello, World!\"}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Check value: ", "Hello, World!", map.get("message"));
    }
    
    @Test
    public void testParse_String2() {
        final String json = "{\"title\" : \"Greetings!\", \"message\" : \"Hello, World!\"}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Check value: ", "Hello, World!", map.get("message"));
        assertEquals("Check value: ", "Greetings!", map.get("title"));
    }
    
    @Test
    public void testParse_Long() {
        final String json = "{\"id\" : 5678}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Check value: ", 5678L, map.get("id"));
    }
    
    @Test
    public void testParse_Double() {
        final String json = "{\"average\" : 0.6789}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Check value: ", 0.6789, map.get("average"));
    }
    
    @Test
    public void testParse_False() {
        final String json = "{\"condition\" : false}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Check value: ", false, map.get("condition"));
    }
    
    @Test
    public void testParse_True() {
        final String json = "{\"condition\" : true}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Check value: ", true, map.get("condition"));
    }
    
    @Test
    public void testParse_Null() {
        final String json = "{\"random\" : null}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertTrue("Key exists: ", map.containsKey("random"));
        assertNull("Check value: ", map.get("random"));
    }
    
    @Test
    public void testParse_Array() {
        final String json = "{\"items\" : [\"one\", \"two\", \"three\"]}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        final List<String> expected = Arrays.asList("one", "two", "three");
        assertNotNull(map);
        assertEquals("Check value: ", expected, map.get("items"));
    }
    
    @Test
    public void testParse_ArrayOfObjects() {
        final String json = "{\"numbers\" : [{\"one\":1}, {\"two\":2}, {\"three\":3}]}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        
        assertNotNull(map);
        @SuppressWarnings("unchecked")
        final List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("numbers");
        
        assertEquals("Check one: ", 1L, list.get(0).get("one"));
        assertEquals("Check one: ", 2L, list.get(1).get("two"));
        assertEquals("Check one: ", 3L, list.get(2).get("three"));
    }
}
