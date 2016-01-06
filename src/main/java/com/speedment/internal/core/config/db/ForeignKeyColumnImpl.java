package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyColumnImpl extends AbstractChildDocument<ForeignKey> implements ForeignKeyColumn {

    public ForeignKeyColumnImpl(ForeignKey parent, Map<String, Object> data) {
        super(parent, data);
    }
}