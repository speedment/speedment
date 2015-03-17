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
package com.speedment.orm.db.impl;

import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.parameters.DbmsType;
import com.speedment.orm.db.DbmsHandler;

/**
 *
 * @author pemi
 */
public abstract class AbstractRelationalDbmsHandler implements DbmsHandler {

    private final Dbms dbms;

    public AbstractRelationalDbmsHandler(Dbms dbms) {
        this.dbms = dbms;
    }

    public String getUrl() {
        final DbmsType dbmsType = getDbms().getType();
        StringBuilder result = new StringBuilder();
        result.append("jdbc:");
        result.append(dbmsType.getJdbcConnectorName());
        result.append("://");
        getDbms().getIpAddress().ifPresent(ip -> result.append(ip));
        getDbms().getPort().ifPresent(p -> result.append(":").append(p));
        result.append("/");

//        if (getDefaultSchemaName() != null) {
//            result.append(getDefaultSchemaName());
//        }
        dbmsType.getDefaultConnectorParameters().ifPresent(d -> result.append("?").append(d));

        return result.toString();
    }

    @Override
    public Dbms getDbms() {
        return dbms;
    }

}
