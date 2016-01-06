package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.Index;
import com.speedment.config.db.IndexColumn;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class IndexColumnImpl extends AbstractChildDocument<Index> implements IndexColumn {
    
    public IndexColumnImpl(Index parent, Map<String, Object> data) {
        super(parent, data);
    }
}