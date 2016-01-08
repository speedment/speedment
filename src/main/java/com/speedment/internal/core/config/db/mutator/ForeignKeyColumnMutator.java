package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.ForeignKeyColumn;
import static com.speedment.config.db.ForeignKeyColumn.*;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasOrdinalPositionMutator;

/**
 *
 * @author Per Minborg
 */
public final class ForeignKeyColumnMutator extends DocumentMutatorImpl implements DocumentMutator, HasNameMutator, HasOrdinalPositionMutator {

    ForeignKeyColumnMutator(ForeignKeyColumn foreignKeyColumn) {
        super(foreignKeyColumn);
    }
    
    public void setForeignTableName(String foreignTableName) {
        put(FOREIGN_TABLE_NAME, foreignTableName);
    }
    
    public void setForeignColumnName(String foreignColumnName) {
        put(FOREIGN_COLUMN_NAME, foreignColumnName);
    }
    
}