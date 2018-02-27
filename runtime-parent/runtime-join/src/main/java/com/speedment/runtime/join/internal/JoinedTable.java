package com.speedment.runtime.join.internal;

import com.speedment.runtime.config.identifier.TableIdentifier;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <T> first table type
 */
public final class JoinedTable<T> {

    private final TableIdentifier<T> identifier;

    public JoinedTable(TableIdentifier<T> identifier) {
        this.identifier = requireNonNull(identifier);
    }

}
