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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNullElements;
import static java.util.Collections.unmodifiableSet;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Common formatting methods used when generating code.
 * 
 * @author Emil Forslund
 */
public final class Formatting {

    private Formatting() { }

    /**
     * Returns a string consisting of the specified blocks concatenated
     * and separated by the specified separator.
     * 
     * @param separator The separator.
     * @param blocks All the blocks.
     * @return The concatenated string.
     */
    public static String separate(String separator, String... blocks) {
        requireNonNullElements(blocks);
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
        return withFirst(input, first -> String.valueOf(Character.toLowerCase(first)));
    }

    /**
     * Returns the specified text but with the first character uppercase.
     *
     * @param input The text.
     * @return The resulting text.
     */
    public static String ucfirst(String input) {
        return withFirst(input, first -> String.valueOf(Character.toUpperCase(first)));
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
            return "";
        } else {
            return String.join("",
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
        return "{" + nl + indent(text) + nl + "}";
    }
	
	/**
     * Indents the specified text, surrounds it with brackets and put the
     * content on a separate line.
     * 
	 * @param row   the first row of the text.
	 * @param rows  the rest of the rows.
     * @return      the text with a '{\n' before and a '\n}' afterwards.
     */
    public static String block(String row, String... rows) {
        requireNonNull(row);
        requireNonNullElements(rows);
        return block(
			Arrays.stream(rows).collect(joining(
				nl(), row + nl(), "")
			)
		);
    }
    
    /**
     * Indents the specified text, surrounds it with brackets and put the
     * content on a separate line.
     * 
	 * @param rows  the rest of the rows.
     * @return      the text with a '{\n' before and a '\n}' afterwards.
     */
    public static String block(Stream<String> rows) {
        return block(rows.collect(joining(nl())));
    }

    /**
     * Indents one level after each new-line-character as defined by
     * <code>nl()</code>.
     *
     * @param text  the text to indent.
     * @return      the indented text.
     */
    public static String indent(String text) {
        return indent + text.replaceAll("\\r?\\n", nl + indent);
    }
    
    /**
     * Indents one level after each new-line-character as defined by
     * <code>nl()</code>. If multiple strings are specified, new-line characters
     * will be added between them.
     *
     * @param text  the text to indent.
     * @return      the indented text.
     */
    public static String indent(String... text) {
        return indent + String.join(nl(), text).replaceAll("\\r?\\n", nl + indent);
    }
    
    /**
     * Indents one level after each new-line-character as defined by
     * <code>nl()</code>. f multiple strings are specified, new-line characters
     * will be added between them.
     *
     * @param text  the text to indent.
     * @return      the indented text.
     */
    public static String indent(Stream<String> text) {
        return indent(text.toArray(String[]::new));
    }
    
    /**
     * Indents a variable number of levels level after each new-line-character 
     * as defined by <code>nl()</code>.
     *
     * @param text   the text to indent
     * @param steps  the number of steps to indent
     * @return       the indented text
     */
    public static String indent(String text, int steps) {
        switch (steps) {
            case 0  : return text;
            case 1  : return indent(text);
            default : return indent(indent(text, steps - 1));
        }
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
        Formatting.nl  = nl;
        Formatting.dnl = nl + nl;
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
     * Returns the current tab character. This can be defined by using
     * <code>tab(String)</code>.
     *
     * @return The current tab character.
     */
    public static String tab() {
        return indent;
    }

    /**
     * Sets the tab character to use when generating code. This should only be
     * called before any indentation occurs to prevent errors.
     *
     * @param tab The new tab character.
     */
    public static void tab(String tab) {
        Formatting.indent = tab;
    }

    /**
     * Returns the 'name' part of a long name. This is everything after the last
     * dot for non-parameterized types. For parameterized types the rule applies to
     * the part proceeding the bracket enclosed parameters e.g.
     * {@code long name java.util.Map<String, java.util.Date>}
     * returns {@code Map<String, java.util.Date>}.
     *
     * @param longName The long name.
     * @return The name part.
     */
    public static String shortName(String longName) {
        String temp = longName.replace('$', '.');
        final int openBrPos = temp.indexOf('<');
        String parameters = "";
        if (openBrPos > 0) {
            parameters = temp.substring(openBrPos);
            temp = temp.substring(0, openBrPos);
        }
        if (temp.contains(".")) {
            temp = temp.substring(temp.lastIndexOf('.') + 1);
        }
        return temp + parameters;
    }

    /**
     * Returns the 'package' part of a long name. This is everything before the
     * last dot.
     *
     * @param longName The long name.
     * @return The package part.
     */
    public static Optional<String> packageName(String longName) {
		if (longName.contains(".")) {
			return Optional.of(longName.substring(0,
                    longName.lastIndexOf('.')
                ).replace(" ", ""));

		} else {
			return Optional.empty();
		}
    }
	
	public static Optional<String> fileToClassName(String fileName) {
		if (fileName.endsWith(".java")) {
			String className = fileName;
			className = className.replace('/', '.');
			className = className.replace('\\', '.');
			className = className.substring(0, className.length() - 5);
			return Optional.of(className);
		}
		return Optional.empty();
	}
    
    public static String classToJavaFileName(String longName) {
        return longName.replace('.', '/') + ".java";
	}
    
    /**
     * Removes the generics and array parts of a class name.
     * 
     * @param className  the full class name
     * @return           class name with decorations stripped
     */
    public static String stripGenerics(String className) {
        String name = className;
        
        if (name.contains("<")) {
            name = name.substring(0, name.indexOf('<'));
        }
        
        if (name.contains("[")) {
            name = name.substring(0, name.indexOf('['));
        }
        
        return name;
    }
    
    /**
     * Replaces every tab character ({@code \t}) in the specified rows with a
     * number spaces so that the character indexes align. This is called 
     * recursively until all tab characters have been replaced. If there are no
     * tabs in the text, the method has no effect.
     * <p>
     * This implementation will begin by splitting the specified string into
     * rows, then apply the alignment. Otherwise it is the same as 
     * {@link #alignTabs(List)}.
     * 
     * @param rows  a single string with all the rows to align
     * @return      the same string but with tabs replaced with aligning spaces
     */
    public static String alignTabs(String rows) {
        final List<String> list = Stream.of(rows.split(nl())).collect(toList());
        alignTabs(list);
        return list.stream().collect(joining(nl()));
    }
    
    /**
     * Replaces every tab character ({@code \t}) in the specified rows with a
     * number spaces so that the character indexes align. This is called 
     * recursively until all tab characters have been replaced. If there are no
     * tabs in the text, the method has no effect.
     * <p>
     * <em>Example:</em>
     * If you call the method on the following code:
     * {@code
     *     this.firstname\t = firstname;\t // Nullable
     *     this.lastname\t = lastname;\t // Nullable
     *     this.email\t = email;\t // Nullable
     *     this.age\t = age;
     * }
     * 
     * You will get the following result:
     * {@code
     *     this.firstname = firstname; // Nullable
     *     this.lastname  = lastname;  // Nullable
     *     this.email     = email;     // Nullable
     *     this.age       = age;
     * }
     * 
     * @param rows list of rows to operate on
     */
    public static void alignTabs(List<String> rows) {
        while (true) {
            int maxIndex = -1;
            for (final String row : rows) {
                final int index = row.indexOf('\t');

                if (index > maxIndex) {
                    maxIndex = index;
                }
            }

            if (maxIndex > -1) {
                for (int i = 0; i < rows.size(); i++) {
                    replaceTabsWithSpace(rows, maxIndex, i);
                }
            } else {
                break;
            }
        }
    }

    private static void replaceTabsWithSpace(List<String> rows, int maxIndex, int i) {
        final String row = rows.get(i);
        final int index = row.indexOf('\t');
        if (index > -1) {
            rows.set(i, row.replaceFirst("\t", Formatting.repeat(" ", maxIndex - index)));
        }
    }

    /**
     * Returns the string but with any leading and trailing quotation marks
     * trimmed.
     *
     * @param s  the string to unquote
     * @return   the string without surrounding quotation marks
     */
    public static String unQuote(final String s) {
        requireNonNull(s);
        if (s.startsWith("\"") && s.endsWith("\"")) {
            // Un-quote the name
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    /**
     * Returns a static field name representation of the specified camel-cased
     * string.
     *
     * @param externalName  the string
     * @return              the static field name representation
     */
    public static String staticField(final String externalName) {
        requireNonNull(externalName);
        return toUnderscoreSeparated(javaNameFromExternal(externalName)).toUpperCase();
    }

    /**
     * Turns the specified string into an underscore-separated string.
     *
     * @param javaName  the string to parse
     * @return          as underscore separated
     */
    public static String toUnderscoreSeparated(final String javaName) {
        requireNonNull(javaName);
        final StringBuilder result = new StringBuilder();
        final String input = unQuote(javaName.trim());
        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            if (result.length() == 0) {
                result.append(Character.toLowerCase(c));
            } else if (Character.isUpperCase(c)) {
                result.append("_").append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String javaNameFromExternal(final String externalName) {
        requireNonNull(externalName);
        return replaceIfIllegalJavaIdentifierCharacter(replaceIfJavaUsedWord(nameFromExternal(externalName)));
    }

    public static String nameFromExternal(final String externalName) {
        requireNonNull(externalName);
        String result = unQuote(externalName.trim()); // Trim if there are initial spaces or trailing spaces...
        /*
         CamelCase
           http://stackoverflow.com/questions/4050381/regular-expression-for-checking-if-capital-letters-are-found-consecutively-in-a
           [A-Z] -> \p{Lu}
           [^A-Za-z0-9] -> [^\pL0-90-9]
        */
        result = Stream.of(result.replaceAll("([\\p{Lu}]+)", "_$1")
            .split("[^\\pL0-9]"))
            .map(String::toLowerCase)
            .map(Formatting::ucfirst)
            .collect(Collectors.joining());
        return result;
    }

    public static String replaceIfJavaUsedWord(final String word) {
        requireNonNull(word);
        // We need to replace regardless of case because we do not know how the returned string is to be used
        if (JAVA_USED_WORDS_LOWER_CASE.contains(word.toLowerCase())) {
            // If it is a java reserved/literal/class, add a "_" at the end to avoid naming conflicts
            return word + "_";
        }
        return word;
    }

    public static String replaceIfIllegalJavaIdentifierCharacter(final String word) {
        requireNonNull(word);
        if (word.isEmpty()) {
            return REPLACEMENT_CHARACTER.toString(); // No name is translated to REPLACEMENT_CHARACTER only
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (i == 0) {
                if (Character.isJavaIdentifierStart(c)) {
                    // Fine! Just add the first character
                    sb.append(c);
                } else if (Character.isJavaIdentifierPart(c)) {
                    // Not ok as the first, but ok otherwise. Add the replacement before it
                    sb.append(REPLACEMENT_CHARACTER).append(c);
                } else {
                    // Cannot be used as a java identifier. Replace it
                    sb.append(REPLACEMENT_CHARACTER);
                }
            } else if (Character.isJavaIdentifierPart(c)) {
                // Fine! Just add it
                sb.append(c);
            } else {
                // Cannot be used as a java identifier. Replace it
                sb.append(REPLACEMENT_CHARACTER);
            }

        }
        return sb.toString();
        /*
           We need to replace regardless of case because we do not know how the return string is to be used
           if (JAVA_USED_WORDS_LOWER_CASE.contains(word.toLowerCase())) {
           If it is a java reserved/literal/class, add a "_" at the end to avoid naming conflicts
              return word + "_";
          }
          return word; */
    }

    private static String nl     = "\n";
    private static String dnl    = "\n\n";
    private static String indent = "    ";

    private static final Character REPLACEMENT_CHARACTER = '_';

    /*
       From http://download.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html

       Literals
    */
    static final Set<String> JAVA_LITERAL_WORDS = unmodifiableSet(Stream.of(
        "true", "false", "null"
    ).collect(toSet()));

    // Java reserved keywords
    static final Set<String> JAVA_RESERVED_WORDS = unmodifiableSet(Stream.of(
        // Unused
        "const", "goto",
        // The real ones...
        "abstract",
        "continue",
        "for",
        "new",
        "switch",
        "assert",
        "default",
        "goto",
        "package",
        "synchronized",
        "boolean",
        "do",
        "if",
        "private",
        "this",
        "break",
        "double",
        "implements",
        "protected",
        "throw",
        "byte",
        "else",
        "import",
        "public",
        "throws",
        "case",
        "enum",
        "instanceof",
        "return",
        "transient",
        "catch",
        "extends",
        "int",
        "short",
        "try",
        "char",
        "final",
        "interface",
        "static",
        "void",
        "class",
        "finally",
        "long",
        "strictfp",
        "volatile",
        "const",
        "float",
        "native",
        "super",
        "while"
    ).collect(toSet()));

    static final Set<Class<?>> JAVA_BUILT_IN_CLASSES = unmodifiableSet(Stream.of(
        Boolean.class,
        Byte.class,
        Character.class,
        Double.class,
        Float.class,
        Integer.class,
        Long.class,
        Object.class,
        Short.class,
        String.class,
        BigDecimal.class,
        BigInteger.class,
        boolean.class,
        byte.class,
        char.class,
        double.class,
        float.class,
        int.class,
        long.class,
        short.class
    ).collect(toSet()));

    private static final Set<String> JAVA_BUILT_IN_CLASS_WORDS = unmodifiableSet(JAVA_BUILT_IN_CLASSES.stream().map(Class::getSimpleName).collect(toSet()));

    private static final  Set<String> JAVA_USED_WORDS = unmodifiableSet(Stream.of(
        JAVA_LITERAL_WORDS,
        JAVA_RESERVED_WORDS,
        JAVA_BUILT_IN_CLASS_WORDS
    ).flatMap(Collection::stream)
        .collect(toSet()));

    private static final Set<String> JAVA_USED_WORDS_LOWER_CASE = unmodifiableSet(JAVA_USED_WORDS.stream()
        .map(String::toLowerCase)
        .collect(toSet()));

}