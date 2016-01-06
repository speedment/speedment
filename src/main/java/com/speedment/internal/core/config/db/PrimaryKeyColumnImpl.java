package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.PrimaryKeyColumn;
import com.speedment.config.db.Table;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class PrimaryKeyColumnImpl extends AbstractChildDocument<Table> implements PrimaryKeyColumn {

    public PrimaryKeyColumnImpl(Table parent, Map<String, Object> data) {
        super(parent, data);
    }
}