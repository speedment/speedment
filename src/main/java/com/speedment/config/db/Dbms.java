/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasChildren;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.internal.core.config.db.mutator.DbmsMutator;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.dbms.StandardDbmsType;
import static com.speedment.internal.util.document.DocumentUtil.newDocument;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Dbms extends 
        Document,
        HasParent<Project>,
        HasEnabled,
        HasName,
        HasChildren,
        HasAlias,
        HasMainInterface,
        HasMutator<DbmsMutator> {
    
    final String
        TYPE_NAME  = "typeName",
        IP_ADDRESS = "ipAddress",
        PORT       = "port",
        USERNAME   = "username",
        SCHEMAS    = "schemas";
        
    default String getTypeName() {
        return getAsString(TYPE_NAME).orElse(StandardDbmsType.defaultType().getName());
    }
    
    /**
     * Returns the address of the database host if it is specified. The address
     * could be an ip-address or a hostname. If no address is specified,
     * {@code empty} will be returned.
     *
     * @return the address of the host or {@code empty}
     */
    default Optional<String> getIpAddress() {
        return getAsString(IP_ADDRESS);
    }
    
    /**
     * Returns the port number of the database on the database host. If no port
     * is specified, {@code empty} is returned.
     *
     * @return the port of the database or {@code empty}
     */
    default OptionalInt getPort() {
        return getAsInt(PORT);
    }
    
    /**
     * Returns the database username to use when connecting to the dbms. If no
     * username is specified, {@code empty} is returned.
     *
     * @return the database username or {@code empty}
     */
    default Optional<String> getUsername() {
        return getAsString(USERNAME);
    }
    
    default Stream<? extends Schema> schemas() {
        return children(SCHEMAS, schemaConstructor());
    }

    default Schema addNewSchema() {
        return schemaConstructor().apply(this, newDocument(this, SCHEMAS));
    }
    
    BiFunction<Dbms, Map<String, Object>, ? extends Schema> schemaConstructor();
   
    @Override
    default Class<Dbms> mainInterface() {
        return Dbms.class;
    }  
    
    @Override
    default DbmsMutator mutator() {
        return DocumentMutator.of(this);
    }
    
}