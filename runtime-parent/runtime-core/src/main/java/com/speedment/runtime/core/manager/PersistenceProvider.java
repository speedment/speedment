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
package com.speedment.runtime.core.manager;

import com.speedment.runtime.core.exception.SpeedmentException;

public interface PersistenceProvider<ENTITY> {
    /**
     * Returns a {@link Persister} which can be used to persist
     * entities in the underlying database.
     * @see Persister#apply
     *
     * @return a Persister
     */
    Persister<ENTITY> persister();

    /**
     * Provides a {@link Persister} that operates on a given subset of entity fields. Useful
     * for example when persisting an entity with auto incremented fields of fields with
     * defaults.
     * See {@link #persister()}
     * @param fields the fields to persist, any others are ignored
     * @return a Persister
     */
    Persister<ENTITY> persister(HasLabelSet<ENTITY> fields);

    /**
     * Returns a {@link Updater} which can be used to update
     * entities in the underlying database.
     * @see Updater#apply
     *
     * @return an Updater
     */
    Updater<ENTITY> updater();

    /**
     * Provides a {@link Updater} that operates on a given subset of entity fields. Useful
     * for example when persisting an entity with auto incremented fields of fields with
     * defaults.
     * See {@link #updater()}
     * @param fields the fields to update, any others are ignored
     * @return a Persister
     */
    Updater<ENTITY> updater(HasLabelSet<ENTITY> fields);

    /**
     * Returns a {@link Remover} which can be used to remove
     * entities in the underlying database.
     * @see Remover#apply
     *
     * @return a Remover
     */
    Remover<ENTITY> remover();

    /**
     * Returns a {@link Merger} which can be used to merge
     * entities in the underlying database.
     * @see Merger#apply
     *
     * @return a Merger
     */
    default Merger<ENTITY> merger() {
        return entity -> {
            try {
                persister().apply(entity);
                return entity;
            } catch (SpeedmentException se) {
                updater().apply(entity);
                return entity;
            }
        };
    }
}