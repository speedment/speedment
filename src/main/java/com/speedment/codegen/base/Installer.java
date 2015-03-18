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

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface Installer {
    /**
     * Returns a unique name of this installer. This can be used to identify
     * a particular installer if multiple ones are used in the same generator.
     * @return The unique name.
     */
    String getName();
    
	/**
	 * Installs the specified View.
	 * @param <M>
	 * @param <V>
	 * @param model The model.
	 * @param view The view.
     * @return A reference to this.
	 */
	<M, V extends CodeView<M>> Installer install(Class<M> model, Class<V> view);
//	
//	/**
//	 * Returns a view if there is one that matched the specified model.
//	 * @param model The model.
//	 * @return The view or empty as an Optional.
//	 */
//	Optional<CodeView<?>> withOne(Class<?> model);
	
	/**
	 * Builds a stream of all views that match the specified model.
     * @param <M>
	 * @param model The model.
	 * @return A stream of all matching views.
	 */
	<M> Stream<CodeView<M>> withAll(Class<M> model);
	
	/**
	 * Instantiates the specified class and returns it.
	 * @param <T>
	 * @param clazz
	 * @return 
	 */
	public static <T> T create(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException ex) {
			Logger.getLogger(DefaultInstaller.class.getName()).log(Level.SEVERE, 
				"The class '" + clazz.getName() + 
				"' could not be instantiated using the default constructor. " +
				"Make sure it is the correct class and that the default " +
				"constructor has been properly defined without no parameters.", ex);
		}
		
		return null;
	}
}