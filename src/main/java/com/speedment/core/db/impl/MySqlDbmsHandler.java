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
package com.speedment.core.db.impl;

import com.speedment.core.config.model.Dbms;
import com.speedment.core.config.model.Table;
import com.speedment.core.db.DbmsHandler;
import java.sql.ResultSet;
import java.util.function.Consumer;

/**
 *
 * @author pemi
 */
public class MySqlDbmsHandler extends AbstractRelationalDbmsHandler implements DbmsHandler {

    public MySqlDbmsHandler(final Dbms dbms) {
        super(dbms);
    }

    @Override
    public <ENTITY> long readAll(Consumer<ENTITY> consumer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <PK> ResultSet read(Table table, PK primaryKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <ENTITY> void insert(Table table, ENTITY entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <ENTITY> void update(Table table, ENTITY entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <ENTITY> void delete(Table table, ENTITY entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
