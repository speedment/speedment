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
package com.speedment.internal.core.config.db.immutable;

import com.speedment.config.db.Dbms;
import com.speedment.config.db.Project;
import com.speedment.config.db.Table;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.util.document.DocumentDbUtil;
import com.speedment.internal.util.document.DocumentUtil;
import com.speedment.stream.MapStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.util.Collections.unmodifiableSet;
import java.util.Optional;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableProject extends ImmutableDocument implements Project {

    private final boolean enabled;
    private final String name;
    private final String packageName;
    private final String packageLocation;
    private final Optional<Path> configPath;
    
    private final Set<ImmutableDbms> dbmses;
    private final Map<String, ImmutableTable> tablesByName;

    ImmutableProject(Map<String, Object> project) {
        super(project);

        this.enabled         = (boolean) project.get(ENABLED);
        this.name            = (String) project.get(NAME);
        this.packageName     = (String) project.get(PACKAGE_NAME);
        this.packageLocation = (String) project.get(PACKAGE_LOCATION);
        this.configPath      = Optional.ofNullable((String) project.get(CONFIG_PATH)).map(Paths::get);
        
        this.dbmses = unmodifiableSet(Project.super.dbmses().map(ImmutableDbms.class::cast).collect(toSet()));
        this.tablesByName = MapStream.fromValues(
            DocumentDbUtil.traverseOver(this, ImmutableTable.class),
            table -> DocumentUtil.relativeName(table, Dbms.class)
        ).toMap();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPackageName() {
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
    public BiFunction<Project, Map<String, Object>, ? extends Dbms> dbmsConstructor() {
        return (parent, map) -> new ImmutableDbms((ImmutableProject) parent, map);
    }

    @Override
    public Stream<ImmutableDbms> dbmses() {
        return dbmses.stream();
    }

    @Override
    public ImmutableTable findTableByName(String fullName) {
        final ImmutableTable table = tablesByName.get(fullName);
        
        if (table == null) {
            throw new SpeedmentException(
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