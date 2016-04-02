/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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

import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.field.ComparableForeignKeyField;
import com.speedment.field.FieldIdentifier;
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
import java.util.Set;
import com.speedment.field.trait.ComparableFieldTrait;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.field.trait.ReferenceForeignKeyFieldTrait;
import static com.speedment.util.NullUtil.requireNonNulls;

/**
 * This class represents a Reference Field. A Reference Field is something that
 * extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 */
public class ComparableForeignKeyFieldImpl<ENTITY, D, V extends Comparable<? super V>, FK> implements ComparableForeignKeyField<ENTITY, D, V, FK> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, D, V> referenceField;
    private final ComparableFieldTrait<ENTITY, D, V> comparableField;
    private final ReferenceForeignKeyFieldTrait<ENTITY, D, FK> referenceForeignKeyField;

    public ComparableForeignKeyFieldImpl(
            FieldIdentifier<ENTITY> identifier,
            Getter<ENTITY, V> getter,
            Setter<ENTITY, V> setter,
            Finder<ENTITY, FK> finder,
            TypeMapper<D, V> typeMapper,
            boolean unique
    ) {
        requireNonNulls(identifier, getter, setter, finder, typeMapper);
        field = new FieldTraitImpl(identifier, unique);
        referenceField = new ReferenceFieldTraitImpl<>(field, getter, setter, typeMapper);
        comparableField = new ComparableFieldTraitImpl<>(field, referenceField);
        referenceForeignKeyField = new ReferenceForeignKeyFieldTraitImpl<>(finder);
    }

    @Override
    public FieldIdentifier<ENTITY> getIdentifier() {
        return referenceField.getIdentifier();
    }

    @Override
    public boolean isUnique() {
        return field.isUnique();
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
    public Finder<ENTITY, FK> finder() {
        return referenceForeignKeyField.finder();
    }

    @Override
    public TypeMapper<D, V> typeMapper() {
        return referenceField.typeMapper();
    }

    @Override
    public FieldSetter<ENTITY, V> setTo(V value) {
        return referenceField.setTo(value);
    }

    @Override
    public SpeedmentPredicate<ENTITY, D, V> isNull() {
        return referenceField.isNull();
    }

    @Override
    public SpeedmentPredicate<ENTITY, D, V> isNotNull() {
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
    public ComparableSpeedmentPredicate<ENTITY, D, V> equal(V value) {
        return comparableField.equal(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> notEqual(V value) {
        return comparableField.notEqual(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> lessThan(V value) {
        return comparableField.lessThan(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> lessOrEqual(V value) {
        return comparableField.lessOrEqual(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> greaterThan(V value) {
        return comparableField.greaterThan(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> greaterOrEqual(V value) {
        return comparableField.greaterOrEqual(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> between(V start, V end) {
        return comparableField.between(start, end);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> between(V start, V end, Inclusion inclusion) {
        return comparableField.between(start, end, inclusion);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // delegator is safe
    @Override
    public final ComparableSpeedmentPredicate<ENTITY, D, V> in(V... values) {
        return comparableField.in(values);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> in(Set<V> values) {
        return comparableField.in(values);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // delegator is safe
    @Override
    public final ComparableSpeedmentPredicate<ENTITY, D, V> notIn(V... values) {
        return comparableField.notIn(values);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> notIn(Set<V> values) {
        return comparableField.notIn(values);
    }

}
