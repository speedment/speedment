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
package com.speedment.util;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.IntBinaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author Duncan
 */
public class $ implements CharSequence, Comparable<CharSequence>, Serializable {

    private static final long serialVersionUID = -2560375428343523574L;

    private final StringBuilder sb;

    public static $ of(final CharSequence firstCharSequences, final CharSequence... otherCharSequences) {
        return new $(firstCharSequences, otherCharSequences);
    }

    public static $ of(final Optional<? extends CharSequence> firstOptional, final Optional<? extends CharSequence>... otherOptionals) {
        return new $(firstOptional, otherOptionals);
    }

    public static $ ofNone() {
        return new $();
    }

    private static StringBuilder newStringBuilder(int size) {
        return new StringBuilder(size + 32);
    }

    public static final IntBinaryOperator SUM = (a, b) -> a + b;

    public $() {
        sb = newStringBuilder(0);
    }

    public $(final CharSequence first, final CharSequence... others) {
        final int size = Stream.of(others).mapToInt(CharSequence::length).reduce(first.length(), SUM);
        sb = newStringBuilder(size);
        $(first, others);
    }

    public $(final Optional<? extends CharSequence> first, final Optional<? extends CharSequence>... others) {
        final int size = Stream.of(others)
                .filter(Optional::isPresent)
                .map((o) -> o.get())
                .mapToInt(CharSequence::length)
                .reduce(first.isPresent() ? first.get().length() : 0, SUM);
        sb = newStringBuilder(size);
        $(first, others);
    }

    public final $ $(final CharSequence s, final CharSequence... ses) {
        sb.append(s);
        Stream.of(ses).forEach(sb::append);
        return this;
    }

    public final $ $(final Optional<? extends CharSequence> o, final Optional<? extends CharSequence>... os) {
        o.ifPresent(sb::append);
        Stream.of(os).filter(Optional::isPresent).forEach(sb::append);
        return this;
    }

    public final $ $(final Object o, final Object... os) {
        sb.append(o);
        Stream.of(os).forEach(sb::append);
        return this;
    }

    public final $ $(final String s, final String... ses) {
        sb.append(s);
        Stream.of(ses).forEach(sb::append);
        return this;
    }

    public final $ $(final StringBuilder sb, final StringBuilder... sbs) {
        sb.append(sb);
        Stream.of(sbs).forEach(sb::append);
        return this;
    }

    public $ $(CharSequence s, int start, int end) {
        sb.append(s, start, end);
        return this;
    }

    public $ $(char[] str) {
        sb.append(str);
        return this;
    }

    public $ $(char[] str, int offset, int len) {
        sb.append(str, offset, len);
        return this;
    }

    public $ $(boolean b, boolean... bs) {
        sb.append(b);
        Stream.of(bs).forEach(sb::append);
        return this;
    }

    public $ $(char c, char... cs) {
        sb.append(c);
        Stream.of(cs).forEach(sb::append);
        return this;
    }

    public $ $(int i, int... is) {
        sb.append(i);
        IntStream.of(is).forEach(sb::append);
        return this;
    }

    public $ $(long l, long... ls) {
        sb.append(l);
        LongStream.of(ls).forEach(sb::append);
        return this;
    }

    public $ $(float f, float... fs) {
        sb.append(f);
        Stream.of(fs).forEach(sb::append);
        return this;
    }

    public $ $(double d, double... ds) {
        sb.append(d);
        DoubleStream.of(ds).forEach(sb::append);
        return this;
    }

    public $ appendCodePoint(int codePoint) {
        sb.appendCodePoint(codePoint);
        return this;
    }

    public $ delete(int start, int end) {
        sb.delete(start, end);
        return this;
    }

    public $ deleteCharAt(int index) {
        sb.deleteCharAt(index);
        return this;
    }

    public $ replace(int start, int end, String str) {
        sb.replace(start, end, str);
        return this;
    }

    public $ insert(int index, char[] str, int offset, int len) {
        sb.insert(index, str, offset, len);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException
     */
    public $ insert(int offset, Object obj) {
        sb.insert(offset, obj);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException
     */
    public $ insert(int offset, String str) {
        sb.insert(offset, str);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException
     */
    public $ insert(int offset, char[] str) {
        sb.insert(offset, str);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException
     */
    public $ insert(int dstOffset, CharSequence s) {
        sb.insert(dstOffset, s);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException
     */
    public $ insert(int dstOffset, CharSequence s, int start, int end) {
        sb.insert(dstOffset, s, start, end);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException
     */
    public $ insert(int offset, boolean b) {
        sb.insert(offset, b);
        return this;
    }

    /**
     * @throws IndexOutOfBoundsException
     */
    public $ insert(int offset, char c) {
        sb.insert(offset, c);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException
     */
    public $ insert(int offset, int i) {
        sb.insert(offset, i);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException
     */
    public $ insert(int offset, long l) {
        sb.insert(offset, l);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException
     */
    public $ insert(int offset, float f) {
        sb.insert(offset, f);
        return this;
    }

    /**
     * @throws StringIndexOutOfBoundsException
     */
    public $ insert(int offset, double d) {
        sb.insert(offset, d);
        return this;
    }

    public int indexOf(String str) {
        return sb.indexOf(str);
    }

    public int indexOf(String str, int fromIndex) {
        return sb.indexOf(str, fromIndex);
    }

    public int lastIndexOf(String str) {
        return sb.lastIndexOf(str);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return sb.lastIndexOf(str, fromIndex);
    }

    public $ reverse() {
        sb.reverse();
        return this;
    }

    @Override
    public int length() {
        return sb.length();
    }

    @Override
    public char charAt(final int index) {
        return sb.charAt(index);
    }

    @Override
    public CharSequence subSequence(final int start, final int end) {
        return sb.subSequence(start, end);
    }

    @Override
    public String toString() {
        return sb.toString();
    }

    @Override
    public int compareTo(CharSequence o) {
        return sb.toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(Object obj) {
        return sb.toString().equals(obj);
    }

    @Override
    public int hashCode() {
        return sb.hashCode();
    }

    /**
     * Save the state of the {@code $} instance to a stream (that is, serialize
     * it).
     *
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        s.defaultWriteObject();
        final String string = sb.toString();
        s.writeUTF(string);
    }

    /**
     * readObject is called to restore the state of the $ from a stream.
     */
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();
        sb.setLength(0);
        sb.append(s.readUTF());
    }

}
