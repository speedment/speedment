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
package com.speedment.runtime.field.internal.expression;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.trait.ToNullable;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldMapper;
import com.speedment.runtime.field.predicate.FieldIsNotNullPredicate;
import com.speedment.runtime.field.predicate.FieldIsNullPredicate;

import static java.util.Objects.requireNonNull;

/**
 * Abstract base implementation of {@link FieldMapper}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
abstract class AbstractFieldMapper<ENTITY, V, T, NON_NULLABLE extends Expression<ENTITY>, MAPPER>
implements FieldMapper<ENTITY, V, T, NON_NULLABLE, MAPPER> {

    final ReferenceField<ENTITY, ?, V> field;
    final MAPPER mapper;

    AbstractFieldMapper(ReferenceField<ENTITY, ?, V> field, MAPPER mapper) {
        this.field  = requireNonNull(field);
        this.mapper = requireNonNull(mapper);
    }

    @Override
    public ReferenceField<ENTITY, ?, V> getField() {
        return field;
    }

    @Override
    public MAPPER getMapper() {
        return mapper;
    }

    @Override
    public FieldIsNullPredicate<ENTITY, T> isNull() {
        return new MapperIsNull<>(this, field);
    }

    @Override
    public FieldIsNotNullPredicate<ENTITY, T> isNotNull() {
        return new MapperIsNotNull<>(this, field);
    }

    private static final class MapperIsNull<ENTITY, V, T>
    implements FieldIsNullPredicate<ENTITY, T> {

        private final ToNullable<ENTITY, T, ?> expression;
        private final ReferenceField<ENTITY, ?, V> field;

        MapperIsNull(ToNullable<ENTITY, T, ?> expression,
                     ReferenceField<ENTITY, ?, V> field) {
            this.expression = requireNonNull(expression);
            this.field      = requireNonNull(field);
        }

        @Override
        public boolean test(ENTITY value) {
            return field.get(value) == null;
        }

        @Override
        public FieldIsNotNullPredicate<ENTITY, T> negate() {
            return new MapperIsNotNull<>(expression, field);
        }

        @Override
        public ToNullable<ENTITY, T, ?> expression() {
            return expression;
        }

        @Override
        public Field<ENTITY> getField() {
            return field;
        }
    }

    private static final class MapperIsNotNull<ENTITY, V, T>
        implements FieldIsNotNullPredicate<ENTITY, T> {

        private final ToNullable<ENTITY, T, ?> expression;
        private final ReferenceField<ENTITY, ?, V> field;

        MapperIsNotNull(ToNullable<ENTITY, T, ?> expression,
                        ReferenceField<ENTITY, ?, V> field) {
            this.expression = requireNonNull(expression);
            this.field      = requireNonNull(field);
        }

        @Override
        public boolean test(ENTITY value) {
            return field.get(value) != null;
        }

        @Override
        public FieldIsNullPredicate<ENTITY, T> negate() {
            return new MapperIsNull<>(expression, field);
        }

        @Override
        public ToNullable<ENTITY, T, ?> expression() {
            return expression;
        }

        @Override
        public Field<ENTITY> getField() {
            return field;
        }
    }
}
