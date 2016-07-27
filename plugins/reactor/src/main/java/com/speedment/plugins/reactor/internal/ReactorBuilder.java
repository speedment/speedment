package com.speedment.plugins.reactor.internal;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.plugins.reactor.Reactor;
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
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import static java.util.stream.Collectors.toList;
import static java.util.Objects.requireNonNull;

/**
 * Builder class for creating new {@link Reactor} instances.
 * 
 * @param <ENTITY>  the entity type to react on
 * @param <T>       the primary key of the entity table
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class ReactorBuilder<ENTITY, T extends Comparable<T>> 
        implements Reactor.Builder<ENTITY, T> {
    
    private final static int
        DEFAULT_INTERVAL = 1000, // in milliseconds.
        DEFAULT_LIMIT    = 100;
    
    private final static Logger LOGGER = 
        LoggerManager.getLogger(Reactor.class);

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
    public ReactorBuilder(
            Manager<ENTITY> manager, 
            ComparableField<ENTITY, ?, T> idField) {

        this.manager   = requireNonNull(manager);
        this.idField   = requireNonNull(idField);
        this.listeners = newSetFromMap(new ConcurrentHashMap<>());
        this.interval  = DEFAULT_INTERVAL;
        this.limit     = DEFAULT_LIMIT;
    }

    @Override
    public ReactorBuilder<ENTITY, T> withListener(Consumer<List<ENTITY>> listener) {
        listeners.add(listener);
        return this;
    }

    @Override
    public ReactorBuilder<ENTITY, T> withInterval(long millis) {
        this.interval = millis;
        return this;
    }

    @Override
    public ReactorBuilder<ENTITY, T> withLimit(long count) {
        this.limit = count;
        return this;
    }

    @Override
    public Reactor build() {

        final AtomicBoolean working = new AtomicBoolean(false);
        final AtomicReference<T> last = new AtomicReference<>();
        final AtomicLong total = new AtomicLong();

        final String managerName = manager.getTable().getName();
        final String fieldName = idField.identifier().columnName();

        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                boolean first = true;

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
                                if (!first) {
                                    LOGGER.debug(String.format(
                                        "%s: View is up to date. A total of %d " +
                                            "rows have been loaded.",
                                        System.identityHashCode(last),
                                        total.get()
                                    ));
                                }

                                break;
                            } else {
                                final ENTITY lastEntity = added.get(added.size() - 1);
                                last.set(idField.get(lastEntity));

                                listeners.forEach(
                                    listener -> listener.accept(added)
                                );

                                total.addAndGet(added.size());

                                LOGGER.debug(String.format(
                                    "%s: Downloaded %d row(s) from %s. Latest %s: %d.", 
                                    System.identityHashCode(last),
                                    added.size(),
                                    managerName,
                                    fieldName,
                                    Long.parseLong("" + last.get())
                                ));
                            }

                            first = false;
                        }
                    } finally {
                        working.set(false);
                    }
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, interval);
        return new ReactorImpl(timer);
    }
}
