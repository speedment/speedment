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
package com.speedment.internal.core.manager;

import com.speedment.Speedment;
import com.speedment.config.Column;
import com.speedment.config.Dbms;
import com.speedment.config.PrimaryKeyColumn;
import com.speedment.config.Table;
import com.speedment.db.CrudHandler;
import com.speedment.db.MetaResult;
import com.speedment.db.crud.Result;
import com.speedment.db.crud.Selector;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.db.crud.CreateImpl;
import com.speedment.internal.core.db.crud.DeleteImpl;
import com.speedment.internal.core.db.crud.ReadImpl;
import com.speedment.internal.core.db.crud.SelectorImpl;
import com.speedment.internal.core.db.crud.UpdateImpl;
import com.speedment.internal.core.stream.MapStream;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Abstract base implementation of a Manager that translates all persist,
 * update and remove operations into their corresponding CRUD operations and
 * feeds them to the installed {@link CrudHandlerComponent}.
 * 
 * @author Emil Forslund
 * @param <ENTITY>  the type of the entity to manage
 */
public abstract class AbstractCrudManager<ENTITY> extends AbstractManager<ENTITY> {
    
    private final CrudHandler handler;
    private final Column primaryKeyColumn;
    
    /**
     * Instantiates the manager. This should not be called until the relevant
     * {@link CrudHandlerComponent} has been loaded!
     * 
     * @param speedment  the speedment instance
     */
    protected AbstractCrudManager(Speedment speedment) {
        super(speedment);
        
        final Dbms dbms = getTable().ancestor(Dbms.class).orElseThrow(
            () -> new SpeedmentException("Can not instantiate an entity manager without a Dbms.")
        );
        
        this.handler          = dbms.getType().makeCrudHandler(speedment, dbms);
        this.primaryKeyColumn = findColumnOfPrimaryKey(getTable());
    }
    
    /**
     * Produces a new instance of the entity that this manager manages. This is
     * only used internally to implement the {@link #persist(java.lang.Object)}, 
     * {@link #update(java.lang.Object)} and {@link #stream()} methods.
     * 
     * @param result
     * @return 
     */
    protected abstract ENTITY newInstance(Result result);
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public final Stream<ENTITY> stream() {
        return handler.read(
            new ReadImpl.Builder(getTable())
                .build(),
            this::newInstance
        );
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public final ENTITY persist(ENTITY entity) throws SpeedmentException {
        return handler.create(
            new CreateImpl.Builder(getTable())
                .with(valuesFor(entity))
                .build(), 
            result -> addGeneratedValuesTo(entity, result)
        );
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public final ENTITY update(ENTITY entity) throws SpeedmentException {
        return handler.update(
            new UpdateImpl.Builder(getTable())
                .with(valuesFor(entity))
                .where(selectorFor(entity))
                .build(), 
            result -> addGeneratedValuesTo(entity, result)
        );
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public final ENTITY remove(ENTITY entity) throws SpeedmentException {
        handler.delete(
            new DeleteImpl.Builder(getTable())
                .where(selectorFor(entity))
                .build()
        );
        
        return entity;
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public final ENTITY persist(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException {
        throw new UnsupportedOperationException("Meta result consumers are not supported with crud operations yet.");
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public final ENTITY update(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException {
        throw new UnsupportedOperationException("Meta result consumers are not supported with crud operations yet.");
    }

    /**
     * {@inheritDoc} 
     */
    @Override
    public final ENTITY remove(ENTITY entity, Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException {
        throw new UnsupportedOperationException("Meta result consumers are not supported with crud operations yet.");
    }

    /**
     * Returns a {@link Selector} that matches the specified entity's primary
     * key.
     * 
     * @param entity  to match
     * @return        matching selector
     */
    private Selector selectorFor(ENTITY entity) {
        return SelectorImpl.standard(
            primaryKeyColumn.getName(), 
            get(entity, primaryKeyColumn)
        );
    }
    
    /**
     * Returns a map of all the values in the specified entity mapped to the
     * name of the column.
     * 
     * @param entity  the entity to get the values from
     * @return        values mapped to column names
     */
    private Map<String, Object> valuesFor(ENTITY entity) {
        return MapStream.fromStream(getTable().streamOf(Column.class), 
            col -> col.getName(), 
            col -> get(entity, col)
        ).toMap();
    }
    
    /**
     * Changes the primary key value in the specified entity by reading from the
     * specified result.
     * 
     * @param entity  the entity to change
     * @param result  the result to load the foreign key value from
     * @return        the same entity as inputed
     */
    private ENTITY addGeneratedValuesTo(ENTITY entity, Result result) {
        final Column pk = findColumnOfPrimaryKey(getTable());
        set(entity, pk, result.get(pk.getName(), pk.getMapping()));
        return entity;
    }
    
    /**
     * Finds the {@link Column} that describes the primary key of the specified
     * table. Note that it is <b>not</b> the {@code PrimaryKeyColumn} node but 
     * the {@link Column} node that is returned!
     * 
     * @param table  the table
     * @return       the column of the primary key
     */
    private static Column findColumnOfPrimaryKey(Table table) {
        return table.streamOf(PrimaryKeyColumn.class).findFirst()
            .orElseThrow(() -> new SpeedmentException(
                "Could not find any primary key in table '" + table.getName() + "'."
            )).getColumn();
    }
}