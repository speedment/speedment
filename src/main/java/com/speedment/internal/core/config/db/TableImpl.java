package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.Column;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Emil Forslund
 */
public class TableImpl extends AbstractChildDocument<Schema> implements Table {

    public TableImpl(Schema parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public BiFunction<Table, Map<String, Object>, Column> columnConstructor() {
        return ColumnImpl::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, Index> indexConstructor() {
        return IndexImpl::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, ForeignKey> foreignKeyConstructor() {
        return ForeignKeyImpl::new;
    }

    @Override
    public BiFunction<Table, Map<String, Object>, PrimaryKeyColumn> primaryKeyColumnConstructor() {
        return PrimaryKeyColumnImpl::new;
    }
}