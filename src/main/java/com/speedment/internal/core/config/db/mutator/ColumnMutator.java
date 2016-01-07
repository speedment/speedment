package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.*;
import static com.speedment.config.db.Column.*;
import com.speedment.internal.core.config.db.mutator.impl.ColumnMutatorImpl;
import com.speedment.internal.core.config.db.mutator.trait.HasAliasMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasOrdinalPositionMutator;

/**
 *
 * @author Per Minborg
 */
public interface ColumnMutator extends DocumentMutator, HasEnabledMutator, HasNameMutator, HasAliasMutator, HasOrdinalPositionMutator {

    default void setNullable(Boolean nullable) {
        put(NULLABLE, nullable);
    }

    default void setAutoIncrement(Boolean autoIncrement) {
        put(AUTO_INCREMENT, autoIncrement);
    }

    default void setTypeMapper(String typeMapper) {
        put(TYPE_MAPPER, typeMapper);
    }

    default void setDatabaseType(String databaseType) {
        put(DATABASE_TYPE, databaseType);
    }

    static ColumnMutator of(Column column) {
        return new ColumnMutatorImpl(column);
    }

}
