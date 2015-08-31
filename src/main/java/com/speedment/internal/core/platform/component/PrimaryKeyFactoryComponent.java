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
package com.speedment.internal.core.platform.component;

import java.util.List;

/**
 * The PrimaryKeyFactoryComponent is responsible for generating primary keys. By
 * plugging in a custom PrimaryKeyFactoryComponent, one may produce custom
 * primary keys.
 *
 * @author pemi
 * @since 2.0
 */
public interface PrimaryKeyFactoryComponent extends Component {

    /**
     * Optionally creates and return a (possibly) new key for the given key. The
     * new key must be immutable and must produce the same hash key during its
     * entire life-cycle.
     *
     * @param <K0> key type
     * @param key the input key
     * @return the output key that corresponds to the given key
     */
    // Identity function by default, Override to change
    default <K0> K0 make(K0 key) {
        return key;
    }

    // Todo: Investigate if any Object can be returned as composite keys. Not just List
    /**
     * Creates and return a new {@code List} that contains the given keys in the
     * given order. The new List must be immutable and must produce the same
     * {@link Object#hashCode()} during its entire life-cycle. List with the
     * same input keys must produce the same {@link Object#hashCode()}.
     *
     * @param <K0> 0:th key type
     * @param <K1> 1:st key type
     * @param k0 0:th key
     * @param k1 1:st key
     * @return an immutable List of the keys provided
     */
    <K0, K1> List<?> make(K0 k0, K1 k1);

    /**
     * Creates and return a new {@code List} that contains the given keys in the
     * given order. The new List must be immutable and must produce the same
     * {@link Object#hashCode()} during its entire life-cycle. List with the
     * same input keys must produce the same {@link Object#hashCode()}.
     *
     * @param <K0> 0:th key type
     * @param <K1> 1:st key type
     * @param <K2> 2:nd key type
     * @param k0 0:th key
     * @param k1 1:st key
     * @param k2 2:nd key
     * @return an immutable List of the keys provided
     */
    <K0, K1, K2> List<?> make(K0 k0, K1 k1, K2 k2);

    /**
     * Creates and return a new {@code List} that contains the given keys in the
     * given order. The new List must be immutable and must produce the same
     * {@link Object#hashCode()} during its entire life-cycle. List with the
     * same input keys must produce the same {@link Object#hashCode()}.
     *
     * @param <K0> 0:th key type
     * @param <K1> 1:st key type
     * @param <K2> 2:nd key type
     * @param <K3> 3:rd key type
     * @param k0 0:th key
     * @param k1 1:st key
     * @param k2 2:nd key
     * @param k3 3:rd key
     * @return an immutable List of the keys provided
     */
    <K0, K1, K2, K3> List<?> make(K0 k0, K1 k1, K2 k2, K3 k3);

    /**
     * Creates and return a new {@code List} that contains the given keys in the
     * given order. The new List must be immutable and must produce the same
     * {@link Object#hashCode()} during its entire life-cycle. List with the
     * same input keys must produce the same {@link Object#hashCode()}.
     *
     * @param <K0> 0:th key type
     * @param <K1> 1:st key type
     * @param <K2> 2:nd key type
     * @param <K3> 3:rd key type
     * @param <K4> 4:th key type
     * @param k0 0:th key
     * @param k1 1:st key
     * @param k2 2:nd key
     * @param k3 3:rd key
     * @param k4 4:th key
     * @return an immutable List of the keys provided
     */
    <K0, K1, K2, K3, K4> List<?> make(K0 k0, K1 k1, K2 k2, K3 k3, K4 k4);

    /**
     * Creates and return a new {@code List} that contains the given keys in the
     * given order. The new List must be immutable and must produce the same
     * {@link Object#hashCode()} during its entire life-cycle. List with the
     * same input keys must produce the same {@link Object#hashCode()}.
     *
     * @param <K0> 0:th key type
     * @param <K1> 1:st key type
     * @param <K2> 2:nd key type
     * @param <K3> 3:rd key type
     * @param <K4> 4:th key type
     * @param k0 0:th key
     * @param k1 1:st key
     * @param k2 2:nd key
     * @param k3 3:rd key
     * @param k4 4:th key
     * @param otherKeys the 5:th to the N:th key
     * @return an immutable List of the keys provided
     */
    <K0, K1, K2, K3, K4> List<?> make(K0 k0, K1 k1, K2 k2, K3 k3, K4 k4, Object... otherKeys);

}
