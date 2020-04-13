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
package com.speedment.runtime.core.util;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;

import static java.util.stream.Collectors.joining;

/**
 * Utility methods for calculating values based on a configuration model.
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class DatabaseUtil {

    private DatabaseUtil() {}

    /**
     * Returns the {@link DbmsType} for the specified {@link Dbms}.
     * 
     * @param handler  the dbms handler to use
     * @param dbms     the dbms
     * @return         the type of that dbms
     */
    public static DbmsType dbmsTypeOf(DbmsHandlerComponent handler, Dbms dbms) {
        final String typeName = dbms.getTypeName();
        return handler.findByName(typeName).orElseThrow(() -> 
            new SpeedmentException(
                "Unable to find the database type " + typeName + 
                ". The installed types are: " + 
                handler.supportedDbmsTypes()
                    .map(DbmsType::getName)
                    .collect(joining(", "))
            )
        );
    }

    /**
     * Determines the connection URL to use for the specified {@code Dbms} by
     * first:
     * <ol>
     *      <li>checking if the {@code CONNECTION_URL} property is set;
     *      <li>otherwise, calculate it using the {@link DbmsType}.
     * </ol>
     * If the {@link DbmsType} can not be found by calling
     * {@link DatabaseUtil#dbmsTypeOf(DbmsHandlerComponent, Dbms)}, a
     * {@code SpeedmentException} will be thrown.
     *
     * @param dbmsHandlerComponent the dbms handler component instance
     * @param dbms                 the database manager
     * @return                     the connection URL to use
     * @throws SpeedmentException  if the {@link DbmsType} couldn't be found
     */
    public static String findConnectionUrl(DbmsHandlerComponent dbmsHandlerComponent, Dbms dbms) {
        final DbmsType type = findDbmsType(dbmsHandlerComponent, dbms);
        return dbms.getConnectionUrl().orElseGet(() -> type.getConnectionUrlGenerator().from(dbms));
    }

    /**
     * Locates the {@link DbmsType} corresponding to the specified {@link Dbms},
     * or throws a {@code SpeedmentException} if it can not be found.
     *
     * @param dbmsHandlerComponent  the handler to look in
     * @param dbms                  the dbms to look for
     * @return                      the dbms type of that dbms
     */
    public static DbmsType findDbmsType(DbmsHandlerComponent dbmsHandlerComponent, Dbms dbms) {
        return dbmsHandlerComponent.findByName(dbms.getTypeName())
            .orElseThrow(() -> new SpeedmentException(
                "Could not find any installed DbmsType named '" + 
                dbms.getTypeName() + "'."
            ));
    }

}