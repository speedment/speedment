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
package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasAlias;
import com.speedment.config.db.trait.HasChildren;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.config.db.trait.HasParent;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.db.mutator.SchemaMutator;
import static com.speedment.internal.util.document.DocumentUtil.newDocument;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Schema extends
        Document,
        HasParent<Dbms>,
        HasEnabled,
        HasName,
        HasChildren,
        HasAlias,
        HasMainInterface,
        HasMutator<SchemaMutator> {

    final String DEFAULT_SCHEMA = "defaultSchema",
            TABLES = "tables";

    /**
     * Returns {@code true} if this schema is the default one, else
     * {@code false}.
     *
     * @return {@code true} if default, else {@code false}
     */
    default boolean isDefaultSchema() {
        return getAsBoolean(DEFAULT_SCHEMA).orElse(false);
    }

    default Stream<? extends Table> tables() {
        return children(TABLES, tableConstructor());
    }

    default Table addNewTable() {
        return tableConstructor().apply(this, newDocument(this, TABLES));
    }

    BiFunction<Schema, Map<String, Object>, ? extends Table> tableConstructor();

    @Override
    default Class<Schema> mainInterface() {
        return Schema.class;
    }

    @Override
    default SchemaMutator mutator() {
        return DocumentMutator.of(this);
    }

}
