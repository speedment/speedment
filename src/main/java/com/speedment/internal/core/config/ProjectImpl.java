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
package com.speedment.internal.core.config;

import com.speedment.Speedment;
import com.speedment.internal.core.config.aspects.ParentHelper;
import com.speedment.config.Dbms;
import com.speedment.config.PluginData;
import com.speedment.config.Project;
import com.speedment.config.ProjectManager;
import com.speedment.config.Table;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Parent;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.utils.ConfigUtil;
import static com.speedment.internal.core.config.utils.ConfigUtil.thereIsNo;
import com.speedment.internal.util.Cast;
import groovy.lang.Closure;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class ProjectImpl extends AbstractNamedConfigEntity implements Project, ParentHelper<Child<Project>> {

    private final Speedment speedment;
    private ProjectManager parent;
    private final ChildHolder<Dbms> dbmsChildren;
    private final ChildHolder<PluginData> pluginDataChildren;
    private String packageName, packageLocation;
    private Path configPath;

    public ProjectImpl(Speedment speedment) {
        this.speedment          = requireNonNull(speedment);
        this.dbmsChildren       = new ChildHolderImpl<>(Dbms.class);
        this.pluginDataChildren = new ChildHolderImpl<>(PluginData.class);
    }

    @Override
    protected void setDefaults() {
        setPackageLocation("src/main/java");
        setPackageName("com.company.speedment.test");
        setConfigPath(Paths.get("src/main/groovy/speedment.groovy"));
    }
    
    @Override
    public Dbms addNewDbms(Speedment speedment) {
        final Dbms e = Dbms.newDbms(speedment);
        add(e);
        return e;
    }

    @Override
    public PluginData addNewPluginData(Speedment speedment) {
        final PluginData e = PluginData.newPluginData(speedment);
        add(e);
        return e;
    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public void setPackageName(String packetName) {
        this.packageName = Objects.requireNonNull(packetName).toLowerCase();
    }

    @Override
    public String getPackageLocation() {
        return packageLocation;
    }

    @Override
    public void setPackageLocation(String packetLocation) {
        this.packageLocation = Objects.requireNonNull(packetLocation);
    }

    @Override
    public ChildHolder<Child<Project>> getChildren() {
        throw new IllegalStateException(Project.class.getSimpleName() + " has several child types");
    }

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.castOrFail(parent, ProjectManager.class);
    }

    @Override
    public Optional<ProjectManager> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public Optional<Path> getConfigPath() {
        return Optional.ofNullable(configPath);
    }

    @Override
    public void setConfigPath(Path configPath) {
        this.configPath = configPath;
    }

    @Override
    public Speedment getSpeedment() {
        return speedment;
    }

    private static final Pattern SPLIT_PATTERN = Pattern.compile("\\."); // Pattern is immutable and therefor thread safe

    @Override
    public Table findTableByName(String fullName) {
        final String[] parts = SPLIT_PATTERN.split(fullName);

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                "fullName should consist of three parts separated by dots. "
                + "These are dbms-name, schema-name and table-name."
            );
        }

        final String dbmsName = parts[0],
            schemaName = parts[1],
            tableName = parts[2];
       
        return streamOf(Dbms.class)
            .filter(d -> dbmsName.equals(d.getName()))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "Could not find dbms: '" + dbmsName + "'."))
            .stream().filter(s -> schemaName.equals(s.getName())).findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "Could not find schema: '" + schemaName + "'."))
            .stream().filter(t -> tableName.equals(t.getName())).findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "Could not find table: '" + tableName + "'."));
    }
    
    @Override
    public Stream<? extends Child<Project>> stream() {
        return Stream.of(dbmsChildren, pluginDataChildren)
            .flatMap(ChildHolder::stream);
    }
    
    @Override
    public <T extends Child<Project>> Stream<T> streamOf(Class<T> childClass) {
        if (Dbms.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) dbmsChildren.stream();
            return result;
        } else if (PluginData.class.equals(childClass)) {
            @SuppressWarnings("unchecked")
            final Stream<T> result = (Stream<T>) pluginDataChildren.stream();
            return result;
        } else {
            throw new SpeedmentException(
                "'" + childClass.getName() + 
                "' is not a child to '" + 
                getClass().getSimpleName() + "'."
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
        } else throw thereIsNo(childClass, this.getClass(), name).get();
    }
    
    @Override
    public Optional<Child<Project>> add(Child<Project> child) {
        if (Dbms.class.equals(child.getInterfaceMainClass())) {
            final Optional<Dbms> result = dbmsChildren.put(Cast.castOrFail(child, Dbms.class), this);
            @SuppressWarnings("unchecked")
            final Optional<Child<Project>> castedResult = (Optional<Child<Project>>) (Optional<?>) result;
            return castedResult;
        } else if (PluginData.class.equals(child.getInterfaceMainClass())) {
            final Optional<PluginData> result = pluginDataChildren.put(Cast.castOrFail(child, PluginData.class), this);
            @SuppressWarnings("unchecked")
            final Optional<Child<Project>> castedResult = (Optional<Child<Project>>) (Optional<?>) result;
            return castedResult;
        } else throw new IllegalArgumentException("Cannot add a " + child.getParentInterfaceMainClass());
    }
    
    @Override
    public Dbms dbms(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewDbms(getSpeedment()));
    }
    
    @Override
    public PluginData pluginData(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewPluginData(getSpeedment()));
    }
}