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
package com.speedment.internal.core.config.db.immutable;

import com.speedment.internal.core.config.*;
import com.speedment.Speedment;
import com.speedment.config.db.Dbms;
import com.speedment.config.PluginData;
import com.speedment.config.db.Project;
import com.speedment.config.ProjectManager;
import com.speedment.config.db.Table;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Nameable;
import com.speedment.config.aspects.Parent;
import com.speedment.exception.SpeedmentException;
import groovy.lang.Closure;
import java.nio.file.Path;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;
import static com.speedment.internal.core.config.db.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;
import static com.speedment.internal.core.config.utils.ConfigUtil.thereIsNo;
import static com.speedment.internal.util.Cast.castOrFail;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class ImmutableProject extends ImmutableAbstractNamedConfigEntity implements Project, ImmutableParentHelper<Child<Project>> {

    private final Speedment speedment;
    private final Optional<ProjectManager> parent; // Rare occation of valid use of Optional as member
    private final ChildHolder<Dbms> dbmsChildren;
    private final ChildHolder<PluginData> pluginDataChildren;
    private final String packageName, packageLocation;
    private final Optional<Path> configPath;
    private final Map<String, Table> tableNameMap;

    public ImmutableProject(Project project) {
        this(null, project);
    }

    public ImmutableProject(ProjectManager parent, Project project) {
        super(requireNonNull(project).getName(), project.isExpanded(), project.isEnabled());

        // Menbers
        this.speedment = project.getSpeedment();
        this.packageName = project.getPackageName();
        this.packageLocation = project.getPackageLocation();
        this.configPath = project.getConfigPath();
        this.parent = Optional.ofNullable(parent);

        // Children
        @SuppressWarnings("unchecked")
        final Class<Child<Project>> dbmsClass = (Class<Child<Project>>) (Class) Dbms.class;
        @SuppressWarnings("unchecked")
        final ChildHolder<Dbms> dbmsCh = (ChildHolder<Dbms>) (ChildHolder) childHolderOf(
                dbmsClass,
                project.streamOf(Dbms.class).map(p -> new ImmutableDbms(this, p)));
        dbmsChildren = dbmsCh;

        @SuppressWarnings("unchecked")
        final Class<Child<Project>> pluginClass = (Class<Child<Project>>) (Class) PluginData.class;
        @SuppressWarnings("unchecked")
        final ChildHolder<PluginData> pluginDataCh = (ChildHolder<PluginData>) (ChildHolder) childHolderOf(
                pluginClass,
                project.streamOf(PluginData.class).map(p -> new ImmutablePluginData(this, p)));

        pluginDataChildren = pluginDataCh;

        // Special
        this.tableNameMap = new HashMap<>();
        traverseOver(Table.class).forEach(t -> {
            tableNameMap.put(t.getRelativeName(Dbms.class), t);
        });

        // resolve all dependencies that needs to be done post tree construction
        traverse().forEach(node -> {
            if (node instanceof ImmutableAbstractConfigEntity) {
                castOrFail(node, ImmutableAbstractConfigEntity.class).resolve();
            }
        });
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public void setPackageName(String packetName) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public String getPackageLocation() {
        return packageLocation;
    }

    @Override
    public void setPackageLocation(String packetLocation) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public ChildHolder<Child<Project>> getChildren() {
        throw new IllegalStateException(Project.class.getSimpleName() + " has several child types");
    }

    @Override
    public void setParent(Parent<?> parent) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<ProjectManager> getParent() {
        return parent;
    }

    @Override
    public Optional<Path> getConfigPath() {
        return configPath;
    }

    @Override
    public void setConfigPath(Path configPath) {
        throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Speedment getSpeedment() {
        return speedment;
    }

    @Override
    public Stream<? extends Child<Project>> stream() {
        return Stream.concat(
            dbmsChildren.stream().sorted(Nameable.COMPARATOR), 
            pluginDataChildren.stream().sorted(Nameable.COMPARATOR)
        );
    }

    @Override
    public <T extends Child<Project>> Stream<T> streamOf(Class<T> childClass) {
        if (Dbms.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) dbmsChildren.stream().sorted(Nameable.COMPARATOR);
            return result;
        } else if (PluginData.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) pluginDataChildren.stream().sorted(Nameable.COMPARATOR);
            return result;
        } else {
            throw new SpeedmentException(
                    "'" + childClass.getName()
                    + "' is not a child to '"
                    + getClass().getSimpleName() + "'."
            );
        }
    }

    @Override
    public <T extends Child<Project>> T find(Class<T> childClass, String name) throws SpeedmentException {
        if (Dbms.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final T result = (T) dbmsChildren.find(name);
            return result;
        } else if (PluginData.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final T result = (T) pluginDataChildren.find(name);
            return result;
        } else {
            throw thereIsNo(childClass, this.getClass(), name).get();
        }
    }

    @Override
    public Table findTableByName(String fullName) {
        final Table table = tableNameMap.get(fullName);
        if (table == null) {
            throw new SpeedmentException("Unable to find table " + fullName);
        }
        return table;
    }

    @Override
    public Dbms dbms(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public PluginData pluginData(Closure<?> c) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Optional<Child<Project>> add(Child<Project> child) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public Dbms addNewDbms() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    @Override
    public PluginData addNewPluginData() {
        return throwNewUnsupportedOperationExceptionImmutable();
    }
}
