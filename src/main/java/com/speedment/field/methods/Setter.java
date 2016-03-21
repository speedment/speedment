/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.field.methods;

import com.speedment.annotation.Api;
import java.util.function.BiFunction;

/**
 * A short-cut functional reference to the {@code setXXX(value)} method for a
 * particular field in an entity. The referenced method should return a 
 * reference to itself.
 * <p>
 * A {@code Setter<ENTITY, V>} has the following signature:
 * <code>
 *      interface ENTITY {
 *          ENTITY setXXX(V value);
 *      }
 * </code>
 * 
 * @param <ENTITY>  the entity
 * @param <V>       the type of the value to return
 * 
 * @author  Emil Forslund
 * @since   2.2
 */
@Api(version = "2.2")
@FunctionalInterface
public interface Setter<ENTITY, V> extends BiFunction<ENTITY, V, ENTITY> {}