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
package com.speedment.runtime.config;

import com.speedment.runtime.config.exception.SpeedmentConfigException;
import com.speedment.runtime.config.internal.ProjectImpl;
import com.speedment.runtime.config.internal.immutable.ImmutableProject;
import com.speedment.runtime.config.mutator.DocumentMutator;
import com.speedment.runtime.config.mutator.ProjectMutator;
import com.speedment.runtime.config.trait.HasChildren;
import com.speedment.runtime.config.trait.HasDeepCopy;
import com.speedment.runtime.config.trait.HasEnabled;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasMainInterface;
import com.speedment.runtime.config.trait.HasMutator;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.trait.HasPackageName;
import com.speedment.runtime.config.util.DocumentUtil;

import static com.speedment.runtime.config.ProjectUtil.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A typed {@link Document} that represents a database project. A 
 * {@code Project} is the root of the document tree and can have multiple 
 * {@link Dbms Dbmses} as children.
 * 
 * @author  Emil Forslund
 * @since   2.0.0
 */
public interface Project
extends Document,
        HasEnabled,
        HasDeepCopy,
        HasId,        
        HasName,
        HasPackageName,
        HasChildren,
        HasMainInterface,
        HasMutator<ProjectMutator<? extends Project>> {

    /**
     * Creates and returns a mutable deep-copy of the specified project.
     *
     * @param existing  the existing project
     * @return  the created copy
     * @since 3.0.22
     */
    static Project deepCopy(Project existing) {
        return DocumentUtil.deepCopy(existing, ProjectImpl::new);
    }
    
    /**
     * Returns the name of the company that should be used in generated code.
     * 
     * @return  the name of the company generating code
     */
    default String getCompanyName() {
        return getAsString(COMPANY_NAME).orElse(DEFAULT_COMPANY_NAME);
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
     * Returns the version of Speedment that was used to generate the code. If
     * this does not correspond with the version of the runtime, then a warning
     * will be shown when Speedment is started.
     * <p>
     * The version has the following structure:
     * <pre>{@code speedment:3.0.21}</pre>
     * First comes the name of the edition (simply {@code speedment} for the
     * open-source edition) followed by a comma and the Maven version.
     * <p>
     * If this value is not present, it should be read as the generated version
     * being unknown. This probably means that it was generated with an older
     * version of Speedment than {@code 3.0.21} in the case of the open-source
     * edition.
     *
     * @return the speedment version
     * @since  3.0.21
     */
    default Optional<String> getSpeedmentVersion() {
        return getAsString(SPEEDMENT_VERSION);
    }

    /**
     * Returns the unique id for this application. This is usually generated on
     * application launch. The value should be formatted as a
     * {@link java.util.UUID}.
     *
     * @return  the app id
     */
    default String getAppId() {
        return getAsString(APP_ID).orElseThrow(
            () -> new SpeedmentConfigException(
                "All applications must have an 'appId' property."
            )
        );
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
    Stream<Dbms> dbmses();

    @Override
    default Class<Project> mainInterface() {
        return Project.class;
    }

    @Override
    default ProjectMutator<? extends Project> mutator() {
        return DocumentMutator.of(this);
    }

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

        final String dbmsId = parts[0];
        final String schemaId = parts[1];
        final String tableId = parts[2];
       
        return dbmses()
            .filter(d -> dbmsId.equals(d.getId()))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "Could not find dbms: '" + dbmsId + "'."))
            .schemas().filter(s -> schemaId.equals(s.getId())).findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "Could not find schema: '" + schemaId + "'."))
            .tables().filter(t -> tableId.equals(t.getId())).findAny()
            .orElseThrow(() -> new IllegalArgumentException(
                "Could not find table: '" + tableId + "'."));
    }

    @Override
    default Project deepCopy() {
        return DocumentUtil.deepCopy(this, ProjectImpl::new);
    }

    /**
     * Creates and returns a new standard implementation of a {@link Project}
     * with the given {@code data}.
     *
     * @param data   of the config document
     * @return a new {@link Project} with the given parameters
     * @throws NullPointerException if the provided {@code data} is {@code null}
     */
    static Project create(Map<String, Object> data) {
        return new ProjectImpl(data);
    }

    /**
     * Creates and returns a new standard implementation of a {@link Project}
     * with the given {@code parent} and {@code data}.
     *
     * @param parent of the config document (nullable)
     * @param data of the config document
     * @return a new {@link Project} with the given parameters
     *
     * @throws NullPointerException if the provided {@code data} is {@code null}
     */
    static Project create(Document parent, Map<String, Object> data) {
        return new ProjectImpl(parent, data);
    }

    static Project createImmutable(Project project) {
        return new ImmutableProject(project.getData());
    }

}