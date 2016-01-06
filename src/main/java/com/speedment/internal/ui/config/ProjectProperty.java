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
package com.speedment.internal.ui.config;

import com.speedment.Speedment;
import com.speedment.config.db.Dbms;
import com.speedment.config.PluginData;
import com.speedment.config.db.Project;
import com.speedment.config.ProjectManager;
import com.speedment.config.db.Table;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Parent;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.utils.ConfigUtil;
import com.speedment.internal.ui.property.StringPropertyItem;
import groovy.lang.Closure;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.util.Collections.newSetFromMap;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import static javafx.collections.FXCollections.observableSet;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import org.controlsfx.control.PropertySheet;

/**
 *
 * @author Emil Forslund
 */
public final class ProjectProperty extends AbstractParentProperty<Project, Child<Project>> implements Project, ChildHelper<Project, ProjectManager> {
    
    private final static Path DEFAULT_CONFIG_PATH = Paths.get("src/main/groovy/speedment.groovy");

    private final ObservableSet<Dbms> dbmsChildren;
    private final ObservableSet<PluginData> pluginDataChildren;
    private final StringProperty packageName;
    private final StringProperty packageLocation;
    
    private ProjectManager parent;
    private Path configPath;
    
    public ProjectProperty(Speedment speedment) {
        super(speedment);
        dbmsChildren       = observableSet(newSetFromMap(new ConcurrentHashMap<>()));
        pluginDataChildren = observableSet(newSetFromMap(new ConcurrentHashMap<>()));
        packageName        = new SimpleStringProperty();
        packageLocation    = new SimpleStringProperty();
        setDefaults();
    }
    
    public ProjectProperty(Speedment speedment, Project prototype) {
        super(speedment, prototype);
        dbmsChildren       = copyChildrenFrom(prototype, Dbms.class, DbmsProperty::new);
        pluginDataChildren = copyChildrenFrom(prototype, PluginData.class, PluginDataProperty::new);
        packageName        = new SimpleStringProperty(prototype.getPackageName());
        packageLocation    = new SimpleStringProperty(prototype.getPackageLocation());
        configPath         = prototype.getConfigPath().orElse(DEFAULT_CONFIG_PATH);
    }
    
    private void setDefaults() {
        setPackageLocation("src/main/java");
        setPackageName("com.company.speedment.test");
        setConfigPath(DEFAULT_CONFIG_PATH);
    }
    
    public void loadSettingsFrom(Project prototype) {
        setName(prototype.getName());
        setEnabled(prototype.isEnabled());
        setExpanded(prototype.isExpanded());
        
        packageName.setValue(prototype.getPackageName());
        packageLocation.setValue(prototype.getPackageLocation());
        configPath = prototype.getConfigPath().orElse(DEFAULT_CONFIG_PATH);
        
        dbmsChildren.clear();
        dbmsChildren.addAll(copyChildrenFrom(prototype, Dbms.class, DbmsProperty::new));
        
        pluginDataChildren.clear();
        pluginDataChildren.addAll(copyChildrenFrom(prototype, PluginData.class, PluginDataProperty::new));
    }
    
    @Override
    protected Stream<PropertySheet.Item> guiVisibleProperties() {
        return Stream.of(
            new StringPropertyItem(
                packageName,
                "Package Name",
                "The name of the package to place all generated files in. This should be a fully qualified java package name."
            ),
            new StringPropertyItem(
                packageLocation,
                "Package Location",
                "The folder to store all generated files in. This should be a relative name from the working directory."
            )
        );
    }
    
    @Override
    public Optional<ProjectManager> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setParent(Parent<?> parent) {
        if (parent instanceof ProjectManager) {
            this.parent = (ProjectManager) parent;
        } else {
            throw wrongParentClass(parent.getClass());
        }
    }
    
    @Override
    public ObservableList<Child<Project>> children() {
        return createChildrenView(dbmsChildren, pluginDataChildren);
    }

    @Override
    public String getPackageName() {
        return packageName.get();
    }

    @Override
    public void setPackageName(String packageName) {
        this.packageName.set(packageName);
    }
    
    public StringProperty packageNameProperty() {
        return packageName;
    }

    @Override
    public String getPackageLocation() {
        return packageLocation.get();
    }

