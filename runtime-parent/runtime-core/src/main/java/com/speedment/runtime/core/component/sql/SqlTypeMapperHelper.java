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
package com.speedment.runtime.core.component.sql;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.core.internal.component.sql.SqlTypeMapperHelperImpl;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @param <DB_TYPE>    the JDBC type of the field
 * @param <JAVA_TYPE>  the java type of the field
 * 
 * @author  Emil Forslund
 * @since   3.0.1
 */
public interface SqlTypeMapperHelper<DB_TYPE, JAVA_TYPE> {
    
    JAVA_TYPE apply(DB_TYPE dbValue);
    
    static <ENTITY, DB_TYPE, JAVA_TYPE> 
    SqlTypeMapperHelper<DB_TYPE, JAVA_TYPE> create(
        TypeMapper<DB_TYPE, JAVA_TYPE> typeMapper,
        Column column,
        Class<ENTITY> entityClass) {
        
        return new SqlTypeMapperHelperImpl<>(typeMapper, column, entityClass);
    }
}