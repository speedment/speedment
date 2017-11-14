package com.speedment.runtime.bulk.internal.builder;

import com.speedment.runtime.bulk.BulkOperation.Builder;
import com.speedment.runtime.bulk.Operation;
import com.speedment.runtime.bulk.internal.BulkOperationBuilder;
import com.speedment.runtime.bulk.internal.operation.UpdateOperationImpl;
import com.speedment.runtime.core.manager.Manager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public final class UpdateOperationBuilderImpl<ENTITY> extends AbstractOperationBuilder<ENTITY> implements Builder.Update<ENTITY> {

    private final List<Predicate<? super ENTITY>> filters;
    private final List<Function<? super ENTITY, ? extends ENTITY>> mappers;
    private final List<Consumer<? super ENTITY>> consumers;

    public UpdateOperationBuilderImpl(Manager<ENTITY> manager, BulkOperationBuilder parent) {
        super(manager, parent);
        this.filters = new ArrayList<>();
        this.mappers = new ArrayList<>();
        this.consumers = new ArrayList<>();
    }

    @Override
    public Update<ENTITY> where(Predicate<? super ENTITY> filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public Update<ENTITY> set(Consumer<? super ENTITY> consumer) {
        consumers.add((Consumer<? super ENTITY>) consumer);
        return this;
    }

    @Override
    public Update<ENTITY> map(Function<? super ENTITY, ? extends ENTITY> mapper) {
        mappers.add(mapper);
        return this;
    }

    @Override
    Operation<ENTITY> buildCurrent() {
        return new UpdateOperationImpl<>(manager(), filters, mappers, consumers);
    }

}
