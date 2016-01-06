package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.Column;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class TableImpl extends AbstractChildDocument<Schema> implements Table {

    public TableImpl(Schema parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public Column newColumn(Map<String, Object> data) {
        return new ColumnImpl(this, data);
    }

    @Override
    public Index newIndex(Map<String, Object> data) {
        return new IndexImpl(this, data);
    }

    @Override
    public ForeignKey newForeignKey(Map<String, Object> data) {
        return new ForeignKeyImpl(this, data);
    }

    @Override
    public PrimaryKeyColumn newPrimaryKeyColumn(Map<String, Object> data) {
        return new PrimaryKeyColumnImpl(this, data);
    }
}