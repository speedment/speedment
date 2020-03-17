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

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * An internal class that can serialize java objects into JSON code.
 * 
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class JsonSerializer {
    
    private final OutputStream out;
    private final boolean pretty;
    private final int tabSize;
    private int level = 0;
    
    public JsonSerializer(OutputStream out, boolean pretty) {
        this.out     = requireNonNull(out);
        this.pretty  = pretty;
        this.tabSize = pretty ? PRETTY_TAB_SIZE : 0;
    }
    
    public void print(Object unknown) throws IOException {
        if (unknown == null) {
            printNull();
        } else {
            final Class<?> type = unknown.getClass();
            if (type == String.class) {
                printString((String) unknown);
            } else if (List.class.isAssignableFrom(type)) {
                @SuppressWarnings("unchecked")
                final List<Object> list = (List<Object>) unknown;
                printList(list);
            } else if (Map.class.isAssignableFrom(type)) {
                @SuppressWarnings("unchecked")
                final Map<String, Object> map = (Map<String, Object>) unknown;
                printMap(map);
            } else if (type == Double.class) {
                printDouble((Double) unknown);
            } else if (type == Float.class) {
                printDouble(((Float) unknown).doubleValue());
            } else if (type == Long.class) {
                printLong((Long) unknown);
            } else if (type == Integer.class) {
                printLong(((Integer) unknown).longValue());
            } else if (type == Short.class) {
                printLong(((Short) unknown).longValue());
            } else if (type == Byte.class) {
                printLong(((Byte) unknown).longValue());
            } else if (type == Boolean.class) {
                printBoolean((Boolean) unknown);
            } else {
                throw new IllegalArgumentException(
                    "Can't parse unsupported type '" + type + "' into JSON."
                );
            }
        }
    }
    
    private void printMap(Map<String, Object> map) throws IOException {
        out.write(BEGIN_OBJECT); // (begin the map)
        level++;
        
        boolean first = true;
        for (final Map.Entry<String, Object> entry : map.entrySet()) {
            
            if (first) {
                first = false;
            } else {
                out.write(SEPARATOR);
            }
            
            if (pretty) {
                out.write(NEW_LINE);
                printIndent();
            }
            
            printString(entry.getKey());
            
            if (pretty) {
                out.write(SPACE);
            }
            
            out.write(ASSIGN);
            
            if (pretty) {
                out.write(SPACE);
            }
            
            print(entry.getValue());
        }
        
        level--;
        
        if (pretty && !first) {
            out.write(NEW_LINE);
            printIndent();
        }
        
        out.write(END_OBJECT); // (close the map)
    }
    
    private void printList(List<Object> list) throws IOException {
        out.write(BEGIN_ARRAY); // (begin the list)
        level++;
        
        boolean first = true;
        for (final Object object : list) {
            
            if (first) {
                first = false;
            } else {
                out.write(SEPARATOR);
            }
            
            if (pretty) {
                out.write(NEW_LINE);
                printIndent();
            }
            
            print(object);
        }
        
        level--;
        
        if (pretty && !first) {
            out.write(NEW_LINE);
            printIndent();
        }
        
        out.write(END_ARRAY); // (close the list)
    }
    
    private void printString(String string) throws IOException {
        out.write(BEGIN_STRING);
        out.write(string.replace("\"", "\\\"").getBytes());
        out.write(END_STRING);
    }
    
    private void printDouble(Double number) throws IOException {
        out.write(Double.toString(number).getBytes());
    }
    
    private void printLong(Long number) throws IOException {
        out.write(Long.toString(number).getBytes());
    }
    
    private void printBoolean(Boolean bool) throws IOException {
        if (bool) {
            out.write("true".getBytes());
        } else {
            out.write("false".getBytes());
        }
    }
    
    private void printNull() throws IOException {
        out.write(0x6E); // n
        out.write(0x75); // u
        out.write(0x6C); // l
        out.write(0x6C); // l
    }

    private void printIndent() throws IOException {
        final int count = tabSize * level;
        for (int i = 0; i < count; i++) {
            out.write(SPACE);
        }
    }
    
    private static final int BEGIN_OBJECT = 0x7B; // {
    private static final int END_OBJECT   = 0x7D; // }
    private static final int BEGIN_ARRAY  = 0x5B; // [
    private static final int END_ARRAY    = 0x5D; // ]
    private static final int BEGIN_STRING = 0x22; // "
    private static final int END_STRING   = 0x22; // "
    private static final int ASSIGN       = 0x3A; // :
    private static final int SEPARATOR    = 0x2C; // ,
    private static final int NEW_LINE     = 0x0A; // new-line
    private static final int SPACE        = 0x20; // space
    
    private static final int PRETTY_TAB_SIZE = 2;
}
