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
package com.speedment.generator.internal;

import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.generator.component.TypeMapperComponent;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.tuple.Tuple1;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.exception.SpeedmentException;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> type of model to translate into
 */
public abstract class EntityAndManagerTranslator<T extends ClassOrInterface<T>>
    extends DefaultJavaClassTranslator<Table, T> {

    private @Inject TypeMapperComponent typeMappers;
    
    protected EntityAndManagerTranslator(
        Table table,
        Function<String, T> modelConstructor) {

        super(table, modelConstructor);
    }

    protected Type typeOfPK() {
        final long pks = primaryKeyColumns().count();

        if (pks == 0) {
            return DefaultType.list(DefaultType.WILDCARD);
        }

        final Column firstColumn = columnsFromPks()
                .findFirst().orElseThrow(() -> new SpeedmentException(
                    "Table '" + table().get().getName() + 
                    "' did not contain any primary key columns."
                ));
        
        Type firstType = typeMappers.get(firstColumn).getJavaType(firstColumn);
        if (DefaultType.isPrimitive(firstType)) {
            firstType = DefaultType.wrapperFor(firstType);
        }

        if (pks == 1) {
            return firstType;
        } else if (columnsFromPks()
            .map(c -> typeMappers.get(c).getJavaType(c))
            .allMatch(firstType::equals)) {
            
            return DefaultType.list(firstType);
        } else {
            return DefaultType.list(DefaultType.WILDCARD);
        }
    }

    protected Type pkTupleType() {
        final long pks = primaryKeyColumns().count();
        final Package pkg = Tuple1.class.getPackage();
        final String tupleClassName = pkg.getName() + ".Tuple" + pks;
        final java.lang.Class<?> tupleClass;
        
        try {
            tupleClass = java.lang.Class.forName(tupleClassName);
        } catch (final ClassNotFoundException cnf) {
            throw new SpeedmentException("Speedment does not support " + pks + " primary keys.", cnf);
        }
        
        return SimpleParameterizedType.create(
            tupleClass,
            columnsFromPks().map(col -> {
                final Type type = typeMappers.get(col).getJavaType(col);
                if (DefaultType.isPrimitive(type)) {
                    return DefaultType.classOf(DefaultType.wrapperFor(type));
                } else {
                    return DefaultType.classOf(type);
                }
            }).toArray(Type[]::new)
        );
    }
    
    private Stream<Column> columnsFromPks() {
        return primaryKeyColumns().map(pk -> {
            try {
                return pk.findColumn().get();
            } catch (final NoSuchElementException ex) {
                throw new SpeedmentException(
                    "Could not find any column belonging to primary key '" + pk.getName() + "'.", ex
                );
            }
        });
    }
}
