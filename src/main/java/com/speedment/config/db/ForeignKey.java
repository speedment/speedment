package com.speedment.config.db;

import com.speedment.config.Document;
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
public interface ForeignKey extends Document, HasParent<Table>, HasEnabled, HasName {
    
    default Stream<ForeignKeyColumn> foreignKeyColumns() {
        return children("foreignKeyColumn", this::newForeignKeyColumn);
    }
    
    ForeignKeyColumn newForeignKeyColumn(Map<String, Object> data);
}