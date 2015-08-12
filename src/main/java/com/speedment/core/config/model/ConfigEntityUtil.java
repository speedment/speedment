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
package com.speedment.core.config.model;

import com.speedment.core.config.model.aspects.Enableable;
import com.speedment.core.config.model.aspects.Node;
import static com.speedment.util.Util.instanceNotAllowed;
import groovy.lang.Closure;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
final class ConfigEntityUtil {

    static protected <E extends Node & Enableable> Column findColumnByName(E configEntity, Optional<Table> optionalTable, String name) {
        final Table table = optionalTable
            .orElseThrow(() -> new IllegalStateException(
                "There is no " + 
                Table.class.getSimpleName() + 
                " associated with this " + 
                configEntity.toString()
            ));
        
        return table
            .streamOf(Column.class)
            .filter(c -> c.getName().equals(name))
            .findAny()
            .orElseThrow(() -> new IllegalStateException(
                "There is no " + 
                Column.class.getSimpleName() + 
                " in the " + 
                table.getInterfaceMainClass().getSimpleName() + 
                " for the " + 
                configEntity.getInterfaceMainClass() + 
                " named " + 
                name
            ));
    }

    static protected <E extends Node & Enableable> Table findTableByName(E configEntity, Optional<Schema> optionalSchema, String name) {
        final Schema currentSchema = optionalSchema.orElseThrow(
            () -> new IllegalStateException(
                "There is no " + 
                Schema.class.getSimpleName() + 
                " associated with this " + 
                configEntity.toString()
            )
        );
        
        final String[] paths = name.split("\\.");
        // Just the name of the table
        if (paths.length == 1) {
            return currentSchema
                .stream()
                .filter(c -> c.getName().equals(name))
                .findAny()
                .orElseThrow(
                    () -> new IllegalStateException(
                        "There is no " + 
                        Table.class.getSimpleName() + 
                        " in the " + 
                        currentSchema.getInterfaceMainClass().getSimpleName() + 
                        " for the " + 
                        configEntity.getInterfaceMainClass() + 
                        " named " + 
                        name
                    )
                );
        }
        // The name is "schema.table"
        if (paths.length == 2) {
            final String otherSchemaName = paths[0];
            final String tableName = paths[1];
            final Dbms dbms = currentSchema
                .ancestor(Dbms.class)
                .orElseThrow(() -> new IllegalStateException(
                    "No " + 
                    Dbms.class.getSimpleName() + 
                    " for " + 
                    currentSchema.toString()
                ));
            
            final Schema otherSchema = dbms
                .stream()
                .filter(t -> t.getName().equals(otherSchemaName))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(
                    "No " + 
                    Schema.class.getSimpleName() + 
                    " named " + otherSchemaName + 
                    " in " + 
                    Dbms.class.getSimpleName() + 
                    " " + 
                    dbms.toString()
                ));

            return otherSchema
                .stream()
                .filter(t -> t.getName().equals(tableName))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(
                    "There is no " + 
                    Table.class.getSimpleName() + 
                    " in the " + 
                    currentSchema.getInterfaceMainClass().getSimpleName() + 
                    " for the " + 
                    configEntity.getInterfaceMainClass() + 
                    " named " + 
                    name
                ));
        }
        
        throw new IllegalStateException(
            "There is no " + 
            Table.class.getSimpleName() + 
            " for the " + 
            configEntity.getInterfaceMainClass() + 
            " named " + 
            name
        );
    }

    static <S> S groovyDelegatorHelper(Closure<?> c, Supplier<S> createAndAdder) {
        Objects.requireNonNull(createAndAdder);
        final S result = createAndAdder.get();
        c.setDelegate(result);
        c.setResolveStrategy(Closure.DELEGATE_ONLY);
        c.call();
        return result;
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private ConfigEntityUtil() { instanceNotAllowed(getClass()); }
}