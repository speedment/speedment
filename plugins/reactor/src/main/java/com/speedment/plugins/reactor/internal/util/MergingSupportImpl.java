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
package com.speedment.plugins.reactor.internal.util;

import com.speedment.common.codegen.model.Type;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.TranslatorSupport;
import com.speedment.generator.typetoken.TypeTokenGenerator;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;

import static com.speedment.plugins.reactor.internal.util.ReactorComponentUtil.validMergingColumns;
import com.speedment.plugins.reactor.internal.component.ReactorComponentImpl;
import com.speedment.plugins.reactor.util.MergingSupport;
import com.speedment.generator.component.TypeMapperComponent;

/**
 * Utility methods that are used by several translators in this package but that
 * doesn't nescessarily need to be shared with others.
 * 
 * @author Emil Forslund
 * @since  1.1.0
 */
public final class MergingSupportImpl implements MergingSupport {
    
    private @Inject Injector injector;
    private @Inject TypeTokenGenerator typeTokenGenerator;
    private @Inject TypeMapperComponent typeMappers;
    
    @Override
    public Column mergingColumn(Table table) {
        return table.getAsString(ReactorComponentImpl.MERGE_ON)
            .flatMap(str -> table.columns()
                .filter(col -> col.getName().equals(str))
                .findAny()
            )
            .map(Column.class::cast)
            .orElseGet(() -> 
                validMergingColumns(table, typeMappers).get(0)
            );
    }
    
    @Override
    public String mergingColumnField(Table table) {
        final TranslatorSupport<Table> support = new TranslatorSupport<>(injector, table);
        return support.namer().javaTypeName(table.getJavaName()) + "." +
            support.namer().javaStaticFieldName(
                mergingColumn(table).getJavaName()
            );
    }

    @Override
    public Type mergingColumnType(Table table) {
        final Column column = mergingColumn(table);
        return typeTokenGenerator.typeOf(column);
    }
    
    private MergingSupportImpl() {}
}
