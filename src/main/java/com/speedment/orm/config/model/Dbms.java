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
package com.speedment.orm.config.model;

import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.parameters.DbmsType;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface Dbms extends ConfigEntity<Dbms, Project, Schema> {

    public static Dbms newInstance() {
        return ConfigEntityFactory.getInstance().newDbms();
    }

    default Schema addNewSchema() {
        final Schema e = Schema.newInstance();
        add(e);
        return e;
    }

    DbmsType getType();

    Dbms setType(DbmsType dbmsType);

    /**
     *
     * @param dbmsTypeName
     * @return the DbmsType
     * @throws IllegalArgumentException if a DbmsType for the given dbmsTypeName
     * could not be found
     */
    Dbms setType(CharSequence dbmsTypeName);

    CharSequence getIpAddress();

    Dbms setIpAddress(CharSequence ipAddress);

    int getPort();

    Dbms setPort(int port);

    CharSequence getUsername();

    Dbms setUsername(CharSequence name);

    CharSequence getPassword();

    Dbms setPassword(CharSequence password);

}
