/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.json;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Emil Forslund
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
    public void testParse_NegativeLong() {
        final String json = "{\"id\" : -5678}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Check value: ", -5678L, map.get("id"));
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
    public void testParse_NegativeDouble() {
        final String json = "{\"average\" : -0.6789}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Check value: ", -0.6789, map.get("average"));
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
    
    @Test
    public void testParse_ArrayOfNegativeObjects() {
        final String json = "{\"numbers\" : [{\"one\":-1}, {\"two\":-2}, {\"three\":-3}]}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        
        assertNotNull(map);
        @SuppressWarnings("unchecked")
        final List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("numbers");
        
        assertEquals("Check one: ", -1L, list.get(0).get("one"));
        assertEquals("Check one: ", -2L, list.get(1).get("two"));
        assertEquals("Check one: ", -3L, list.get(2).get("three"));
    }
}