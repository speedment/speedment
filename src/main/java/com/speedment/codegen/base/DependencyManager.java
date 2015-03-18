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

/**
 *
 * @author Emil Forslund
 */
public interface DependencyManager {
	/**
	 * Attempts to add the specified resource to the dependency list.
	 * If the name is already taken it will return false.
	 * @param fullname The full name of the resource.
	 * @return True if it was added, else false.
	 */
	boolean load(String fullname);
	
	/**
	 * Returns true if the specified fullname is either:
	 *		(A) loaded into the dependency list;
	 *		(B) on the ignore list.
	 * Else it returns false.
	 * @param fullname The full name of the resource.
	 * @return True if it don't have to be loaded.
	 */
	boolean isLoaded(String fullname);
	
	/**
	 * Clear all dependencies.
	 */
	void clearDependencies();
	
	/**
	 * Adds the specified package to the ignore list. This is the opposite as
	 * calling <code>acceptPackage</code>.
	 * @param packageName The full name of the package.
	 */
	void ignorePackage(String packageName);
	
	/**
	 * Removes the specified package from the ignore list. This is the opposite 
	 * as calling <code>ignorePackage</code>.
	 * @param packageName The full name of the package.
	 */
	void acceptPackage(String packageName);
	
	/**
	 * Returns true if the specified class belongs to a package that is on the
	 * ignore list.
	 * @param fullname The full name of a package or a class.
	 * @return True if it should be ignored as a dependency.
	 */
	boolean isIgnored(String fullname);
}
