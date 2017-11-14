package com.speedment.runtime.bulk.internal.operation;

import com.speedment.runtime.bulk.RemoveOperation;
import com.speedment.runtime.core.manager.Manager;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public final class RemoveOperationImpl<ENTITY> extends AbstractOperation<ENTITY> implements RemoveOperation<ENTITY> {

    private final List<Predicate<? super ENTITY>> predicates;

    public RemoveOperationImpl(
        final Manager<ENTITY> manager,
        final List<Predicate<? super ENTITY>> predicates
    ) {
        super(Type.REMOVE, manager);
        this.predicates = new ArrayList<>(requireNonNull(predicates));
    }

    @Override
    public Stream<Predicate<? super ENTITY>> predicates() {
        return predicates.stream();
    }

}
