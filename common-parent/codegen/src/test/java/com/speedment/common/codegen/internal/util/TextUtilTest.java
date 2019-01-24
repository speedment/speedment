package com.speedment.common.codegen.internal.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

final class TextUtilTest {

    private static final String[] TEXT_ARRAY = {"The", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog.", "The", "rain", "in", "Spain", "keeps", "mainly", "on", "the", "plain."};

    @Test
    void testWordsOf() {
        final String text = Stream.of(TEXT_ARRAY).collect(Collectors.joining(" "));
        final List<String> expResult = Stream.of(TEXT_ARRAY).collect(toList());
        final List<String> result = TextUtil.wordsOf(text).collect(toList());
        assertEquals(expResult, result);

    }

    @Test
    void testFormatTextBox() {
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

        final String result = TextUtil.formatTextBox(text, width);

        //System.out.println(result);

        assertEquals(expResult, result);
    }

    @Test
    void testFormatJavaDocBox() {
        String text = "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789.";
        String eRes = "123456789 123456789 123456789 123456789 123456789 123456789 123456789\n123456789 123456789 123456789 123456789 123456789 123456789.";
        String result = TextUtil.formatJavaDocBox(text);
        assertEquals(eRes, result);
    }

}