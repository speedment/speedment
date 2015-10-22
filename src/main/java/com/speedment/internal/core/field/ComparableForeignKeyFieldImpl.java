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
package com.speedment.internal.core.field;

import com.speedment.field.ComparableForeignKeyField;
import com.speedment.field.Inclusion;
import com.speedment.field.methods.FieldSetter;
import com.speedment.internal.core.field.trait.ComparableFieldTraitImpl;
import com.speedment.internal.core.field.trait.FieldTraitImpl;
import com.speedment.internal.core.field.trait.ReferenceFieldTraitImpl;
import com.speedment.internal.core.field.trait.ReferenceForeignKeyFieldTraitImpl;
import com.speedment.field.methods.Finder;
import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import com.speedment.field.predicate.ComparableSpeedmentPredicate;
import com.speedment.field.predicate.SpeedmentPredicate;
import java.util.Comparator;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.function.Predicate;
import com.speedment.field.trait.ComparableFieldTrait;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.field.trait.ReferenceForeignKeyFieldTrait;

/**
 * This class represents a Reference Field. A Reference Field is something that
 * extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 */
public class ComparableForeignKeyFieldImpl<ENTITY, V extends Comparable<? super V>, FK> implements ComparableForeignKeyField<ENTITY, V, FK> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, V> referenceField;
    private final ComparableFieldTrait<ENTITY, V> comparableField;
    private final ReferenceForeignKeyFieldTrait<ENTITY, FK> referenceForeignKeyField;

    public ComparableForeignKeyFieldImpl(
        String columnName,
        Getter<ENTITY, V> getter,
        Setter<ENTITY, V> setter,
        Finder<ENTITY, FK> finder
    ) {
        field = new FieldTraitImpl(requireNonNull(columnName));
        referenceField = new ReferenceFieldTraitImpl<>(field, requireNonNull(getter), requireNonNull(setter));
        comparableField = new ComparableFieldTraitImpl<>(field, referenceField);
        referenceForeignKeyField = new ReferenceForeignKeyFieldTraitImpl<>(requireNonNull(finder));
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
    public SpeedmentPredicate<ENTITY, V> isNull() {
        return referenceField.isNull();
    }

    @Override
    public SpeedmentPredicate<ENTITY, V> isNotNull() {
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
    public ComparableSpeedmentPredicate<ENTITY, V> equal(V value) {
        return comparableField.equal(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> notEqual(V value) {
        return comparableField.notEqual(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> lessThan(V value) {
        return comparableField.lessThan(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> lessOrEqual(V value) {
        return comparableField.lessOrEqual(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> greaterThan(V value) {
        return comparableField.greaterThan(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> greaterOrEqual(V value) {
        return comparableField.greaterOrEqual(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> between(V start, V end) {
        return comparableField.between(start, end);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> between(V start, V end, Inclusion inclusion) {
        return comparableField.between(start, end, inclusion);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // delegator is safe
    @Override
    public final ComparableSpeedmentPredicate<ENTITY, V> in(V... values) {
        return comparableField.in(values);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, V> in(Set<V> values) {
        return comparableField.in(values);
    }

    @Override
    public Finder<ENTITY, FK> finder() {
        return referenceForeignKeyField.finder();
    }

}
