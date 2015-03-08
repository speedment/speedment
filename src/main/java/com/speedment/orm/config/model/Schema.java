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
package com.speedment.orm.config.model;

import com.speedment.orm.annotations.Api;
import com.speedment.orm.config.model.aspects.Parent;
import com.speedment.orm.config.model.aspects.Child;
import com.speedment.orm.config.model.parameters.ColumnCompressionTypeable;
import com.speedment.orm.config.model.parameters.FieldStorageTypeable;
import com.speedment.orm.config.model.parameters.StorageEngineTypeable;
import com.speedment.orm.platform.SpeedmentPlatform;
import groovy.lang.Closure;
import java.util.Optional;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface Schema extends ConfigEntity, Child<Dbms>, Parent<Table>,
        FieldStorageTypeable,
        ColumnCompressionTypeable,
        StorageEngineTypeable {

    @Override
    default Class<Schema> getInterfaceMainClass() {
        return Schema.class;
    }

    @Override
    default Class<Dbms> getParentInterfaceMainClass() {
        return Dbms.class;
    }

    default Table addNewTable() {
        final Table e = SpeedmentPlatform.getInstance().getConfigEntityFactory().newTable();
        add(e);
        return e;
    }

    @External
    boolean isDefaultSchema();

    @External
    void setDefaultSchema(boolean defaultSchema);

    @External
    Optional<String> getCatalogName();

    @External
    void setCatalogName(String catalogName);

    @External
    Optional<String> getSchemaName();

    @External
    void setSchemaName(String schemaName);

    // Groovy
    default Table table(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewTable);
    }

}
