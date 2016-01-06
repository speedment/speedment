package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Index extends Document, HasParent<Table>, HasEnabled, HasName {

    final String 
        UNIQUE       = "unique",
        INDEX_COLUMN = "indexColumns";
    
    /**
     * Returns whether or not this index is an {@code UNIQUE} index.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @return {@code true} if this index is {@code UNIQUE}
     */    
    default boolean isUnique() {
        return getAsBoolean(UNIQUE).orElse(false);
    }
    
    default Stream<IndexColumn> indexColumns() {
        return children(INDEX_COLUMN, this::newIndexColumn);
    }
    
    IndexColumn newIndexColumn(Map<String, Object> data);
}