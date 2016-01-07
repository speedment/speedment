package com.speedment.internal.core.config.db.mutator;

import com.speedment.config.db.Dbms;
import static com.speedment.config.db.Dbms.*;
import com.speedment.internal.core.config.db.mutator.impl.DbmsMutatorImpl;
import com.speedment.internal.core.config.db.mutator.trait.HasEnabledMutator;
import com.speedment.internal.core.config.db.mutator.trait.HasNameMutator;

/**
 *
 * @author Per Minborg
 */
public interface DbmsMutator extends DocumentMutator, HasEnabledMutator, HasNameMutator {
    
    default void setTypeName(String typeName) {
        put(TYPE_NAME, typeName);
    }
    
    default void setIpAddress(String ipAddress) {
        put(IP_ADDRESS, ipAddress);
    }
    
    default void setPort(Integer port) {
        put(PORT, port);
    }
    
    default void setUsername(String username) {
        put(USERNAME, username);
    }
    
    static DbmsMutator of(Dbms dbms) {
        return new DbmsMutatorImpl(dbms);
    }
    
}