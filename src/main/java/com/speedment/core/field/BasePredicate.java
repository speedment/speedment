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
package com.speedment.core.field;

import com.speedment.core.field.CombinedBasePredicate.AndCombinedBasePredicate;
import com.speedment.core.field.CombinedBasePredicate.OrCombinedBasePredicate;
import java.util.function.Predicate;

/**
 *
 * @author pemi
 * @param <T> Type
 */
public abstract class BasePredicate<T> implements Predicate<T> {

    private boolean negated;

    public BasePredicate() {
        negated = false;
    }

    @Override
    public Predicate<T> and(Predicate<? super T> other) {
        return new AndCombinedBasePredicate<>(this, other);
    }

    @Override
    public Predicate<T> or(Predicate<? super T> other) {
        return new OrCombinedBasePredicate<>(this, other);
    }

    @Override
    public BasePredicate<T> negate() {
        negated = !negated;
        return this;
    }

}
