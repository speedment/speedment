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
package com.speedment.runtime.config.internal.identifier;

import com.speedment.runtime.config.identifier.SchemaIdentifier;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY>  the entity type
 */
public final class SchemaIdentifierImpl<ENTITY> implements SchemaIdentifier<ENTITY> {

    private final String dbmsName;
    private final String schemaName;
    private final int hashCode;

    public SchemaIdentifierImpl(String dbmsName, String schemaName) {
        this.dbmsName = requireNonNull(dbmsName);
        this.schemaName = requireNonNull(schemaName);
        this.hashCode = privateHashCode();
    }

    @Override
    public String getDbmsId() {
        return dbmsName;
    }

    @Override
    public String getSchemaId() {
        return schemaName;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SchemaIdentifier) {
            final SchemaIdentifier<?> that = (SchemaIdentifier<?>) obj;
            return 
                Objects.equals(dbmsName, that.getDbmsId()) &&
                Objects.equals(schemaName, that.getSchemaId());
        }
        return false;
    }

    private int privateHashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(dbmsName);
        hash = 53 * hash + Objects.hashCode(schemaName);
        return hash;
    }

    @Override
    public String toString() {
        return dbmsName + "." + schemaName;
    }
}