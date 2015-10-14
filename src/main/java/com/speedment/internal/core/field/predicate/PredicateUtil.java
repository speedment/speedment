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
package com.speedment.internal.core.field.predicate;

import com.speedment.field.Inclusion;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.internal.util.Cast;
import java.util.Set;
import com.speedment.internal.core.field.predicate.iface.type.HasFirstOperand;
import com.speedment.internal.core.field.predicate.iface.type.HasFirstSetOperand;
import com.speedment.internal.core.field.predicate.iface.type.HasSecondOperand;
import com.speedment.internal.core.field.predicate.iface.type.HasThirdOperand;
import com.speedment.internal.core.field.predicate.iface.type.HasThirdInclusionOperand;

/**
 *
 * @author pemi
 */
public class PredicateUtil {

    public static Object getFirstOperandAsRaw(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasFirstOperand.class).getFirstOperand();
    }

    public static <ENTITY, V> V getFirstOperand(SpeedmentPredicate<ENTITY, V> p) {
        @SuppressWarnings("unchecked")
        final V result = (V) Cast.castOrFail(p, HasFirstOperand.class).getFirstOperand();
        return result;
    }

    public static Set<?> getFirstOperandAsRawSet(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasFirstSetOperand.class).getFirstOperand();
    }

    public static <ENTITY, V> Set<?> getFirstOperandAsSet(SpeedmentPredicate<ENTITY, V> p) {
        @SuppressWarnings("unchecked")
        final Set<V> result = (Set<V>) Cast.castOrFail(p, HasFirstOperand.class).getFirstOperand();
        return result;
    }

    public static <ENTITY, V> V getSecondOperand(SpeedmentPredicate<ENTITY, V> p) {
        @SuppressWarnings("unchecked")
        final V result = (V) Cast.castOrFail(p, HasSecondOperand.class).getSecondOperand();
        return result;
    }

    public static Object getSecondOperandAsRaw(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasSecondOperand.class).getSecondOperand();
    }

    public static <ENTITY, V> V getThirdOperand(SpeedmentPredicate<ENTITY, V> p) {
        @SuppressWarnings("unchecked")
        final V result = (V) Cast.castOrFail(p, HasThirdOperand.class).getThirdOperand();
        return result;
    }

    public static Object getThirdOperandAsRaw(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasThirdOperand.class).getThirdOperand();
    }

    public static Inclusion getThirdOperandAsInclusion(SpeedmentPredicate<?, ?> p) {
        //QuaternaryInclusionOperation
        return Cast.castOrFail(p, HasThirdInclusionOperand.class).getThirdOperand();
    }

    private PredicateUtil() {
    }

}
