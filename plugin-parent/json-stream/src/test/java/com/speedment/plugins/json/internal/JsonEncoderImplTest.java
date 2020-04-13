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
package com.speedment.plugins.json.internal;

import com.speedment.plugins.json.*;
import com.speedment.plugins.json.datamodel.*;
import com.speedment.runtime.config.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

final class JsonEncoderImplTest {

    private static final String NAME = "John";
    private static final byte BYTE = 1;
    private static final short SHORT = 2;
    private static final int ID = 3;
    private static final long LONG = 4;
    private static final float FLOAT = 5f;
    private static final double DOUBLE = 6d;
    private static final char CHAR = 7;
    private static final boolean BOOLEAN = true;

    private static final String LANGUAGE_NAME = "English";
    private static final int LANGUAGE_ID = 1;

    private JsonEncoderImpl<User> instance;
    private Project project;
    private UserManager userManager;
    private User user;
    private LanguageManager languageManager;
    private Language language;

    @BeforeEach
    void setup() {
        project = TestUtil.project();
        userManager = new UserManager();
        user = new UserImpl()
                .setId(ID)
                .setName(NAME)
                .setByte(BYTE)
                .setShort(SHORT)
                .setLong(LONG)
                .setFloat(FLOAT)
                .setDouble(DOUBLE)
                .setChar(CHAR)
                .setBoolean(BOOLEAN);

        languageManager = new LanguageManager();
        language = new LanguageImpl()
                .setId(LANGUAGE_ID)
                .setName(LANGUAGE_NAME);

        instance = new JsonEncoderImpl<>(project, userManager);
    }

    @Test
    void getManager() {
        assertSame(userManager, instance.getManager());
    }

    @Test
    void put() {
        instance.put(User.NAME);
        assertContainsWhenRendered(NAME);
    }

    @Test
    void putByte() {
        instance.putByte(User.BYTE_FIELD);
        assertContainsWhenRendered(BYTE);
    }

    @Test
    void putShort() {
        instance.putShort(User.SHORT_FIELD);
        assertContainsWhenRendered(SHORT);
    }

    @Test
    void putInt() {
        instance.putInt(User.ID);
        assertContainsWhenRendered(ID);
    }

    @Test
    void putLong() {
        instance.putLong(User.LONG_FIELD);
        assertContainsWhenRendered(LONG);
    }

    @Test
    void putFloat() {
        instance.putFloat(User.FLOAT_FIELD);
        assertContainsWhenRendered(FLOAT);
    }

    @Test
    void putDouble() {
        instance.putDouble(User.DOUBLE_FIELD);
        assertContainsWhenRendered(DOUBLE);
    }

    @Test
    void putChar() {
        instance.putChar(User.CHAR_FIELD);
        assertContainsWhenRendered(CHAR);
    }

    @Test
    void putBoolean() {
        instance.putBoolean(User.BOOLEAN_FIELD);
        assertContainsWhenRendered(BOOLEAN);
    }

    @Test
    void testPut() {
        instance.put("A", User.NAME.getter());
        assertContainsWhenRendered(NAME, "A");
    }

    @Test
    void testPutByte() {
        instance.putByte("B", User.BYTE_FIELD.getter());
        assertContainsWhenRendered(BYTE, "B");
    }

    @Test
    void testPutShort() {
        instance.putShort("S", User.SHORT_FIELD.getter());
        assertContainsWhenRendered(SHORT, "S");
    }

    @Test
    void testPutInt() {
        instance.putInt("I", User.ID.getter());
        assertContainsWhenRendered(ID, "I");
    }

    @Test
    void testPutLong() {
        instance.putLong("L", User.LONG_FIELD.getter());
        assertContainsWhenRendered(LONG, "L");
    }

    @Test
    void testPutFloat() {
        instance.putFloat("F", User.FLOAT_FIELD.getter());
        assertContainsWhenRendered(FLOAT, "F");
    }

    @Test
    void testPutDouble() {
        instance.putDouble("D", User.DOUBLE_FIELD.getter());
        assertContainsWhenRendered(DOUBLE, "D");
    }

