package com.speedment.internal.core.db.crud;

import com.speedment.config.Table;
import com.speedment.db.crud.Join;
import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link Join} interface.
 *
 * @author Emil
 */
public final class JoinImpl implements Join {
    
    private final String columnName;
    private final String otherColumnName;
    private final Table otherTable;

    /**
     * Instantiates the Join.
     * 
     * @param columnName       the name of the local column
     * @param otherColumnName  the name of the foreign column
     * @param otherTable       the name of the foreign table
     */
    private JoinImpl(String columnName, String otherColumnName, Table otherTable) {
        this.columnName      = requireNonNull(columnName);
        this.otherColumnName = requireNonNull(otherColumnName);
        this.otherTable      = requireNonNull(otherTable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColumnName() {
        return columnName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOtherColumnName() {
        return otherColumnName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Table getOtherTable() {
        return otherTable;
    }
    
    // TODO figure out how the join should be instantiated...
}