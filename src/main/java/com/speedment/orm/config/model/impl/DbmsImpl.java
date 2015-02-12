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
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class DbmsImpl extends AbstractConfigEntity<Dbms, Project, Schema> implements Dbms {

    private DbmsType type;
    private String ipAddress;
    private Integer port;
    private String username, password;

    @Override
    protected void setDefaults() {
        setType(DbmsType.MYSQL);
        setIpAddress("localhost");
        setPort(getType().getDefaultPort());
        setUsername("root");
        setPassword("");
    }

    @Override
    public Class<Dbms> getInterfaceMainClass() {
        return Dbms.class;
    }

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
    public Optional<String> getIpAddress() {
        return Optional.ofNullable(ipAddress);
    }

    @Override
    public Dbms setIpAddress(CharSequence ipAddress) {
        return with(ipAddress, i -> this.ipAddress = makeNullSafeString(i));
    }

    @Override
    public Optional<Integer> getPort() {
        return Optional.of(port);
    }

    @Override
    public Dbms setPort(Integer port) {
        return with(port, p -> this.port = p);
    }

    @Override
    public Optional<String> getUsername() {
        return Optional.of(username);
    }

    @Override
    public Dbms setUsername(CharSequence name) {
        return with(name, n -> this.username = makeNullSafeString(n));
    }

    @Override
    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    @Override
    public Dbms setPassword(CharSequence password) {
        return with(password, p -> this.password = makeNullSafeString(p));
    }

}
