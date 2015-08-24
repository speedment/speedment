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
package com.speedment.core.field.reference.string;

import com.speedment.core.config.model.Column;
import com.speedment.core.field.StandardStringBinaryOperator;
import com.speedment.core.field.reference.ComparableReferenceField;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class represents a String Reference Field. A String Field maps to a
 * {@link String} Column.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 */
public class StringReferenceField<ENTITY> extends ComparableReferenceField<ENTITY, String> {

    public StringReferenceField(final Supplier<Column> columnSupplier, Function<ENTITY, String> getter, BiFunction<ENTITY, String, ENTITY> setter) {
        super(columnSupplier, getter, setter);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>equal</em> to the given
     * value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>equal</em> to the given value while ignoring the case
     * of the Strings that are compared
     *
     * @see String#compareToIgnoreCase(java.lang.String)
     */
    public StringBinaryPredicateBuilder<ENTITY> equalIgnoreCase(String value) {
        return newBinary(value, StandardStringBinaryOperator.EQUAL_IGNORE_CASE);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not equal</em> to the
     * given value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not equal</em> to the given value while ignoring the
     * case of the Strings that are compared
     *
     * @see String#compareToIgnoreCase(java.lang.String)
     */
    public StringBinaryPredicateBuilder<ENTITY> notEqualIgnoreCase(String value) {
        return newBinary(value, StandardStringBinaryOperator.NOT_EQUAL_IGNORE_CASE);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>starts with</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>starts with</em> the given value
     *
     * @see String#startsWith(java.lang.String)
     */
    public StringBinaryPredicateBuilder<ENTITY> startsWith(String value) {
        return newBinary(value, StandardStringBinaryOperator.STARTS_WITH);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>ends with</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>ends with</em> the given value
     *
     * @see String#endsWith(java.lang.String)
     */
    public StringBinaryPredicateBuilder<ENTITY> endsWith(String value) {
        return newBinary(value, StandardStringBinaryOperator.ENDS_WITH);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>contains</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>contains</em> the given value
     *
     * @see String#endsWith(java.lang.String)
     */
    public StringBinaryPredicateBuilder<ENTITY> contains(String value) {
        return newBinary(value, StandardStringBinaryOperator.CONTAINS);
    }

    protected StringBinaryPredicateBuilder<ENTITY> newBinary(String value, StandardStringBinaryOperator binaryOperator) {
        return new StringBinaryPredicateBuilder<>(this, value, binaryOperator);
    }

}
