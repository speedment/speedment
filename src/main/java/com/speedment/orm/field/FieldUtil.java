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
package com.speedment.orm.field;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.ManagerComponent;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class FieldUtil {

    private FieldUtil() {
    }

    public static Column findColumn(Class<?> entityClass, String name) {
        final Table table = Platform.get().get(ManagerComponent.class).managerOf(entityClass).getTable();
        final Optional<Column> oColumn = table.streamOf(Column.class).filter(c -> c.getName().equals(name)).findAny();
        return oColumn.orElseThrow(() -> new IllegalStateException("A column named " + name + " can not be found in the table " + table.getName()));
    }

}
