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
package com.speedment.tool.config.internal.component;

import com.speedment.runtime.config.*;
import com.speedment.tool.config.*;
import com.speedment.tool.config.component.DocumentPropertyComponent;
import com.speedment.tool.config.component.DocumentPropertyComponentUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class DocumentPropertyComponentImpl implements DocumentPropertyComponent {

    private final Branch root;

    private static final Constructor<?> DEFAULT_CONSTRUCTOR = parent -> {
        final AbstractDocumentProperty<?> castedParent
            = (AbstractDocumentProperty<?>) parent;
        return new DefaultDocumentProperty(castedParent, null);
    };

    public DocumentPropertyComponentImpl() {
        root = new Branch(DEFAULT_CONSTRUCTOR);

        root.find(emptyList()).set(parent -> new ProjectProperty());
        root.find(DocumentPropertyComponentUtil.DBMSES).set(parent -> new DbmsProperty((Project) parent));
        root.find(DocumentPropertyComponentUtil.SCHEMAS).set(parent -> new SchemaProperty((Dbms) parent));
        root.find(DocumentPropertyComponentUtil.TABLES).set(parent -> new TableProperty((Schema) parent));
        root.find(DocumentPropertyComponentUtil.COLUMNS).set(parent -> new ColumnProperty((Table) parent));
        root.find(DocumentPropertyComponentUtil.PRIMARY_KEY_COLUMNS).set(parent -> new PrimaryKeyColumnProperty((Table) parent));
        root.find(DocumentPropertyComponentUtil.INDEXES).set(parent -> new IndexProperty((Table) parent));
        root.find(DocumentPropertyComponentUtil.INDEX_COLUMNS).set(parent -> new IndexColumnProperty((Index) parent));
        root.find(DocumentPropertyComponentUtil.FOREIGN_KEYS).set(parent -> new ForeignKeyProperty((Table) parent));
        root.find(DocumentPropertyComponentUtil.FOREIGN_KEY_COLUMNS).set(parent -> new ForeignKeyColumnProperty((ForeignKey) parent));
    }
    
    @Override
    public <PARENT extends DocumentProperty> void setConstructor(Constructor<PARENT> constructor, List<String> keyPath) {
        root.find(keyPath).set(constructor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <PARENT extends DocumentProperty> Constructor<PARENT> getConstructor(List<String> keyPath) {
        return (Constructor<PARENT>) root.find(keyPath).get();
    }

    private static final class Branch {

        private final ConstructorHolder holder;
        private final Map<String, Branch> children;

        private Branch(Constructor<?> constructor) {
            this.holder = new ConstructorHolder(constructor);
            this.children = new ConcurrentHashMap<>();
        }

        public ConstructorHolder find(List<String> keyPath) {
            return find(0, keyPath);
        }

        private ConstructorHolder find(int i, List<String> keyPath) {
            // If we are at the last key, return our constructor.
            if (i == keyPath.size()) {
                return holder;

                // If there are still keys in the path, find the one mentioned in
                // the i:th key and recurse into that branch
            } else if (i < keyPath.size()) {
                return children.computeIfAbsent(
                    keyPath.get(i), k -> new Branch(parent -> {
                    final AbstractDocumentProperty<?> castedParent
                        = (AbstractDocumentProperty<?>) parent;
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

        @Override
        public String toString() {
            return children.toString();
        }
    }

    private static final class ConstructorHolder {

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
