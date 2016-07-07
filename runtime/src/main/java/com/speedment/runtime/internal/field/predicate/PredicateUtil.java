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
package com.speedment.runtime.internal.field.predicate;

import com.speedment.runtime.field.Inclusion;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;
import com.speedment.runtime.internal.field.predicate.iface.type.*;
import com.speedment.runtime.internal.util.Cast;

import java.util.Set;

/**
 *
 * @author pemi
 */
public class PredicateUtil {

    public static Object getFirstOperandAsRaw(SpeedmentPredicate<?, ?, ?> p) {
        return Cast.castOrFail(p, HasFirstOperand.class).getFirstOperand();
    }

    public static <ENTITY, D, V> V getFirstOperand(SpeedmentPredicate<ENTITY, D, V> p) {
        @SuppressWarnings("unchecked")
        final V result = (V) Cast.castOrFail(p, HasFirstOperand.class).getFirstOperand();
        return result;
    }

    public static Set<?> getFirstOperandAsRawSet(SpeedmentPredicate<?, ?, ?> p) {
        return Cast.castOrFail(p, HasFirstSetOperand.class).getFirstOperand();
    }

    public static <ENTITY, D, V> Set<V> getFirstOperandAsSet(SpeedmentPredicate<ENTITY, D, V> p) {
        @SuppressWarnings("unchecked")
        final Set<V> result = (Set<V>) Cast.castOrFail(p, HasFirstOperand.class).getFirstOperand();
        return result;
    }

    public static <ENTITY, D, V> V getSecondOperand(SpeedmentPredicate<ENTITY, D, V> p) {
        @SuppressWarnings("unchecked")
        final V result = (V) Cast.castOrFail(p, HasSecondOperand.class).getSecondOperand();
        return result;
    }

    public static Object getSecondOperandAsRaw(SpeedmentPredicate<?, ?, ?> p) {
        return Cast.castOrFail(p, HasSecondOperand.class).getSecondOperand();
    }

    public static <ENTITY, D, V> V getThirdOperand(SpeedmentPredicate<ENTITY, D, V> p) {
        @SuppressWarnings("unchecked")
        final V result = (V) Cast.castOrFail(p, HasThirdOperand.class).getThirdOperand();
        return result;
    }

    public static Object getThirdOperandAsRaw(SpeedmentPredicate<?, ?, ?> p) {
        return Cast.castOrFail(p, HasThirdOperand.class).getThirdOperand();
    }

    public static Inclusion getThirdOperandAsInclusion(SpeedmentPredicate<?, ?, ?> p) {
        //QuaternaryInclusionOperation
        return Cast.castOrFail(p, HasThirdInclusionOperand.class).getThirdOperand();
    }

    private PredicateUtil() {
    }

}
