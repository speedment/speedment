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
package com.speedment.runtime.core.internal.field.predicate;

import com.speedment.common.tuple.Tuple;
import com.speedment.runtime.core.field.predicate.FieldPredicate;
import com.speedment.runtime.core.field.predicate.Inclusion;
import com.speedment.runtime.core.internal.util.Cast;

import java.util.Set;

/**
 *
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class PredicateUtil {

    public static Object getFirstOperandAsRaw(FieldPredicate<?> p) {
        return Cast.castOrFail(p, Tuple.class).get(0);
    }

    public static Set<?> getFirstOperandAsRawSet(FieldPredicate<?> p) {
        return Cast.castOrFail(getFirstOperandAsRaw(p), Set.class);
    }

    public static Object getSecondOperand(FieldPredicate<?> p) {
        return Cast.castOrFail(p, Tuple.class).get(1);
    }

    public static Object getThirdOperand(FieldPredicate<?> p) {
        return Cast.castOrFail(p, Tuple.class).get(2);
    }

    public static Inclusion getInclusionOperand(FieldPredicate<?> p) {
        return Cast.castOrFail(p, BetweenPredicate.class).getInclusion();
    }

    private PredicateUtil() {}
}