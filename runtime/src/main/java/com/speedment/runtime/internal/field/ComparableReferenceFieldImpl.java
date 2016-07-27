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
package com.speedment.runtime.internal.field;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.field.Inclusion;
import com.speedment.runtime.field.predicate.ComparableSpeedmentPredicate;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.field.trait.ComparableFieldTrait;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.internal.field.trait.ComparableReferenceFieldTraitImpl;
import com.speedment.runtime.internal.field.trait.FieldTraitImpl;
import com.speedment.runtime.internal.field.trait.ReferenceFieldImpl;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import com.speedment.runtime.field.method.SetToReference;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;

/**
 * This class represents a Comparable Reference Field. A Reference Field is
 * something that extends {@link Comparable}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 */
public class ComparableReferenceFieldImpl<ENTITY, D, V extends Comparable<? super V>> implements ComparableField<ENTITY, D, V> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, D, V> referenceField;
    private final ComparableFieldTrait<ENTITY, D, V> comparableField;
    private final TypeMapper<D, V> typeMapper;

    public ComparableReferenceFieldImpl(
        FieldIdentifier<ENTITY> identifier,
        ReferenceGetter<ENTITY, V> getter,
        ReferenceSetter<ENTITY, V> setter,
        TypeMapper<D, V> typeMapper,
        boolean unique
    ) {
        requireNonNulls(identifier, getter, setter, typeMapper);
        this.field           = new FieldTraitImpl(identifier, getter, setter, unique);
        this.referenceField  = new ReferenceFieldImpl<>(field, typeMapper);
        this.comparableField = new ComparableReferenceFieldTraitImpl<>(field, referenceField);
        this.typeMapper      = typeMapper;
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
    public Optional<Column> findColumn(Project project) {
        return Optional.of(DocumentDbUtil.referencedColumn(project, getIdentifier()));
    }

    @Override
    public ReferenceSetter<ENTITY, V> setter() {
        return referenceField.setter();
    }

    @Override
    public ReferenceGetter<ENTITY, V> getter() {
        return referenceField.getter();
    }

    @Override
    public TypeMapper<D, V> typeMapper() {
        return typeMapper;
    }

    @Override
    public SetToReference<ENTITY, V> setTo(V value) {
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

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> notBetween(V start, V end) {
        return comparableField.notBetween(start, end);
    }
        
    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, V> notBetween(V start, V end, Inclusion inclusion) {
        return comparableField.notBetween(start, end, inclusion);
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
