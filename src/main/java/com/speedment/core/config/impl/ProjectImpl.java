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
package com.speedment.core.config.impl;

import com.speedment.api.Speedment;
import com.speedment.core.config.impl.aspects.ParentHelper;
import com.speedment.api.config.Dbms;
import com.speedment.api.config.Project;
import com.speedment.api.config.ProjectManager;
import com.speedment.api.config.Table;
import com.speedment.api.config.aspects.Parent;
import com.speedment.core.config.impl.utils.ConfigUtil;
import com.speedment.util.Cast;
import groovy.lang.Closure;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public class ProjectImpl extends AbstractNamedConfigEntity implements Project, ParentHelper<Dbms> {

    private final Speedment speedment;
    private ProjectManager parent;
    private final ChildHolder children;
    private String packageName, packageLocation;
    private Path configPath;

    public ProjectImpl(Speedment speedment) {
        this.speedment = speedment;
        this.children  = new ChildHolder();
    }

    @Override
    protected void setDefaults() {
        setPackageLocation("src/main/java");
        setPackageName("com.company.speedment.test");
        //setConfigPath(Paths.get("src/main/groovy/speedment.groovy"));
        setConfigPath(Paths.get("src/main/groovy/speedment.groovy"));
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
    public ChildHolder getChildren() {
        return children;
    }

    @Override
    public void setParent(Parent<?> parent) {
        this.parent = Cast.orFail(parent, ProjectManager.class);
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

    @Override
    public Table findTableByName(String fullName) {
        final String[] parts = fullName.split("\\.");

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
        return ConfigUtil.groovyDelegatorHelper(c, () -> addNewDbms(getSpeedment()));
    }
}