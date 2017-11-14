package com.speedment.runtime.bulk.internal.operation;

import com.speedment.runtime.bulk.Operation.Type;
import com.speedment.runtime.core.manager.Manager;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public abstract class AbstractOperation<ENTITY> {

    private final Type type;
    private final Manager<ENTITY> manager;

    protected AbstractOperation(Type type, Manager<ENTITY> manager) {
        this.type = requireNonNull(type);
        this.manager = requireNonNull(manager);
    }

    public Manager<ENTITY> manager() {
        return manager;
    }

    public Type type() {
        return type;
    }

}
