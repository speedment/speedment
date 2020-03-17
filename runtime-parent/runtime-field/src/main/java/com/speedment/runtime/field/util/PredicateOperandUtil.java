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
package com.speedment.runtime.field.util;

import com.speedment.common.tuple.Tuple;
import com.speedment.runtime.field.predicate.trait.HasInclusion;
import com.speedment.runtime.field.internal.util.Cast;
import com.speedment.runtime.field.predicate.FieldPredicate;
import com.speedment.runtime.field.predicate.Inclusion;

import java.util.Set;

/**
 * Utility class for getting individual operands from a {@link FieldPredicate}.
 *
 * @author  Emil Forslund
 * @since   3.0.11
 */
public final class PredicateOperandUtil {
    private PredicateOperandUtil() {}
    /**
     * Returns the first operand of the specified {@link FieldPredicate},
     * throwing an exception if it doesn't exist. The operand may however be
     * {@code null}.
     *
     * @param p  the predicate
     * @return   the operand
     */
    public static Object getFirstOperandAsRaw(FieldPredicate<?> p) {
        return Cast.castOrFail(p, Tuple.class).get(0);
    }

    /**
     * Returns the first operand of the specified {@link FieldPredicate} as
     * a {@code java.util.Set}, throwing an exception if it doesn't exist or if
     * it wasn't a {@code Set}. The operand may however be {@code null}.
     *
     * @param p  the predicate
     * @return   the operand
     */
    public static Set<?> getFirstOperandAsRawSet(FieldPredicate<?> p) {
        return Cast.castOrFail(getFirstOperandAsRaw(p), Set.class);
    }

    /**
     * Returns the second operand of the specified {@link FieldPredicate},
     * throwing an exception if it doesn't exist. The operand may however be
     * {@code null}.
     *
     * @param p  the predicate
     * @return   the operand
     */
    public static Object getSecondOperand(FieldPredicate<?> p) {
        return Cast.castOrFail(p, Tuple.class).get(1);
    }

    /**
     * Returns the {@link Inclusion} operand of the specified
     * {@link FieldPredicate}, throwing an exception if it doesn't exist. The
     * operand may however be {@code null}.
     *
     * @param p  the predicate
     * @return   the operand
     */
    public static Inclusion getInclusionOperand(FieldPredicate<?> p) {
        return Cast.castOrFail(p, HasInclusion.class).getInclusion();
    }

}