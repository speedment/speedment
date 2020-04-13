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
package com.speedment.runtime.field.method;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.Field;

import java.util.function.Function;

/**
 * A handle for a find-operation that can be replaced runtime to optimize a 
 * {@code Stream}. Formally, a {@code FindFrom} has the following signature:
 * {@code
 *      FK_ENTITY apply(ENTITY entity);
 * }
 * <p>
 * Each {@code FindFrom} contains metadata of which fields it compares to 
 * determine the mapping as well as a reference to the foreign manager.
 * 
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * 
 * @author Emil Forslund
 * @since  3.0.0
 */
public interface FindFrom<ENTITY, FK_ENTITY> extends Function<ENTITY, FK_ENTITY> {
    
    /**
     * Returns the field that the stream originates from. 
     * <p>
     * In the following example, {@code foo} is the source:
     * {@code
     *      foos.stream()
     *          .flatMap(foo.findBars()) // findBars returns Streamer<Foo, Bar>
     *          .forEach(...);
     * }
     * 
     * @return  the source field
     */
    Field<ENTITY> getSourceField();
    
    /**
     * Returns the field that the stream references. 
     * <p>
     * In the following example, {@code bar} is the target:
     * {@code
     *      foos.stream()
     *          .flatMap(foo.findBars()) // findBars returns Streamer<Foo, Bar>
     *          .forEach(...);
     * }
     * 
     * @return  the target field
     */
    Field<FK_ENTITY> getTargetField();
    
    /**
     * Returns the identifier for the referenced (foreign) table.
     * 
     * @return  target (foreign) table identifier
     */
    TableIdentifier<FK_ENTITY> getTableIdentifier();

}
