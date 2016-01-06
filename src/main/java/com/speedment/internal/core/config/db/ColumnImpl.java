package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.Column;
import com.speedment.config.db.Table;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class ColumnImpl extends AbstractChildDocument<Table> implements Column {
    public ColumnImpl(Table parent, Map<String, Object> data) {
        super(parent, data);
    }
}
