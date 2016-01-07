package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface ForeignKey extends Document, HasParent<Table>, HasEnabled, HasName, HasMainInterface {
    
    final String FOREIGN_KEY_COLUMN = "foreignKeyColumn";
    
    default Stream<ForeignKeyColumn> foreignKeyColumns() {
        return children(FOREIGN_KEY_COLUMN, this::newForeignKeyColumn);
    }
    
    ForeignKeyColumn newForeignKeyColumn(Map<String, Object> data);
    
     @Override
    default Class<ForeignKey> mainInterface() {
        return ForeignKey.class;
    }    
    
}