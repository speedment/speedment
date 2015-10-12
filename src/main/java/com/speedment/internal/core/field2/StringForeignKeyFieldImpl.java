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
import com.speedment.field2.StringForeignKeyField;
import com.speedment.field2.methods.FieldSetter;
import com.speedment.internal.core.field2.trait.ComparableFieldTraitImpl;
import com.speedment.internal.core.field2.trait.FieldTraitImpl;
import com.speedment.internal.core.field2.trait.ReferenceFieldTraitImpl;
import com.speedment.internal.core.field2.trait.ReferenceForeignKeyFieldTraitImpl;
import com.speedment.internal.core.field2.trait.StringFieldTraitImpl;
import com.speedment.field2.methods.Finder;
import com.speedment.field2.methods.Getter;
import com.speedment.field2.methods.Setter;
import com.speedment.field2.trait.ComparableFieldTrait;
import com.speedment.field2.trait.FieldTrait;
import com.speedment.field2.trait.ReferenceFieldTrait;
import com.speedment.field2.trait.ReferenceForeignKeyFieldTrait;
import com.speedment.field2.trait.StringFieldTrait;
import java.util.Comparator;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.function.Predicate;

/**
 * This class represents a Reference Field. A Reference Field is something that
 * extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <FK> Foreign Key type
 */
public class StringForeignKeyFieldImpl<ENTITY, FK> implements StringForeignKeyField<ENTITY, FK> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, String> referenceField;
    private final ComparableFieldTrait<ENTITY, String> comparableField;
    private final StringFieldTrait<ENTITY> stringField;
    private final ReferenceForeignKeyFieldTrait<ENTITY, FK> referenceForeignKeyField;

    public StringForeignKeyFieldImpl(
        String columnName,
        Getter<ENTITY, String> getter,
        Setter<ENTITY, String> setter,
        Finder<ENTITY, FK> finder
    ) {

        field = new FieldTraitImpl(requireNonNull(columnName));
        referenceField = new ReferenceFieldTraitImpl<>(field, requireNonNull(getter), requireNonNull(setter));
        comparableField = new ComparableFieldTraitImpl<>(field, requireNonNull(getter));
        stringField = new StringFieldTraitImpl<>(requireNonNull(getter));
        referenceForeignKeyField = new ReferenceForeignKeyFieldTraitImpl<>(requireNonNull(finder));
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
    public FieldSetter<ENTITY, String> setTo(String value) {
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
    public Predicate<ENTITY> equal(String value) {
        return comparableField.equal(value);
    }

    @Override
    public Predicate<ENTITY> notEqual(String value) {
        return comparableField.notEqual(value);
    }

    @Override
    public Predicate<ENTITY> lessThan(String value) {
        return comparableField.lessThan(value);
    }

    @Override
    public Predicate<ENTITY> lessOrEqual(String value) {
        return comparableField.lessOrEqual(value);
    }

    @Override
    public Predicate<ENTITY> greaterThan(String value) {
        return comparableField.greaterThan(value);
    }

    @Override
    public Predicate<ENTITY> greaterOrEqual(String value) {
        return comparableField.greaterOrEqual(value);
    }

    @Override
    public Predicate<ENTITY> between(String start, String end) {
        return comparableField.between(start, end);
    }

    @Override
    public Predicate<ENTITY> between(String start, String end, Inclusion inclusion) {
        return comparableField.between(start, end, inclusion);
    }

    @Override
    public Predicate<ENTITY> in(String... values) {
        return comparableField.in(values);
    }

    @Override
    public Predicate<ENTITY> in(Set<String> values) {
        return comparableField.in(values);
    }

    @Override
    public Predicate<ENTITY> equalIgnoreCase(String value) {
        return stringField.equalIgnoreCase(value);
    }

    @Override
    public Predicate<ENTITY> notEqualIgnoreCase(String value) {
        return stringField.notEqualIgnoreCase(value);
    }

    @Override
    public Predicate<ENTITY> startsWith(String value) {
        return stringField.startsWith(value);
    }

    @Override
    public Predicate<ENTITY> endsWith(String value) {
        return stringField.endsWith(value);
    }

    @Override
    public Predicate<ENTITY> contains(String value) {
        return stringField.contains(value);
    }

    @Override
    public Predicate<ENTITY> isEmpty() {
        return stringField.isEmpty();
    }

    @Override
    public Predicate<ENTITY> isNotEmpty() {
        return stringField.isNotEmpty();
    }

    @Override
    public Finder<ENTITY, FK> finder() {
        return referenceForeignKeyField.finder();
    }

}
