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
package com.speedment.runtime.field.method;

import com.speedment.runtime.annotation.Api;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A short-cut functional reference to the streaming {@code findXXXs()} method 
 * for a particular field referencing this entity.
 * <p>
 * A {@code Streamer<ENTITY, FK_ENTITY>} has the following signature:
 * <code>
 *      interface ENTITY {
 *          Stream&lt;FK_ENTITY&gt; findXXXs();
 *      }
 * </code>
 * 
 * @param <ENTITY>     the entity
 * @param <FK_ENTITY>  the type of the foreign entity referencing this
 * 
 * @author  Emil Forslund
 * @since   2.2.0
 */
@Api(version = "3.0")
@FunctionalInterface
public interface Streamer<ENTITY, FK_ENTITY> extends 
    Function<ENTITY, Stream<FK_ENTITY>> {}