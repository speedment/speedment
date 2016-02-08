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
import com.speedment.internal.core.config.db.mutator.DocumentMutator;
import com.speedment.internal.core.config.db.mutator.ProjectMutator;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.regex.Pattern;
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

    final String 
            PACKAGE_NAME = "packageName",
            PACKAGE_LOCATION = "packageLocation",
            CONFIG_PATH = "configPath",
            DBMSES = "dbmses";
    
    final String 
            DEFAULT_PACKAGE_NAME = "com.speedment.example",
            DEFAULT_PACKAGE_LOCATION = "src/main/java/",
            DEFAULT_PROJECT_NAME = Project.class.getSimpleName();
    
           

    /**
     * Returns the name of the generated package where this project will be
     * located.
     *
     * @return the name of the generated package
     */
    default String getPackageName() {
        return getAsString(PACKAGE_NAME).orElse(DEFAULT_PACKAGE_NAME);
    }

    /**
     * Returns where the code generated for this project will be located.
     *
     * @return the package location
     */
    default String getPackageLocation() {
        return getAsString(PACKAGE_LOCATION).orElse(DEFAULT_PACKAGE_LOCATION);
    }

    /**
     * Returns the path to the configuration file for this project. The
     * path may not be set at the time of the calling and the result may
     * therefore be {@code empty}.
     *
     * @return the path to the configuration file
     */
    default Optional<Path> getConfigPath() {
        return getAsString(CONFIG_PATH).map(Paths::get);
    }
    
    /**
     * Return a {@code Stream} of all dbmses that exists in this Project.
     *
     * @return all dbmses
     */
    Stream<? extends Dbms> dbmses();

//    /**
//     * Return a {@link Stream} of all dbmses that exists in this Project.
//     *
//     * @return a {@link Stream} of all dbmses that exists in this Project
//     */
//    default Stream<? extends Dbms> dbmses() {
//        return children(DBMSES, dbmsConstructor());
//    }
//
//    /**
//     * Creates and adds a new {@link Dbms} as a child to this node in the
//     * configuration tree.
//     *
//     * @return the newly added child
//     */
//    default Dbms addNewDbms() {
//        return dbmsConstructor().apply(this, newDocument(this, DBMSES));
//    }
//
//    BiFunction<Project, Map<String, Object>, ? extends Dbms> dbmsConstructor();

    @Override
    default Class<Project> mainInterface() {
        return Project.class;
    }

    @Override
    default ProjectMutator mutator() {
        return DocumentMutator.of(this);
    }

    static final Pattern SPLIT_PATTERN = Pattern.compile("\\."); // Pattern is immutable and therefor thread safe
    
    /**
     * Locates the table with the specified full name in this project. The name
     * should be separated by dots (.) and have exactly three parts; the name of
     * the {@link Dbms}, the name of the {@link Schema} and the name of the
     * {@link Table}. If the inputed name is malformed, an
     * {@code IllegalArgumentException} will be thrown.
     * <p>
     * Example of a valid name: {@code db0.socialnetwork.image}
     * <p>
     * If no table matching the specified name was found, an exception is also
     * thrown.
     *
     * @param fullName the full name of the table
     * @return the table found
     */
    

    default Table findTableByName(String fullName) {

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
       
        return dbmses()
            .filter(d -> dbmsName.equals(d.getName()))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "Could not find dbms: '" + dbmsName + "'."))
            .schemas().filter(s -> schemaName.equals(s.getName())).findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "Could not find schema: '" + schemaName + "'."))
            .tables().filter(t -> tableName.equals(t.getName())).findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "Could not find table: '" + tableName + "'."));
    }

}
