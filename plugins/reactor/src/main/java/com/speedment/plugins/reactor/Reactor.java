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

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.manager.Manager;
import static java.util.Collections.newSetFromMap;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import static java.util.stream.Collectors.toList;

/**
 * A reactor is an object that polls the database for changes at a particular 
 * interval, and if changes was found, notifies a set of listeners.
 * 
 * @author          Emil Forslund
 * @param <ENTITY>  the entity type
 * @param <T>       the primary key of the entity table
 */
public final class Reactor<ENTITY, T extends Comparable<T>> {
    
    public final static int 
        DEFAULT_INTERVAL = 1000, // in milliseconds.
        DEFAULT_LIMIT    = 100;
    
    private final static Logger LOGGER = 
        LoggerManager.getLogger(Reactor.class);
    
    private final Timer timer;
    
    /**
     * Constructs a new Reactor. This should only be called as part of the
     * builder pattern for this class and never directly.
     * 
     * @param timer  the timer that is polling the database
     */
    private Reactor(Timer timer) {
        this.timer = requireNonNull(timer);
    }
    
    /**
     * Stops the reactor from polling the database.
     */
    public void stop() {
        timer.cancel();
    }

    /**
     * Creates a new reactor builder for the specified {@link Manager} by using
     * the specified field to identify which events refer to the same entity.
     * This is normally different from the primary key.
     * 
     * @param <ENTITY>  the entity type
     * @param <T>       the primary key of the entity table
     * @param manager   the manager to use
     * @param idField   the field that identifier which entity is refered to in 
     *                  the event
     * @return  the new builder
     */
    public static <ENTITY, T extends Comparable<T>> Builder<ENTITY, T> builder(
        Manager<ENTITY> manager, 
        ComparableField<ENTITY, ?, T> idField) {
        
        return new Builder<>(manager, idField);
    }
    
    /**
     * Builder class for creating new {@link Reactor} instances.
     * 
     * @param <ENTITY>  the entity type to react on
     * @param <T>       the primary key of the entity table
     */
    public final static class Builder<ENTITY, T extends Comparable<T>> {
        
        private final Manager<ENTITY> manager;
        private final ComparableField<ENTITY, ?, T> idField;
        private final Set<Consumer<List<ENTITY>>> listeners;
        private long interval; 
        private long limit;

        /**
         * Initiates the builder with default values.
         * 
         * @param manager  the manager to use for database polling
         * @param idField  the field that identifier which entity is refered to 
         *                 in the event
         */
        private Builder(
                Manager<ENTITY> manager, 
                ComparableField<ENTITY, ?, T> idField) {
            
            this.manager   = requireNonNull(manager);
            this.idField   = requireNonNull(idField);
            this.listeners = newSetFromMap(new ConcurrentHashMap<>());
            this.interval  = DEFAULT_INTERVAL;
            this.limit     = DEFAULT_LIMIT;
        }
        
        /**
         * Adds a listener to the reactor being built. The listener will be
         * notified each time new rows are loaded.
         * 
         * @param listener  the new listener
         * @return          a reference to this builder
         */
        public Builder<ENTITY, T> withListener(Consumer<List<ENTITY>> listener) {
            listeners.add(listener);
            return this;
        }
        
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
        public Builder<ENTITY, T> withInterval(long millis) {
            this.interval = millis;
            return this;
        }
        
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
        public Builder<ENTITY, T> withLimit(long count) {
            this.limit = count;
            return this;
        }
        
        /**
         * Builds and starts this reactor. When this method is called, the 
         * reactor will start polling the database at the specified interval.
         * The returned instance could be ignored, but it might be good to hold
         * on toit since that is the only way to stop the reactor once started.
         * 
         * @return  the running reactor
         */
        public Reactor<ENTITY, T> build() {
            
            final AtomicBoolean working = new AtomicBoolean(false);
            final AtomicReference<T> last = new AtomicReference<>();
            final String managerName = manager.getTable().getName();
            final String fieldName = idField.getIdentifier().columnName();
            
            final Timer timer = new Timer();
            final TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (working.compareAndSet(false, true)) {
                        try {
                            while (true) {
                                final List<ENTITY> added = unmodifiableList(
                                    manager.stream()
                                        .filter(idField.greaterThan(last.get()))
                                        .limit(limit)
                                        .sorted(idField.comparator())
                                        .collect(toList())
                                );

                                if (added.isEmpty()) {
                                    break;
                                } else {
                                    final ENTITY lastEntity = added.get(added.size() - 1);
                                    last.set(idField.get(lastEntity));

                                    listeners.forEach(
                                        listener -> listener.accept(added)
                                    );

                                    LOGGER.debug(String.format(
                                        "%s: Downloaded %d row(s) from %s. Latest %s: %d.", 
                                        System.identityHashCode(last),
                                        added.size(),
                                        managerName,
                                        fieldName,
                                        Long.parseLong("" + last.get())
                                    ));
                                }
                            }
                        } finally {
                            working.set(false);
                        }
                    }
                }
            };
            
            timer.scheduleAtFixedRate(task, 0, interval);
            return new Reactor<>(timer);
        }
    }
}
