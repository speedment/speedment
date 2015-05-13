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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.core.code.model;

import com.speedment.core.config.model.Column;
import com.speedment.core.config.model.Dbms;
import com.speedment.core.config.model.ForeignKey;
import com.speedment.core.config.model.Index;
import com.speedment.core.config.model.PrimaryKeyColumn;
import com.speedment.core.config.model.Project;
import com.speedment.core.config.model.Schema;
import com.speedment.core.config.model.Table;
import com.speedment.core.config.model.aspects.Node;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> The ConfigEntity type to use
 */
public interface Translator<T extends Node, R> extends Supplier<R> {

    T getNode();

    default Project project() {
        return getGenericConfigEntity(Project.class);
    }

    default Dbms dbms() {
        return getGenericConfigEntity(Dbms.class);
    }

    default Schema schema() {
        return getGenericConfigEntity(Schema.class);
    }

    default Table table() {
        return getGenericConfigEntity(Table.class);
    }

    default Column column() {
        return getGenericConfigEntity(Column.class);
    }

    default Stream<Column> columns() {
        return table().streamOf(Column.class);
    }

    default Stream<Index> indexes() {
        return table().streamOf(Index.class);
    }
    
    default Stream<ForeignKey> foreignKeys() {
        return table().streamOf(ForeignKey.class);
    }
    
    default Stream<PrimaryKeyColumn> primaryKeyColumns() {
        return table().streamOf(PrimaryKeyColumn.class);
    }

    default <E extends Node> E getGenericConfigEntity(Class<E> clazz) {
        if (clazz.isAssignableFrom(getNode().getInterfaceMainClass())) {
            @SuppressWarnings("unchecked")
            final E result = (E) getNode();
            return result;
        }
        
        return getNode().ancestor(clazz)
            .orElseThrow(() -> new IllegalStateException(
                getNode() + " is not a " + clazz.getSimpleName() + 
                " and does not have a parent that is a " + clazz.getSimpleName()
            ));
    }

    default <T> T with(T initial, Consumer<T> consumer) {
        consumer.accept(initial);
        return initial;
    }

    default <T> T with(Supplier<T> supplier, Consumer<T> consumer) {
        return with(supplier.get(), consumer);
    }

}
