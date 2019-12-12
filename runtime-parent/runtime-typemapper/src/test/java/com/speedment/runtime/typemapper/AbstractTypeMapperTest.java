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

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractTypeMapperTest<D, V, T extends TypeMapper<D, V>> {

    protected static final Class<?> ENTITY_TYPE = Object.class;
    private final T typeMapper;

    private final Class<D> databaseClass;
    private final Class<V> javaClass;

    @Mock
    private Column column;

    protected AbstractTypeMapperTest(Class<D> databaseClass, Class<V> javaClass, Supplier<T> supplier) {
        this.databaseClass = requireNonNull(databaseClass);
        this.javaClass = requireNonNull(javaClass);
        this.typeMapper = requireNonNull(supplier).get();
    }

    protected abstract Map<D,V> testVector();

    @TestFactory
    Stream<DynamicTest> dynamicGetJavaType() {
        return testVector().entrySet().stream()
            .map((e -> DynamicTest.dynamicTest(": " + e.getKey(),
                () -> assertEquals(e.getValue(), typeMapper().toJavaType(column(), ENTITY_TYPE, e.getKey())))));
    }

    @TestFactory
    Stream<DynamicTest> dynamicToDatabaseType() {
        return testVector().entrySet().stream()
            .map((e -> DynamicTest.dynamicTest("Mapping: " + e.getValue(),
                () -> assertEquals(e.getKey(), typeMapper().toDatabaseType(e.getValue())))));
    }

    /*@Test
    void getJavaType() {
        assertEquals(javaClass, typeMapper().getJavaType(column()));
    }*/

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

    public Class<D> databaseClass() {
        return databaseClass;
    }

    public Class<V> javaClass() {
        return javaClass;
    }

}
