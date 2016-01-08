package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.db.mutator.ForeignKeyMutator;
import static com.speedment.internal.util.document.DocumentUtil.newDocument;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface ForeignKey extends
        Document,
        HasParent<Table>,
        HasEnabled,
        HasName,
        HasMainInterface,
        HasMutator<ForeignKeyMutator> {

    final String FOREIGN_KEY_COLUMNS = "foreignKeyColumns";

    default Stream<? extends ForeignKeyColumn> foreignKeyColumns() {
        return children(FOREIGN_KEY_COLUMNS, foreignKeyColumnConstructor());
    }

    default ForeignKeyColumn addNewForeignKeyColumn() {
        return foreignKeyColumnConstructor().apply(this, newDocument(this, FOREIGN_KEY_COLUMNS));
    }

    BiFunction<ForeignKey, Map<String, Object>, ? extends ForeignKeyColumn> foreignKeyColumnConstructor();

    @Override
    default Class<ForeignKey> mainInterface() {
        return ForeignKey.class;
    }

    @Override
    default ForeignKeyMutator mutator() {
        return DocumentMutator.of(this);
    }

}
