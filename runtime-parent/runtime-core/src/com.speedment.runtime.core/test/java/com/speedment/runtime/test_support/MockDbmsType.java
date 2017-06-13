/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.test_support;

import com.speedment.runtime.core.db.ConnectionUrlGenerator;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.internal.db.AbstractDbmsType;
import com.speedment.runtime.core.internal.manager.sql.MySqlSpeedmentPredicateView;
import java.util.List;

/**
 *
 * @author Per Minborg
 */
public class MockDbmsType extends AbstractDbmsType implements DbmsType {

    @Override
    public String getName() {
        return "MockDb";
    }

    @Override
    public String getDriverManagerName() {
        return "MockDB JDBC Driver";
    }

    @Override
    public int getDefaultPort() {
        return 42;
    }

    @Override
    public String getDbmsNameMeaning() {
        return "mock";
    }

    @Override
    public String getDriverName() {
        return "MockDb";
    }

    @Override
    public DbmsMetadataHandler getMetadataHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DbmsOperationHandler getOperationHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConnectionUrlGenerator getConnectionUrlGenerator() {
        return dbms -> "jdbc:mockdb://" + dbms.getIpAddress().orElse("") + ":" + dbms.getPort().orElse(0);
    }

    @Override
    public FieldPredicateView getFieldPredicateView() {
        return new MySqlSpeedmentPredicateView();
    }
        
}
