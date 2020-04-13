/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.component;

import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.DbmsType;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class DbmsHandlerComponentImpl implements DbmsHandlerComponent {

    private final Map<String, DbmsType> dbmsTypes;

    public DbmsHandlerComponentImpl() {
        this.dbmsTypes = new ConcurrentHashMap<>();
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