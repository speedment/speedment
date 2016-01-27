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
import com.speedment.config.db.Dbms;
import com.speedment.config.db.ForeignKey;
import com.speedment.config.db.Index;
import com.speedment.config.db.Project;
import com.speedment.config.db.Schema;
import com.speedment.config.db.Table;
import com.speedment.internal.ui.config.AbstractDocumentProperty;
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
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class DocumentPropertyComponentImpl extends Apache2AbstractComponent implements DocumentPropertyComponent {
    
    private final Branch root;
    
    private final static Constructor<?>
        DEFAULT_CONSTRUCTOR = parent -> {
            final AbstractDocumentProperty<?> castedParent =
                (AbstractDocumentProperty<?>) parent;
            return new DefaultDocumentProperty(castedParent, null);
        };

    public DocumentPropertyComponentImpl(Speedment speedment) {
        super(speedment);
        root = new Branch(DEFAULT_CONSTRUCTOR);
    }

    @Override
    public AbstractComponent initialize() {
        
        System.out.println("Initializing DocumentPropertyComponenet.....");
        root.find().set((parent) -> new ProjectProperty());
        root.find(DBMSES).set(parent -> new DbmsProperty((Project) parent));
        root.find(SCHEMAS).set(parent -> new SchemaProperty((Dbms) parent));
        root.find(TABLES).set(parent -> new TableProperty((Schema) parent));
        root.find(COLUMNS).set(parent -> new ColumnProperty((Table) parent));
        root.find(PRIMARY_KEY_COLUMNS).set(parent -> new PrimaryKeyColumnProperty((Table) parent));
        root.find(INDEXES).set(parent -> new IndexProperty((Table) parent));
        root.find(INDEX_COLUMNS).set(parent -> new IndexColumnProperty((Index) parent));
        root.find(FOREIGN_KEYS).set(parent -> new ForeignKeyProperty((Table) parent));
        root.find(FOREIGN_KEY_COLUMNS).set(parent -> new ForeignKeyColumnProperty((ForeignKey) parent));
        
        return super.initialize();
    }
    
    @Override
    public <PARENT extends DocumentProperty> void setConstructor(Constructor<PARENT> constructor, String... keyPath) {
        System.out.println("Setting constructor to use on key path: '" + Arrays.asList(keyPath) + "'.");
        root.find(keyPath).set(constructor);
    }

    @Override
    public Constructor<?> getConstructor(String... keyPath) {
        return root.find(keyPath).get();
    }
    
    private final static class Branch {
        
        private final ConstructorHolder holder;
        private final Map<String, Branch> children;
        
        private Branch(Constructor<?> constructor) {
            this.holder   = new ConstructorHolder(constructor);
            this.children = new ConcurrentHashMap<>();
        }
        
        public ConstructorHolder find(String... keyPath) {
            return find(0, keyPath);
        }

        private ConstructorHolder find(int i, String... keyPath) {
            // If we are at the last key, return our constructor.
            if (i == keyPath.length) {
                return holder;
                
            // If there are still keys in the path, find the one mentioned in
            // the i:th key and recurse into that branch
            } else if (i < keyPath.length) {
                return children.computeIfAbsent(
                    keyPath[i], k -> new Branch(parent -> {
                        final AbstractDocumentProperty<?> castedParent =
                            (AbstractDocumentProperty<?>) parent;
                        return new DefaultDocumentProperty(castedParent, k);
                    })
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