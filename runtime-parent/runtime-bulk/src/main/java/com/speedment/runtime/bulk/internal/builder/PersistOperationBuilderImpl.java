package com.speedment.runtime.bulk.internal.builder;

import com.speedment.runtime.bulk.BulkOperation.Builder;
import com.speedment.runtime.bulk.Operation;
import com.speedment.runtime.bulk.internal.BulkOperationBuilder;
import com.speedment.runtime.bulk.internal.operation.PersistOperationImpl;
import com.speedment.runtime.config.identifier.HasTableIdentifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public final class PersistOperationBuilderImpl<ENTITY> extends AbstractOperationBuilder<ENTITY> implements Builder.Persist<ENTITY> {

    private final List<Supplier<Stream<? extends ENTITY>>> generatorSuppliers;

    public PersistOperationBuilderImpl(HasTableIdentifier<ENTITY> identifier, BulkOperationBuilder parent) {
        super(identifier, parent);
        this.generatorSuppliers = new ArrayList<>();
    }

    @Override
    public Builder values(Supplier<Stream<? extends ENTITY>> generatorSupplier) {
        generatorSuppliers.add(generatorSupplier);
        return this;
    }

    @Override
    Operation<ENTITY> buildCurrent() {
        return new PersistOperationImpl<>(identifier(), generatorSuppliers);
    }

}
