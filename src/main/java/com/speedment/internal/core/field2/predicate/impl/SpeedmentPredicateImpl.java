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
package com.speedment.internal.core.field2.predicate.impl;

import com.speedment.internal.core.field2.predicate.PredicateType;
import com.speedment.internal.core.field2.predicate.iface.SpeedmentPredicate;
import com.speedment.field2.methods.Getter;

/**
 *
 * @author pemi
 * @param <ENTITY> entity type
 * @param <V> value type
 */
public abstract class SpeedmentPredicateImpl<ENTITY, V> implements SpeedmentPredicate<ENTITY, V> {

    private final PredicateType predicateType;
    private final Getter<ENTITY, V> getter;
//    private final Predicate<V> fieldPredicate;

    public SpeedmentPredicateImpl(PredicateType predicateType, Getter<ENTITY, V> getter/*, Predicate<V> fieldPredicate*/) {
        this.predicateType = predicateType;
        this.getter = getter;
//        this.fieldPredicate = fieldPredicate;
    }

    @Override
    public PredicateType getPredicateType() {
        return predicateType;
    }

    @Override
    public Getter<ENTITY, V> getter() {
        return getter;
    }

    public abstract boolean testField(V fieldValue);

    @Override
    public boolean test(ENTITY t) {
        return testField(getter.apply(t));
    }
}
