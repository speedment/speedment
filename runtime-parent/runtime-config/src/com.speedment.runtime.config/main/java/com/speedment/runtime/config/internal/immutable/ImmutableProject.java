/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.mapstream.MapStream;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.config.internal.ProjectImpl;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.speedment.runtime.config.util.DocumentUtil.Name.DATABASE_NAME;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableProject extends ImmutableDocument implements Project {

    private final transient boolean enabled;
    private final transient String id;
    private final transient String name;
    private final transient String companyName;
    private final transient Optional<String> packageName;
    private final transient String packageLocation;
    private final transient Optional<Path> configPath;
    
    private final transient List<ImmutableDbms> dbmses;
    private final transient Map<String, ImmutableTable> tablesByName;

    ImmutableProject(Map<String, Object> project) {
        super(project);
        
        final Project prototype = new ProjectImpl(project);

        this.enabled         = prototype.isEnabled();
        this.id              = prototype.getId();
        this.name            = prototype.getName();
        this.companyName     = prototype.getCompanyName();
        this.packageName     = prototype.getPackageName();
        this.packageLocation = prototype.getPackageLocation();
        this.configPath      = prototype.getConfigPath();
        
        this.dbmses = unmodifiableList(super.children(DBMSES, ImmutableDbms::new).collect(toList()));
        
        this.tablesByName = MapStream.fromValues(
            DocumentDbUtil.traverseOver(this, ImmutableTable.class),
            table -> DocumentUtil.relativeName(table, Dbms.class, DATABASE_NAME)
        ).toMap();
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
        return packageName;
    }

    @Override
    public String getPackageLocation() {
        return packageLocation;
    }

    @Override
    public Optional<Path> getConfigPath() {
        return configPath;
    }

    @Override
    public Stream<ImmutableDbms> dbmses() {
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
    
    public static ImmutableProject wrap(Project project) {
        return new ImmutableProject(project.getData());
    }
}