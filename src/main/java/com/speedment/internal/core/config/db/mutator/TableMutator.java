package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.*;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface TableMutator extends Document, HasParent<SchemaMutator>, HasEnabled, HasName, HasAlias, HasMainInterface {
    
    final String
        COLUMNS = "columns",
        INDEXES = "indexes",
        FOREIGN_KEYS = "foreignKeys",
        PRIMARY_KEY_COLUMNS = "primaryKeyColumns";
    
    default Stream<Column> columns() {
        return children(COLUMNS, this::newColumn);
    }
    
    default Stream<IndexMutator> indexes() {
        return children(INDEXES, this::newIndex);
    }
    
    default Stream<ForeignKeyMutator> foreignKeys() {
        return children(FOREIGN_KEYS, this::newForeignKey);
    }
    
    default Stream<PrimaryKeyColumnMutator> primaryKeyColumns() {
        return children(PRIMARY_KEY_COLUMNS, this::newPrimaryKeyColumn);
    }
    
    default Optional<Column> findColumn(String name) {
        return columns().filter(child -> child.getName().equals(name)).findAny();
    }
    
    default Optional<IndexMutator> findIndex(String name) {
        return indexes().filter(child -> child.getName().equals(name)).findAny();
    }
    
    default Optional<ForeignKeyMutator> findForeignKey(String name) {
        return foreignKeys().filter(child -> child.getName().equals(name)).findAny();
    }
    
    default Optional<PrimaryKeyColumnMutator> findPrimaryKeyColumn(String name) {
        return primaryKeyColumns().filter(child -> child.getName().equals(name)).findAny();
    }
    
    Column newColumn(Map<String, Object> data);
    IndexMutator newIndex(Map<String, Object> data);
    ForeignKeyMutator newForeignKey(Map<String, Object> data);
    PrimaryKeyColumnMutator newPrimaryKeyColumn(Map<String, Object> data);
    
     @Override
    default Class<TableMutator> mainInterface() {
        return TableMutator.class;
    }    
    
}