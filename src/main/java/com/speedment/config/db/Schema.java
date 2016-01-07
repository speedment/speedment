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
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Schema extends Document, HasParent<Dbms>, HasEnabled, HasName, HasAlias, HasMainInterface {
    
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
    
    default Stream<Table> tables() {
        return children(TABLES, tableConstructor());
    }
    
    default Table newTable() {
        return tableConstructor().apply(this, newDocument(this, TABLES));
    }
    
    BiFunction<Schema, Map<String, Object>, Table> tableConstructor();

    @Override
    default Class<Schema> mainInterface() {
        return Schema.class;
    }
}