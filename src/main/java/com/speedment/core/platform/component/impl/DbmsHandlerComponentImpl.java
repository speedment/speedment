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
package com.speedment.core.platform.component.impl;

import com.speedment.api.config.Dbms;
import com.speedment.api.config.parameters.DbmsType;
import com.speedment.core.config.dbms.StandardDbmsType;
import com.speedment.api.db.DbmsHandler;
import com.speedment.core.platform.SpeedmentImpl;
import com.speedment.core.platform.component.DbmsHandlerComponent;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class DbmsHandlerComponentImpl implements DbmsHandlerComponent {

    private final Map<String, DbmsType> dbmsTypes;
    private final Map<Dbms, DbmsHandler> map;
    private final SpeedmentImpl speedment;

    public DbmsHandlerComponentImpl(SpeedmentImpl speedment) {
        this.dbmsTypes = new ConcurrentHashMap<>();
        this.map = new ConcurrentHashMap<>();
        this.speedment = speedment;
        StandardDbmsType.stream().forEach(this::install);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<DbmsHandlerComponent> getComponentClass() {
        return DbmsHandlerComponent.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DbmsHandler make(final Dbms dbms) {
        return dbms.getType().makeDbmsHandler(speedment, dbms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DbmsHandler get(Dbms dbms) {
        return map.computeIfAbsent(dbms, this::make);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void install(DbmsType dbmsType) {
        dbmsTypes.put(dbmsType.getName(), dbmsType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<DbmsType> supportedDbmsTypes() {
        return dbmsTypes.values().stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DbmsType> findByName(String dbmsTypeName) {
        return Optional.ofNullable(
            dbmsTypes.get(dbmsTypeName)
        );
    }
}
