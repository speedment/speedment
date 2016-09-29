/*
 * Copyright (c) Emil Forslund, 2016.
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Emil Forslund and his suppliers, if any. 
 * The intellectual and technical concepts contained herein 
 * are proprietary to Emil Forslund and his suppliers and may 
 * be covered by U.S. and Foreign Patents, patents in process, 
 * and are protected by trade secret or copyright law. 
 * Dissemination of this information or reproduction of this 
 * material is strictly forbidden unless prior written 
 * permission is obtained from Emil Forslund himself.
 */
package com.speedment.runtime.util;

import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.common.dbmodel.Dbms;
import com.speedment.runtime.db.DbmsType;
import com.speedment.runtime.exception.SpeedmentException;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;
import static java.util.stream.Collectors.joining;

/**
 *
 * @author  Emil Forslund
 * @since   1.0.0
 */
public final class DatabaseUtil {

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
     * {@link DocumentDbUtil#dbmsTypeOf(Speedment, Dbms)}, a
     * {@code SpeedmentException} will be thrown.
     *
     * @param dbmsHandlerComponent the dbms handler component instance
     * @param dbms                 the database manager
     * @return                     the connection URL to use
     * @throws SpeedmentException  if the {@link DbmsType} couldn't be found
     */
    public static String findConnectionUrl(DbmsHandlerComponent dbmsHandlerComponent, Dbms dbms) throws SpeedmentException {
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
        return dbmsHandlerComponent.findByName(dbms.getTypeName()).orElseThrow(() -> new SpeedmentException("Could not find any installed DbmsType named '" + dbms.getTypeName() + "'."));
    }

    private DatabaseUtil() {
        instanceNotAllowed(getClass());
    }
}