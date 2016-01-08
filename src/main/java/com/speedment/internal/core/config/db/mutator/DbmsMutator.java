package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.Dbms;
import static com.speedment.config.db.Dbms.*;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;

/**
 *
 * @author Per Minborg
 */
public final class DbmsMutator extends DocumentMutatorImpl implements DocumentMutator, HasEnabledMutator, HasNameMutator {
    
    DbmsMutator(Dbms dbms) {
        super(dbms);
    }
    
    public void setTypeName(String typeName) {
        put(TYPE_NAME, typeName);
    }
    
    public void setIpAddress(String ipAddress) {
        put(IP_ADDRESS, ipAddress);
    }
    
    public void setPort(Integer port) {
        put(PORT, port);
    }
    
    public void setUsername(String username) {
        put(USERNAME, username);
    }
 
}