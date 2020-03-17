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
package com.speedment.runtime.connector.sqlite.internal.types;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.db.JavaTypeMap;
import com.speedment.runtime.core.db.metadata.ColumnMetaData;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Used to load the type map from a SQL database asynchronously.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public interface SqlTypeMappingHelper {

    CompletableFuture<Map<String, Class<?>>> loadFor(Dbms dbms);

    Optional<Class<?>> findFor(Map<String, Class<?>> loaded, ColumnMetaData metaData);

    static SqlTypeMappingHelper create(Injector injector, JavaTypeMap mappings) {
        return new SqlTypeMappingHelperImpl(
            injector.getOrThrow(ConnectionPoolComponent.class),
            injector.getOrThrow(DbmsHandlerComponent.class),
            mappings
        );
    }

}