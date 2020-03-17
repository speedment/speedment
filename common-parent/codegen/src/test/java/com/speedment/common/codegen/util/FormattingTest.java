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
package com.speedment.common.codegen.util;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

final class FormattingTest {

    private static final String INDENT = "    ";
    private static final String A = "A";
    private static final String B = "B";
    private static final String FOO_PACKAGE_NAME = "com.speedment.test";
    private static final String FOO_SIMPLE_NAME = "Foo";
    private static final String FOO_NAME = FOO_PACKAGE_NAME + "." + FOO_SIMPLE_NAME;

    @Test
    void separate() {
        final String actual = Formatting.separate(",", A, B);
        assertEquals(A + "," + B, actual);
    }

    @Test
    void lcfirst() {
        assertEquals("foo", Formatting.lcfirst("Foo"));
    }

    @Test
    void ucfirst() {
        assertEquals("Foo", Formatting.ucfirst("foo"));
    }

    @Test
    void withFirst() {
        assertEquals("Xoo", Formatting.withFirst("foo", c -> "X"));
    }

    @Test
    void withFirstNull() {
        assertNull(Formatting.withFirst(null, c -> "X"));
    }

    @Test
    void repeat() {
        assertEquals(A + B + A + B, Formatting.repeat(A + B, 2));
    }

    @Test
    void block() {
        final String result = Formatting.block("return 1;");
        assertTrue(result.contains("{"));
        assertTrue(result.contains(" return 1;"));
        assertTrue(result.contains("}"));
    }

    @Test
    void testBlock() {
        final String result = Formatting.block(Stream.of("x++;", "return x;"));
        assertTrue(result.contains("{"));
        assertTrue(result.contains(" x++;"));
        assertTrue(result.contains(" return x;"));
        assertTrue(result.contains("}"));
    }

    @Test
    void testBlock1() {
        final String result = Formatting.block("x++;", "return x;");
        assertTrue(result.contains("{"));
        assertTrue(result.contains(" x++;"));
        assertTrue(result.contains(" return x;"));
        assertTrue(result.contains("}"));
    }

    @Test
    void indent() {
        final String result = Formatting.indent(A + Formatting.nl() + B);
        assertEquals(INDENT + A + Formatting.nl() + INDENT + B, result);
    }

    @Test
    void testIndent() {
        final String result = Formatting.indent(A , B);
        assertEquals(INDENT + A + Formatting.nl() + INDENT + B, result);
    }

    @Test
    void testIndent1() {
        final String result = Formatting.indent(Stream.of(A , B));
        assertEquals(INDENT + A + Formatting.nl() + INDENT + B, result);
    }

    @Test
    void testIndent2() {
        final String result = Formatting.indent(A + Formatting.nl() + B, 2);
        assertEquals(INDENT + INDENT + A + Formatting.nl() + INDENT + INDENT + B, result);
    }

    @Test
    void testIndent0() {
        final String result = Formatting.indent(A + Formatting.nl() + B, 0);
        assertEquals(A + Formatting.nl() + B, result);
    }

    @Test
    void ifelse() {
        assertEquals(A, Formatting.ifelse(Optional.of(A), s -> s, B));
        assertEquals(B, Formatting.ifelse(Optional.empty(), s -> s, B));
    }

    @Test
    void nl() {
        assertEquals("\n", Formatting.nl());
    }

    @Test
    void testNl() {
        final String nl = Formatting.nl();
        try {
            Formatting.nl(A);
            assertEquals(A, Formatting.nl());
            assertEquals(A + A, Formatting.dnl());
        } finally {
            Formatting.nl(nl);
        }
    }

    @Test
    void dnl() {
        assertEquals(Formatting.nl() + Formatting.nl(), Formatting.dnl());
    }

    @Test
    void tab() {
        assertEquals(INDENT, Formatting.tab());
    }

    @Test
    void testTab() {
        final String tab = Formatting.tab();
        try {
            Formatting.tab(A);
            assertEquals(A, Formatting.tab());
        } finally {
            Formatting.tab(tab);
        }
    }

    @Test
    void shortName() {
        assertEquals(FOO_SIMPLE_NAME, Formatting.shortName(FOO_NAME));
    }

    @Test
    void packageName() {
        assertEquals(FOO_PACKAGE_NAME, Formatting.packageName(FOO_NAME).orElseThrow(NoSuchElementException::new));
    }

    @Test
    void packageNameNone() {
        assertEquals(Optional.empty(), Formatting.packageName(A));
    }

    @Test
    void fileToClassName() {
        assertEquals(FOO_SIMPLE_NAME, Formatting.fileToClassName(FOO_SIMPLE_NAME + ".java").orElseThrow(NoSuchElementException::new));
    }

    @Test
    void fileToClassNameWrong() {
        assertEquals(Optional.empty(), Formatting.fileToClassName(FOO_SIMPLE_NAME + ".c++"));
    }

    @Test
    void classToJavaFileName() {
        assertEquals(FOO_SIMPLE_NAME + ".java", Formatting.classToJavaFileName(FOO_SIMPLE_NAME));
    }

    @Test
    void stripGenerics() {
        assertEquals(FOO_SIMPLE_NAME, Formatting.stripGenerics(FOO_SIMPLE_NAME+"<String>"));
        assertEquals(FOO_SIMPLE_NAME, Formatting.stripGenerics(FOO_SIMPLE_NAME+"[]"));
    }

    @Test
    void alignTabs() {
        final String result = Formatting.alignTabs("a\t=1;" + Formatting.nl() + "veryLongName\t=2;");
        final String[] rows = result.split(Formatting.nl());
        assertEquals(rows[0].length(), rows[1].length());
    }

    @Test
    void testAlignTabs() {
        final List<String> rows = new ArrayList<>(Arrays.asList("a\t=1;", "veryLongName\t=2;"));
        Formatting.alignTabs(rows);
        assertEquals(rows.get(0).length(), rows.get(1).length());
    }

    @Test
    void unQuote() {
        assertEquals(A, Formatting.unQuote("\"" + A + "\""));
    }

    @Test
    void staticField() {
        assertEquals("THE_OVAL_ROOM", Formatting.staticField("the_oval_room"));
    }

    @Test
    void toUnderscoreSeparated() {
        assertEquals("the_oval_room", Formatting.toUnderscoreSeparated("theOvalRoom"));
    }

    @Test
    void javaNameFromExternal() {
        assertEquals("TheOvalRoom", Formatting.javaNameFromExternal("the_oval_room"));
    }

    @Test
    void nameFromExternal() {
        assertEquals("TheOvalRoom", Formatting.nameFromExternal("the oval_room "));
    }

    @Test
    void replaceIfJavaUsedWord() {
        assertEquals("static_", Formatting.replaceIfJavaUsedWord("static"));
    }

    @Test
    void replaceIfIllegalJavaIdentifierCharacterEmpty() {
        assertEquals("_", Formatting.replaceIfIllegalJavaIdentifierCharacter(""));
    }

    @Test
    void replaceIfIllegalJavaIdentifierCharacter() {
        assertEquals("_1" + FOO_SIMPLE_NAME + "_", Formatting.replaceIfIllegalJavaIdentifierCharacter("1" + FOO_SIMPLE_NAME + "~"));
    }

    @Test
    void replaceIfIllegalJavaIdentifierCharacter2() {
        assertEquals("_" + FOO_SIMPLE_NAME, Formatting.replaceIfIllegalJavaIdentifierCharacter("~" + FOO_SIMPLE_NAME));
    }

}