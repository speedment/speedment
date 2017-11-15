package com.speedment.runtime.bulk.internal.operation;

import com.speedment.runtime.bulk.Operation.Type;
import com.speedment.runtime.config.identifier.HasTableIdentifier;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public abstract class AbstractOperation<ENTITY> {

    private final Type type;
    private final HasTableIdentifier<ENTITY> identifier;

    protected AbstractOperation(Type type, HasTableIdentifier<ENTITY> identifier) {
        this.type = requireNonNull(type);
        this.identifier = requireNonNull(identifier);
    }

    public HasTableIdentifier<ENTITY> identifier() {
        return identifier;
    }

    public Type type() {
        return type;
    }

}
