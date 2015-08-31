/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.internal.util;

import com.speedment.internal.util.TextUtil;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pemi
 */
public class TextUtilTest {

    private static final String[] TEXT_ARRAY = {"The", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog.", "The", "rain", "in", "Spain", "keeps", "mainly", "on", "the", "plain."};

    public TextUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testWordsOf() {
        System.out.println("wordsOf");
        final String text = Stream.of(TEXT_ARRAY).collect(Collectors.joining(" "));
        final List<String> expResult = Stream.of(TEXT_ARRAY).collect(toList());
        final List<String> result = TextUtil.wordsOf(text).collect(toList());
        assertEquals(expResult, result);

    }

    @Test
    public void testFormatTextBox() {
        System.out.println("formatTextBox");
        String text = "A AB ABC AB Abc ad skh sjh ajsh a aksjka ka sdja aVeryLongWordThatCertainlyWillOverflow bye";
        int width = 10;
        String tenChars
            = "          ";
        String expResult
            = "A AB ABC\n"
            + "AB Abc ad\n"
            + "skh sjh\n"
            + "ajsh a\n"
            + "aksjka ka\n"
            + "sdja\n"
            + "aVeryLongWordThatCertainlyWillOverflow\n"
            + "bye";

        String result = TextUtil.formatTextBox(text, width);

        System.out.println(result);

        assertEquals(expResult, result);
    }

    @Test
    public void testFormatJavaDocBox() {
        System.out.println("formatJavaDocBox");
        String text = "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789.";
        String eRes = "123456789 123456789 123456789 123456789 123456789 123456789 123456789\n123456789 123456789 123456789 123456789 123456789 123456789.";
        String result = TextUtil.formatJavaDocBox(text);
        assertEquals(eRes, result);
    }

}
