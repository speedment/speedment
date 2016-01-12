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
package com.speedment.config.db;

import com.speedment.annotation.Api;
import com.speedment.config.Document;
import com.speedment.config.db.trait.HasChildren;
import com.speedment.config.db.trait.HasEnabled;
import com.speedment.config.db.trait.HasMainInterface;
import com.speedment.config.db.trait.HasMutator;
import com.speedment.config.db.trait.HasName;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.db.mutator.ProjectMutator;
import static com.speedment.internal.util.document.DocumentUtil.newDocument;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
@Api(version = "2.3")
public interface Project extends
        Document,
        HasEnabled,
        HasName,
        HasChildren,
        HasMainInterface,
        HasMutator<ProjectMutator> {

    final String PACKAGE_NAME = "packageName",
            PACKAGE_LOCATION = "packageLocation",
            CONFIG_PATH = "configPath",
            DBMSES = "dbmses";

    /**
     * Returns the name of the generated package where this project will be
     * located.
     *
     * @return the name of the generated package
     */
    default String getPackageName() {
        return getAsString(PACKAGE_NAME).orElse("com.speedment.example");
    }

    /**
     * Returns where the code generated for this project will be located.
     *
     * @return the package location
     */
    default String getPackageLocation() {
        return getAsString(PACKAGE_LOCATION).orElse("src/main/java/");
    }

    /**
     * Returns the path to the groovy configuration file for this project. The
     * path may not be set at the time of the calling and the result may
     * therefore be {@code empty}.
     *
     * @return the path to the groovy configuration file
     */
    default Optional<Path> getConfigPath() {
        return getAsString(CONFIG_PATH).map(Paths::get);
    }

    /**
     * Return a {@link Stream} of all dbmses that exists in this Project.
     *
     * @return a {@link Stream} of all dbmses that exists in this Project
     */
    default Stream<? extends Dbms> dbmses() {
        return children(DBMSES, dbmsConstructor());
    }

    default Dbms addNewDbms() {
        return dbmsConstructor().apply(this, newDocument(this, DBMSES));
    }

    BiFunction<Project, Map<String, Object>, ? extends Dbms> dbmsConstructor();

    @Override
    default Class<Project> mainInterface() {
        return Project.class;
    }

    @Override
    default ProjectMutator mutator() {
        return DocumentMutator.of(this);
    }

    @Override
    default String getName() throws SpeedmentException {
        final Optional<String> name = getAsString(NAME);

        if (name.isPresent()) {
            return name.get();
        } else {
            final String defaultName = Project.class.getSimpleName();
            getData().put(NAME, defaultName);
            return defaultName;
        }
    }

}
