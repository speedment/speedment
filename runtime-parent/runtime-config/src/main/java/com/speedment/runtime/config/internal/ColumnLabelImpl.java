package com.speedment.runtime.config.internal;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.ColumnLabel;

public class ColumnLabelImpl implements ColumnLabel {
    private final String label;

    public ColumnLabelImpl(ColumnIdentifier identifier) {
        label = identifier.getDbmsName() + "." +
            identifier.getSchemaName() + "." +
            identifier.getTableName() + "." +
            identifier.getColumnName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnLabelImpl that = (ColumnLabelImpl) o;

        return label.equals(that.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    @Override
    public String toString() {
        return "ColumnLabelImpl{" +
            "label='" + label + '\'' +
            '}';
    }
}
