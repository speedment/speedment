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
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.db.ProjectImpl;
import com.speedment.internal.util.document.DocumentDbUtil;
import com.speedment.internal.util.document.DocumentUtil;
import static com.speedment.internal.util.document.DocumentUtil.toStringHelper;
import com.speedment.stream.MapStream;
import java.nio.file.Path;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public final class ImmutableProject extends ImmutableDocument implements Project {

    private final transient boolean enabled;
    private final transient String name;
    private final transient String packageName;
    private final transient String packageLocation;
    private final transient Optional<Path> configPath;
    
    private final transient List<ImmutableDbms> dbmses;
    private final transient Map<String, ImmutableTable> tablesByName;

    ImmutableProject(Map<String, Object> project) {
        super(project);
        
        final Project prototype = new ProjectImpl(project);

        this.enabled         = prototype.isEnabled();
        this.name            = prototype.getName();
        this.packageName     = prototype.getPackageName();
        this.packageLocation = prototype.getPackageName();
        this.configPath      = prototype.getConfigPath();
        
        this.dbmses = unmodifiableList(super.children(DBMSES, ImmutableDbms::new).collect(toList()));
        
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