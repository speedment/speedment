package com.speedment.runtime.bulk.internal.builder;

import com.speedment.runtime.bulk.BulkOperation.Builder;
import com.speedment.runtime.bulk.Operation;
import com.speedment.runtime.bulk.internal.BulkOperationBuilder;
import com.speedment.runtime.bulk.internal.operation.AbstractOperation;
import com.speedment.runtime.bulk.internal.operation.RemoveOperationImpl;
import com.speedment.runtime.core.manager.Manager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public final class RemoveOperationBuilderImpl<ENTITY> extends AbstractOperationBuilder<ENTITY> implements Builder.Remove<ENTITY> {

    private final List<Predicate<? super ENTITY>> filters;

    public RemoveOperationBuilderImpl(Manager<ENTITY> manager, BulkOperationBuilder parent) {
        super(manager, parent);
        this.filters = new ArrayList<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Remove<ENTITY> where(Predicate<? super ENTITY> filter) {
        filters.add((Predicate<Object>) filter);
        return this;
    }

    @Override
    Operation<ENTITY> buildCurrent() {
        return new RemoveOperationImpl<>(manager(), filters);
    }

}
