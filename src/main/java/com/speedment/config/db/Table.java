package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Table extends Document, HasParent<Schema>, HasEnabled, HasName, HasAlias {
    
    final String
        COLUMNS = "columns",
        INDEXES = "indexes",
        FOREIGN_KEYS = "foreignKeys",
        PRIMARY_KEY_COLUMNS = "primaryKeyColumns";
    
    default Stream<Column> columns() {
        return children(COLUMNS, this::newColumn);
    }
    
    default Stream<Index> indexes() {
        return children(INDEXES, this::newIndex);
    }
    
    default Stream<ForeignKey> foreignKeys() {
        return children(FOREIGN_KEYS, this::newForeignKey);
    }
    
    default Stream<PrimaryKeyColumn> primaryKeyColumns() {
        return children(PRIMARY_KEY_COLUMNS, this::newPrimaryKeyColumn);
    }
    
    default Optional<Column> findColumn(String name) {
        return columns().filter(child -> child.getName().equals(name)).findAny();
    }
    
    default Optional<Index> findIndex(String name) {
        return indexes().filter(child -> child.getName().equals(name)).findAny();
    }
    
    default Optional<ForeignKey> findForeignKey(String name) {
        return foreignKeys().filter(child -> child.getName().equals(name)).findAny();
    }
    
    default Optional<PrimaryKeyColumn> findPrimaryKeyColumn(String name) {
        return primaryKeyColumns().filter(child -> child.getName().equals(name)).findAny();
    }
    
    Column newColumn(Map<String, Object> data);
    Index newIndex(Map<String, Object> data);
    ForeignKey newForeignKey(Map<String, Object> data);
    PrimaryKeyColumn newPrimaryKeyColumn(Map<String, Object> data);
}