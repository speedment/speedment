package com.speedment.internal.core.config.db;

import com.speedment.config.Document;
import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.ForeignKeyColumn;
import com.speedment.config.db.Table;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Emil Forslund
 */
public final class ForeignKeyImpl extends AbstractChildDocument<Table> implements ForeignKey {

    public ForeignKeyImpl(Table parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public BiFunction<ForeignKey, Map<String, Object>, ForeignKeyColumn> foreignKeyColumnConstructor() {
        return ForeignKeyColumnImpl::new;
    }
}