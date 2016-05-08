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
package com.speedment.tool.component;

import com.speedment.runtime.Speedment;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.ForeignKey;
import com.speedment.runtime.config.Index;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.internal.component.InternalOpenSourceComponent;
import com.speedment.tool.config.AbstractDocumentProperty;
import com.speedment.tool.config.ColumnProperty;
import com.speedment.tool.config.DbmsProperty;
import com.speedment.tool.config.DefaultDocumentProperty;
import com.speedment.tool.config.ForeignKeyColumnProperty;
import com.speedment.tool.config.ForeignKeyProperty;
import com.speedment.tool.config.IndexColumnProperty;
import com.speedment.tool.config.IndexProperty;
import com.speedment.tool.config.PrimaryKeyColumnProperty;
import com.speedment.tool.config.ProjectProperty;
import com.speedment.tool.config.SchemaProperty;
import com.speedment.tool.config.TableProperty;
import com.speedment.runtime.internal.util.ImmutableListUtil;
import com.speedment.runtime.license.Software;
import com.speedment.tool.config.DocumentProperty;
import java.util.List;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class DocumentPropertyComponentImpl extends InternalOpenSourceComponent implements DocumentPropertyComponent {

    private final Branch root;

    private final static Constructor<?> DEFAULT_CONSTRUCTOR = parent -> {
        final AbstractDocumentProperty<?> castedParent
            = (AbstractDocumentProperty<?>) parent;
        return new DefaultDocumentProperty(castedParent, null);
    };

    public DocumentPropertyComponentImpl(Speedment speedment) {
        super(speedment);
        root = new Branch(DEFAULT_CONSTRUCTOR);

        root.find(ImmutableListUtil.of()).set(parent -> new ProjectProperty());
        root.find(DBMSES).set(parent -> new DbmsProperty((Project) parent));
        root.find(SCHEMAS).set(parent -> new SchemaProperty((Dbms) parent));
        root.find(TABLES).set(parent -> new TableProperty((Schema) parent));
        root.find(COLUMNS).set(parent -> new ColumnProperty((Table) parent));
        root.find(PRIMARY_KEY_COLUMNS).set(parent -> new PrimaryKeyColumnProperty((Table) parent));
        root.find(INDEXES).set(parent -> new IndexProperty((Table) parent));
        root.find(INDEX_COLUMNS).set(parent -> new IndexColumnProperty((Index) parent));
        root.find(FOREIGN_KEYS).set(parent -> new ForeignKeyProperty((Table) parent));
        root.find(FOREIGN_KEY_COLUMNS).set(parent -> new ForeignKeyColumnProperty((ForeignKey) parent));
    }

    public DocumentPropertyComponentImpl(Speedment speedment, DocumentPropertyComponentImpl template) {
        this(speedment);
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

    @Override
    public Stream<Software> getDependencies() {
        return Stream.empty();
    }

    private final static class Branch {

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

    @Override
    public DocumentPropertyComponent defaultCopy(Speedment speedment) {
        return new DocumentPropertyComponentImpl(speedment, this);
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
