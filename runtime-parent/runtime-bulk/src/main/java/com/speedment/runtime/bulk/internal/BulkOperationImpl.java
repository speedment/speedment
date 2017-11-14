package com.speedment.runtime.bulk.internal;

import com.speedment.runtime.bulk.BulkOperation;
import com.speedment.runtime.bulk.Operation;
import java.util.ArrayList;
import java.util.Collection;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class BulkOperationImpl implements BulkOperation {

    private final Collection<? extends Operation<?>> operations;

    public BulkOperationImpl(Collection<? extends Operation<?>> operations) {
        this.operations = requireNonNull(new ArrayList<>(operations));
    }

    @Override
    public Stream<? extends Operation<?>> operations() {
        return operations.stream();
    }

}
