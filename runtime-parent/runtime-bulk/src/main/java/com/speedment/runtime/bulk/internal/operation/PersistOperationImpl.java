package com.speedment.runtime.bulk.internal.operation;

import com.speedment.runtime.bulk.PersistOperation;
import com.speedment.runtime.core.manager.Manager;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public final class PersistOperationImpl<ENTITY> extends AbstractOperation<ENTITY> implements PersistOperation<ENTITY> {

    private final List<Supplier<Stream<? extends ENTITY>>> generatorSuppliers;

    public PersistOperationImpl(
        final Manager<ENTITY> manager,
        final List<Supplier<Stream<? extends ENTITY>>> generatorSuppliers
    ) {
        super(Type.PERSIST, manager);
        this.generatorSuppliers = new ArrayList<>(requireNonNull(generatorSuppliers));
    }

    @Override
    public Stream<Supplier<Stream<? extends ENTITY>>> generatorSuppliers() {
        return generatorSuppliers.stream();
    }

}
