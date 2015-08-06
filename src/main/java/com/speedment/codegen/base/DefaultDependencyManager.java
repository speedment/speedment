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
package com.speedment.codegen.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import static com.speedment.codegen.util.Formatting.*;

/**
 * Default implementation of the {@link DependencyManager} interface.
 * 
 * @author Emil Forslund
 */
public class DefaultDependencyManager implements DependencyManager {
    
	private final Set<String> dependencies = new HashSet<>();
	private final Set<String> ignorePackages;
	
	/**
	 * Initalises the DependencyManager.
	 */
	public DefaultDependencyManager() {
		ignorePackages = new HashSet<>();
	}
	
	/**
	 * Initalises the DependencyManager.
     * 
	 * @param ignoredPackage  a package that should be on the ignore list
	 */
	public DefaultDependencyManager(String ignoredPackage) {
		ignorePackages = new HashSet<>();
		ignorePackages.add(ignoredPackage);
	}
    
    /**
	 * Initalises the DependencyManager.
     * 
	 * @param ignoredPackages  packages that should be ignored
	 */
	public DefaultDependencyManager(String[] ignoredPackages) {
		ignorePackages = Arrays.stream(ignoredPackages)
			.collect(Collectors.toSet());
	}
	
	/**
	 * Initalises the DependencyManager.
     * 
	 * @param ignoredPackage   a package that should be on the ignore list
	 * @param ignoredPackages  more packages that should be on the ignore list
	 */
	public DefaultDependencyManager(String ignoredPackage, String... ignoredPackages) {
		this (ignoredPackages);
		ignorePackages.add(ignoredPackage);
	}

	/**
	 * Adds the specified package to the ignore list. This is the opposite as
	 * calling <code>acceptPackage</code>.
     * 
	 * @param packageName  the full name of the package
	 */
	@Override
	public void ignorePackage(String packageName) {
		ignorePackages.add(packageName);
	}
	
	/**
	 * Removes the specified package from the ignore list. This is the opposite 
	 * as calling <code>ignorePackage</code>.
     * 
	 * @param packageName  the full name of the package
	 */
	@Override
	public void acceptPackage(String packageName) {
		ignorePackages.removeIf(p -> packageName.startsWith(p + DOT));
	}
	
	/**
	 * Returns true if the specified class belongs to a package that is on the
	 * ignore list.
     * 
	 * @param fullname  the full name of a package or a class
	 * @return          true if it should be ignored as a dependency
	 */
	@Override
	public boolean isIgnored(String fullname) {
		return ignorePackages.stream().anyMatch(p -> 
			fullname.startsWith(p + DOT) ||
			fullname.equals(p)
		);
	}

    /**
     * Add the specified dependency to the manager. If the dependency is 
     * redundant, false is returned and the dependency is not added.
     * 
     * @param fullname  the full name of the dependency
     * @return          true if the dependency was accepted
     */
	@Override
	public boolean load(String fullname) {
		if (isLoaded(fullname)) {
			return false;
		} else {
			dependencies.add(fullname);
			return true;
		}
	}

    /**
     * Checks if the specified dependency is already loaded. If the dependency
     * matches an ignored package, true is returned even if it is not
     * specifically loaded.
     * 
     * @param fullname  the name of the dependency
     * @return          true if it is loaded or redundant
     */
	@Override
	public boolean isLoaded(String fullname) {
		return dependencies.contains(fullname)
            || isIgnored(fullname);
	}

    /**
     * Clear the list of dependencies.
     */
	@Override
	public void clearDependencies() {
		dependencies.clear();
	}
}