package com.speedment.internal.core.config.db;

import com.speedment.internal.core.config.AbstractChildDocument;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 * @author Emil Forslund
 */
public final class DbmsImpl extends AbstractChildDocument<Project> implements Dbms {

    public DbmsImpl(Project parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public BiFunction<Dbms, Map<String, Object>, Schema> schemaConstructor() {
        return SchemaImpl::new;
    }
}