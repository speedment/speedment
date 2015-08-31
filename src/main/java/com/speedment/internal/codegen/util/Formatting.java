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
package com.speedment.internal.codegen.util;

import static com.speedment.internal.util.NullUtil.requireNonNulls;
import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Emil Forslund
 */
public final class Formatting {
    
    /**
     * Returns a string consisting of the specified blocks concatenated
     * and separated by the specified separator.
     * 
     * @param separator The separator.
     * @param blocks All the blocks.
     * @return The concatenated string.
     */
    public static String separate(String separator, String... blocks) {
        requireNonNulls(blocks);
        return Stream.of(blocks)
            .collect(joining(separator));
    }

    /**
     * Returns the specified text but with the first character lowercase.
     *
     * @param input The text.
     * @return The resulting text.
     */
    public static String lcfirst(String input) {
        return withFirst(input, (first) -> String.valueOf(Character.toLowerCase(first)));
    }

    /**
     * Returns the specified text but with the first character uppercase.
     *
     * @param input The text.
     * @return The resulting text.
     */
    public static String ucfirst(String input) {
        return withFirst(input, (first) -> String.valueOf(Character.toUpperCase(first)));
    }

    /**
     * Does something with the first character in the specified String.
     *
     * @param input The String.
     * @param callback The something.
     * @return The new String.
     */
    public static String withFirst(String input, Function<Character, String> callback) {
        if (input == null) {
            return null;
        } else if (input.length() == 0) {
            return EMPTY;
        } else {
            return String.join(EMPTY,
                    callback.apply(input.charAt(0)),
                    input.subSequence(1, input.length())
            );
        }
    }
	
	/**
	 * Repeats the specified substring count times.
	 * @param str The string to repeat.
	 * @param count The number of times to repeat it.
	 * @return The new String.
	 */
	public static String repeat(String str, int count) {
		final StringBuilder result = new StringBuilder();

		for (int i = 0; i < count; i++) {
			result.append(str);
		}
		
		return result.toString();
	}

    /**
     * Indents the specified text, surrounds it with brackets and put the
     * content on a separate line.
     *
     * @param text The text to surround.
     * @return The text with a '{\n' before and a '\n}' afterwards.
     */
    public static String block(String text) {
        return BS + nl + indent(text) + nl + BE;
    }
	
	/**
     * Indents the specified text, surrounds it with brackets and put the
     * content on a separate line.
	 * @param row The first row of the text.
	 * @param rows The rest of the rows.
     * @return The text with a '{\n' before and a '\n}' afterwards.
     */
    public static String block(String row, String... rows) {
        requireNonNull(row);
        requireNonNulls(rows);
        return block(
			Arrays.stream(rows).collect(joining(
				nl(), row + nl(), EMPTY)
			)
		);
    }
    
    /**
     * Indents the specified text, surrounds it with brackets and put the
     * content on a separate line.
	 * @param rows The rest of the rows.
     * @return The text with a '{\n' before and a '\n}' afterwards.
     */
    public static String block(Stream<String> rows) {
        return block(rows.collect(joining(nl())));
    }

    /**
     * Indents one level after each new-line-character as defined by
     * <code>nl()</code>.
     *
     * @param text The text to indent.
     * @return The indented text.
     */
    public static String indent(String text) {
        return tab + text.replaceAll("\\r?\\n", nltab);
    }
	
	/**
	 * If the condition is present, the true-function will be called
	 * with its value and the result will be returned. Else the
	 * false-value will be returned.
	 * @param <E> The inner type of the condition.
	 * @param <R> The result type.
	 * @param condition The condition to be evaluated for presence.
	 * @param trueMap The function that will be called if present.
	 * @param falseValue The value returned if the condition is not present.
	 * @return The value.
	 */
	public static <E, R> R ifelse(Optional<E> condition, Function<E, R> trueMap, R falseValue) {
		if (condition.isPresent()) {
			return trueMap.apply(condition.get());
		} else {
			return falseValue;
		}
	}

