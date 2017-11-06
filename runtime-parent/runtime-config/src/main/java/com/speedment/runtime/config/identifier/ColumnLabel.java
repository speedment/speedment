package com.speedment.runtime.config.identifier;

import com.speedment.runtime.config.internal.ColumnLabelImpl;

public interface ColumnLabel {
    static ColumnLabel of(ColumnIdentifier identifier) {
        return new ColumnLabelImpl(identifier);
    }
}
