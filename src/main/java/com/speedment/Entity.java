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
package com.speedment;

import com.speedment.db.MetaResult;
import com.speedment.annotation.Api;
import com.speedment.exception.SpeedmentException;
import com.speedment.field.ReferenceField;
import com.speedment.internal.core.field.encoder.JsonEncoder;
import java.util.function.Consumer;

/**
 * This interface contains the common methods that are the same for all
 * entities. Do not assume that an entity must implement this interface.
 *
 * @author pemi, Emil Forslund
 * @param <ENTITY> type
 */
@Api(version = "2.1")
public interface Entity<ENTITY> {

    /**
     * Creates and returns a new copy of this entity.
     *
     * @return Creates and returns a new copy of this entity
     */
    ENTITY copy();

    /**
     * Returns a JSON representation of this Entity using the default {@link
     * JsonEncoder}. All of the fields in this Entity will appear in the
     * returned JSON String.
     *
     * @return Returns a JSON representation of this Entity using the default
     * {@link JsonEncoder}
     */
    String toJson();

    /**
     * Returns a JSON representation of this Entity using the provided {@link
     * JsonEncoder}.
     *
     * @param jsonFormatter to use as a formatter
     * @return Returns a JSON representation of this Entity using the provided
     * {@link JsonEncoder}
     * @see JsonEncoder
     */
    String toJson(JsonEncoder<ENTITY> jsonFormatter);

    /**
     * Persists this entity to the underlying database and returns a potentially
     * updated entity. If the persistence fails for any reason, an unchecked
     * {@link SpeedmentException} is thrown.
     * <p>
     * It is unspecified if the returned updated entity is this entity instance
     * or another entity instance. It is erroneous to assume either, so you
     * should use only the returned entity after the method has been called.
     * However, it is guaranteed that this entity is untouched if an exception
     * is thrown.
     * <p>
     * The fields of returned entity instance may differ from this entity's
     * fields due to auto generated column(s) or because of any other
     * modification that the underlying database imposed on the persisted
     * entity.
     *
     * @return an entity reflecting the result of the persisted entity
     * @throws SpeedmentException if the underlying database throws an exception
     * (e.g. SQLException)
     */
    ENTITY persist() throws SpeedmentException;

    /**
     * Updates this entity in the underlying database and returns a potentially
     * updated entity. If the update fails for any reason, an unchecked
     * {@link SpeedmentException} is thrown.
     * <p>
     * It is unspecified if the returned updated entity is the same as this
     * entity instance or another entity instance. It is erroneous to assume
     * either, so you should use only the returned entity after the method has
     * been called. However, it is guaranteed that this entity is untouched if
     * an exception is thrown.
     * <p>
     * The fields of returned entity instance may differ from the provided
     * entity fields due to auto generated column(s) or because of any other
     * modification that the underlying database imposed on the persisted
     * entity.
     * <p>
     * Entities are uniquely identified by their primary key(s).
     *
     * @return an entity reflecting the result of the updated entity
     * @throws SpeedmentException if the underlying database throws an exception
     * (e.g. SQLException)
     */
    ENTITY update() throws SpeedmentException;

    /**
     * Removes the provided entity from the underlying database and returns this
     * entity instance. If the deletion fails for any reason, an unchecked
     * {@link SpeedmentException} is thrown.
     * <p>
     * Entities are uniquely identified by their primary key(s).
     *
     * @return the provided entity instance
     * @throws SpeedmentException if the underlying database throws an exception
     * (e.g. SQLException)
     */
    ENTITY remove() throws SpeedmentException;

    /**
     * Persists this entity to the underlying database and returns a potentially
     * updated entity. If the persistence fails for any reason, an unchecked
     * {@link SpeedmentException} is thrown.
     * <p>
     * It is unspecified if the returned updated entity is this entity instance
     * or another entity instance. It is erroneous to assume either, so you
     * should use only the returned entity after the method has been called.
     * However, it is guaranteed that this entity is untouched if an exception
     * is thrown.
     * <p>
     *      * The fields of returned entity instance may differ from this entity's
     * fields due to auto generated column(s) or because of any other
     * modification that the underlying database imposed on the persisted
     * entity.
     * <p>
     * The {@link MetaResult} {@link Consumer} provided will be called, with
     * meta data regarding the underlying database transaction, after the
     * persistence was attempted.
     *
     * @param consumer callback
     * @return an entity reflecting the result of the persisted entity
     * @throws SpeedmentException if the underlying database throws an exception
     * (e.g. SQLException)
     */
    ENTITY persist(Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException;

    /**
     * Updates this entity in the underlying database and returns a potentially
     * updated entity. If the update fails for any reason, an unchecked
     * {@link SpeedmentException} is thrown.
     * <p>
     * It is unspecified if the returned updated entity is the same as this
     * entity instance or another entity instance. It is erroneous to assume
     * either, so you should use only the returned entity after the method has
     * been called. However, it is guaranteed that this entity is untouched if
     * an exception is thrown.
     * <p>
     * The fields of returned entity instance may differ from the provided
     * entity fields due to auto generated column(s) or because of any other
     * modification that the underlying database imposed on the persisted
     * entity.
     * <p>
     * Entities are uniquely identified by their primary key(s).
     * <p>
     * The {@link MetaResult} {@link Consumer} provided will be called, with
     * meta data regarding the underlying database transaction, after the update
     * was attempted.
     *
     * @param consumer callback
     * @return an entity reflecting the result of the updated entity
     * @throws SpeedmentException if the underlying database throws an exception
     * (e.g. SQLException)
     */
    ENTITY update(Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException;

    /**
     * Removes the provided entity from the underlying database and returns this
     * entity instance. If the deletion fails for any reason, an unchecked
     * {@link SpeedmentException} is thrown.
     * <p>
     * Entities are uniquely identified by their primary key(s).
     * <p>
     * The {@link MetaResult} {@link Consumer} provided will be called, with
     * meta data regarding the underlying database transaction, after the
     * removal was attempted.
     *
     * @param consumer callback
     * @return the provided entity instance
     * @throws SpeedmentException if the underlying database throws an exception
     * (e.g. SQLException)
     */
    ENTITY remove(Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException;
}
