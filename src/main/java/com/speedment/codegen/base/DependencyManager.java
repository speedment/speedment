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
 * Keeps track of which dependencies have been imported and which is not 
 * included. This is useful for solving the issue of when to write the full name
 * of a class and when to only include the short name.
 * 
 * @author Emil Forslund
 */
public interface DependencyManager {
    
	/**
	 * Attempts to add the specified resource to the dependency list.
	 * If the name is already taken it will return <code>false</code>.
     * 
	 * @param fullname  the full name of the resource.
	 * @return          <code>true</code> if it was added, else <code>false</code>
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
	 * @return          <code>true</code> if it don't have to be loaded.
	 */
	boolean isLoaded(String fullname);
	
	/**
	 * Returns true if the specified class belongs to a package that is on the
	 * ignore list.
     * 
	 * @param fullname  the full name of a package or a class
	 * @return          <code>true</code> if it should be ignored as a dependency
	 */
	boolean isIgnored(String fullname);
    
    /**
	 * Removes the specified package from the ignore list. This is the opposite 
	 * as calling <code>ignorePackage</code>.
     * 
	 * @param packageName  the full name of the package
	 */
	void acceptPackage(String packageName);
	
	/**
	 * Adds the specified package to the ignore list. This is the opposite as
	 * calling <code>acceptPackage</code>.
     * 
	 * @param packageName  the full name of the package
	 */
	void ignorePackage(String packageName);
	
    /**
	 * Clear all dependencies.
	 */
	void clearDependencies();
}