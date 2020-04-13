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
package com.speedment.common.codegen.constant;

import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

final class DefaultTypeTest {

    @Test
    void genericType() {
        final ParameterizedType genericType = (ParameterizedType) DefaultType.genericType(List.class, String.class);
        assertEquals(List.class.getName(), genericType.getRawType().getTypeName());
        assertArrayEquals(new Type[]{String.class}, genericType.getActualTypeArguments());
    }

    @Test
    void testGenericType() {
        final ParameterizedType genericType = (ParameterizedType) DefaultType.genericType(List.class, String.class.getName());
        assertEquals(List.class.getName(), genericType.getRawType().getTypeName());
        assertArrayEquals(new String[]{String.class.getName()}, Stream.of(genericType.getActualTypeArguments()).map(Type::getTypeName).toArray());
    }

    @Test
    void classOf() {
        assertSingleParameter(Class.class, Integer.class, DefaultType::classOf);
    }

    @Test
    void list() {
        assertSingleParameter(List.class, Integer.class, DefaultType::list);
    }

    @Test
    void set() {
        assertSingleParameter(Set.class, Integer.class, DefaultType::set);
    }

    @Test
    void map() {
        assertDoubleParameters(Map.class, Integer.class, Long.class,  DefaultType::map);
    }

    @Test
    void queue() {
        assertSingleParameter(Queue.class, Integer.class, DefaultType::queue);
    }

    @Test
    void stack() {
        assertSingleParameter(Stack.class, Integer.class, DefaultType::stack);
    }

    @Test
    void optional() {
        assertSingleParameter(Optional.class, Integer.class, DefaultType::optional);
    }

    @Test
    void entry() {
        assertDoubleParameters(Map.Entry.class, Integer.class, Long.class,  DefaultType::entry);
    }

    @Test
    void function() {
        assertDoubleParameters(Function.class, Integer.class, Long.class,  DefaultType::function);
    }

    @Test
    void bifunction() {
        assertTripleParameters(BiFunction.class, Integer.class, Long.class, Float.class,  DefaultType::bifunction);
    }

    @Test
    void intFunction() {
        assertSingleParameter(IntFunction.class, Long.class, DefaultType::intFunction);
    }

    @Test
    void longFunction() {
        assertSingleParameter(LongFunction.class, Integer.class, DefaultType::longFunction);
    }

    @Test
    void doubleFunction() {
        assertSingleParameter(DoubleFunction.class, Integer.class, DefaultType::doubleFunction);
    }

    @Test
    void toIntFunction() {
        assertSingleParameter(ToIntFunction.class, Long.class, DefaultType::toIntFunction);
    }

    @Test
    void toLongFunction() {
        assertSingleParameter(ToLongFunction.class, Integer.class, DefaultType::toLongFunction);
    }

    @Test
    void toDoubleFunction() {
        assertSingleParameter(ToDoubleFunction.class, Integer.class, DefaultType::toDoubleFunction);
    }

    @Test
    void unaryOperator() {
        assertSingleParameter(UnaryOperator.class, Integer.class, DefaultType::unaryOperator);
    }

    @Test
    void binaryOperator() {
        assertSingleParameter(BinaryOperator.class, Integer.class, DefaultType::binaryOperator);
    }

    @Test
    void predicate() {
        assertSingleParameter(Predicate.class, Integer.class, DefaultType::predicate);
    }

    @Test
    void bipredicate() {
        assertDoubleParameters(BiPredicate.class, Integer.class, Long.class,  DefaultType::bipredicate);
    }

    @Test
    void consumer() {
        assertSingleParameter(Consumer.class, Integer.class, DefaultType::consumer);
    }

    @Test
    void biconsumer() {
        assertDoubleParameters(BiConsumer.class, Integer.class, Long.class,  DefaultType::biconsumer);
    }

    @Test
    void supplier() {
        assertSingleParameter(Supplier.class, Integer.class, DefaultType::supplier);
    }

    @Test
    void stream() {
        assertSingleParameter(Stream.class, Integer.class, DefaultType::stream);
    }

    @Test
    void isPrimitive() {
    }

    @Test
    void isWrapper() {
    }

    @Test
    void wrapperFor() {
    }

    @Test
    void primitiveTypes() {
    }

    @Test
    void wrapperTypes() {
    }

    private <B, T> void assertSingleParameter(Class<B> rawClass, Class<T> clazz, Function<Class<T>, Type> extractor) {
        final ParameterizedType genericType = (ParameterizedType) extractor.apply(clazz);
        assertEquals(rawClass.getName(), genericType.getRawType().getTypeName());
        assertEquals(1, genericType.getActualTypeArguments().length);
        assertEquals(clazz.getName(), genericType.getActualTypeArguments()[0].getTypeName());
    }

    private <B, K, V> void assertDoubleParameters(Class<B> rawClass, Class<K> keyClass, Class<V> valueClass, BiFunction<Class<K>, Class<V>, Type> extractor) {
        final ParameterizedType genericType = (ParameterizedType) extractor.apply(keyClass, valueClass);
        assertEquals(rawClass.getName(), genericType.getRawType().getTypeName());
        assertEquals(2, genericType.getActualTypeArguments().length);
        assertEquals(keyClass.getName(), genericType.getActualTypeArguments()[0].getTypeName());
        assertEquals(valueClass.getName(), genericType.getActualTypeArguments()[1].getTypeName());
    }

    private <B, K, V, W> void assertTripleParameters(Class<B> rawClass, Class<K> keyClass, Class<V> valueClass, Class<W> triClass, TriFunction<Class<K>, Class<V>, Class<W>, Type> extractor) {
        final ParameterizedType genericType = (ParameterizedType) extractor.apply(keyClass, valueClass, triClass);
        assertEquals(rawClass.getName(), genericType.getRawType().getTypeName());
        assertEquals(3, genericType.getActualTypeArguments().length);
        assertEquals(keyClass.getName(), genericType.getActualTypeArguments()[0].getTypeName());
        assertEquals(valueClass.getName(), genericType.getActualTypeArguments()[1].getTypeName());
        assertEquals(triClass.getName(), genericType.getActualTypeArguments()[2].getTypeName());
    }

    @FunctionalInterface
    interface TriFunction<T0, T1, T2, R> {
        R apply(T0 t0, T1 t1, T2 t2);
    }

}