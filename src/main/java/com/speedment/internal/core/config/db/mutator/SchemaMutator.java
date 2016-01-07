package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.Document;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public interface SchemaMutator extends Document, HasParent<DbmsMutator>, HasEnabled, HasName, HasAlias, HasMainInterface {
    
    final String
        DEFAULT_SCHEMA = "defaultSchema",
        TABLES         = "tables";
    
    /**
     * Returns {@code true} if this schema is the default one, else
     * {@code false}.
     *
     * @return {@code true} if default, else {@code false}
     */
    default boolean isDefaultSchema() {
        return getAsBoolean(DEFAULT_SCHEMA).orElse(false);
    }
    
    default Stream<TableMutator> tables() {
        return children(TABLES, this::newTable);
    }
    
    TableMutator newTable(Map<String, Object> data);
    
     @Override
    default Class<SchemaMutator> mainInterface() {
        return SchemaMutator.class;
    }    
    
}