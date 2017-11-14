package com.speedment.runtime.bulk.internal.operation;

import com.speedment.runtime.bulk.UpdateOperation;
import com.speedment.runtime.core.manager.Manager;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public final class UpdateOperationImpl<ENTITY> extends AbstractOperation<ENTITY> implements UpdateOperation<ENTITY> {

    private final List<Predicate<? super ENTITY>> predicates;
    private final List<Function<? super ENTITY, ? extends ENTITY>> mappers;
    private final List<Consumer<? super ENTITY>> consumers;

    public UpdateOperationImpl(
        final Manager<ENTITY> manager,
        final List<Predicate<? super ENTITY>> predicates,
        final List<Function<? super ENTITY, ? extends ENTITY>> mappers,
        final List<Consumer<? super ENTITY>> consumers
    ) {
        super(Type.UPDATE, manager);
        this.predicates = new ArrayList<>(requireNonNull(predicates));
        this.mappers = new ArrayList<>(requireNonNull(mappers));
        this.consumers = new ArrayList<>(requireNonNull(consumers));
    }

    @Override
    public Stream<Predicate<? super ENTITY>> predicates() {
        return predicates.stream();
    }

    @Override
    public Stream<Consumer<? super ENTITY>> consumers() {
        return consumers.stream();
    }

    @Override
    public Stream<Function<? super ENTITY, ? extends ENTITY>> mappers() {
        return mappers.stream();
    }

}
