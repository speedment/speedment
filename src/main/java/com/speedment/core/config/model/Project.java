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
package com.speedment.core.config.model;

import com.speedment.core.annotations.Api;
import com.speedment.core.config.model.aspects.Parent;
import com.speedment.core.config.model.aspects.Child;
import com.speedment.core.config.model.impl.ProjectImpl;
import groovy.lang.Closure;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = 0)
public interface Project extends ConfigEntity, Parent<Dbms>, Child<ProjectManager> {

    enum Holder {

        HOLDER;
        private Supplier<Project> provider = () -> new ProjectImpl();
    }

    static void setSupplier(Supplier<Project> provider) {
        Holder.HOLDER.provider = provider;
    }

    static Project newProject() {
        return Holder.HOLDER.provider.get();
    }

    @Override
    @SuppressWarnings("unchecked")
    default Class<Project> getInterfaceMainClass() {
        return Project.class;
    }

    @Override
    default Class<ProjectManager> getParentInterfaceMainClass() {
        return ProjectManager.class;
    }

    default Dbms addNewDbms() {
        final Dbms e = Dbms.newDbms();
        add(e);
        return e;
    }

    @External(type = String.class)
    String getPacketName();

    @External(type = String.class)
    void setPacketName(String packetName);

    @External(type = String.class)
    String getPacketLocation();

    @External(type = String.class)
    void setPacketLocation(String packetLocation);

    Optional<Path> getConfigPath();

    void setConfigPath(Path configPath);

    // Groovy
    default Dbms dbms(Closure<?> c) {
        return ConfigEntityUtil.groovyDelegatorHelper(c, this::addNewDbms);
    }

    default Table findTableByName(String fullName) {
        final String[] parts = fullName.split("\\.");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "fullName should consist of three parts separated by dots. These are dbms-name, schema-name and table-name."
            );
        }

        final String dbmsName = parts[0];
        final String schemaName = parts[1];
        final String tableName = parts[2];

        return stream().filter(d -> dbmsName.equals(d.getName())).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Could not find dbms: '" + dbmsName + "'."))
                .stream().filter(s -> schemaName.equals(s.getName())).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Could not find schema: '" + schemaName + "'."))
                .stream().filter(t -> tableName.equals(t.getName())).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Could not find table: '" + tableName + "'."));
    }
}
