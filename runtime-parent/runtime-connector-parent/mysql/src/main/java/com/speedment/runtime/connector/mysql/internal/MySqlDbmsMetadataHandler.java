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
package com.speedment.runtime.connector.mysql.internal;

import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.JavaTypeMap;
import com.speedment.runtime.core.abstracts.AbstractDbmsMetadataHandler;
import java.sql.Blob;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Specific MySQL implementation of a {@link DbmsMetadataHandler}.
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.0.0
 */
public final class MySqlDbmsMetadataHandler extends AbstractDbmsMetadataHandler {

    public MySqlDbmsMetadataHandler(
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final ProjectComponent projectComponent
    ) {
        super(connectionPoolComponent, dbmsHandlerComponent, projectComponent);
    }

    @Override
    protected JavaTypeMap newJavaTypeMap() {
        final JavaTypeMap javaTypeMap = super.newJavaTypeMap();

        javaTypeMap.put("YEAR", Integer.class);
        javaTypeMap.put("JSON", String.class);

        Stream.of("LONG", "MEDIUM", "TINY").forEach(key -> javaTypeMap.put(key + "BLOB", Blob.class));

        javaTypeMap.addRule((sqlTypeMapping, md) -> {
            if ("GEOMETRY".equalsIgnoreCase(md.getTypeName())) {
                return Optional.of(Blob.class);
            } else {
                return Optional.empty();
            }
        });

        javaTypeMap.addRule((sqlTypeMapping, md) -> {
            // Map a BIT(1) to boolean
            if ("BIT".equalsIgnoreCase(md.getTypeName()) && md.getColumnSize() == 1) {
                return Optional.of(Boolean.class);
            } else {
                return Optional.empty();
            }
        });

        return javaTypeMap;
    }
}