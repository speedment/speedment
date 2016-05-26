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
package com.speedment.plugins.reactor.component;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.PrimaryKeyColumn;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.internal.util.document.DocumentDbUtil;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Emil Forslund
 * @since  1.1.0
 */
public final class ReactorPluginUtil {

    public static List<Column> validMergingColumns(Table table) {
        return table.columns()

            // Only consider non-primary-key columns
            .filter(col -> table.primaryKeyColumns()
                .map(PrimaryKeyColumn::findColumn)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .noneMatch(c -> DocumentDbUtil.isSame(col, c))
            )

            // Nullable columns does not make good
            // merging candidates.
            .filter(col -> !col.isNullable())

            // Only include columns that are 
            // comparable.
            .filter(col -> Comparable.class
                .isAssignableFrom(
                    col.findTypeMapper().getJavaType()
                )
            )

            // Return list of names.
            .collect(toList());
    }
    
    private ReactorPluginUtil() {}
}
