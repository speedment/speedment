package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import static com.speedment.internal.util.document.DocumentUtil.newDocument;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Table extends Document, HasParent<Schema>, HasEnabled, HasName, HasAlias, HasMainInterface {
    
    final String
        COLUMNS = "columns",
        INDEXES = "indexes",
        FOREIGN_KEYS = "foreignKeys",
        PRIMARY_KEY_COLUMNS = "primaryKeyColumns";
    
    default Stream<? extends Column> columns() {
        return children(COLUMNS, columnConstructor());
    }
    
    default Stream<? extends Index> indexes() {
        return children(INDEXES, indexConstructor());
    }
    
    default Stream<? extends ForeignKey> foreignKeys() {
        return children(FOREIGN_KEYS, foreignKeyConstructor());
    }
    
    default Stream<? extends PrimaryKeyColumn> primaryKeyColumns() {
        return children(PRIMARY_KEY_COLUMNS, primaryKeyColumnConstructor());
    }
    
    default Optional<? extends Column> findColumn(String name) {
        return columns().filter(child -> child.getName().equals(name)).findAny();
    }
    
    default Optional<? extends Index> findIndex(String name) {
        return indexes().filter(child -> child.getName().equals(name)).findAny();
    }
    
    default Optional<? extends ForeignKey> findForeignKey(String name) {
        return foreignKeys().filter(child -> child.getName().equals(name)).findAny();
    }
    
    default Optional<? extends PrimaryKeyColumn> findPrimaryKeyColumn(String name) {
        return primaryKeyColumns().filter(child -> child.getName().equals(name)).findAny();
    }
    
    default Column addNewColumn() {
        return columnConstructor().apply(this, newDocument(this, COLUMNS));
    }
    
    default Index addNewIndex() {
        return indexConstructor().apply(this, newDocument(this, INDEXES));
    }
    
    default ForeignKey addNewForeignKey() {
        return foreignKeyConstructor().apply(this, newDocument(this, FOREIGN_KEYS));
    }
    
    default PrimaryKeyColumn addNewPrimaryKeyColumn() {
        return primaryKeyColumnConstructor().apply(this, newDocument(this, PRIMARY_KEY_COLUMNS));
    }
    
    BiFunction<Table, Map<String, Object>, ? extends Column> columnConstructor();
    
    BiFunction<Table, Map<String, Object>, ? extends Index> indexConstructor();
    
    BiFunction<Table, Map<String, Object>, ? extends ForeignKey> foreignKeyConstructor();
    
    BiFunction<Table, Map<String, Object>, ? extends PrimaryKeyColumn> primaryKeyColumnConstructor();

     @Override
    default Class<Table> mainInterface() {
        return Table.class;
    }
}