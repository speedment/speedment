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
package com.speedment.common.json.internal;

import com.speedment.common.json.JsonSyntaxException;
import com.speedment.common.json.exception.JsonException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Internal class that parses a stream of JSON characters into an object
 * representation.
 * <p>
 * This implementation includes a line and column counter that makes it easier
 * to debug an erroneous JSON string. These might add additional overhead to the
 * parsing time and should therefore only be used if the input is expected to
 * have errors.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class JsonDeserializer implements AutoCloseable {

    private static final String UNKNOWN_ENUM_CONSTANT = "Unknown enum constant '";
    private static final Charset ENCODING = StandardCharsets.UTF_8;
    private static final int TAB_SIZE = 4;

    private final InputStreamReader reader;
    private final AtomicLong row;
    private final AtomicLong col;
    
    private int character;
    
    public JsonDeserializer(InputStream in) {
        reader = new InputStreamReader(in, ENCODING);
        row = new AtomicLong(0);
        col = new AtomicLong(0);
    }
    
    private enum CloseMethod {
        EXIT_FROM_PARENT, 
        CONTINUE_IN_PARENT, 
        NOT_DECIDED
    }
    
    public Object get() throws IOException {
        final int nextNonBlankspace = nextNonBlankspace();
        switch (nextNonBlankspace) {
            case 0x7B : // { (begin parsing object)
                return parseObject();
            case 0x5B : // [ (begin parsing array)
                return parseArray();
            case 0x22 : // " (begin parsing string)
                return parseString();
            case 0x66 : // f (begin parsing false)
                return parseFalse();
            case 0x74 : // t (begin parsing true)
                return parseTrue();
            case 0x6E : // n (begin parsing null)
                return parseNull();
                
            // Digit '0 - 9'
            case 0x30 : case 0x31 : case 0x32 : case 0x33 : case 0x34 :
            case 0x35 : case 0x36 : case 0x37 : case 0x38 : case 0x39 :
            case 0x2E : // . (decimal sign)
            case 0x2D : // - (minus sign)
                final AtomicReference<Number> number = new AtomicReference<>();
                final CloseMethod closeMethod = parseNumber(number::set);
                if (closeMethod == CloseMethod.NOT_DECIDED) {
                    return number.get();
                }
                throw new JsonSyntaxException(row, col, "parseNumber should return " + CloseMethod.NOT_DECIDED + " but actually returned: " + closeMethod);
            default:
                throw unexpectedCharacterException();
        }
    }
    
    private Map<String, Object> parseObject() throws IOException {
        final Map<String, Object> object = new LinkedHashMap<>();

        firstChar: switch (nextNonBlankspace()) {
            // If the map should be closed with no entries:
            case 0x7D : // } (close the map)
                return object;

            // If this character begins a new entry
            case 0x22 : // " (begin key)

                final CloseMethod close = parseEntryInto(object);
                switch (close) {
                    case EXIT_FROM_PARENT :
                        if (character == 0x7D) { // }
                            return object;
                        } else {
                            throw unexpectedCharacterException();
                        }

                    case CONTINUE_IN_PARENT :
                        break firstChar;

                    case NOT_DECIDED :
                        switch (nextNonBlankspace()) {
                            case 0x2C : // , (continue with next entry)
                                break firstChar;
                            case 0x7D : // } (close the map)
                                return object;
                            default :
                                throw unexpectedCharacterException();
                        }
                    default :
                        throw new IllegalStateException(
                            UNKNOWN_ENUM_CONSTANT + close + "'."
                        );
                }

            // If the first non-whitespace character was not neither
            // a '}' nor a '"':
            default :
                throw unexpectedCharacterException();
        }

        // Parse each remaining entry
        while ((nextNonBlankspace()) == 0x22) { // "
            final CloseMethod close = parseEntryInto(object);

            switch (close) {
                case EXIT_FROM_PARENT:
                    if (character == 0x7D) { // }
                        return object;
                    } else {
                        throw unexpectedCharacterException();
                    }

                case CONTINUE_IN_PARENT:
                    continue;

                case NOT_DECIDED:
                    switch (nextNonBlankspace()) {
                        case 0x2C: // , (continue with next entry)
                            continue;
                        case 0x7D: // } (close the map)
                            return object;
                        default:
                            throw unexpectedCharacterException();
                    }
                default:
                    throw new IllegalStateException(
                        UNKNOWN_ENUM_CONSTANT + close + "'."
                    );
            }
        }
        
        throw unexpectedCharacterException();
    }

    private CloseMethod parseEntryInto(Map<String, Object> object) throws IOException {
        final StringBuilder keyBuilder = new StringBuilder();
        
        parseKey: while (true) {
            switch (next()) {
                
                // If this is an escape character, add the following character
                // without parsing it.
                case 0x5C : // backslash
                    keyBuilder.append(Character.toChars(next()));
                    continue;
                    
                // If this terminates the key, break the loop.
                case 0x22 : // " (end key)
                    break parseKey;
                    
                // Every other character should be added to the key.
                default :
                    keyBuilder.append(Character.toChars(character));
            }
        }
        
        final String key = keyBuilder.toString();
        
        // Read the assignment operator
        if (nextNonBlankspace() != 0x3A) { // :
            throw unexpectedCharacterException();
        }
        
        // Read the value
        switch (nextNonBlankspace()) {
            case 0x7B : // { (begin parsing object)
                putIfUniqueOrThrow(object, key, parseObject());
                return CloseMethod.NOT_DECIDED;
                
            case 0x5B : // [ (begin parsing array)
                putIfUniqueOrThrow(object, key, parseArray());
                return CloseMethod.NOT_DECIDED;
                
            case 0x22 : // " (begin parsing string)
                putIfUniqueOrThrow(object, key, parseString());
                return CloseMethod.NOT_DECIDED;
                
            case 0x66 : // f (begin parsing false)
                putIfUniqueOrThrow(object, key, parseFalse());
                return CloseMethod.NOT_DECIDED;
                
            case 0x74 : // t (begin parsing true)
                putIfUniqueOrThrow(object, key, parseTrue());
                return CloseMethod.NOT_DECIDED;
                
            case 0x6E : // n (begin parsing null)
                putIfUniqueOrThrow(object, key, parseNull());
                return CloseMethod.NOT_DECIDED;
                
            // Digit '0 - 9'
            case 0x30 : case 0x31 : case 0x32 : case 0x33 : case 0x34 :
            case 0x35 : case 0x36 : case 0x37 : case 0x38 : case 0x39 :
            case 0x2E : // . (decimal sign)
            case 0x2D : // - (minus sign)
                return parseNumber(num -> putIfUniqueOrThrow(object, key, num));
                
            default :
                throw unexpectedCharacterException();
        }
    }
    
    private List<Object> parseArray() throws IOException {
        final List<Object> list = new LinkedList<>();
        
        firstChar: switch (nextNonBlankspace()) {
            // If the list should be closed with no entries:
            case 0x5D : // ] (close the list)
                return list;
                
            case 0x7B : // { (begin parsing object)
                list.add(parseObject());

                switch (nextNonBlankspace()) {
                    case 0x2C : // , (continue with next element)
                        break firstChar; // nextEntry
                    case 0x5D : // ] (close the list)
                        return list;
                    default :
                        throw unexpectedCharacterException();
                }

            case 0x5B : // [ (begin parsing array)
                list.add(parseArray());
                
                switch (nextNonBlankspace()) {
                    case 0x2C : // , (continue with next element)
                        break firstChar; // nextEntry
                    case 0x5D : // ] (close the list)
                        return list;
                    default :
                        throw unexpectedCharacterException();
                }
                
            case 0x22 : // " (begin parsing string)
                list.add(parseString());
                
                switch (nextNonBlankspace()) {
                    case 0x2C : // , (continue with next element)
                        break firstChar; // nextEntry
                    case 0x5D : // ] (close the list)
                        return list;
                    default :
                        throw unexpectedCharacterException();
                }
                
            case 0x66 : // f (begin parsing false)
                list.add(parseFalse());
                
                switch (nextNonBlankspace()) {
                    case 0x2C : // , (continue with next element)
                        break firstChar; // nextEntry
                    case 0x5D : // ] (close the list)
                        return list;
                    default :
                        throw unexpectedCharacterException();
                }
                
            case 0x74 : // t (begin parsing true)
                list.add(parseTrue());
                
                switch (nextNonBlankspace()) {
                    case 0x2C : // , (continue with next element)
                        break firstChar; // nextEntry
                    case 0x5D : // ] (close the list)
                        return list;
                    default :
                        throw unexpectedCharacterException();
                }
                
            case 0x6E : // n (begin parsing null)
                list.add(parseNull());
                
                switch (nextNonBlankspace()) {
                    case 0x2C : // , (continue with next element)
                        break firstChar; // nextEntry
                    case 0x5D : // ] (close the list)
                        return list;
                    default :
                        throw unexpectedCharacterException();
                }
                
            // Digit '0 - 9'
            case 0x30 : case 0x31 : case 0x32 : case 0x33 : case 0x34 :
            case 0x35 : case 0x36 : case 0x37 : case 0x38 : case 0x39 :
            case 0x2E : // . (decimal sign)
            case 0x2D : // - (minus sign)
                final CloseMethod method = parseNumber(list::add);
                switch (method) {
                    case CONTINUE_IN_PARENT :
                        break firstChar;
                        
                    case EXIT_FROM_PARENT :
                        if (character == 0x5D) { // ]
                            return list;
                        } else {
                            throw unexpectedCharacterException();
                        }
                        
                    case NOT_DECIDED :
                        switch (nextNonBlankspace()) {
                            case 0x2C : // , (continue with next element)
                                break firstChar;
                            case 0x5D : // ] (close the list)
                                return list;
                            default :
                                throw unexpectedCharacterException();
                        }
                        
                    default :
                        throw new IllegalStateException(
                            UNKNOWN_ENUM_CONSTANT + method + "'."
                        );
                }
                
            default :
                throw unexpectedCharacterException();
        }

        // Parse each remaining entry
        return parseEachRemainingEntry(list);
    }

    private List<Object> parseEachRemainingEntry(List<Object> list) throws IOException {
        while (true) {
            switch (nextNonBlankspace()) {
                case 0x7B: // { (begin parsing object)
                    list.add(parseObject());
                    break;

                case 0x5B: // [ (begin parsing array)
                    list.add(parseArray());
                    break;

                case 0x22: // " (begin parsing string)
                    list.add(parseString());
                    break;

                case 0x66: // f (begin parsing false)
                    list.add(parseFalse());
                    break;

                case 0x74: // t (begin parsing true)
                    list.add(parseTrue());
                    break;

                case 0x6E: // n (begin parsing null)
                    list.add(parseNull());
                    break;

                // Digit '0 - 9'
                case 0x30: case 0x31: case 0x32: case 0x33: case 0x34:
                case 0x35: case 0x36: case 0x37: case 0x38: case 0x39:
                case 0x2E: // . (decimal sign)
                case 0x2D: // - (minus sign)
                    final CloseMethod method = parseNumber(list::add);
                    switch (method) {
                        case CONTINUE_IN_PARENT:
                            continue; // nextEntry

                        case EXIT_FROM_PARENT:
                            if (character == 0x5D) { // ]
                                return list;
                            } else {
                                throw unexpectedCharacterException();
                            }

                        case NOT_DECIDED:
                            switch (nextNonBlankspace()) {
                                case 0x2C: // , (continue with next element)
                                    continue; // nextEntry
                                case 0x5D: // ] (close the list)
                                    return list;
                                default:
                                    throw unexpectedCharacterException();
                            }

                        default:
                            throw new IllegalStateException(
                                UNKNOWN_ENUM_CONSTANT + method + "'."
                            );
                    }

                default:
                    throw unexpectedCharacterException();
            }

            switch (nextNonBlankspace()) {
                case 0x2C: // , (continue with next element)
                    continue; // nextEntry
                case 0x5D: // ] (close the list)
                    return list;
                default:
                    throw unexpectedCharacterException();
            }
        }
    }

    private String parseString() throws IOException {
        final StringBuilder builder = new StringBuilder();

        while (true) {
            switch (next()) {

                // If this is an escape character, add the following character
                // without parsing it.
                case 0x5C: // backslash
                    final int n = next();
                    switch (n) {
                        case 0x22: builder.append('"'); break;  // \"
                        case 0x62: builder.append('\b'); break; // \b
                        case 0x66: builder.append('\f'); break; // \f
                        case 0x6E: builder.append('\n'); break; // \n
                        case 0x72: builder.append('\r'); break; // \r
                        case 0x74: builder.append('\t'); break; // \t
                        case 0x5C: builder.append('\\'); break; // \\
                        default: builder.append('\\').appendCodePoint(n);
                    }
                    continue;

                    // If this terminates the string, break the loop.
                case 0x22: // " (end key)
                    return builder.toString();

                // Every other character should be added to the key.
                default:
                    builder.append(Character.toChars(character));
            }
        }
    }
    
    private Boolean parseFalse() throws IOException {
        if (next() == 0x61    // a
        &&  next() == 0x6C    // l
        &&  next() == 0x73    // s
        &&  next() == 0x65) { // e
            return Boolean.FALSE;
        } else {
            throw unexpectedCharacterException();
        }
    }
    
    private Boolean parseTrue() throws IOException {
        if (next() == 0x72    // r
        &&  next() == 0x75    // u
        &&  next() == 0x65) { // e
            return Boolean.TRUE;
        } else {
            throw unexpectedCharacterException();
        }
    }
    
    private Object parseNull() throws IOException {
        // SonarQube detects bug if next() is called with 0x6C two times
        if (next() == 0x75) {   // u
            if (next() == 0x6C) {    // l
                if (next() == 0x6C) {  // l
                    return null;
                }
            }
        }
        throw unexpectedCharacterException();
    }
    
    private CloseMethod parseNumber(Consumer<Number> consumer) throws IOException {
        final StringBuilder builder = new StringBuilder();
        
        final CloseMethod method;
        parser: while (true) {
            switch (character) {
                // . (decimal sign)
                case 0x2E : 
                    builder.append('.');
                    return parseNumberDecimal(consumer, builder);
                    
                // - (minus sign)
                case 0x2D : 
                    builder.append('-');
                    next();
                    continue;
                    
                // Digit '0 - 9'
                case 0x30 : case 0x31 : case 0x32 : case 0x33 : case 0x34 :
                case 0x35 : case 0x36 : case 0x37 : case 0x38 : case 0x39 :
                    builder.append(Character.toChars(character));
                    next();
                    continue;
                    
                case 0x7D : // }
                case 0x5D : // ]
                    method = CloseMethod.EXIT_FROM_PARENT;
                    break parser;
                    
                case 0x2C : // ,
                    method = CloseMethod.CONTINUE_IN_PARENT;
                    break parser;
                    
                case 0x0A : // new line
                    row.incrementAndGet();
                    col.set(-1);
                    method = CloseMethod.NOT_DECIDED;
                    break parser;
                    
                case 0x09 : // tab
                    col.addAndGet(TAB_SIZE - 1L);
                    method = CloseMethod.NOT_DECIDED;
                    break parser;
                    
                case 0x20 : // space
                case 0x0D : // return (ignore)
                    method = CloseMethod.NOT_DECIDED;
                    break parser;
                    
                default :
                    throw unexpectedCharacterException();
            }
        }
        
        consumer.accept(Long.parseLong(builder.toString()));
        return method;
    }
    
    private CloseMethod parseNumberDecimal(Consumer<Number> consumer, StringBuilder builder) throws IOException {
        final CloseMethod method;
        parser: while (true) {
            switch (next()) {
                // Digit '0 - 9'
                case 0x30 : case 0x31 : case 0x32 : case 0x33 : case 0x34 :
                case 0x35 : case 0x36 : case 0x37 : case 0x38 : case 0x39 :
                    builder.append(Character.toChars(character));
                    continue;
                    
                case 0x7D : // }
                case 0x5D : // ]
                    method = CloseMethod.EXIT_FROM_PARENT;
                    break parser;
                    
                case 0x2C : // ,
                    method = CloseMethod.CONTINUE_IN_PARENT;
                    break parser;
                    
                case 0x0A : // new line
                    row.incrementAndGet();
                    col.set(-1);
                    method = CloseMethod.NOT_DECIDED;
                    break parser;
                    
                case 0x09 : // tab
                    col.addAndGet(TAB_SIZE - 1L);
                    method = CloseMethod.NOT_DECIDED;
                    break parser;
                    
                case 0x20 : // space
                case 0x0D : // return (ignore)
                    method = CloseMethod.NOT_DECIDED;
                    break parser;
                    
                default :
                    throw unexpectedCharacterException();
                    
            }
        }
        
        final String result = builder.toString();
        if (result.equals(".")) {
            throw new JsonSyntaxException(row, col,
                "Unexpected character '.'"
            );
        }
        
        consumer.accept(Double.parseDouble(builder.toString()));
        return method;
    }
    
    private int nextNonBlankspace() throws IOException {
        while ((character = reader.read()) != -1) {
            col.incrementAndGet();
            
            switch (character) {
                case 0x0A : // new line
                    row.incrementAndGet();
                    col.set(-1);
                    continue;
                case 0x09 : // tab
                    col.addAndGet(TAB_SIZE - 1L);
                    continue;
                case 0x20 : // space
                case 0x0D : // return (ignore)
                    continue;
                default :
                    return character;
            }
        }
        
        throw unexpectedEndOfStreamException();
    }
    
    private int next() throws IOException {
        if ((character = reader.read()) != -1) {
            col.incrementAndGet();

            if (character == 0x0A) { // new line
                row.incrementAndGet();
                col.set(-1);

            } else if (character == 0x09) { // new tab
                col.addAndGet(TAB_SIZE - 1L);

            }
            
            return character;
        }
        
        throw unexpectedEndOfStreamException();
    }
    
    private JsonSyntaxException unexpectedCharacterException() {
        final String c = new String(Character.toChars(character));
        throw new JsonSyntaxException(row, col,
            "Unexpected character '" + c + "' (Unicode: " + codePoints(c) + ")"
        );
    }
    
    private JsonSyntaxException duplicateKeyException(String key) {
        throw new JsonSyntaxException(row, col,
            "Duplicate key '" + key + "'"
        );
    }
    
    private JsonSyntaxException unexpectedEndOfStreamException() {
        throw new JsonSyntaxException(row, col,
            "Unexpected end of stream"
        );
    }

    private void putIfUniqueOrThrow(Map<String, Object> object, String key, Object value) {
        if (object.put(key, value) != null) {
            throw duplicateKeyException(key);
        }
    }

    private String codePoints(String c) {
        final StringJoiner str = new StringJoiner(" ");
        for (int i = 0; i < c.length(); i++) {
            str.add(String.valueOf(Character.codePointAt(c, i)));
        }
        return str.toString();
    }
    
    @Override
    public void close() {
        try { 
            reader.close(); 
        } catch (final IOException ex) {
            throw new JsonException("Failed to safely close stream.", ex);
        }
    }
}