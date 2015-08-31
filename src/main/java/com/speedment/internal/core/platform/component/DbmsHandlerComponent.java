/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.platform.component;

import com.speedment.config.Dbms;
import com.speedment.config.parameters.DbmsType;
import com.speedment.db.DbmsHandler;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class is a pluggable factory that produces
 * {@link DbmsHandler DbmsHandlers} for a given Dbms. The DbmsHandler is
 * obtained via the #get() method and if an existing DbmsHandler can not be
 * found, the #make() method is called to provide a new instance.
 *
 * @author pemi
 * @since 2.0
 */
public interface DbmsHandlerComponent extends Component {
    
    /**
     * Installs a new {@link DbmsType} so that handlers can be created using the
     * {@link #make(com.speedment.api.config.Dbms)} method.
     * <p>
     * The type will be indexed by its name as returned by 
     * {@link DbmsType#getName()}. If multiple {@code DbmsTypes} share name, 
     * only the most recently installed will be saved.
     * 
     * @param dbmsType  the type to install
     */
    void install(DbmsType dbmsType);
    
    /**
     * Returns a stream of all {@link DbmsType DbmsTypes} that has been 
     * installed in this component.
     * 
     * @return  a stream of installed DbmsTypes.
     */
    Stream<DbmsType> supportedDbmsTypes();
    
    /**
     * Searches for the specified {@link DbmsType} by its name as defined by
     * {@link DbmsType#getName()}. If none is found, an {@code empty} is 
     * returned.
     * 
     * @param dbmsTypeName  the name to search for
     * @return              the {@link DbmsType} found or {@code empty}
     */
    Optional<DbmsType> findByName(String dbmsTypeName);

    /**
     * Creates and returns a new DbmsHandler for the given Dbms.
     *
     * @param dbms the dbms to use
     * @return a new DbmsHandler for the given dbms
     */
    DbmsHandler make(Dbms dbms);

    /**
     * Gets the DbmsHandler for the given Dbms. If no DbmsHandler can be found,
     * a new DbmsHandler is created using the make() method.
     *
     * @param dbms dbms the dbms to use
     * @return the DbmsHandler for the given dbms
     */
    DbmsHandler get(Dbms dbms);
}