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
import com.speedment.field.Inclusion;
import com.speedment.field.StringForeignKeyField;
import com.speedment.field.methods.FieldSetter;
import com.speedment.internal.core.field.trait.ComparableFieldTraitImpl;
import com.speedment.internal.core.field.trait.FieldTraitImpl;
import com.speedment.internal.core.field.trait.ReferenceFieldTraitImpl;
import com.speedment.internal.core.field.trait.ReferenceForeignKeyFieldTraitImpl;
import com.speedment.internal.core.field.trait.StringFieldTraitImpl;
import com.speedment.field.methods.Finder;
import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import com.speedment.field.predicate.ComparableSpeedmentPredicate;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.trait.ComparableFieldTrait;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.field.trait.ReferenceForeignKeyFieldTrait;
import com.speedment.field.trait.StringFieldTrait;
import java.util.Comparator;
import java.util.Set;
import com.speedment.field.predicate.StringSpeedmentPredicate;
import static com.speedment.util.NullUtil.requireNonNulls;
import static java.util.Objects.requireNonNull;

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
            String columnName,
            Getter<ENTITY, String> getter,
            Setter<ENTITY, String> setter,
            Finder<ENTITY, FK> finder,
            TypeMapper<D, String> typeMapper
    ) {
        requireNonNulls(columnName, getter, setter, finder, typeMapper);
        field = new FieldTraitImpl(columnName);
        referenceField = new ReferenceFieldTraitImpl<>(field, getter, setter, typeMapper);
        comparableField = new ComparableFieldTraitImpl<>(field, referenceField);
        stringField = new StringFieldTraitImpl<>(field, referenceField);
        referenceForeignKeyField = new ReferenceForeignKeyFieldTraitImpl<>(finder);
    }

    @Override
    public String getColumnName() {
        return field.getColumnName();
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
    public ComparableSpeedmentPredicate<ENTITY, D, String> in(String... values) {
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
        return comparableField.in(values);
    }

    @Override
    public ComparableSpeedmentPredicate<ENTITY, D, String> notIn(Set<String> values) {
        return comparableField.in(values);
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
    public StringSpeedmentPredicate<ENTITY, D> endsWith(String value) {
        return stringField.endsWith(value);
    }

    @Override
    public StringSpeedmentPredicate<ENTITY, D> contains(String value) {
        return stringField.contains(value);
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
