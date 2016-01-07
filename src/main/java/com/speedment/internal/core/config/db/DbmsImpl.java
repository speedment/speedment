package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class DbmsImpl extends AbstractChildDocument<Project> implements Dbms {

    public DbmsImpl(Project parent, Map<String, Object> data) {
        super(parent, data);
    }
    
    @Override
    public Schema newSchema(Map<String, Object> data) {
        return new SchemaImpl(this, data);
    }

    @Override
    public Schema newSchema() {
        return newSchema(newEmptyMap(SCHEMAS));
    }
}