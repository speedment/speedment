package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import com.speedment.config.db.Table;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class IndexImpl extends AbstractChildDocument<Table> implements Index {

    public IndexImpl(Table parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public IndexColumn newIndexColumn(Map<String, Object> data) {
        return new IndexColumnImpl(this, data);
    }
}