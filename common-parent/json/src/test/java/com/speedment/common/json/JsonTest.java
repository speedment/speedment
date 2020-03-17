/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Execution(ExecutionMode.CONCURRENT)
final class JsonTest {
    
    @Test
    void testParse_String() {
        final String json = "{\"message\":\"Hello, World!\"}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Hello, World!", map.get("message"));
    }
    
    @Test
    void testParse_String2() {
        final String json = "{\"title\" : \"Greetings!\", \"message\" : \"Hello, World!\"}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Hello, World!", map.get("message"));
        assertEquals("Greetings!", map.get("title"));
    }
    
    @Test
    void testParse_Long() {
        final String json = "{\"id\" : 5678}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals(5678L, map.get("id"));
    }
    
    @Test
    void testParse_NegativeLong() {
        final String json = "{\"id\" : -5678}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals(-5678L, map.get("id"));
    }
    
    @Test
    void testParse_Double() {
        final String json = "{\"average\" : 0.6789}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals(0.6789, map.get("average"));
    }
    
    @Test
    void testParse_NegativeDouble() {
        final String json = "{\"average\" : -0.6789}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals(-0.6789, map.get("average"));
    }
    
    @Test
    void testParse_False() {
        final String json = "{\"condition\" : false}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals(false, map.get("condition"));
    }
    
    @Test
    void testParse_True() {
        final String json = "{\"condition\" : true}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals(true, map.get("condition"));
    }
    
    @Test
    void testParse_Null() {
        final String json = "{\"random\" : null}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertTrue(map.containsKey("random"));
        assertNull(map.get("random"));
    }
    
    @Test
    void testParse_Array() {
        final String json = "{\"items\" : [\"one\", \"two\", \"three\"]}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        final List<String> expected = Arrays.asList("one", "two", "three");
        assertNotNull(map);
        assertEquals(expected, map.get("items"));
    }
    
    @Test
    void testParse_ArrayOfObjects() {
        final String json = "{\"numbers\" : [{\"one\":1}, {\"two\":2}, {\"three\":3}]}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        
        assertNotNull(map);
        @SuppressWarnings("unchecked")
        final List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("numbers");
        
        assertEquals(1L, list.get(0).get("one"));
        assertEquals(2L, list.get(1).get("two"));
        assertEquals(3L, list.get(2).get("three"));
    }
    
    @Test
    void testParse_ArrayOfNegativeObjects() {
        final String json = "{\"numbers\" : [{\"one\":-1}, {\"two\":-2}, {\"three\":-3}]}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        
        assertNotNull(map);
        @SuppressWarnings("unchecked")
        final List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("numbers");
        
        assertEquals(-1L, list.get(0).get("one"));
        assertEquals(-2L, list.get(1).get("two"));
        assertEquals(-3L, list.get(2).get("three"));
    }

    @Test
    void testParse_EscapedString() {
        final String json = "{\"message\":\"Hello, \\\"World\\\"!\\n\"}";
        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) Json.fromJson(json);
        assertNotNull(map);
        assertEquals("Hello, \"World\"!\n", map.get("message"));
    }

    @TestFactory
    Stream<DynamicTest> toJson() {
        final Map<String, Object> testCases = toJsonTestCases();

        assertThrows(IllegalArgumentException.class, () -> Json.toJson(new RuntimeException()));

        return testCases.entrySet().stream().map(testCase -> dynamicTest(testCase.getKey(), () -> {
            final Object value = testCase.getValue();

            final String pretty = Json.toJson(value);
            assertNotNull(pretty);

            final String compressed = Json.toJson(value, false);
            assertNotNull(compressed);

            final OutputStream prettyOutput = new DataOutputStream(new ByteArrayOutputStream());
            final OutputStream compressedOutput = new DataOutputStream(new ByteArrayOutputStream());

            Json.toJson(value, prettyOutput);
            Json.toJson(value, compressedOutput, false);
        }));
    }

    @TestFactory
    Stream<DynamicTest> fromJson() {
        final Map<String, String> testCases = fromJsonTestCases();
        final Map<String, String> invalidTestCases = fromJsonInvalidTestCases();

        Stream<DynamicTest> validTests =
            testCases.entrySet().stream().map(testCase -> dynamicTest(testCase.getKey(), () -> {
                final String value = testCase.getValue();

                assertDoesNotThrow(() -> Json.fromJson(value));
            }));

        Stream<DynamicTest> invalidTests =
            invalidTestCases.entrySet().stream().map(testCase -> dynamicTest(testCase.getKey(), () -> {
                final String value = testCase.getValue();

                assertThrows(JsonSyntaxException.class, () -> Json.fromJson(value));
            }));

        return Stream.concat(validTests, invalidTests);
    }

    private Map<String, Object> toJsonTestCases() {
        final Map<String, Object> testCases = new HashMap<>();

        final Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "Michael");

        final List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        list.add(map);

        testCases.put("testing null serialization", null);
        testCases.put("testing map serialization", map);
        testCases.put("testing list serialization", list);
        testCases.put("testing double serialization", 1d);
        testCases.put("testing float serialization", 1f);
        testCases.put("testing long serialization", 1L);
        testCases.put("testing integer serialization", 1);
        testCases.put("testing short serialization", (short) 1);
        testCases.put("testing byte serialization", (byte) 1);
        testCases.put("testing true serialization", true);
        testCases.put("testing false serialization", false);

        return testCases;
    }

    private Map<String, String> fromJsonTestCases() {
        final Map<String, String> testCases = new HashMap<>();

        final String emptyJsonObject = "{}";
        final String emptyJsonArray = "[]";

        final String jsonNull = "null";
        final String jsonObject = "{\"id\":1,\n \"attributes\":{\"name\":\"Michael\"},\n \"key\":\"value\"}";
        final String jsonArray_object = "[{}]";
        final String jsonArray_objectWith = "[{}, {}]";
        final String jsonArray_array = "[[]]";
        final String jsonArray_arrayWith = "[[], []]";
        final String jsonArray_string = "[\"string\"]";
        final String jsonArray_strings = "[\"string\", \"\", \"hello\"]";
        final String jsonArray_null = "[null]";
        final String jsonArray_nullWith = "[null, null]";
        final String jsonArray_true = "[true]";
        final String jsonArray_trueWith = "[true, false]";
        final String jsonArray_false = "[false]";
        final String jsonArray_falseWith = "[false, true]";
        final String jsonArray_number = "[1]";
        final String jsonArray_numberWith = "[1, 2]";
        final String jsonArray_decimal = "[1.1]";
        final String jsonArray_decimalWith = "[1.1, 2.2]";
        final String jsonString_raw = "\"string\"";
        final String jsonString_b = "\"\\b\"";
        final String jsonString_f = "\"\\f\"";
        final String jsonString_n = "\"\\n\"";
        final String jsonString_r = "\"\\r\"";
        final String jsonString_t = "\"\\t\"";
        final String jsonTrue = "true";
        final String jsonFalse = "false";

        testCases.put("testing json null deserialization", jsonNull);
        testCases.put("testing json object deserialization", jsonObject);
        testCases.put("testing json array array deserialization (1)", jsonArray_array);
        testCases.put("testing json array array deserialization (2)", jsonArray_arrayWith);
        testCases.put("testing json object array deserialization (1)", jsonArray_object);
        testCases.put("testing json object array deserialization (2)", jsonArray_objectWith);
        testCases.put("testing json string array deserialization (1)", jsonArray_string);
        testCases.put("testing json string array deserialization (2)", jsonArray_strings);
        testCases.put("testing json null array deserialization (1)", jsonArray_null);
        testCases.put("testing json null array deserialization (2)", jsonArray_nullWith);
        testCases.put("testing json boolean array deserialization (1)", jsonArray_true);
        testCases.put("testing json boolean array deserialization (2)", jsonArray_trueWith);
        testCases.put("testing json boolean array deserialization (3)", jsonArray_false);
        testCases.put("testing json boolean array deserialization (4)", jsonArray_falseWith);
        testCases.put("testing json number array deserialization (1)", jsonArray_number);
        testCases.put("testing json number array deserialization (2)", jsonArray_numberWith);
        testCases.put("testing json decimal array deserialization (1)", jsonArray_decimal);
        testCases.put("testing json decimal array deserialization (2)", jsonArray_decimalWith);
        testCases.put("testing empty json object deserialization", emptyJsonObject);
        testCases.put("testing empty json array deserialization", emptyJsonArray);
        testCases.put("testing json string deserialization (1)", jsonString_raw);
        testCases.put("testing json string deserialization (2)", jsonString_b);
        testCases.put("testing json string deserialization (3)", jsonString_f);
        testCases.put("testing json string deserialization (4)", jsonString_n);
        testCases.put("testing json string deserialization (5)", jsonString_r);
        testCases.put("testing json string deserialization (6)", jsonString_t);
        testCases.put("testing json boolean deserialization (1)", jsonTrue);
        testCases.put("testing json boolean deserialization (2)", jsonFalse);

        return testCases;
    }

    private Map<String, String> fromJsonInvalidTestCases() {
        final Map<String, String> testCases = new HashMap<>();

        final String jsonObject_notClosed = "{\"id\":1";
        final String jsonObject_missingKey = "{: 1}";
        final String jsonObject_missingValue = "{\"id\": }";
        final String jsonObject_missingColon = "{\"id\" 1}";
        final String jsonObject_keyNotClosed = "{\"id:1}";
        final String jsonObject_invalidEnding = "{\"a\"";
        final String jsonArray_notClosed = "[{}";

        testCases.put("testing non-closed json object deserialization", jsonObject_notClosed);
        testCases.put("testing missing key json object deserialization", jsonObject_missingKey);
        testCases.put("testing missing value json object deserialization", jsonObject_missingValue);
        testCases.put("testing missing colon json object deserialization", jsonObject_missingColon);
        testCases.put("testing non-closed key json object deserialization", jsonObject_keyNotClosed);
        testCases.put("testing invalid ending json object deserialization", jsonObject_invalidEnding);
        testCases.put("testing non-closed json array deserialization", jsonArray_notClosed);

        final String invalidNull_1 = "nul-";
        final String invalidNull_2 = "nu-";
        final String invalidNull_3 = "n-";
        final String invalidNull_4 = "n";

        testCases.put("testing invalid null serialization (1)", invalidNull_1);
        testCases.put("testing invalid null serialization (2)", invalidNull_2);
        testCases.put("testing invalid null serialization (3)", invalidNull_3);
        testCases.put("testing invalid null serialization (4)", invalidNull_4);

        final String invalidTrue_1 = "tru-";
        final String invalidTrue_2 = "tr-";
        final String invalidTrue_3 = "t-";
        final String invalidTrue_4 = "t";

        testCases.put("testing invalid true serialization (1)", invalidTrue_1);
        testCases.put("testing invalid true serialization (2)", invalidTrue_2);
        testCases.put("testing invalid true serialization (3)", invalidTrue_3);
        testCases.put("testing invalid true serialization (4)", invalidTrue_4);

        final String invalidFalse_1 = "fals-";
        final String invalidFalse_2 = "fal-";
        final String invalidFalse_3 = "fa-";
        final String invalidFalse_4 = "f-";
        final String invalidFalse_5 = "f";

        testCases.put("testing invalid false serialization (1)", invalidFalse_1);
        testCases.put("testing invalid false serialization (2)", invalidFalse_2);
        testCases.put("testing invalid false serialization (3)", invalidFalse_3);
        testCases.put("testing invalid false serialization (4)", invalidFalse_4);
        testCases.put("testing invalid false serialization (5)", invalidFalse_5);

        return testCases;
    }

}
