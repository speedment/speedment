package com.speedment.config.db;

import com.speedment.config.Document;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasName;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface Project extends Document, HasEnabled, HasName {
    
    final String DBMSES = "dbmses";
    
    default Stream<Dbms> dbms() {
        return children(DBMSES, this::newDbms);
    }
    
    Dbms newDbms(Map<String, Object> data);
}