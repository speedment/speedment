package com.speedment.internal.core.config.db;

import com.speedment.config.BaseDocument;
import com.speedment.config.Document;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import java.util.Map;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectImpl extends BaseDocument implements Project {

    public ProjectImpl(Document parent, Map<String, Object> data) {
        super(parent, data);
    }

    @Override
    public Dbms newDbms(Map<String, Object> data) {
        return new DbmsImpl(this, data);
    }
}