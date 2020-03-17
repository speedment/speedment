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
package com.speedment.runtime.field.internal.predicate;

import com.speedment.common.tuple.Tuple;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.internal.util.Cast;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.PredicateType;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * A predicate that contains meta-data about the {@link Field} that was used to
 * construct it.
 *
 * @param <ENTITY> the entity type that is being tested
 * @param <FIELD>  the field in the entity that is operated on
 *
 * @author Emil Forslund
 * @since 3.0.0
 */
public abstract class AbstractFieldPredicate<ENTITY, FIELD extends Field<ENTITY>>
extends AbstractPredicate<ENTITY>
implements FieldPredicate<ENTITY> {

    private final PredicateType predicateType;
    private final FIELD field;
    private final Predicate<ENTITY> tester;

    protected AbstractFieldPredicate(
            final PredicateType predicateType,
            final FIELD field,
            final Predicate<ENTITY> tester) {

        this.predicateType = requireNonNull(predicateType);
        this.field         = requireNonNull(field);
        this.tester        = requireNonNull(tester);
    }

    protected final Predicate<ENTITY> getTester() {
        return tester;
    }

    @Override
    public boolean applyAsBoolean(ENTITY instance) {
        return tester.test(instance);
    }

    @Override
    public final PredicateType getPredicateType() {
        return predicateType;
    }

    @Override
    public final FIELD getField() {
        return field;
    }


    @Override
    public String toString() {
        final ColumnIdentifier<ENTITY> cId = field.identifier();
        final StringBuilder sb = new StringBuilder();

        sb.append(getClass().getSimpleName())
            .append(" {")
            .append("field: ")
            .append(cId.getDbmsId()).append('.')
            .append(cId.getSchemaId()).append('.')
            .append(cId.getTableId()).append('.')
            .append(cId.getColumnId())
            .append(", type: '").append(predicateType).append("'");

        Cast.cast(this, Tuple.class).ifPresent(tuple -> {
            for (int i = 0; i < tuple.degree(); i++) {
                sb.append(", operand ").append(i)
                    .append(": ").append(tuple.get(i));
            }
        });

        return sb.append("}").toString();
    }
}
