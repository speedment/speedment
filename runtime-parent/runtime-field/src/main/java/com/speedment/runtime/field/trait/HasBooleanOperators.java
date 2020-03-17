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
package com.speedment.runtime.field.trait;

import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.predicate.SpeedmentPredicate;

/**
 * A representation of an Entity field that is of boolean type
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @since  3.0.17
 */
public interface HasBooleanOperators<ENTITY> extends Field<ENTITY> {

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>equal</em> to the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>equal</em> to the given value
     */
    SpeedmentPredicate<ENTITY> equal(boolean value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not equal</em> to the
     * given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not equal</em> to the given value
     */
    SpeedmentPredicate<ENTITY> notEqual(boolean value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>true</em>
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>true</em>.
     */
    default SpeedmentPredicate<ENTITY> isTrue() {
        return equal(true);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>false</em>
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>false</em>.
     */
    default SpeedmentPredicate<ENTITY> isFalse() {
        return equal(false);
    }

}
