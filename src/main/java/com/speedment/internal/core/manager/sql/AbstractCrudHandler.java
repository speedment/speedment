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
package com.speedment.internal.core.manager.sql;

import com.speedment.Speedment;
import com.speedment.db.crud.Create;
import com.speedment.db.crud.Delete;
import com.speedment.db.crud.Read;
import com.speedment.db.crud.Result;
import com.speedment.db.crud.Update;
import com.speedment.exception.SpeedmentException;
import com.speedment.component.CrudHandlerComponent;
import com.speedment.db.CrudHandler;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Abstract base class for different CrudHandlers.
 * 
 * @author Emil Forslund
 */
public abstract class AbstractCrudHandler implements CrudHandler {
    
    private final Speedment speedment;

    /**
     * Initiates this AbstractCrudHandler.
     * @param speedment  the speedment instance to use.
     */
    protected AbstractCrudHandler(Speedment speedment) {
        this.speedment = speedment;
    }
    
    /**
     * Sets this handler as primary in the {@link CrudHandlerComponent}.
     */
    @Override
    public final void setAsPrimary() {
        speedment.get(CrudHandlerComponent.class)
            .setCreator(this::create)
            .setUpdater(this::update)
            .setDeleter(this::delete)
            .setReader(this::read);
    }

    /**
     * The create logic for this handler.
     * 
     * @param <T>                  the type of the entity to create
     * @param create               the {@link Create} operation to execute
     * @param mapper               used to transform the {@link Result} to an entity
     * @return                     the transformed entity
     * @throws SpeedmentException  if the entity could not be created
     */
    protected abstract <T> T create(Create create, Function<Result, T> mapper) throws SpeedmentException;
    
    /**
     * The update logic for this handler.
     * 
     * @param <T>                  the type of the entity to update
     * @param update               the {@link Update} operation to execute
     * @param mapper               used to transform the {@link Result} to an entity
     * @return                     the transformed entity
     * @throws SpeedmentException  if the entity could not be updated
     */
    protected abstract <T> T update(Update update, Function<Result, T> mapper) throws SpeedmentException;
    
    /**
     * The delete logic for this handler.
     * 
     * @param delete               the {@link Delete} operation to execute
     * @throws SpeedmentException  if the entity could not be deleted
     */
    protected abstract void delete(Delete delete) throws SpeedmentException;
    
    /**
     * The read logic for this handler.
     * 
     * @param <T>                  the type of the entities to read
     * @param read                 the {@link Read} operation to execute
     * @param mapper               used to transform the {@link Result} to an entity
     * @return                     a stream of transformed entities
     * @throws SpeedmentException  if the entities could not be read
     */
    protected abstract <T> Stream<T> read(Read read, Function<Result, T> mapper) throws SpeedmentException;
    
    /**
     * Returns the current speedment instance.
     * 
     * @return  the speedment instance
     */
    protected final Speedment speedment() {
        return speedment;
    }
}