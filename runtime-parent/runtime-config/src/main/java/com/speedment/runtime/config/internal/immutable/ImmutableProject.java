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
package com.speedment.runtime.config.internal.immutable;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.speedment.runtime.config.ProjectUtil.*;
import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableProject extends ImmutableDocument implements Project {

    private final boolean enabled;
    private final String id;
    private final String name;
    private final String companyName;
    private final String packageName;
    private final String packageLocation;
    private final Path configPath;
    
    private final List<Dbms> dbmses;
    private final Map<String, ImmutableTable> tablesByName;

    public ImmutableProject(Map<String, Object> project) {
        super(project);
        final Project prototype = Project.create(project);
        this.enabled         = prototype.isEnabled();
        this.id              = prototype.getId();
        this.name            = prototype.getName();
        this.companyName     = prototype.getCompanyName();
        this.packageName     = prototype.getPackageName().orElse(null);
        this.packageLocation = prototype.getPackageLocation();
        this.configPath      = prototype.getConfigPath().orElse(null);
        this.dbmses = unmodifiableList(super.children(DBMSES, ImmutableDbms::new).collect(toList()));

        this.tablesByName = DocumentDbUtil.traverseOver(this, ImmutableTable.class)
            .collect(toMap(
                table -> DocumentUtil.relativeName(table, Dbms.class, DATABASE_NAME),
                Function.identity()
            ));
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public Optional<String> getPackageName() {
        return Optional.ofNullable(packageName);
    }

    @Override
    public String getPackageLocation() {
        return packageLocation;
    }

    @Override
    public Optional<Path> getConfigPath() {
        return Optional.ofNullable(configPath);
    }

    @Override
    public Stream<Dbms> dbmses() {
        return dbmses.stream();
    }

    @Override
    public ImmutableTable findTableByName(String fullName) {
        final ImmutableTable table = tablesByName.get(fullName);
        
        if (table == null) {
            throw new SpeedmentConfigException(
                "Unable to find table '" + 
                fullName + 
                "' in immutable config model."
            );
        }
        
        return table;
    }

}