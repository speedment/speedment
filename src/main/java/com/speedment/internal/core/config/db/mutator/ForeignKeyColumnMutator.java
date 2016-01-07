package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.ForeignKeyColumn;
import static com.speedment.config.db.ForeignKeyColumn.*;
import com.speedment.internal.core.config.db.mutator.impl.ForeignKeyColumnMutatorImpl;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasOrdinalPositionMutator;

/**
 *
 * @author Per Minborg
 */
public interface ForeignKeyColumnMutator extends DocumentMutator, HasNameMutator, HasOrdinalPositionMutator {
    
    default void setForeignTableName(String foreignTableName) {
        put(FOREIGN_TABLE_NAME, foreignTableName);
    }
    
    default void setForeignColumnName(String foreignColumnName) {
        put(FOREIGN_COLUMN_NAME, foreignColumnName);
    }
    
    static ForeignKeyColumnMutator of(ForeignKeyColumn foreignKeyColumn) {
        return new ForeignKeyColumnMutatorImpl(foreignKeyColumn);
    }
    
}