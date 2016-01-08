package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.db.mutator.ForeignKeyColumnMutator;
import com.speedment.internal.core.config.db.mutator.IndexMutator;
import static com.speedment.internal.util.document.DocumentUtil.newDocument;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Index extends 
        Document,
        HasParent<Table>,
        HasEnabled,
        HasName,
        HasMainInterface,
        HasMutator<IndexMutator> {

    final String UNIQUE = "unique",
            INDEX_COLUMNS = "indexColumns";

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

    default Stream<? extends IndexColumn> indexColumns() {
        return children(INDEX_COLUMNS, indexColumnConstructor());
    }

    default IndexColumn addNewIndexColumn() {
        return indexColumnConstructor().apply(this, newDocument(this, INDEX_COLUMNS));
    }

    BiFunction<Index, Map<String, Object>, ? extends IndexColumn> indexColumnConstructor();

    @Override
    default Class<Index> mainInterface() {
        return Index.class;
    }

    @Override
    default IndexMutator mutator() {
        return DocumentMutator.of(this);
    }

}
