package com.speedment.runtime.bulk.internal;

import com.speedment.runtime.bulk.internal.builder.RemoveOperationBuilderImpl;
import com.speedment.runtime.bulk.BulkOperation;
import com.speedment.runtime.bulk.Operation;
import com.speedment.runtime.bulk.internal.builder.PersistOperationBuilderImpl;
import com.speedment.runtime.bulk.internal.builder.UpdateOperationBuilderImpl;
import com.speedment.runtime.core.manager.Manager;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class BulkOperationBuilder implements BulkOperation.Builder {

    private final List<Operation<?>> operations;

    public BulkOperationBuilder() {
        this.operations = new ArrayList<>();
    }

    @Override
    public <ENTITY> Persist<ENTITY> persist(Manager<ENTITY> manager) {
        return new PersistOperationBuilderImpl<>(requireNonNull(manager), this);
    }

    @Override
    public <ENTITY> Update<ENTITY> update(Manager<ENTITY> manager) {
        return new UpdateOperationBuilderImpl<>(requireNonNull(manager), this);
    }

    @Override
    public <ENTITY> Remove<ENTITY> remove(Manager<ENTITY> manager) {
        return new RemoveOperationBuilderImpl<>(requireNonNull(manager), this);
    }

    @Override
    public BulkOperation build() {
        return new BulkOperationImpl(operations);
    }

    public <ENTITY> void add(Operation<ENTITY> operation) {
        operations.add(operation);
    }

}
