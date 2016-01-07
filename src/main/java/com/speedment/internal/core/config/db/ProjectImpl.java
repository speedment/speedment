package com.speedment.internal.core.config.db;

import com.speedment.config.BaseDocument;
import com.speedment.config.Document;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectImpl extends BaseDocument implements Project {

    public ProjectImpl(Map<String, Object> data) {
        super(null, data);
    }

    @Override
    public Optional<? extends Document> getParent() {
        return Optional.empty();
    }
    
    @Override
    public Stream<Document> ancestors() {
        return Stream.empty();
    }

    @Override
    public Dbms newDbms(Map<String, Object> data) {
        return new DbmsImpl(this, data);
    }

    @Override
    public Dbms newDbms() {
        return newDbms(newEmptyMap(DBMSES));
    }
}