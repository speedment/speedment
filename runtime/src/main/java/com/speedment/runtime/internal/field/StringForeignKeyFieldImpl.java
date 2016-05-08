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

import com.speedment.runtime.Speedment;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.mapper.TypeMapper;
import com.speedment.runtime.field.FieldIdentifier;
import com.speedment.runtime.field.Inclusion;
import com.speedment.runtime.field.StringForeignKeyField;
import com.speedment.runtime.field.predicate.ComparableSpeedmentPredicate;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.field.predicate.StringSpeedmentPredicate;
import com.speedment.runtime.field.trait.ComparableFieldTrait;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.field.trait.ReferenceForeignKeyFieldTrait;
import com.speedment.runtime.field.trait.StringFieldTrait;
import com.speedment.runtime.internal.field.trait.FieldTraitImpl;
import com.speedment.runtime.internal.field.trait.ReferenceFieldTraitImpl;
import com.speedment.runtime.internal.field.trait.ReferenceForeignKeyFieldTraitImpl;
import com.speedment.runtime.internal.field.trait.StringFieldTraitImpl;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import com.speedment.runtime.field.method.FieldSetter;
import com.speedment.runtime.field.method.Finder;
import com.speedment.runtime.field.method.Getter;
import com.speedment.runtime.field.method.Setter;
import com.speedment.runtime.internal.field.trait.ComparableFieldTraitImpl;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

/**
 * This class represents a Reference Field. A Reference Field is something that
 * extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <FK> Foreign Key type
 */
public class StringForeignKeyFieldImpl<ENTITY, D, FK> implements StringForeignKeyField<ENTITY, D, FK> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, D, String> referenceField;
    private final ComparableFieldTrait<ENTITY, D, String> comparableField;
    private final StringFieldTrait<ENTITY, D> stringField;
    private final ReferenceForeignKeyFieldTrait<ENTITY, D, FK> referenceForeignKeyField;

    public StringForeignKeyFieldImpl(
        FieldIdentifier<ENTITY> identifier,
        Getter<ENTITY, String> getter,
        Setter<ENTITY, String> setter,
        Finder<ENTITY, FK> finder,
        TypeMapper<D, String> typeMapper,
        boolean unique
    ) {
        requireNonNulls(identifier, getter, setter, finder, typeMapper);
        field = new FieldTraitImpl(identifier, unique);
        referenceField = new ReferenceFieldTraitImpl<>(field, getter, setter, typeMapper);
        comparableField = new ComparableFieldTraitImpl<>(field, referenceField);
        stringField = new StringFieldTraitImpl<>(field, referenceField);
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
    public Optional<Column> findColumn(Speedment speedment) {
        return Optional.of(DocumentDbUtil.referencedColumn(speedment, getIdentifier()));
    }

    @Override
    public Setter<ENTITY, String> setter() {
        return referenceField.setter();
    }

    @Override
    public Getter<ENTITY, String> getter() {
        return referenceField.getter();
    }

    @Override
    public Finder<ENTITY, FK> finder() {
        return referenceForeignKeyField.finder();
    }

    @Override
    public TypeMapper<D, String> typeMapper() {
        return referenceField.typeMapper();
    }

    @Override
    public FieldSetter<ENTITY, String> setTo(String value) {
        return referenceField.setTo(value);
    }

    @Override
    public SpeedmentPredicate<ENTITY, D, String> isNull() {
        return referenceField.isNull();
    }

    @Override
    public SpeedmentPredicate<ENTITY, D, String> isNotNull() {
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
    public ComparableSpeedmentPredicate<ENTITY, D, String> equal(String value) {
        return comparableField.equal(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> notEqual(String value) {
        return comparableField.notEqual(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> lessThan(String value) {
        return comparableField.lessThan(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> lessOrEqual(String value) {
        return comparableField.lessOrEqual(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> greaterThan(String value) {
        return comparableField.greaterThan(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> greaterOrEqual(String value) {
        return comparableField.greaterOrEqual(value);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> between(String start, String end) {
        return comparableField.between(start, end);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> between(String start, String end, Inclusion inclusion) {
        return comparableField.between(start, end, inclusion);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> notBetween(String start, String end) {
        return comparableField.notBetween(start, end);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> notBetween(String start, String end, Inclusion inclusion) {
        return comparableField.notBetween(start, end, inclusion);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // delegator is safe
    @Override
    public final ComparableSpeedmentPredicate<ENTITY, D, String> in(String... values) {
        return comparableField.in(values);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> in(Set<String> values) {
        return comparableField.in(values);
    }

    @SafeVarargs
    @SuppressWarnings("varargs") // delegator is safe
    @Override
    public final ComparableSpeedmentPredicate<ENTITY, D, String> notIn(String... values) {
        return comparableField.notIn(values);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> notIn(Set<String> values) {
        return comparableField.notIn(values);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> equalIgnoreCase(String value) {
        return stringField.equalIgnoreCase(value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> notEqualIgnoreCase(String value) {
        return stringField.notEqualIgnoreCase(value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> startsWith(String value) {
        return stringField.startsWith(value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> notStartsWith(String value) {
        return stringField.notStartsWith(value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> endsWith(String value) {
        return stringField.endsWith(value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> notEndsWith(String value) {
        return stringField.notEndsWith(value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> contains(String value) {
        return stringField.contains(value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> notContains(String value) {
        return stringField.notContains(value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> isEmpty() {
        return stringField.isEmpty();
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> isNotEmpty() {
        return stringField.isNotEmpty();
    }

}
