package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.*;
import static com.speedment.config.db.Column.*;
import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.internal.core.config.db.mutator.trait.HasAliasMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasOrdinalPositionMutator;

/**
 *
 * @author Per Minborg
 */
public final class ColumnMutator extends DocumentMutatorImpl implements DocumentMutator, HasEnabledMutator, HasNameMutator, HasAliasMutator, HasOrdinalPositionMutator {

    ColumnMutator(Column column) {
        super(column);
    }

    public void setNullable(Boolean nullable) {
        put(NULLABLE, nullable);
    }

    public void setAutoIncrement(Boolean autoIncrement) {
        put(AUTO_INCREMENT, autoIncrement);
    }

    public void setTypeMapper(TypeMapper typeMapper) {
        put(TYPE_MAPPER, typeMapper.getClass().getName());
    }

    public void setDatabaseType(Class<?> databaseType) {
        put(DATABASE_TYPE, databaseType.getName());
    }

}