    @Test
    void testPutChar() {
        instance.putChar("C", User.CHAR_FIELD.getter());
        assertContainsWhenRendered(CHAR, "C");
    }

    @Test
    void testPutBoolean() {
        instance.putBoolean("B", User.BOOLEAN_FIELD.getter());
        assertContainsWhenRendered(BOOLEAN, "B");
    }

    @Test
    void testPut1() {
        final JsonEncoder<Language> other = new JsonEncoderImpl<>(project, languageManager);
        other.put(Language.NAME);
        instance.put(User.LANGUAGE, other);
        assertContainsWhenRendered( "Swedish");
    }

    @Test
    void testPut2() {
        final String label = "lang";
        final JsonEncoder<Language> other = new JsonEncoderImpl<>(project, languageManager);
        other.put(Language.NAME);
        instance.put(label, User.LANGUAGE.finder(languageManager.getTableIdentifier(), languageManager::stream), other);
        final String actual = instance.apply(user);
        assertContainsWhenRendered("Swedish");
    }

    @Test
    void putStreamer() {
        final String label = "lang";
        final JsonEncoder<Language> other = new JsonEncoderImpl<>(project, languageManager);
        other.put(Language.NAME);
        instance.putStreamer(label, u -> Stream.of(language), other);
        instance.put(User.NAME);
        assertContainsWhenRendered(NAME, LANGUAGE_NAME, label);
    }

    @Test
    void testPutStreamer() {
        final String label = "lang";
        final JsonEncoder<Language> other = new JsonEncoderImpl<>(project, languageManager);
        other.put(Language.NAME);
        instance.putStreamer(label, u -> Stream.of(language), l -> "\"" + l.getName() + "\"");
        instance.put(User.NAME);
        assertContainsWhenRendered(NAME, LANGUAGE_NAME, label);
    }

    @Test
    void remove() {
        instance.put(User.NAME);
        instance.remove(User.NAME);
        assertContainsWhenRendered(); // Nothing
    }

    @Test
    void testRemove() {
        final String label = "A";
        instance.put(label, User.NAME.getter());
        instance.remove(label);
        assertContainsWhenRendered(); // Nothing
    }

    @Test
    void apply() {
        instance.apply(null);
    }

    @Test
    void applyNull() {
        assertEquals("null", instance.apply(null));
    }

    @Test
    void applyWithOptionalEmptyProperty() {
        instance.put("optional", e -> Optional.empty());
        final String actual = instance.apply(user);
        assertTrue(actual.contains("optional"));
        assertTrue(actual.contains("null"));
    }

    @Test
    void applyWithOptionalProperty() {
        final String val = "A";
        instance.put("optional", e -> Optional.of(val));
        final String actual = instance.apply(user);
        assertTrue(actual.contains("optional"));
        assertTrue(actual.contains(val));
    }

    @Test
    void applyWithNullProperty() {
        final String val = "A";
        instance.put("optional", e -> null);
        final String actual = instance.apply(user);
        assertTrue(actual.contains("optional"));
        assertTrue(actual.contains("null"));
    }

    @Test
    void collector() {
        instance.putInt(User.ID);
        instance.put(User.NAME);
        final JsonCollector<User> collector = instance.collector();
        final String json = Stream.of(user).collect(collector);
        assertTrue(json.contains(NAME));
        assertTrue(json.contains(Integer.toString(ID)));
    }

    void assertContainsWhenRendered(Object... items) {
        final Set<Object> has = Stream.of(items).collect(toSet());
        final Set<Object> hasNot = Stream.of(ID, NAME, BYTE, SHORT, LONG, FLOAT, DOUBLE, CHAR, BOOLEAN).filter(o -> !has.contains(o)).collect(toSet());
        final String actual = instance.apply(user);
        has.forEach(o -> assertTrue(actual.contains(o.toString()), "result " + actual + " did not contain " + o.toString()));
        hasNot.forEach(o -> assertFalse(actual.contains(o.toString()), "result " + actual + " contains " + o.toString()));
    }
}