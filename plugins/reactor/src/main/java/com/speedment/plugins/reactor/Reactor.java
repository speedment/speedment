/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.plugins.reactor;

import com.speedment.plugins.reactor.internal.builder.ReferenceReactorBuilder;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.manager.Manager;
import java.util.List;
import java.util.function.Consumer;


/**
 * A reactor is an object that polls the database for changes at a particular 
 * interval, and if changes was found, notifies a set of listeners.
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface Reactor {

    /**
     * Stops the reactor from polling the database.
     */
    void stop();

    /**
     * Creates a new reactor builder for the specified {@link Manager} by using
     * the specified field to identify which events refer to the same entity.
     * This is normally different from the primary key.
     * 
     * @param <ENTITY>  the entity type
     * @param <T>       the primary key of the entity table
     * @param manager   the manager to use
     * @param idField   the field that identifier which entity is referred to in
     *                  the event
     * @return  the new builder
     */
    static <ENTITY, T extends Comparable<T>> Builder<ENTITY, T> builder(
            Manager<ENTITY> manager, 
            ComparableField<ENTITY, ?, T> idField) {
        
        return new ReferenceReactorBuilder<>(manager, idField);
    }
    
    /**
     * Builder pattern for the {@link Reactor} interface.
     * 
     * @param <ENTITY>  the entity type
     * @param <T>       the merging type
     */
    interface Builder<ENTITY, T extends Comparable<T>> {
        
        /**
         * Adds a listener to the reactor being built. The listener will be
         * notified each time new rows are loaded.
         * 
         * @param listener  the new listener
         * @return          a reference to this builder
         */
        Builder<ENTITY, T> withListener(Consumer<List<ENTITY>> listener);

        /**
         * Sets the interval for which the database will be polled for changes.
         * The interval is specified in milliseconds.
         * <p>
         * This setting is optional. If it is not specified, an interval of 1000 
         * milliseconds will be used.
         * 
         * @param millis  the interval in milliseconds
         * @return        a reference to this builder
         */
        Builder<ENTITY, T> withInterval(long millis);

        /**
         * Sets the maximum amount of rows that might be loaded at once from
         * the database. Setting a limit might be a good way to prevent the 
         * reactor from clogging up the system during load.
         * <p>
         * This setting is optional. If it is not specified, the limit will be
         * 100 elements per load.
         * 
         * @param count  the maximum amount of rows that might be loaded at once
         * @return       a reference to this builder
         */
        Builder<ENTITY, T> withLimit(long count);
        
        /**
         * Builds and starts this reactor. When this method is called, the 
         * reactor will start polling the database at the specified interval.
         * The returned instance could be ignored, but it might be good to hold
         * on to it since that is the only way to stop the reactor once started.
         * 
         * @return  the running reactor
         */
        Reactor build();
    }
}