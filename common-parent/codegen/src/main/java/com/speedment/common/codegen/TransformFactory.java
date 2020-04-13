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
package com.speedment.common.codegen;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Different {@link Transform} implementations can be <i>installed</i> in the
 * <code>TransformFactory</code> to be instantiated when needed. The transforms
 * are ordered in a graph-like manner so that all tranforms leading away from
 * a particular model type easily can be located.
 * 
 * @author Emil Forslund
 * @since   2.0
 */
public interface TransformFactory {
    /**
     * Returns a unique name of this factory. This can be used to identify
     * a particular factory if multiple ones are used in the same generator.
     * 
     * @return  the unique name
     */
    String getName();
    
    /**
	 * Installs the specified {@link Transform}, assuming that the resulting 
     * class is a String.
     * 
	 * @param <A>          the type to transform from
	 * @param <T>          the transformer
	 * @param from         the model
	 * @param transformer  the view
     * @return             a reference to this
	 */
    default <A, T extends Transform<A, String>>
	TransformFactory install(
        	Class<A> from, Supplier<T> transformer) {
        
        return install(from, String.class, transformer);
    }
    
	/**
	 * Installs the specified {@link Transform}.
     * 
	 * @param <A>          the type to transform from
     * @param <B>          the type to transform to
	 * @param <T>          the transformer
	 * @param from         the model class to transform from
     * @param to           the model class to transform to
	 * @param transformer  the view
     * @return             a reference to this
	 */
	<A, B, T extends Transform<A, B>> TransformFactory install(
        Class<A> from, Class<B> to, Supplier<T> transformer);

	/**
	 * Builds a stream of all transforms that match the specified model.
     * 
     * @param <A>   the class to transform from
	 * @param <T>   the transformer
	 * @param from  the model class to transform from
	 * @return      a stream of all matching transforms
	 */
	<A, T extends Transform<A, ?>> Set<Map.Entry<Class<?>, T>> allFrom(Class<A> from);
}