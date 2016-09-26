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
package com.speedment.runtime.manager;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.db.SqlFunction;
import com.speedment.runtime.internal.manager.JdbcManagerSupportImpl;
import java.sql.ResultSet;

/**
 *
 * @param <ENTITY>  the entity type
 * 
 * @author  Emil Forslund
 * @since   1.0.0
 */
public interface JdbcManagerSupport<ENTITY> extends ManagerSupport<ENTITY> {

    static <ENTITY> JdbcManagerSupport<ENTITY> create(
            Injector injector, 
            Manager<ENTITY> manager, 
            SqlFunction<ResultSet, ENTITY> entityMapper) {
        
        return new JdbcManagerSupportImpl<>(injector, manager, entityMapper);
    }
    
    String sqlSelect();
    
    long sqlCount();
    
}