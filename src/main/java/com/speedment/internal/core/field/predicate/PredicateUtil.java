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
import com.speedment.internal.core.field.predicate.iface.type.HasInclusionOperand2;
import com.speedment.internal.core.field.predicate.iface.type.HasOperand0;
import com.speedment.internal.core.field.predicate.iface.type.HasOperand1;
import com.speedment.internal.core.field.predicate.iface.type.HasOperand2;
import com.speedment.internal.core.field.predicate.iface.type.HasSetOperand0;
import com.speedment.internal.util.Cast;
import java.util.Set;

/**
 *
 * @author pemi
 */
public class PredicateUtil {

    public static Object get0Raw(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasOperand0.class).getOperand0();
    }

    public static <ENTITY, V> V get0(SpeedmentPredicate<ENTITY, V> p) {
        @SuppressWarnings("unchecked")
        final V result = (V) Cast.castOrFail(p, HasOperand0.class).getOperand0();
        return result;
    }

    public static Set<?> getSet0Raw(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasSetOperand0.class).getOperand0();
    }

    public static <ENTITY, V> Set<V> getSet0(SpeedmentPredicate<ENTITY, V> p) {
        @SuppressWarnings("unchecked")
        final Set<V> result = (Set<V>) Cast.castOrFail(p, HasOperand0.class).getOperand0();
        return result;
    }

    public static <ENTITY, V> V get1(SpeedmentPredicate<ENTITY, V> p) {
        @SuppressWarnings("unchecked")
        final V result = (V) Cast.castOrFail(p, HasOperand1.class).getOperand1();
        return result;
    }

    public static Object get1Raw(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasOperand1.class).getOperand1();
    }

    public static <ENTITY, V> V get2(SpeedmentPredicate<ENTITY, V> p) {
        @SuppressWarnings("unchecked")
        final V result = (V) Cast.castOrFail(p, HasOperand2.class).getOperand2();
        return result;
    }

    public static Object get2Raw(SpeedmentPredicate<?, ?> p) {
        return Cast.castOrFail(p, HasOperand2.class).getOperand2();
    }

    public static Inclusion getInclusion2(SpeedmentPredicate<?, ?> p) {
        //QuaternaryInclusionOperation
        return Cast.castOrFail(p, HasInclusionOperand2.class).getOperand2();
    }

    private PredicateUtil() {
    }

}
