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
package com.speedment.runtime.internal.manager.sql;

import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.FieldPredicateView;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.predicate.PredicateType;
import com.speedment.runtime.field.predicate.SqlPredicateFragment;
import com.speedment.runtime.manager.ManagerSupport;

import java.util.Collection;
import java.util.Set;

import static com.speedment.runtime.internal.field.predicate.PredicateUtil.*;
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
    public <ENTITY> SqlPredicateFragment transform(ManagerSupport<ENTITY> manager, FieldPredicate<ENTITY> model) {
        return render(
            requireNonNull(manager),
            requireNonNull(model)
        );
    }

    protected <ENTITY> SqlPredicateFragment render(ManagerSupport<ENTITY> manager, FieldPredicate<ENTITY> model) {
        final PredicateType pt = model.getEffectivePredicateType();
        
        final String cn = manager.fullColumnName(model.getField());
        
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
            case EQUAL:
                return equal(cn, model);
            case NOT_EQUAL:
                return notEqual(cn, model);
            case GREATER_THAN:
                return greaterThan(cn, model);
            case GREATER_OR_EQUAL:
                return greaterOrEqual(cn, model);
            case LESS_THAN:
                return lessThan(cn, model);
            case LESS_OR_EQUAL:
                return lessOrEqual(cn, model);
            case BETWEEN:
                return between(cn, model);
            case NOT_BETWEEN:
                return notBetween(cn, model);
            case IN:
                return in(cn, model);
            case NOT_IN:
                return notIn(cn, model);

            // String
            case EQUAL_IGNORE_CASE:
                return equalIgnoreCase(cn, model);
            case NOT_EQUAL_IGNORE_CASE:
                return notEqualIgnoreCase(cn, model);

            case STARTS_WITH:
                return startsWith(cn, model);
            case NOT_STARTS_WITH:
                return notStartsWith(cn, model);
            case STARTS_WITH_IGNORE_CASE:
                return startsWithIgnoreCase(cn, model);
            case NOT_STARTS_WITH_IGNORE_CASE:
                return notStartsWithIgnoreCase(cn, model);   
            
            case ENDS_WITH:
                return endsWith(cn, model);
            case NOT_ENDS_WITH:
                return notEndsWith(cn, model);
            case ENDS_WITH_IGNORE_CASE:
                return endsWithIgnoreCase(cn, model);
            case NOT_ENDS_WITH_IGNORE_CASE:
                return notEndsWithIgnoreCase(cn, model);

            case CONTAINS:
                return contains(cn, model);
            case NOT_CONTAINS:
                return notContains(cn, model);
            case CONTAINS_IGNORE_CASE:
                return containsIgnoreCase(cn, model);
            case NOT_CONTAINS_IGNORE_CASE:
                return notContainsIgnoreCase(cn, model);

            case IS_EMPTY:
                return isEmpty(cn);
            case IS_NOT_EMPTY:
                return isNotEmpty(cn);
            default:
                throw new UnsupportedOperationException(
                    "Unknown PredicateType  " + pt.name() + ". Column name:" + model.getField().identifier().columnName()
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

    protected SqlPredicateFragment equal(String cn, FieldPredicate<?> model) {
        return of("(" + cn + " = ?)").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment notEqual(String cn, FieldPredicate<?> model) {
        return of("(NOT (" + cn + " = ?))").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment greaterThan(String cn, FieldPredicate<?> model) {
        return of("(" + cn + " > ?)").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment greaterOrEqual(String cn, FieldPredicate<?> model) {
        return of("(" + cn + " >= ?)").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment lessThan(String cn, FieldPredicate<?> model) {
        return of("(" + cn + " < ?)").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment lessOrEqual(String cn, FieldPredicate<?> model) {
        return of("(" + cn + " <= ?)").add(getFirstOperandAsRaw(model));
    }

    protected SqlPredicateFragment between(String cn, FieldPredicate<?> model) {
        return betweenHelper(cn, model, false);
    }

    protected SqlPredicateFragment notBetween(String cn, FieldPredicate<?> model) {
        return betweenHelper(cn, model, true);
    }

    protected SqlPredicateFragment betweenHelper(String cn, FieldPredicate<?> model, boolean negated) {
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

    protected SqlPredicateFragment in(String cn, FieldPredicate<?> model) {
        return inHelper(cn, model, false);
    }

    protected SqlPredicateFragment notIn(String cn, FieldPredicate<?> model) {
        return inHelper(cn, model, true);
    }

    protected SqlPredicateFragment inHelper(String cn, FieldPredicate<?> model, boolean negated) {
        final Set<?> set = getFirstOperandAsRawSet(model);
        return of("(" + cn + " IN (" + set.stream().map($ -> "?").collect(joining(",")) + "))", negated).addAll(set);
    }

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