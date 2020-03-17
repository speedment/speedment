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
package com.speedment.runtime.config.identifier;

import com.speedment.runtime.config.identifier.trait.HasDbmsId;
import com.speedment.runtime.config.identifier.trait.HasSchemaId;
import com.speedment.runtime.config.internal.identifier.SchemaIdentifierImpl;
import com.speedment.runtime.config.util.DocumentDbUtil;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Identifies a particular Schema. The identifier is an immutable non-complex
 * object that only contains the names of the nodes required to uniquely
 * identify it in the database.
 * <p>
 * To find the actual documents referred to by the identifier, the following
 * utility methods can be used:
 * <ul>
 *
 * <li>DocumentDbUtil#referencedSchema(Project, Project, TableIdentifier)
 * <li>DocumentDbUtil#referencedDbms(Project, TableIdentifier)
 * </ul>
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @since 3.0.1
 * @see DocumentDbUtil
 */
public interface SchemaIdentifier<ENTITY> extends HasDbmsId, HasSchemaId {

    default DbmsIdentifier<ENTITY> asDbmsIdentifier() {
        return DbmsIdentifier.of(getDbmsId());
    }
    
    class Hidden {
        private Hidden() {}

        private static final Map<SchemaIdentifier<?>, SchemaIdentifier<?>> INTERNED = new ConcurrentHashMap<>();

    }

    static <ENTITY> SchemaIdentifier<ENTITY> of(String dbmsName, String schemaName) {
        final SchemaIdentifier<ENTITY> newId = new SchemaIdentifierImpl<>(dbmsName, schemaName);
        Hidden.INTERNED.putIfAbsent(newId, newId);

        @SuppressWarnings("unchecked")
        final SchemaIdentifier<ENTITY> result = (SchemaIdentifier<ENTITY>) Hidden.INTERNED.get(newId);
        return result;
    }
}
