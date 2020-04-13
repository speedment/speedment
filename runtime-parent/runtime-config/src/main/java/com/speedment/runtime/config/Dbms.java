/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
import com.speedment.runtime.config.internal.DbmsImpl;
import com.speedment.runtime.config.mutator.DbmsMutator;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.trait.*;
import com.speedment.runtime.config.util.DocumentUtil;

import java.nio.file.Paths;
import java.util.Map;
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
        HasDeepCopy,
        HasId,        
        HasName,
        HasChildren,
        HasAlias,
        HasMainInterface,
        HasMutator<DbmsMutator<? extends Dbms>> {

    /**
     * Returns the type name of the {@code DbmsType} of this {@code Dbms}. This
     * will be the fully qualified class name of the {@code DbmsType} 
     * implementing class.
     * 
     * @return  the type name
     */
    default String getTypeName() {
        return getAsString(DbmsUtil.TYPE_NAME).orElseThrow(() -> new SpeedmentConfigException(
            "Every " + Dbms.class.getSimpleName() + 
            " document is required to have the '" + 
            DbmsUtil.TYPE_NAME + "' attribute."
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
        return getAsString(DbmsUtil.IP_ADDRESS);
    }
    
    /**
     * Returns the port number of the database on the database host. If no port
     * is specified, {@code empty} is returned.
     *
     * @return the port of the database or {@code empty}
     */
    default OptionalInt getPort() {
        return getAsInt(DbmsUtil.PORT);
    }

    /**
     * Returns the local path to the file where the data of this Dbms is stored.
     * The file name should be parsable using {@link Paths#get}. This property
     * is optional and not even supported in most database systems.
     *
     * @return the local path to the data file
     */
    default Optional<String> getLocalPath() {
        return getAsString(DbmsUtil.LOCAL_PATH);
    }
    
    /**
     * Returns the explicit connection URL to use for this {@code Dbms} if the
     * user has specified one, or an empty {@code Optional} if one should be
     * generated automatically by the {@code DbmsType}.
     * 
     * @return  the explicit connection URL to use for this {@code Dbms}
     */
    default Optional<String> getConnectionUrl() {
        return getAsString(DbmsUtil.CONNECTION_URL);
    }
    
    /**
     * Returns the database username to use when connecting to the dbms. If no
     * username is specified, {@code empty} is returned.
     *
     * @return the database username or {@code empty}
     */
    default Optional<String> getUsername() {
        return getAsString(DbmsUtil.USERNAME);
    }

    /**
     * Returns the server name to use when connecting to the {@code Dbms}. If no
     * server name is specified, {@code empty} is returned.
     * <p>
     * Some database implementations, such as Informix, require the server name as a
     * part of the connection url.
     *
     * @return the server name or {@code empty}
     */
    default Optional<String> getServerName() {
        return getAsString(DbmsUtil.SERVER_NAME);
    }
    
    /**
     * Creates a stream of schemas located in this document.
     * 
     * @return  schemas
     */
    Stream<Schema> schemas();

    @Override
    default Class<Dbms> mainInterface() {
        return Dbms.class;
    }  
    
    @Override
    default DbmsMutator<? extends Dbms> mutator() {
        return DocumentMutator.of(this);
    }

    @Override
    default Dbms deepCopy() {
        return DocumentUtil.deepCopy(this, DbmsImpl::new);
    }

    /**
     * Creates and returns a new standard implementation of a {@link Dbms}
     * with the given {@code parent} and {@code data}.
     *
     * @param parent of the config document (nullable)
     * @param data of the config document
     * @return a new {@link Dbms} with the given parameters
     *
     * @throws NullPointerException if the provided {@code data} is {@code null}
     */
    static Dbms create(Project parent, Map<String, Object> data) {
        return new DbmsImpl(parent, data);
    }
}
