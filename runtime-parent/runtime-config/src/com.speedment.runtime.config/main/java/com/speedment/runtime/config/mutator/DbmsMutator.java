/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.config.mutator;


import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.internal.SchemaImpl;
import com.speedment.runtime.config.mutator.trait.HasEnabledMutator;
import com.speedment.runtime.config.mutator.trait.HasNameMutator;

import static com.speedment.runtime.config.Dbms.*;
import com.speedment.runtime.config.mutator.trait.HasIdMutator;
import static com.speedment.runtime.config.util.DocumentUtil.newDocument;

/**
 *
 * @author       Per Minborg
 * @param <DOC>  document type
 */

public class DbmsMutator<DOC extends Dbms> extends DocumentMutatorImpl<DOC> implements 
        HasEnabledMutator<DOC>,
        HasIdMutator<DOC>,    
        HasNameMutator<DOC> {
    
    public DbmsMutator(DOC dbms) {
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
    
    public void setConnectionUrl(String connectionUrl) {
        put(CONNECTION_URL, connectionUrl);
    }
    
    public Schema addNewSchema() {
        return new SchemaImpl(document(), newDocument(document(), SCHEMAS));
    }
}