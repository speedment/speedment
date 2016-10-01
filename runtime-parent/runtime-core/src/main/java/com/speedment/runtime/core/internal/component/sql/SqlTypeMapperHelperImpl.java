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
package com.speedment.runtime.core.internal.component.sql;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.core.component.sql.SqlTypeMapperHelper;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @param <ENTITY>     the entity type
 * @param <DB_TYPE>    the JDBC type of the field
 * @param <JAVA_TYPE>  the java type of the field
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class SqlTypeMapperHelperImpl<ENTITY, DB_TYPE, JAVA_TYPE> 
implements SqlTypeMapperHelper<DB_TYPE, JAVA_TYPE> {

    private final TypeMapper<DB_TYPE, JAVA_TYPE> typeMapper;
    private final Column column;
    private final Class<ENTITY> entityClass;

    public SqlTypeMapperHelperImpl(
            TypeMapper<DB_TYPE, JAVA_TYPE> typeMapper,
            Column column,
            Class<ENTITY> entityClass) {

        this.typeMapper  = typeMapper;
        this.column      = column;
        this.entityClass = entityClass;
    }

    @Override
    public JAVA_TYPE apply(DB_TYPE dbValue) {
        return typeMapper.toJavaType(column, entityClass, dbValue);
    }
}