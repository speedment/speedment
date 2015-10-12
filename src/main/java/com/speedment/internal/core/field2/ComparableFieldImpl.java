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
package com.speedment.internal.core.field2;

import com.speedment.field2.Inclusion;
import com.speedment.internal.core.field2.trait.ComparableFieldTraitImpl;
import com.speedment.internal.core.field2.trait.FieldTraitImpl;
import com.speedment.internal.core.field2.trait.ReferenceFieldTraitImpl;
import com.speedment.field2.methods.Getter;
import com.speedment.field2.methods.Setter;
import java.util.Set;
import java.util.function.Predicate;
import com.speedment.field2.ComparableField;
import com.speedment.field2.methods.FieldSetter;
import java.util.Comparator;
import static java.util.Objects.requireNonNull;
import com.speedment.field2.trait.ComparableFieldTrait;
import com.speedment.field2.trait.FieldTrait;
import com.speedment.field2.trait.ReferenceFieldTrait;

/**
 * This class represents a Comparable Reference Field. A Reference Field is
 * something that extends {@link Comparable}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 */
public class ComparableFieldImpl<ENTITY, V extends Comparable<? super V>> implements ComparableField<ENTITY, V> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, V> referenceField;
    private final ComparableFieldTrait<ENTITY, V> comparableField;

    public ComparableFieldImpl(
        String columnName,
        Getter<ENTITY, V> getter,
        Setter<ENTITY, V> setter
    ) {
        field = new FieldTraitImpl(requireNonNull(columnName));
        referenceField = new ReferenceFieldTraitImpl<>(field, requireNonNull(getter), requireNonNull(setter));
        comparableField = new ComparableFieldTraitImpl<>(field, requireNonNull(getter));
    }

    @Override
    public String getColumnName() {
        return field.getColumnName();
    }

    @Override
    public Setter<ENTITY, V> setter() {
        return referenceField.setter();
    }

    @Override
    public Getter<ENTITY, V> getter() {
        return referenceField.getter();
    }

    @Override
    public FieldSetter<ENTITY, V> setTo(V value) {
        return referenceField.setTo(value);
    }

    @Override
    public Predicate<ENTITY> isNull() {
        return referenceField.isNull();
    }

    @Override
    public Predicate<ENTITY> isNotNull() {
        return referenceField.isNotNull();
    }

    @Override
    public Comparator<ENTITY> comparator() {
        return comparableField.comparator();
    }

    @Override
    public Comparator<ENTITY> comparatorNullFieldsFirst() {
        return comparableField.comparatorNullFieldsFirst();
    }

    @Override
    public Comparator<ENTITY> comparatorNullFieldsLast() {
        return comparableField.comparatorNullFieldsLast();
    }

    @Override
    public Predicate<ENTITY> equal(V value) {
        return comparableField.equal(value);
    }

    @Override
    public Predicate<ENTITY> notEqual(V value) {
        return comparableField.notEqual(value);
    }

    @Override
    public Predicate<ENTITY> lessThan(V value) {
        return comparableField.lessThan(value);
    }

    @Override
    public Predicate<ENTITY> lessOrEqual(V value) {
        return comparableField.lessOrEqual(value);
    }

    @Override
    public Predicate<ENTITY> greaterThan(V value) {
        return comparableField.greaterThan(value);
    }

    @Override
    public Predicate<ENTITY> greaterOrEqual(V value) {
        return comparableField.greaterOrEqual(value);
    }

    @Override
    public Predicate<ENTITY> between(V start, V end) {
        return comparableField.between(start, end);
    }

    @Override
    public Predicate<ENTITY> between(V start, V end, Inclusion inclusion) {
        return comparableField.between(start, end, inclusion);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // delegator is safe
    @Override
    public final Predicate<ENTITY> in(V... values) {
        return comparableField.in(values);
    }

    @Override
    public Predicate<ENTITY> in(Set<V> values) {
        return comparableField.in(values);
    }

}
