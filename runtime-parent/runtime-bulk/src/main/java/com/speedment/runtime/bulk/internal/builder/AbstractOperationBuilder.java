package com.speedment.runtime.bulk.internal.builder;

import com.speedment.runtime.bulk.BulkOperation;
import com.speedment.runtime.bulk.BulkOperation.Builder;
import com.speedment.runtime.bulk.Operation;
import com.speedment.runtime.bulk.internal.BulkOperationBuilder;
import com.speedment.runtime.config.identifier.HasTableIdentifier;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public abstract class AbstractOperationBuilder<ENTITY> implements Builder {

    private final BulkOperationBuilder parent;
    private final HasTableIdentifier<ENTITY> identifier;

    protected AbstractOperationBuilder(HasTableIdentifier<ENTITY> identifier, BulkOperationBuilder parent) {
        this.identifier = requireNonNull(identifier);
        this.parent = requireNonNull(parent);
    }

    HasTableIdentifier<ENTITY> identifier() {
        return identifier;
    }

    BulkOperationBuilder parent() {
        return parent;
    }

    @Override
    public <NEXT_ENTITY> Persist<NEXT_ENTITY> persist(HasTableIdentifier<NEXT_ENTITY> manager) {
        requireNonNull(manager);
        parent().add(buildCurrent());
        return new PersistOperationBuilderImpl<>(manager, parent());
    }

    @Override
    public <NEXT_ENTITY> Update<NEXT_ENTITY> update(HasTableIdentifier<NEXT_ENTITY> manager) {
        requireNonNull(manager);
        parent().add(buildCurrent());
        return new UpdateOperationBuilderImpl<>(manager, parent());
    }

    @Override
    public <NEXT_ENTITY> Remove<NEXT_ENTITY> remove(HasTableIdentifier<NEXT_ENTITY> manager) {
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
