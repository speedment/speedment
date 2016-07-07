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
package com.speedment.plugins.reactor.internal.translator;

import com.speedment.common.codegen.model.Type;
import com.speedment.generator.TranslatorSupport;
import com.speedment.plugins.reactor.component.ReactorComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;

import static com.speedment.plugins.reactor.component.ReactorComponentUtil.validMergingColumns;

/**
 * Utility methods that are used by several translators in this package but that
 * doesn't nescessarily need to be shared with others.
 * 
 * @author Emil Forslund
 * @since  1.1.0
 */
final class TranslatorUtil {
    
    static Column mergingColumn(Table table) {
        return table.getAsString(ReactorComponent.MERGE_ON)
            .flatMap(str -> table.columns()
                .filter(col -> col.getName().equals(str))
                .findAny()
            )
            .map(Column.class::cast)
            .orElseGet(() -> 
                validMergingColumns(table).get(0)
            );
    }
    
    static String mergingColumnField(Table table, TranslatorSupport<Table> support) {
        return support.namer().javaTypeName(table.getJavaName()) + "." +
            support.namer().javaStaticFieldName(
                mergingColumn(table).getJavaName()
            );
    }

    static Type mergingColumnType(Table table) {
        return Type.of(mergingColumn(table).findTypeMapper().getJavaType());
    }
    
    private TranslatorUtil() {}
}
