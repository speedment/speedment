/*
 * Copyright 2016 Speedment, Inc..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.component.DocumentPropertyComponent;
import com.speedment.internal.ui.config.ColumnProperty;
import com.speedment.internal.ui.config.DbmsProperty;
import com.speedment.internal.ui.config.DefaultDocumentProperty;
import com.speedment.internal.ui.config.DocumentProperty;
import com.speedment.internal.ui.config.ForeignKeyColumnProperty;
import com.speedment.internal.ui.config.ForeignKeyProperty;
import com.speedment.internal.ui.config.IndexColumnProperty;
import com.speedment.internal.ui.config.IndexProperty;
import com.speedment.internal.ui.config.PrimaryKeyColumnProperty;
import com.speedment.internal.ui.config.ProjectProperty;
import com.speedment.internal.ui.config.SchemaProperty;
import com.speedment.internal.ui.config.TableProperty;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Emil Forslund
 */
public final class DocumentPropertyComponentImpl extends Apache2AbstractComponent implements DocumentPropertyComponent {
    
    private final Branch root;

    public DocumentPropertyComponentImpl(Speedment speedment) {
        super(speedment);
        root = new Branch(DefaultDocumentProperty::new);
    }

    @Override
    public AbstractComponent initialize() {
        
        root.find().set((parent, data) -> new ProjectProperty(data));
        root.find(DBMSES).set(DbmsProperty::new);
        root.find(SCHEMAS).set(SchemaProperty::new);
        root.find(TABLES).set(TableProperty::new);
        root.find(COLUMNS).set(ColumnProperty::new);
        root.find(PRIMARY_KEY_COLUMNS).set(PrimaryKeyColumnProperty::new);
        root.find(INDEXES).set(IndexProperty::new);
        root.find(INDEX_COLUMNS).set(IndexColumnProperty::new);
        root.find(FOREIGN_KEYS).set(ForeignKeyProperty::new);
        root.find(FOREIGN_KEY_COLUMNS).set(ForeignKeyColumnProperty::new);
        
        return super.initialize();
    }
    
    @Override
    public <PARENT extends DocumentProperty> void setConstructor(Constructor<PARENT> constructor, String... keyPath) {
        root.find(keyPath).set(constructor);
    }

    @Override
    public Constructor<?> getConstructor(String... keyPath) {
        return root.find(keyPath).get();
    }
    
    private final static class Branch {
        
        private final ConstructorHolder constructor;
        private final Map<String, Branch> children;
        
        private Branch(Constructor<?> constructor) {
            this.constructor = new ConstructorHolder(constructor);
            this.children    = new ConcurrentHashMap<>();
        }
        
        public ConstructorHolder find(String... keyPath) {
            return find(-1, keyPath);
        }

        private ConstructorHolder find(int i, String... keyPath) {
            // If we are at the last key, return our constructor.
            if (i == keyPath.length - 1) {
                return constructor;
                
            // If there are still keys in the path, find the one mentioned in
            // the i:th key and recurse into that branch
            } else if (i < keyPath.length) {
                return children.computeIfAbsent(
                    keyPath[i], k -> new Branch(DefaultDocumentProperty::new)
                ).find(i + 1, keyPath);
                
            // If we are out of keys, something is terrible wrong...
            } else {
                throw new UnsupportedOperationException(
                    "iterator is outside the specified keyPath."
                );
            }
        }
    }
    
    private final static class ConstructorHolder {
        
        private Constructor<?> constructor;
        
        private ConstructorHolder(Constructor<?> constructor) {
            this.constructor = requireNonNull(constructor);
        }
        
        public <PARENT extends DocumentProperty> void set(Constructor<PARENT> constructor) {
            this.constructor = requireNonNull(constructor);
        }
        
        public Constructor<?> get() {
            return constructor;
        }
    }
}