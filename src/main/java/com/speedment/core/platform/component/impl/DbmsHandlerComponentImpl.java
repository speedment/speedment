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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.core.platform.component.impl;

import com.speedment.core.config.model.Dbms;
import com.speedment.core.config.model.parameters.StandardDbmsType;
import com.speedment.core.db.DbmsHandler;
import com.speedment.core.db.impl.MySqlDbmsHandler;
import com.speedment.core.platform.component.DbmsHandlerComponent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author pemi
 */
public class DbmsHandlerComponentImpl implements DbmsHandlerComponent {

    private final Map<Dbms, DbmsHandler> map;

    public DbmsHandlerComponentImpl() {
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public Class<DbmsHandlerComponent> getComponentClass() {
        return DbmsHandlerComponent.class;
    }

    @Override
    public DbmsHandler make(final Dbms dbms) {
        if (dbms.getType() == StandardDbmsType.MYSQL) {
            return new MySqlDbmsHandler(dbms);
        }
        throw new UnsupportedOperationException(dbms.getType().getName() + " not supported yet.");
    }

    @Override
    public DbmsHandler get(Dbms dbms) {
        return map.computeIfAbsent(dbms, d -> make(d));
    }

}
