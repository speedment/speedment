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

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * An action that takes an entity and persists it to a data store. This 
 * interface extends the standard {@code UnaryOperator}- and 
 * {@code Consumer}-interfaces so that it can be used inside a {@code Stream}.
 * 
 * @param <ENTITY>  the entity type
 * 
 * @author  Per Minborg
 * @since   3.0.1
 */
@FunctionalInterface
public interface Persister<ENTITY> extends UnaryOperator<ENTITY>, Consumer<ENTITY>  {
    /**
     * Persists the provided entity to the underlying database and returns a
     * potentially updated entity. If the persistence fails for any reason, an
     * unchecked {@link SpeedmentException} is thrown.
     * <p>
     * It is unspecified if the returned updated entity is the same provided
     * entity instance or another entity instance. It is erroneous to assume
     * either, so you should use only the returned entity after the method has
     * been called. However, it is guaranteed that the provided entity is
     * untouched if an exception is thrown.
     * <p>
     * The fields of returned entity instance may differ from the provided
     * entity fields due to auto generated column(s) or because of any other
     * modification that the underlying database imposed on the persisted
     * entity.
     *
     * @param entity to persist
     * @return an entity reflecting the result of the persisted entity
     *
     * @throws SpeedmentException if the underlying database throws an exception
     * (e.g. SQLException)
     */
    @Override
    ENTITY apply(ENTITY entity);

    /**
     * Persists the entity in the data store. The specified instance might be
     * modified by this method in some implementations.
     * 
     * @param entity  the entity to persist
     * 
     * @throws SpeedmentException  if persisting the entity failed
     */
    @Override
    default void accept(ENTITY entity) {
        apply(entity);
    }
}