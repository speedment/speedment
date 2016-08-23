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
package com.speedment.plugins.reactor.internal.builder;

import com.speedment.internal.common.logger.Logger;
import com.speedment.internal.common.logger.LoggerManager;
import com.speedment.plugins.reactor.Reactor;
import com.speedment.plugins.reactor.internal.ReactorImpl;
import com.speedment.runtime.manager.Manager;
import static java.util.Collections.newSetFromMap;
import static java.util.Collections.unmodifiableList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toList;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   1.1.0
 */
public abstract class AbstractReactorBuilder<ENTITY, T extends Comparable<T>, ID_HOLDER> 
        implements Reactor.Builder<ENTITY, T> {
    
    private final static int
        DEFAULT_INTERVAL = 1000, // in milliseconds.
        DEFAULT_LIMIT    = 100;
    
    private final static Logger LOGGER = 
        LoggerManager.getLogger(Reactor.class);

    private final Manager<ENTITY> manager;
    private final Set<Consumer<List<ENTITY>>> listeners;
    
    private long interval; 
    private long limit;
    
    /**
     * Initiates the builder with default values.
     * 
     * @param manager  the manager to use for database polling
     */
    protected AbstractReactorBuilder(
            Manager<ENTITY> manager) {

        this.manager   = requireNonNull(manager);
        this.listeners = newSetFromMap(new ConcurrentHashMap<>());
        this.interval  = DEFAULT_INTERVAL;
        this.limit     = DEFAULT_LIMIT;
    }
    
    protected abstract String fieldName();
    
    protected abstract Predicate<ENTITY> idPredicate(ID_HOLDER idHolder);
    
    protected abstract Comparator<ENTITY> idComparator();
    
    protected abstract ID_HOLDER idHolder();
    
    protected abstract void bumpLatestId(ID_HOLDER idHolder, ENTITY lastEntity);
    
    protected abstract String idToString(ID_HOLDER idHolder);

    @Override
    public Reactor.Builder<ENTITY, T> withListener(Consumer<List<ENTITY>> listener) {
        listeners.add(listener);
        return this;
    }

    @Override
    public Reactor.Builder<ENTITY, T> withInterval(long millis) {
        this.interval = millis;
        return this;
    }

    @Override
    public Reactor.Builder<ENTITY, T> withLimit(long count) {
        this.limit = count;
        return this;
    }

    @Override
    public Reactor build() {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        final ID_HOLDER idHolder = idHolder();
        final AtomicLong total   = new AtomicLong();
        final String managerName = manager.getTable().getName();
        final String fieldName   = fieldName();
        
        scheduler.scheduleAtFixedRate(() -> {
            boolean first = true;

            while (true) {
                final List<ENTITY> added = unmodifiableList(
                    manager.stream()
                        .filter(idPredicate(idHolder))
                        .limit(limit)
                        .sorted(idComparator())
                        .collect(toList())
                );

                if (added.isEmpty()) {
                    if (!first) {
                        LOGGER.debug(String.format(
                            "%s: View is up to date. A total of %d " +
                                "rows have been loaded.",
                            System.identityHashCode(idHolder),
                            total.get()
                        ));
                    }

                    break;
                } else {
                    final ENTITY lastEntity = added.get(added.size() - 1);
                    bumpLatestId(idHolder, lastEntity);

                    listeners.forEach(listener -> listener.accept(added));

                    total.addAndGet(added.size());

                    LOGGER.debug(String.format(
                        "%s: Downloaded %d row(s) from %s. Latest %s: %s.", 
                        System.identityHashCode(idHolder),
                        added.size(),
                        managerName,
                        fieldName,
                        idToString(idHolder)
                    ));
                }

                first = false;
            }
        }, 0, interval, TimeUnit.MILLISECONDS);
        
        return new ReactorImpl(scheduler);
    }
}
