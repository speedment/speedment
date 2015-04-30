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
import static com.speedment.codegen.Formatting.*;

/**
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
	 * @param ignoredPackage A package that should be on the ignore list.
	 */
	public DefaultDependencyManager(String ignoredPackage) {
		ignorePackages = new HashSet<>();
		ignorePackages.add(ignoredPackage);
	}
    
    /**
	 * Initalises the DependencyManager.
	 * @param ignoredPackages Packages that should be ignored.
	 */
	public DefaultDependencyManager(String[] ignoredPackages) {
		ignorePackages = Arrays.stream(ignoredPackages)
			.collect(Collectors.toSet());
	}
	
	/**
	 * Initalises the DependencyManager.
	 * @param ignoredPackage A package that should be on the ignore list.
	 * @param ignoredPackages More packages that should be on the ignore list.
	 */
	public DefaultDependencyManager(String ignoredPackage, String... ignoredPackages) {
		this (ignoredPackages);
		ignorePackages.add(ignoredPackage);
	}

	/**
	 * Adds the specified package to the ignore list. This is the opposite as
	 * calling <code>acceptPackage</code>.
	 * @param packageName The full name of the package.
	 */
	@Override
	public void ignorePackage(String packageName) {
		ignorePackages.add(packageName);
	}
	
	/**
	 * Removes the specified package from the ignore list. This is the opposite 
	 * as calling <code>ignorePackage</code>.
	 * @param packageName The full name of the package.
	 */
	@Override
	public void acceptPackage(String packageName) {
		ignorePackages.removeIf(p -> packageName.startsWith(p + DOT));
	}
	
	/**
	 * Returns true if the specified class belongs to a package that is on the
	 * ignore list.
	 * @param fullname The full name of a package or a class.
	 * @return True if it should be ignored as a dependency.
	 */
	@Override
	public boolean isIgnored(String fullname) {
		return ignorePackages.stream().anyMatch(p -> 
			fullname.startsWith(p + DOT) ||
			fullname.equals(p)
		);
	}

	@Override
	public boolean load(String fullname) {
		if (isNameTaken(fullname)) {
			return false;
		} else {
			dependencies.add(fullname);
			return true;
		}
	}

	@Override
	public boolean isLoaded(String fullname) {
		return (dependencies.contains(fullname)) 
		|| ignorePackages.stream().anyMatch(
			p -> fullname.startsWith(p + DOT)
			||   fullname.equals(p)
		);
	}
	
	private boolean isNameTaken(String fullname) {
		return dependencies.stream().anyMatch(
			d -> d.endsWith(DOT + shortName(fullname))
		);
	}

	@Override
	public void clearDependencies() {
		dependencies.clear();
	}
}
