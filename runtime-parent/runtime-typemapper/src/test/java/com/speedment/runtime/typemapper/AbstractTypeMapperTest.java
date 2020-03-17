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
package com.speedment.runtime.typemapper;

import com.speedment.runtime.config.Column;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.speedment.runtime.typemapper.TypeMapper.Category;
import static com.speedment.runtime.typemapper.TypeMapper.Ordering;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractTypeMapperTest<D, V, T extends TypeMapper<D, V>> {

    protected static final Class<?> ENTITY_TYPE = Object.class;
    private final T typeMapper;

    private final Class<D> databaseClass;
    private final Class<V> javaClass;
    private final Category category;
    private final Ordering ordering;

    @Mock
    private Column column;

    protected AbstractTypeMapperTest(Class<D> databaseClass, Class<V> javaClass, Category category, Ordering ordering, Supplier<T> supplier) {
        this.databaseClass = requireNonNull(databaseClass);
        this.javaClass = requireNonNull(javaClass);
        this.category = requireNonNull(category);
        this.ordering = requireNonNull(ordering);
        this.typeMapper = requireNonNull(supplier).get();
    }

    protected AbstractTypeMapperTest(Class<D> databaseClass, Class<V> javaClass, Category category, Supplier<T> supplier) {
        this.databaseClass = requireNonNull(databaseClass);
        this.javaClass = requireNonNull(javaClass);
        this.category = requireNonNull(category);
        this.ordering = Ordering.UNSPECIFIED;
        this.typeMapper = requireNonNull(supplier).get();
    }

    protected abstract Map<D,V> testVector();

    @TestFactory
    Stream<DynamicTest> dynamicToJavaType() {
        return testVector().entrySet().stream()
            .map((e -> DynamicTest.dynamicTest("Mapping: " + e.getKey(),
                () -> assertEquals(e.getValue(), typeMapper().toJavaType(column(), ENTITY_TYPE, e.getKey())))));
    }

    @TestFactory
    Stream<DynamicTest> dynamicToDatabaseType() {
        return testVector().entrySet().stream()
            .map((e -> DynamicTest.dynamicTest("Mapping: " + e.getValue(),
                () -> assertEquals(e.getKey(), typeMapper().toDatabaseType(e.getValue())))));
    }

    @Test
    protected void getJavaType() {
        assertEquals(javaClass, typeMapper().getJavaType(column()));
    }

    @Test
    void getJavaTypeCategory() {
        assertEquals(category(), typeMapper().getJavaTypeCategory(column()));
    }

    @Test
    void getOrdering() {
        assertEquals(ordering(), typeMapper().getOrdering());
    }

    @Test
    void getLabel() {
        assertNotNull(typeMapper.getLabel());
    }

    protected T typeMapper() {
        return typeMapper;
    }

    protected Column column() {
        return column;
    }

    protected Category category() {
        return category;
    }

    protected Ordering ordering() {
        return ordering;
    }

    public Class<D> databaseClass() {
        return databaseClass;
    }

    public Class<V> javaClass() {
        return javaClass;
    }

}
