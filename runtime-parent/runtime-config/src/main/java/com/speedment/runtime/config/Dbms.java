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
package com.speedment.runtime.config;

import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.config.mutator.DbmsMutator;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.runtime.config.trait.HasChildren;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasMutator;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasParent;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

/**
 * A typed {@link Document} that represents a dbms instance in the database. A
 * {@code Dbms} is located inside a {@link Project} and can have multiple 
 * {@link Schema Schemas} as children.
 * 
 * @author  Emil Forslund
 * @since   2.0.0
 */

public interface Dbms extends 
        Document,
        HasParent<Project>,
        HasEnabled,
        HasId,        
        HasName,
        HasChildren,
        HasAlias,
        HasMainInterface,
        HasMutator<DbmsMutator<? extends Dbms>> {
    
    String
        TYPE_NAME      = "typeName",
        IP_ADDRESS     = "ipAddress",
        PORT           = "port",
        CONNECTION_URL = "connectionUrl",
        USERNAME       = "username",
        SCHEMAS        = "schemas";
        
    /**
     * Returns the type name of the {@code DbmsType} of this {@code Dbms}. This
     * will be the fully qualified class name of the {@code DbmsType} 
     * implementing class.
     * 
     * @return  the type name
     */
    default String getTypeName() {
        return getAsString(TYPE_NAME).orElseThrow(() -> new SpeedmentConfigException(
            "Every " + Dbms.class.getSimpleName() + 
            " document is required to have the '" + 
            TYPE_NAME + "' attribute."
        ));
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
     * Returns the explicit connection URL to use for this {@code Dbms} if the
     * user has specified one, or an empty {@code Optional} if one should be
     * generated automatically by the {@code DbmsType}.
     * 
     * @return  the explicit connection URL to use for this {@code Dbms}
     */
    default Optional<String> getConnectionUrl() {
        return getAsString(CONNECTION_URL);
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
    
    /**
     * Creates a stream of schemas located in this document.
     * 
     * @return  schemas
     */
    Stream<? extends Schema> schemas();

    @Override
    default Class<Dbms> mainInterface() {
        return Dbms.class;
    }  
    
    @Override
    default DbmsMutator<? extends Dbms> mutator() {
        return DocumentMutator.of(this);
    }
}