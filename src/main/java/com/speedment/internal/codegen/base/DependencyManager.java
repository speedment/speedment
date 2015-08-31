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
package com.speedment.internal.codegen.base;

import java.util.Optional;

/**
 * Keeps track of which dependencies have been imported and which is not
 * included. This is useful for solving the issue of when to write the full name
 * of a class and when to only include the short name.
 *
 * @author Emil Forslund
 */
public interface DependencyManager {

    /**
     * Attempts to add the specified resource to the dependency list. If the
     * name is already taken it will return {@code false}.
     *
     * @param fullname  the full name of the resource.
     * @return          {@code true} if it was added, else <code>false</code>
     */
    boolean load(String fullname);

    /**
     * Returns <code>true</code> if the specified fullname is either:
     *
     * <pre>
     *		(A) loaded into the dependency list;
     *		(B) on the ignore list.
     * </pre>
     *
     * Else it returns <code>false</code>.
     *
     * @param fullname  the full name of the resource.
     * @return          {@code true} if it don't have to be loaded.
     */
    boolean isLoaded(String fullname);

    /**
     * Returns true if the specified class belongs to a package that is on the
     * ignore list.
     *
     * @param fullname  the full name of a package or a class
     * @return          {@code true} if it should be ignored as a dependency
     */
    boolean isIgnored(String fullname);

    /**
     * Clear all dependencies.
     */
    void clearDependencies();

    /**
     * Attempts to set the package that is currently being rendered. If a
     * package is already set, the operation will fail and {@code false} will be
     * returned. If the operation succeeded, {@code true} is returned and the
     * {@code DependencyManager} will expect
     * {@link #unsetCurrentPackage(java.lang.String)} to be called later before
     * the generation is finished.
     *
     * @param pack  the new current package
     * @return      {@code true} if the operation succeeded, else {@code false}
     */
    boolean setCurrentPackage(String pack);

    /**
     * Attempts to unset the currently rendered package. If the specified
     * package is the one that is currently being rendered, it will be unset and
     * {@code true} will be returned. If the package specified is not correct,
     * the current package will <b>not</b> be unset and {@code false} will be
     * returned. If no package was set the operation will throw an
     * {@link IllegalStateException}.
     *
     * @param pack  the current package to unset
     * @return      {@code true} if the operation succeeded, else {@code false}
     */
    boolean unsetCurrentPackage(String pack);
    
    /**
     * Returns the current package if one is set, or else {@code empty}.
     * @return  the package
     */
    Optional<String> getCurrentPackage();
}