package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Table;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyImpl extends AbstractChildDocument<Table> implements ForeignKey {

    public ForeignKeyImpl(Table parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public ForeignKeyColumn newForeignKeyColumn(Map<String, Object> data) {
        return new ForeignKeyColumnImpl(this, data);
    }
}