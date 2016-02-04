/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.config.db.Dbms;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.internal.core.config.dbms.StandardDbmsType;
import com.speedment.db.DbmsHandler;
import com.speedment.component.DbmsHandlerComponent;
import com.speedment.exception.SpeedmentException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class DbmsHandlerComponentImpl extends Apache2AbstractComponent implements DbmsHandlerComponent {

    private final Map<String, DbmsType> dbmsTypes;
    private final Map<Dbms, DbmsHandler> map;

    public DbmsHandlerComponentImpl(Speedment speedment) {
        super(speedment);
        this.dbmsTypes = new ConcurrentHashMap<>();
        this.map = new ConcurrentHashMap<>();
        StandardDbmsType.stream().forEach(this::install);
    }

    @Override
    public Class<DbmsHandlerComponent> getComponentClass() {
        return DbmsHandlerComponent.class;
    }

    @Override
    public DbmsHandler make(final Dbms dbms) {
        requireNonNull(dbms);
        
        final String dbmsTypeName = dbms.getTypeName();
        final DbmsType dbmsType = findByName(dbmsTypeName)
            .orElseThrow(() -> new SpeedmentException(
                "Unable to find a DbmsType with name "+dbmsTypeName
            ));
        
        return dbmsType.makeDbmsHandler(getSpeedment(), dbms);
    }

    @Override
    public DbmsHandler get(Dbms dbms) {
        requireNonNull(dbms);
        return map.computeIfAbsent(dbms, this::make);
    }

    @Override
    public void install(DbmsType dbmsType) {
        requireNonNull(dbmsType);
        dbmsTypes.put(dbmsType.getName(), dbmsType);
    }

    @Override
    public Stream<DbmsType> supportedDbmsTypes() {
        return dbmsTypes.values().stream()
            .filter(DbmsType::isSupported);
            
    }

    @Override
    public Optional<DbmsType> findByName(String dbmsTypeName) {
        requireNonNull(dbmsTypeName);
        return Optional.ofNullable(
            dbmsTypes.get(dbmsTypeName)
        );
    }
}