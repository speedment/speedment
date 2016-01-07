package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface ForeignKey extends Document, HasParent<Table>, HasEnabled, HasName {
    
    final String FOREIGN_KEY_COLUMNS = "foreignKeyColumns";
    
    default Stream<ForeignKeyColumn> foreignKeyColumns() {
        return children(FOREIGN_KEY_COLUMNS, foreignKeyColumnConstructor());
    }
    
    default ForeignKeyColumn newForeignKeyColumn() {
        return foreignKeyColumnConstructor().apply(this, newDocument(this, FOREIGN_KEY_COLUMNS));
    }
    
    BiFunction<ForeignKey, Map<String, Object>, ForeignKeyColumn> foreignKeyColumnConstructor();
}