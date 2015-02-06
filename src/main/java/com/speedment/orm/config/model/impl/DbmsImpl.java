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
package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.model.*;
import com.speedment.orm.config.model.parameters.DbmsType;

/**
 *
 * @author pemi
 */
public class DbmsImpl extends AbstractConfigEntity<Dbms, Project, Schema> implements Dbms {

    private DbmsType type;
    private CharSequence ipAddress;
    private int port;
    private CharSequence username, password;

    @Override
    public DbmsType getType() {
        return type;
    }

    @Override
    public Dbms setType(DbmsType dbmsType) {
        return with(dbmsType, dt -> this.type = dt);
    }

    @Override
    public Dbms setType(CharSequence dbmsTypeName) {
        return setType(DbmsType.findByNameIgnoreCase(dbmsTypeName.toString()).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public CharSequence getIpAddress() {
        return ipAddress;
    }

    @Override
    public Dbms setIpAddress(CharSequence ipAddress) {
        return with(ipAddress, i -> this.ipAddress = i);
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public Dbms setPort(int port) {
        return with(port, p -> this.port = p);
    }

    @Override
    public CharSequence getUsername() {
        return username;
    }

    @Override
    public Dbms setUsername(CharSequence name) {
        return with(name, n -> this.username = n);
    }

    @Override
    public CharSequence getPassword() {
        return password;
    }

    @Override
    public Dbms setPassword(CharSequence password) {
        return with(password, p -> this.password = p);
    }

}
