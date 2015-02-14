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
package com.speedment.orm.config.model.impl;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.ConfigEntity;
import com.speedment.orm.config.model.ConfigEntityFactory;
import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.ForeignKey;
import com.speedment.orm.config.model.ForeignKeyColumn;
import com.speedment.orm.config.model.Index;
import com.speedment.orm.config.model.IndexColumn;
import com.speedment.orm.config.model.Project;
import com.speedment.orm.config.model.ProjectManager;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
public class ConfigEntityFactoryImpl implements ConfigEntityFactory {

    private final ConcurrentMap<Class<? extends ConfigEntity<?, ?, ?>>, Supplier<? extends ConfigEntity<?, ?, ?>>> factoryMap;

    public ConfigEntityFactoryImpl() {
        this.factoryMap = new ConcurrentHashMap<>();
        init();
    }

    protected void init() {
        registerFactoryClass(ProjectManager.class, ProjectManagerImpl::new);
        registerFactoryClass(Project.class, ProjectImpl::new);
        registerFactoryClass(Dbms.class, DbmsImpl::new);
        registerFactoryClass(Schema.class, SchemaImpl::new);
        registerFactoryClass(Table.class, TableImpl::new);
        registerFactoryClass(Column.class, ColumnImpl::new);
        registerFactoryClass(Index.class, IndexImpl::new);
        registerFactoryClass(IndexColumn.class, IndexColumnImpl::new);
        registerFactoryClass(ForeignKey.class, ForeignKeyImpl::new);
        registerFactoryClass(ForeignKeyColumn.class, ForeignKeyColumnImpl::new);
    }

    // Using a method instead of just put(), we can correlate the type of the Key and Value.
    private <T extends ConfigEntity<T, ?, ?>> void registerFactoryClass(Class<T> clazz, Supplier<? extends T> supplier) {
        factoryMap.put(clazz, supplier);
    }

    @Override
    public <T extends ConfigEntity<T, ?, ?>> T newOf(Class<T> interfaceMainClass) {
        return (T) factoryMap.getOrDefault(interfaceMainClass, () -> {
            throw new IllegalArgumentException("Unable to instanciate class of " + interfaceMainClass.getName() + ", know " + factoryMap.keySet());
        }).get();
    }

}
