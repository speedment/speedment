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
package com.speedment.generator.translator;

import com.speedment.common.codegen.constant.DefaultType;
import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.injector.Injector;
import com.speedment.generator.translator.exception.SpeedmentTranslatorException;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;

import java.lang.reflect.Type;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> type of model to translate into
 */
public abstract class AbstractEntityAndManagerTranslator<T extends ClassOrInterface<T>>
    extends AbstractJavaClassTranslator<Table, T> {

    protected AbstractEntityAndManagerTranslator(
        final Injector injector,
        final Table table,
        final Function<String, T> modelConstructor
    ) {
        super(injector, table, modelConstructor);
    }

    protected Type typeOfPK() {
        final long pks = primaryKeyColumns().count();

        if (pks == 0) {
            return DefaultType.list(DefaultType.WILDCARD);
        }

        final Column firstColumn = columnsFromPks()
                .findFirst().orElseThrow(() -> new SpeedmentTranslatorException(
                    "Table '" + table().get().getId() + 
                    "' did not contain any primary key columns."
                ));
        
        Type firstType = typeMappers().get(firstColumn).getJavaType(firstColumn);
        if (DefaultType.isPrimitive(firstType)) {
            firstType = DefaultType.wrapperFor(firstType);
        }

        if (pks == 1) {
            return firstType;
        } else if (columnsFromPks()
            .map(c -> typeMappers().get(c).getJavaType(c))
            .allMatch(firstType::equals)) {
            
            return DefaultType.list(firstType);
        } else {
            return DefaultType.list(DefaultType.WILDCARD);
        }
    }
    
    private Stream<Column> columnsFromPks() {
        return primaryKeyColumns().map(pk -> {
            try {
                return pk.findColumnOrThrow();
            } catch (final NoSuchElementException ex) {
                throw new SpeedmentTranslatorException(
                    "Could not find any column belonging to primary key '" + pk.getId() + "'.", ex
                );
            }
        });
    }
}
