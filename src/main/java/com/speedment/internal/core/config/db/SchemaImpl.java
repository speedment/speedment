package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.Document;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class SchemaImpl extends AbstractChildDocument<Dbms> implements Schema {

    public SchemaImpl(Dbms parent, Map data) {
        super(parent, data);
    }

    @Override
    public Table newTable(Map<String, Object> data) {
        return new TableImpl(this, data);
    }
}