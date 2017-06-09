/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.manager.sql;

import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.SqlPredicateFragment;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.PredicateType;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import static com.speedment.runtime.field.util.PredicateOperandUtil.*;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author Per Minborg
 */
public abstract class AbstractFieldPredicateView implements FieldPredicateView {

    protected abstract SqlPredicateFragment equalIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated);

    protected abstract SqlPredicateFragment startsWithHelper(String cn, FieldPredicate<?> model, boolean negated);

    protected abstract SqlPredicateFragment startsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated);

    protected abstract SqlPredicateFragment endsWithHelper(String cn, FieldPredicate<?> model, boolean negated);

    protected abstract SqlPredicateFragment endsWithIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated);

    protected abstract SqlPredicateFragment containsHelper(String cn, FieldPredicate<?> model, boolean negated);

    protected abstract SqlPredicateFragment containsIgnoreCaseHelper(String cn, FieldPredicate<?> model, boolean negated);

    @Override
    public <ENTITY> SqlPredicateFragment transform(
        final Function<Field<ENTITY>, String> columnNamer,
        final Function<Field<ENTITY>, Class<?>> columnDbTypeFunction,
        final FieldPredicate<ENTITY> model
    ) {
        return render(
            requireNonNull(columnNamer),
            requireNonNull(columnDbTypeFunction),
            requireNonNull(model)
        );
    }

    protected <ENTITY> SqlPredicateFragment render(
            final Function<Field<ENTITY>, String> columnNamer,
            final Function<Field<ENTITY>, Class<?>> columnDbTypeFunction,
            final FieldPredicate<ENTITY> predicate) {

        final PredicateType pt = predicate.getPredicateType();

        final Field<ENTITY> f = predicate.getField();
        final String cn = columnNamer.apply(f);

        switch (pt) {
            // Constants
            case ALWAYS_TRUE:
                return alwaysTrue();
            case ALWAYS_FALSE:
                return alwaysFalse();

            // Reference
            case IS_NULL:
                return isNull(cn);
            case IS_NOT_NULL:
                return isNotNull(cn);

            // Comparable
            case EQUAL: {
                final Class<?> dbClass = columnDbTypeFunction.apply(f);
                return String.class.equals(dbClass)
                    ? equalString(cn, predicate)
                    : equal(cn, dbClass, predicate);
            }
            case NOT_EQUAL: {
                final Class<?> dbClass = columnDbTypeFunction.apply(f);
                return String.class.equals(dbClass)
                    ? notEqualString(cn, predicate)
                    : notEqual(cn, dbClass, predicate);
            }
            case GREATER_THAN: {
                final Class<?> dbClass = columnDbTypeFunction.apply(f);
                return String.class.equals(dbClass)
                    ? greaterThanString(cn, predicate)
                    : greaterThan(cn, dbClass, predicate);
            }
            case GREATER_OR_EQUAL: {
                final Class<?> dbClass = columnDbTypeFunction.apply(f);
                return String.class.equals(dbClass)
                    ? greaterOrEqualString(cn, predicate)
                    : greaterOrEqual(cn, dbClass, predicate);
            }
            case LESS_THAN: {
                final Class<?> dbClass = columnDbTypeFunction.apply(f);
                return String.class.equals(dbClass)
                    ? lessThanString(cn, predicate)
                    : lessThan(cn, dbClass, predicate);
            }
            case LESS_OR_EQUAL: {
                final Class<?> dbClass = columnDbTypeFunction.apply(f);
                return String.class.equals(dbClass)
                    ? lessOrEqualString(cn, predicate)
                    : lessOrEqual(cn, dbClass, predicate);
            }
            case BETWEEN: {
                final Class<?> dbClass = columnDbTypeFunction.apply(f);
                return String.class.equals(dbClass)
                    ? betweenString(cn, predicate)
                    : between(cn, dbClass, predicate);
            }
            case NOT_BETWEEN: {
                final Class<?> dbClass = columnDbTypeFunction.apply(f);
                return String.class.equals(dbClass)
                    ? notBetweenString(cn, predicate)
                    : notBetween(cn, dbClass, predicate);
            }
            case IN: {
                final Class<?> dbClass = columnDbTypeFunction.apply(f);
                return String.class.equals(dbClass)
                    ? inString(cn, predicate)
                    : in(cn, dbClass, predicate);
            }
            case NOT_IN: {
                final Class<?> dbClass = columnDbTypeFunction.apply(f);
                return String.class.equals(dbClass)
                    ? notInString(cn, predicate)
                    : notIn(cn, dbClass, predicate);
            }

            // String
            case EQUAL_IGNORE_CASE:
                return equalIgnoreCase(cn, predicate);
            case NOT_EQUAL_IGNORE_CASE:
                return notEqualIgnoreCase(cn, predicate);

            case STARTS_WITH:
                return startsWith(cn, predicate);
            case NOT_STARTS_WITH:
                return notStartsWith(cn, predicate);
            case STARTS_WITH_IGNORE_CASE:
                return startsWithIgnoreCase(cn, predicate);
            case NOT_STARTS_WITH_IGNORE_CASE:
                return notStartsWithIgnoreCase(cn, predicate);

            case ENDS_WITH:
                return endsWith(cn, predicate);
            case NOT_ENDS_WITH:
                return notEndsWith(cn, predicate);
            case ENDS_WITH_IGNORE_CASE:
                return endsWithIgnoreCase(cn, predicate);
            case NOT_ENDS_WITH_IGNORE_CASE:
                return notEndsWithIgnoreCase(cn, predicate);

            case CONTAINS:
                return contains(cn, predicate);
            case NOT_CONTAINS:
                return notContains(cn, predicate);
            case CONTAINS_IGNORE_CASE:
                return containsIgnoreCase(cn, predicate);
            case NOT_CONTAINS_IGNORE_CASE:
                return notContainsIgnoreCase(cn, predicate);

            case IS_EMPTY:
                return isEmpty(cn);
            case IS_NOT_EMPTY:
                return isNotEmpty(cn);
            default:
                throw new UnsupportedOperationException(
                    "Unknown PredicateType  " + pt.name() + ". Column name:" + predicate.getField().identifier().getColumnName()
                );
        }
    }

    protected SqlPredicateFragment alwaysTrue() {
        return of("(TRUE)");
    }

    protected SqlPredicateFragment alwaysFalse() {
        return of("(FALSE)");
    }

    protected SqlPredicateFragment isNull(String cn) {
        return of("(" + cn + " IS NULL)");
    }

    protected SqlPredicateFragment isNotNull(String cn) {
        return of("(" + cn + " IS NOT NULL)");
    }

    protected SqlPredicateFragment equal(String cn, Class<?> dbClass, FieldPredicate<?> model) {
        return of("(" + cn + " = ?)").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment equalString(String cn, FieldPredicate<?> model) {
        return equal(cn, String.class, model);
    }

    protected SqlPredicateFragment notEqual(String cn, Class<?> dbClass, FieldPredicate<?> model) {
        return of("(NOT (" + cn + " = ?))").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment notEqualString(String cn, FieldPredicate<?> model) {
        return notEqual(cn, String.class, model);
    }

    protected SqlPredicateFragment greaterThan(String cn, Class<?> dbClass, FieldPredicate<?> model) {
        return of("(" + cn + " > ?)").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment greaterThanString(String cn, FieldPredicate<?> model) {
        return greaterThan(cn, String.class, model);
    }

    protected SqlPredicateFragment greaterOrEqual(String cn, Class<?> dbClass, FieldPredicate<?> model) {
        return of("(" + cn + " >= ?)").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment greaterOrEqualString(String cn, FieldPredicate<?> model) {
        return greaterOrEqual(cn, String.class, model);
    }

    protected SqlPredicateFragment lessThan(String cn, Class<?> dbClass, FieldPredicate<?> model) {
        return of("(" + cn + " < ?)").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment lessThanString(String cn, FieldPredicate<?> model) {
        return lessThan(cn, String.class, model);
    }

    protected SqlPredicateFragment lessOrEqual(String cn, Class<?> dbClass, FieldPredicate<?> model) {
        return of("(" + cn + " <= ?)").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment lessOrEqualString(String cn, FieldPredicate<?> model) {
        return lessOrEqual(cn, String.class, model);
    }

    protected SqlPredicateFragment between(String cn, Class<?> dbClass, FieldPredicate<?> model) {
        return betweenHelper(cn, dbClass, model, false);
    }

    protected SqlPredicateFragment betweenString(String cn, FieldPredicate<?> model) {
        return between(cn, String.class, model);
    }

    protected SqlPredicateFragment notBetween(String cn, Class<?> dbClass, FieldPredicate<?> model) {
        return betweenHelper(cn, dbClass, model, true);
    }

    protected SqlPredicateFragment notBetweenString(String cn, FieldPredicate<?> model) {
        return notBetween(cn, String.class, model);
    }

    protected SqlPredicateFragment betweenHelper(String cn, Class<?> dbClass, FieldPredicate<?> model, boolean negated) {
        final Inclusion inclusion = getInclusionOperand(model);
        switch (inclusion) {
            case START_EXCLUSIVE_END_EXCLUSIVE: {
                return of("(" + cn + " > ? AND " + cn + " < ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
            case START_INCLUSIVE_END_EXCLUSIVE: {
                return of("(" + cn + " >= ? AND " + cn + " < ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
            case START_EXCLUSIVE_END_INCLUSIVE: {
                return of("(" + cn + " > ? AND " + cn + " <= ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
            case START_INCLUSIVE_END_INCLUSIVE: {
                return of("(" + cn + " >= ? AND " + cn + " <= ?)", negated).add(getFirstOperandAsRaw(model)).add(getSecondOperand(model));
            }
        }
        throw new IllegalArgumentException("Unknown Inclusion:" + inclusion);
    }

    protected SqlPredicateFragment in(String cn, Class<?> dbClass, FieldPredicate<?> model) {
        return inHelper(cn, dbClass, model, false);
    }

    protected SqlPredicateFragment inString(String cn, FieldPredicate<?> model) {
        return in(cn, String.class, model);
    }

    protected SqlPredicateFragment notIn(String cn, Class<?> dbClass, FieldPredicate<?> model) {
        return inHelper(cn, dbClass, model, true);
    }

    protected SqlPredicateFragment notInString(String cn, FieldPredicate<?> model) {
        return notIn(cn, String.class, model);
    }

    protected SqlPredicateFragment inHelper(String cn, Class<?> dbClass, FieldPredicate<?> model, boolean negated) {
        final Set<?> set = getFirstOperandAsRawSet(model);
        return of("(" + cn + " IN (" + set.stream().map($ -> "?").collect(joining(",")) + "))", negated).addAll(set);
    }

    /// String methods
    protected SqlPredicateFragment equalIgnoreCase(String cn, FieldPredicate<?> model) {
        return equalIgnoreCaseHelper(cn, model, false);
    }

    protected SqlPredicateFragment notEqualIgnoreCase(String cn, FieldPredicate<?> model) {
        return equalIgnoreCaseHelper(cn, model, true);
    }

    protected SqlPredicateFragment startsWith(String cn, FieldPredicate<?> model) {
        return startsWithHelper(cn, model, false);
    }

    protected SqlPredicateFragment notStartsWith(String cn, FieldPredicate<?> model) {
        return startsWithHelper(cn, model, true);
    }

    protected SqlPredicateFragment startsWithIgnoreCase(String cn, FieldPredicate<?> model) {
        return startsWithIgnoreCaseHelper(cn, model, false);
    }

    protected SqlPredicateFragment notStartsWithIgnoreCase(String cn, FieldPredicate<?> model) {
        return startsWithIgnoreCaseHelper(cn, model, true);
    }

    protected SqlPredicateFragment endsWith(String cn, FieldPredicate<?> model) {
        return endsWithHelper(cn, model, false);
    }

    protected SqlPredicateFragment notEndsWith(String cn, FieldPredicate<?> model) {
        return endsWithHelper(cn, model, true);
    }

    protected SqlPredicateFragment endsWithIgnoreCase(String cn, FieldPredicate<?> model) {
        return endsWithIgnoreCaseHelper(cn, model, false);
    }

    protected SqlPredicateFragment notEndsWithIgnoreCase(String cn, FieldPredicate<?> model) {
        return endsWithIgnoreCaseHelper(cn, model, true);
    }

    protected SqlPredicateFragment contains(String cn, FieldPredicate<?> model) {
        return containsHelper(cn, model, false);
    }

    protected SqlPredicateFragment notContains(String cn, FieldPredicate<?> model) {
        return containsHelper(cn, model, true);
    }

    protected SqlPredicateFragment containsIgnoreCase(String cn, FieldPredicate<?> model) {
        return containsIgnoreCaseHelper(cn, model, false);
    }

    protected SqlPredicateFragment notContainsIgnoreCase(String cn, FieldPredicate<?> model) {
        return containsIgnoreCaseHelper(cn, model, true);
    }

    protected SqlPredicateFragment isEmpty(String cn) {
        return of("(" + cn + " = '')");
    }

    protected SqlPredicateFragment isNotEmpty(String cn) {
        return of("(" + cn + " <> '')");
    }

    public static SqlPredicateFragment of(String sql) {
        return SqlPredicateFragment.of(sql);
    }

    public static SqlPredicateFragment of(String sql, Object object) {
        return SqlPredicateFragment.of(sql, object);
    }

    public static SqlPredicateFragment of(String sql, Collection<Object> objects) {
        return SqlPredicateFragment.of(sql, objects);
    }

    public static SqlPredicateFragment of(String sql, boolean negated) {
        if (negated) {
            return of("(NOT(" + sql + "))");
        } else {
            return of(sql);
        }
    }

    public static SqlPredicateFragment of(String sql, Object object, boolean negated) {
        if (negated) {
            return of("(NOT(" + sql + "))", object);
        } else {
            return of(sql, object);
        }
    }

    public static SqlPredicateFragment of(String sql, Collection<Object> objects, boolean negated) {
        if (negated) {
            return of("(NOT(" + sql + "))", objects);
        } else {
            return of(sql, objects);
        }
    }
}
