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
package com.speedment.internal.core.config.immutable;

import com.speedment.internal.core.config.*;
import com.speedment.Speedment;
import com.speedment.internal.core.config.aspects.ParentHelper;
import com.speedment.config.Dbms;
import com.speedment.config.Project;
import com.speedment.config.ProjectManager;
import com.speedment.config.Table;
import com.speedment.config.aspects.Parent;
import groovy.lang.Closure;
import java.nio.file.Path;
import java.util.Optional;
import java.util.regex.Pattern;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.immutableClassModification;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author pemi
 */
public final class ImmutableProjectImpl extends ImmutableAbstractNamedConfigEntity implements Project, ParentHelper<Dbms> {

    private final Speedment speedment;
    private final Optional<ProjectManager> parent; // Rare occation of valid use of Optional as member
    private final ChildHolder children;
    private final String packageName, packageLocation;
    private final Optional<Path> configPath;

    public ImmutableProjectImpl(ProjectManager parent, Project project) {
        super(requireNonNull(project).getName(), project.isEnabled());
        requireNonNull(parent);
        this.speedment = project.getSpeedment();
        this.packageName = project.getPackageName();
        this.packageLocation = project.getPackageLocation();
        this.configPath = project.getConfigPath();
        this.parent = Optional.of(parent);

        children = ImmutableChildHolderImpl.of(
            project.stream()
            .map(p -> new ImmutableDbmsImpl(this, p))
            .collect(toList())
        );

    }

    @Override
    public String getPackageName() {
        return packageName;
    }

    @Override
    public void setPackageName(String packetName) {
        immutableClassModification();
    }

    @Override
    public String getPackageLocation() {
        return packageLocation;
    }

    @Override
    public void setPackageLocation(String packetLocation) {
        immutableClassModification();
    }

    @Override
    public ChildHolder getChildren() {
        return children;
    }

    @Override
    public void setParent(Parent<?> parent) {
        immutableClassModification();
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
        immutableClassModification();
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

        return stream().filter(d -> dbmsName.equals(d.getName())).findAny()
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
    public Dbms dbms(Closure<?> c) {
        return immutableClassModification();
    }

    @Override
    public Optional<Dbms> add(Dbms child) {
        return immutableClassModification();
    }

    @Override
    public Dbms addNewDbms(Speedment speedment) {
        return immutableClassModification();
    }

    @Override
    public void setName(String name) {
        immutableClassModification();
    }

    @Override
    public void setEnabled(Boolean enabled) {
        immutableClassModification();
    }

}
