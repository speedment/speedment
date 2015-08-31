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
package com.speedment.config;

import com.speedment.HasSpeedment;
import com.speedment.Speedment;
import com.speedment.annotation.Api;
import com.speedment.annotation.External;
import com.speedment.config.aspects.Parent;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Enableable;
import com.speedment.internal.core.config.ProjectImpl;
import groovy.lang.Closure;
import java.nio.file.Path;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Function;

/**
 *
 * @author pemi
 */
@Api(version = "2.1")
public interface Project extends Node, Enableable, HasSpeedment, Parent<Dbms>, Child<ProjectManager> {

    /**
     * Factory holder.
     */
    enum Holder {
        HOLDER;
        private Function<Speedment, Project> provider = ProjectImpl::new;
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     *
     * @param provider the new constructor
     */
    static void setSupplier(Function<Speedment, Project> provider) {
        Holder.HOLDER.provider = requireNonNull(provider);
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Function) setSupplier} method.
     *
     * @param speedment instance to use
     * @return the new instance
     */
    static Project newProject(Speedment speedment) {
        return Holder.HOLDER.provider.apply(speedment);
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
     * @param speedment the {@link Speedment} instance
     * @return the newly added child
     */
    default Dbms addNewDbms(Speedment speedment) {
        final Dbms e = Dbms.newDbms(speedment);
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
    Table findTableByName(String fullName);

    /**
     * Creates and returns a new Dbms.
     * <p>
     * This method is used by the Groovy parser.
     *
     * @param c Closure
     * @return the new Dbms
     */
    Dbms dbms(Closure<?> c);
}
