package com.speedment.runtime.bulk.internal.builder;

import com.speedment.runtime.bulk.BulkOperation;
import com.speedment.runtime.bulk.BulkOperation.Builder;
import com.speedment.runtime.bulk.Operation;
import com.speedment.runtime.bulk.internal.BulkOperationBuilder;
import com.speedment.runtime.core.manager.Manager;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public abstract class AbstractOperationBuilder<ENTITY> implements Builder {

    private final BulkOperationBuilder parent;
    private final Manager<ENTITY> manager;

    protected AbstractOperationBuilder(Manager<ENTITY> manager, BulkOperationBuilder parent) {
        this.manager = requireNonNull(manager);
        this.parent = requireNonNull(parent);
    }

    Manager<ENTITY> manager() {
        return manager;
    }

    BulkOperationBuilder parent() {
        return parent;
    }

    @Override
    public <NEXT_ENTITY> Persist<NEXT_ENTITY> persist(Manager<NEXT_ENTITY> manager) {
        requireNonNull(manager);
        parent().add(buildCurrent());
        return new PersistOperationBuilderImpl<>(manager, parent());
    }

    @Override
    public <NEXT_ENTITY> Update<NEXT_ENTITY> update(Manager<NEXT_ENTITY> manager) {
        requireNonNull(manager);
        parent().add(buildCurrent());
        return new UpdateOperationBuilderImpl<>(manager, parent());
    }

    @Override
    public <NEXT_ENTITY> Remove<NEXT_ENTITY> remove(Manager<NEXT_ENTITY> manager) {
        requireNonNull(manager);
        parent().add(buildCurrent());
        return new RemoveOperationBuilderImpl<>(manager, parent());
    }

    @Override
    public BulkOperation build() {
        parent().add(buildCurrent());
        return parent().build();
    }

    abstract Operation<ENTITY> buildCurrent();

}