    /**
     * Returns the new-line-character as defined in <code>nl(String)</code>.
     *
     * @return The new-line character.
     */
    public static String nl() {
        return nl;
    }

    /**
     * Sets which new-line-character to use. Should be set before any code
     * generation happens for indentation to work as expected.
     *
     * @param nl The new character to use.
     */
    public static void nl(String nl) {
        Formatting.nl = nl;
        Formatting.dnl = nl + nl;
        Formatting.nltab = nl + tab;
        Formatting.scnl = SC + nl;
        Formatting.scdnl = SC + dnl;
		Formatting.cnl = COMMA + nl; 
    }

    /**
     * Returns a semicolon followed by a new line character as defined by the
     * <code>nl(String)</code> function.
     *
     * @return A semicolon (;) followed by a new line.
     */
    public static String scnl() {
        return scnl;
    }

    /**
     * Returns a semicolon followed by two new lines character as defined by the
     * <code>nl(String)</code> function.
     *
     * @return A semicolon (;) followed by two new new lines.
     */
    public static String scdnl() {
        return scdnl;
    }

    /**
     * Returns two new-line-characters as defined in <code>nl(String)</code>.
     *
     * @return The new-line character.
     */
    public static String dnl() {
        return dnl;
    }
	
	/**
     * Returns a comma followed by a new-line-character as defined in 
	 * <code>nl(String)</code>.
     *
     * @return Comma followed by new line.
     */
    public static String cnl() {
        return cnl;
    }

    /**
     * Returns the current tab character. This can be defined by using
     * <code>tab(String)</code>.
     *
     * @return The current tab character.
     */
    public static String tab() {
        return tab;
    }

    /**
     * Sets the tab character to use when generating code. This should only be
     * called before any indentation occurs to prevent errors.
     *
     * @param tab The new tab character.
     */
    public static void tab(String tab) {
        Formatting.tab = tab;
        Formatting.nltab = nl + tab;
    }

    /**
     * Returns the 'name' part of a long name. This is everything after the last
     * dot.
     *
     * @param longName The long name.
     * @return The name part.
     */
    public static String shortName(String longName) {
        if (longName.contains(DOT)) {
            return longName.substring(longName.lastIndexOf(DOT) + 1);
        } else {
            return longName;
        }
    }

    /**
     * Returns the 'package' part of a long name. This is everything before the
     * last dot.
     *
     * @param longName The long name.
     * @return The package part.
     */
    public static Optional<String> packageName(String longName) {
		if (longName.contains(DOT)) {
			return Optional.of(longName.substring(0,
				longName.lastIndexOf(DOT)
			));
		} else {
			return Optional.empty();
		}
    }
	
	public static Optional<String> fileToClassName(String fileName) {
		Optional<String> result = Optional.empty();
		if (fileName.endsWith(".java")) {
			String className = fileName;
			className = className.replace('/', '.');
			className = className.replace('\\', '.');
			className = className.substring(0, className.length() - 6);
			result = Optional.of(className);
		}
		return result;
	}
    
    public static String classToJavaFileName(String longName) {
        return longName.replace('.', '/') + ".java";
	}

    private static String nl = "\n",
            dnl = "\n\n",
            tab = "\t",
            nltab = "\n\t",
            scnl = ";\n",
            scdnl = ";\n\n",
			cnl = ",\n";

    public final static String BS = "{",
            BE = "}",
            PS = "(",
            PE = ")",
            AS = "[",
            AE = "]",
            SS = "<",
            SE = ">",
            SPACE = " ",
            EMPTY = "",
            COMMA = ",",
            COMMA_SPACE = ", ",
            SC = ";",
            DOT = ".",
            AT = "@",
			EQUALS = "=",
			AND = "&",
			STAR = "*",
			SLASH = "/",
			H = "\"",
            DOLLAR = "$";
    
    /**
     * Utility classes should not be instantiated.
     */
    private Formatting() { instanceNotAllowed(getClass()); }
}
