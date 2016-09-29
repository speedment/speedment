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
package com.speedment.runtime.core.field.method;


import com.speedment.runtime.core.manager.Manager;

import java.util.function.BiFunction;

/**
 * A short-cut functional reference to the {@code findXXX()} method for a
 * particular field in an entity.
 * <p>
 * A {@code Finder<ENTITY, FK_ENTITY>} has the following signature:
 * <code>
 *      interface ENTITY {
 *          FK_ENTITY findXXX(fk_manager);
 *      }
 * </code>
 * 
 * @param <ENTITY>     the entity
 * @param <FK_ENTITY>  the type of the foreign key stream
 * 
 * @author  Emil Forslund
 * @since   2.2.0
 */

@FunctionalInterface
public interface Finder<ENTITY, FK_ENTITY> extends 
    BiFunction<ENTITY, Manager<FK_ENTITY>, FK_ENTITY> {}