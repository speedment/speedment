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
import com.speedment.runtime.typemapper.internal.IdentityTypeMapper;
import com.speedment.runtime.typemapper.primitive.PrimitiveTypeMapper;

import java.lang.reflect.Type;
import java.util.Comparator;

import static java.util.Comparator.comparing;

/**
 * A type mapper contains logic for converting between the database and the java
 * type for a field. Implementations of this class should be installed in the
 * {@code TypeMapperComponent}.
 *
 * @param <DB_TYPE>   the type as it is represented in the JDBC driver
 * @param <JAVA_TYPE> the type as it should be represented in generated code
 *
 * @author  Emil Forslund
 * @since   2.2.0
 */
public interface TypeMapper<DB_TYPE, JAVA_TYPE> {

    /**
     * Which category the {@link #getJavaType(Column)} result belong to.
     *
     * @author Emil Forslund
     * @since  3.0.10
     */
    enum Category {
        BYTE,
        SHORT,
        INT,
        LONG,
        DOUBLE,
        FLOAT,
        BOOLEAN,
        CHAR,
        REFERENCE,
        COMPARABLE,
        STRING,
        ENUM
    }

    /**
     * Returns the standard comparator to use for instances of the {@link TypeMapper}
     * interface. This comparator will use the name of the database type as
     * comparison index and if two mappers share the same database type, it will
     * use the label in alphabetical order.
     *
     * @return the standard comparator to use for instances of the {@link TypeMapper}
     *         interface
     */
    static Comparator<TypeMapper<?, ?>> standardComparator() {
      return comparing(TypeMapper::getLabel);
    }

    /**
     * Returns the label for this mapper that should appear to the end user.
     *
     * @return  the label
     */
    String getLabel();

    /**
     * Returns a type describing the resulting java type when this mapper is
     * applied to a database result.
     * 
     * @param column    the column that is being mapped
     * @return          the resulting java type
     */
    Type getJavaType(Column column);

    /**
     * Returns the {@link Category} of the type returned by
     * {@link #getJavaType(Column)}.
     *
     * @param column  the column
     * @return        the category
     */
    default Category getJavaTypeCategory(Column column) {
        final Type type       = getJavaType(column);
        final String typeName = type.getTypeName();

        switch (typeName) {
            case "byte"    : return Category.BYTE;
            case "short"   : return Category.SHORT;
            case "int"     : return Category.INT;
            case "long"    : return Category.LONG;
            case "double"  : return Category.DOUBLE;
            case "float"   : return Category.FLOAT;
            case "boolean" : return Category.BOOLEAN;
            case "char"    : return Category.CHAR;
            case "java.lang.String" : return Category.STRING;
            default :
                Category x = mapToDefault(type);
                if (x != null) return x;
        }
        return Category.REFERENCE;
    }

    default Category mapToDefault(Type type) {
        if (type instanceof Class<?>) {
            final Class<?> clazz = (Class<?>) type;
            if (Enum.class.isAssignableFrom(clazz)) {
                return Category.ENUM;
            } else if (Comparable.class.isAssignableFrom(clazz)) {
                return Category.COMPARABLE;
            }
        }
        return null;
    }

    /**
     * Converts a value from the database domain to the java domain.
     *
     * @param column      the column that is being mapped
     * @param entityType  the entity type that the mapping is for
     * @param value       the value to convert
     * @return            the converted value
     */
    JAVA_TYPE toJavaType(Column column, Class<?> entityType, DB_TYPE value);

    /**
     * Converts a value from the java domain to the database domain.
     *
     * @param value  the value to convert
     * @return       the converted value
     */
    DB_TYPE toDatabaseType(JAVA_TYPE value);
   
     /**
     * Indicates how the ordering of mapped vs un-mapped types relate to each
     * other.
     *
     * @author Per Minborg
     * @since  3.0.11
     */
    enum Ordering {
        /**
         * Objects of the DB_TYPE type and objects of the the JAVA_TYPE have the
         * same order. I.e. ordering is retained for mapped/un-mapped objects in
         * both directions of mapping.
         */
        RETAIN,
         /**
         * Objects of the DB_TYPE type and objects of the the JAVA_TYPE have the
         * inverted order. I.e. ordering is reversed for mapped/un-mapped objects in
         * both directions of mapping.
         */
        INVERT,
        /**
         * Objects of the DB_TYPE type and objects of the the JAVA_TYPE may have
         * different order. I.e. ordering is unrelated for mapped/un-mapped
         * objects in either direction of mapping. If either of the types does
         * not have an order, then this alternative applies by definition.
         */
        UNSPECIFIED
    }

    
     /**
     * Returns how the {@link Ordering} of mapped and un-mapped types relate
     * to each other.
     * <p>
     * Speedment is using this method to determine if certain optimizations of
     * streams can be made. Make sure to implicitly specify the Ordering in any
     * custom TypeMapper to allow maximum execution speed.
     * <p>
     * Specifying {@link Ordering#UNSPECIFIED } prevents some optimizations so
     * if possible, write a TypeMapper that RETAINS ordering.
     *
     * @return        the ordering
     *
     * @see Ordering#RETAIN
     * @see Ordering#INVERT
     * @see Ordering#UNSPECIFIED
     */
    default Ordering getOrdering() {
        // Defensively assume an unspecified order.
        return Ordering.UNSPECIFIED;
    }

    /**
     * Indicates if the type mapper can be used in the Speedment tool.
     *
     * @return if type mapper is usable in Speedment tool
     */
    default boolean isToolApplicable() {
        return true;
    }

    /**
     * Returns if this TypeMapper is an <em>identity</em> TypeMapper.
     * <p>
     * An identity TypeMapper will always map to the
     * same type database {@code ->} java {@code ->} database and
     * is guaranteed to just pass elements.
     *
     * @return if this TypeMapper is an <em>identity</em> TypeMapper
     */
    default boolean isIdentity() {
        return false;
    }

    /**
     * Returns an identity type mapper.
     *
     * @param <T>  the type of the identity type mapper
     * @return     an identity type mapper
     */
    static <T> TypeMapper<T, T> identity() {
        return IdentityTypeMapper.shared();
    }
    
    /**
     * Returns an primitive type mapper.
     *
     * @param <T>  the wrapper type of the primitive type mapper
     * @return     an primitive type mapper
     */
    static <T> TypeMapper<T, T> primitive() {
        return new PrimitiveTypeMapper<>();
    }
}