    @Override
    public void setPackageLocation(String packageLocation) {
        this.packageLocation.set(packageLocation);
    }
    
    public StringProperty packageLocationProperty() {
        return packageLocation;
    }

    @Override
    public Optional<Path> getConfigPath() {
        return Optional.ofNullable(configPath);
    }

    @Override
    public void setConfigPath(Path configPath) {
        this.configPath = configPath;
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
    public Dbms addNewDbms() {
        final Dbms dbms = new DbmsProperty(getSpeedment());
        addDbms(dbms);
        return dbms;
    }

    @Override
    public PluginData addNewPluginData() {
        final PluginData pluginData = new PluginDataProperty(getSpeedment());
        addPluginData(pluginData);
        return pluginData;
    }

    @Override
    public Dbms dbms(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewDbms());
    }

    @Override
    public PluginData pluginData(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewPluginData());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<? extends Child<Project>> add(Child<Project> child) throws IllegalStateException {
        requireNonNull(child);
        
        if (child instanceof Dbms) {
            return addDbms((Dbms) child);
        } else if (child instanceof PluginData) {
            return addPluginData((PluginData) child);
        } else {
            throw wrongChildTypeException(child.getClass());
        }
    }
    
    public Optional<Dbms> addDbms(Dbms child) {
        requireNonNull(child);
        return dbmsChildren.add(child) ? Optional.empty() : Optional.of(child);
    }
    
    public Optional<PluginData> addPluginData(PluginData child) {
        requireNonNull(child);
        return pluginDataChildren.add(child) ? Optional.empty() : Optional.of(child);
    }
    
    public Optional<Dbms> removeDbms(Dbms child) {
        requireNonNull(child);
        if (dbmsChildren.remove(child)) {
            child.setParent(null);
            return Optional.of(child);
        } else return Optional.empty();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Optional<? extends Child<Project>> remove(Child<Project> child) {
        requireNonNull(child);
        
        if (child instanceof Dbms) {
            return removeDbms((Dbms) child);
        } else if (child instanceof PluginData) {
            return removePluginData((PluginData) child);
        } else {
            throw wrongChildTypeException(child.getClass());
        }
    }
    
    public Optional<PluginData> removePluginData(PluginData child) {
        requireNonNull(child);
        if (pluginDataChildren.remove(child)) {
            child.setParent(null);
            return Optional.of(child);
        } else return Optional.empty();
    }

    @Override
    public Stream<? extends Child<Project>> stream() {
        return Stream.concat(
            dbmsChildren.stream().sorted(COMPARATOR),
            pluginDataChildren.stream().sorted(COMPARATOR)
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Child<Project>> Stream<T> streamOf(Class<T> childType) {
        requireNonNull(childType);
        
        if (Dbms.class.isAssignableFrom(childType)) {
            return (Stream<T>) dbmsChildren.stream().sorted(COMPARATOR);
        } else if (PluginData.class.isAssignableFrom(childType)) {
            return (Stream<T>) pluginDataChildren.stream().sorted(COMPARATOR);
        } else {
            throw wrongChildTypeException(childType);
        }
    }
    
    @Override
    public int count() {
        return dbmsChildren.size() + pluginDataChildren.size();
    }

    @Override
    public int countOf(Class<? extends Child<Project>> childType) {
        requireNonNull(childType);
        
        if (Dbms.class.isAssignableFrom(childType)) {
            return dbmsChildren.size();
        } else if (PluginData.class.isAssignableFrom(childType)) {
            return pluginDataChildren.size();
        } else {
            throw wrongChildTypeException(childType);
        }
    }

    @Override
    public <T extends Child<Project>> T find(Class<T> childType, String name) throws SpeedmentException {
        requireNonNull(childType);
        requireNonNull(name);
        
        final Stream<? extends Child<Project>> stream;
        if (Dbms.class.isAssignableFrom(childType)) {
            stream = dbmsChildren.stream();
        } else if (PluginData.class.isAssignableFrom(childType)) {
            stream = pluginDataChildren.stream();
        } else {
            throw wrongChildTypeException(childType);
        }
        
        @SuppressWarnings("unchecked")
        final T result = (T) stream.filter(child -> name.equals(child.getName()))
            .findAny().orElseThrow(() -> noChildWithNameException(childType, name));
        
        return result;
    }
}