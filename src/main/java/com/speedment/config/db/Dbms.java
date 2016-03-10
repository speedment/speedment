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

import com.speedment.Speedment;
import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasChildren;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.exception.SpeedmentException;
import com.speedment.config.db.mutator.DbmsMutator;
import com.speedment.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.dbms.StandardDbmsType;
import com.speedment.internal.util.document.DocumentDbUtil;
import static com.speedment.internal.util.document.DocumentDbUtil.dbmsTypeOf;
import java.util.Optional;
import java.util.OptionalInt;
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
        TYPE_NAME      = "typeName",
        IP_ADDRESS     = "ipAddress",
        PORT           = "port",
        CONNECTION_URL = "connectionUrl",
        USERNAME       = "username",
        SCHEMAS        = "schemas";
        
    /**
     * Returns the type name of the {@link DbmsType} of this {@code Dbms}. This
     * will be the fully qualified class name of the {@link DbmsType} 
     * implementing class.
     * 
     * @return  the type name
     */
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
     * Returns the default port for the current database type.
     * <p>
     * Warning, this method does not take a custom port set in the 
     * {@link #PORT} property into consideration. For the actual
     * port to use, call {@link #getPort()}.
     * 
     * @param speedment            the {@link Speedment} instance
     * @return                     the default port
     * @throws SpeedmentException  if the {@link DbmsType} couldn't be found
     */
    default int defaultPort(Speedment speedment) throws SpeedmentException {
        return dbmsTypeOf(speedment, this)
            .getDefaultPort();
    }
    
    /**
     * Returns the explicit connection URL to use for this {@code Dbms} if the
     * user has specified one, or an empty {@code Optional} if one should be
     * generated automatically by the {@link DbmsType}.
     * 
     * @return  the explicit connection URL to use for this {@code Dbms}
     */
    default Optional<String> getConnectionUrl() {
        return getAsString(CONNECTION_URL);
    }
    
    /**
     * Creates a default connection URL for this {@code Dbms} by looking up the
     * {@link DbmsType} and using its 
     * {@link DbmsType#getConnectionUrlGenerator()} to produce a default value.
     * <p>
     * Warning, this method does not take a custom connection URL set in the 
     * {@link #CONNECTION_URL} property into consideration. For the actual
     * connection URL to use, call 
     * {@link DocumentDbUtil#findConnectionUrl(Speedment, Dbms)}.
     * 
     * @param speedment  the {@link Speedment} instance
     * @return  the default connection URL to use if an explicit one is not set
     * @throws SpeedmentException  if the {@link DbmsType} couldn't be found
     */
    default String defaultConnectionUrl(Speedment speedment) throws SpeedmentException {
        return dbmsTypeOf(speedment, this)
            .getConnectionUrlGenerator().from(this);
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
    default DbmsMutator mutator() {
        return DocumentMutator.of(this);
    }
}