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
package com.speedment.runtime.config;


import com.speedment.runtime.config.internal.SchemaImpl;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.mutator.SchemaMutator;
import com.speedment.runtime.config.trait.HasAlias;
import com.speedment.runtime.config.trait.HasChildren;
import com.speedment.runtime.config.trait.HasDeepCopy;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasMutator;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasParent;
import com.speedment.runtime.config.util.DocumentUtil;

import java.util.Map;
import java.util.stream.Stream;

/**
 * A typed {@link Document} that represents a schema instance in the database. A
 * {@code Schema} is located inside a {@link Dbms} and can have multiple 
 * {@link Table Tables} as children.
 * 
 * @author  Emil Forslund
 */

public interface Schema extends
        Document,
        HasParent<Dbms>,
        HasDeepCopy,
        HasEnabled,
        HasId,        
        HasName,
        HasChildren,
        HasAlias,
        HasMainInterface,
        HasMutator<SchemaMutator<? extends Schema>> {

    /**
     * Returns {@code true} if this schema is the default one, else
     * {@code false}. Default value is {@code true}.
     *
     * @return {@code true} if default, else {@code false}
     */
    default boolean isDefaultSchema() {
        return getAsBoolean(SchemaUtil.DEFAULT_SCHEMA).orElse(true);
    }
    
    /**
     * Creates a stream of tables located in this document.
     * 
     * @return  tables
     */
    Stream<Table> tables();

    @Override
    default Class<Schema> mainInterface() {
        return Schema.class;
    }

    @Override
    default SchemaMutator<? extends Schema> mutator() {
        return DocumentMutator.of(this);
    }

    @Override
    default Schema deepCopy() {
        return DocumentUtil.deepCopy(this, SchemaImpl::new);
    }

    /**
     * Creates and returns a new standard implementation of a {@link Schema}
     * with the given {@code parent} and {@code data}
     *
     * @param parent of the config document (nullable)
     * @param data of the config document
     * @return new {@link Schema} with the given parameters
     *
     * @throws NullPointerException if the provided {@code data} is {@code null}
     */
    static Schema create(Dbms parent, Map<String, Object> data) {
        return new SchemaImpl(parent, data);
    }

}
