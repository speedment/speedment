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
import com.speedment.core.config.model.aspects.Enableable;
import com.speedment.core.config.model.aspects.Node;
import com.speedment.core.config.model.impl.ProjectImpl;
import groovy.lang.Closure;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
@Api(version = "2.0")
public interface Project extends Node, Enableable, Parent<Dbms>, Child<ProjectManager> {

    /**
     * Factory holder.
     */
    enum Holder {
        HOLDER;
        private Supplier<Project> provider = ProjectImpl::new;
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     *
     * @param provider the new constructor
     */
    static void setSupplier(Supplier<Project> provider) {
        Holder.HOLDER.provider = provider;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     *
     * @return the new instance
     */
    static Project newProject() {
        return Holder.HOLDER.provider.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    default Class<Project> getInterfaceMainClass() {
        return Project.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Class<ProjectManager> getParentInterfaceMainClass() {
        return ProjectManager.class;
    }

    /**
     * Creates and adds a new {@link Dbms} as a child to this node in the
     * configuration tree.
     *
     * @return the newly added child
     */
    default Dbms addNewDbms() {
        final Dbms e = Dbms.newDbms();
        add(e);
        return e;
    }

    /**
     * Returns the name of the generated package where this project will be
     * located.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @return the name of the generated package
     */
    @External(type = String.class)
    String getPackageName();

    /**
     * Sets the name of the generated package where this project will be
     * located.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @param packageName the name of the generated package
     */
    @External(type = String.class)
    void setPackageName(String packageName);

    /**
     * Returns where the code generated for this project will be located.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @return the package location
     */
    @External(type = String.class)
    String getPackageLocation();

    /**
     * Sets where the code generated for this project will be located.
     * <p>
     * This property is editable in the GUI through reflection.
     *
     * @param packageLocation the new package location
     */
    @External(type = String.class)
    void setPackageLocation(String packageLocation);

    /**
     * Returns the path to the groovy configuration file for this project. The
     * path may not be set at the time of the calling and the result may
     * therefore be {@code empty}.
     *
     * @return the path to the groovy configuration file
     */
    Optional<Path> getConfigPath();

    /**
     * Sets the path to the groovy configuration file for this project. If the
     * input is {@code null} then the path is considered unknown at this point.
     *
     * @param configPath the path to the configuration file or {@code null}
     */
    void setConfigPath(Path configPath);

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

    /**
     * Creates and returns a new Dbms.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new Dbms
     */
    // DO NOT REMOVE, CALLED VIA REFLECTION
    default Dbms dbms(Closure<?> c) {
        return ConfigUtil.groovyDelegatorHelper(c, this::addNewDbms);
    }
}
