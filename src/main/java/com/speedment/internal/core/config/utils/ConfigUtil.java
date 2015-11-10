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
package com.speedment.internal.core.config.utils;

import com.speedment.exception.SpeedmentException;
import static com.speedment.util.StaticClassUtil.instanceNotAllowed;
import groovy.lang.Closure;
import java.util.function.Supplier;
import static java.util.Objects.requireNonNull;

/**
 * Utility class with static methods for finding columns and tables from various
 * configuration nodes.
 *
 * @author pemi
 */
public final class ConfigUtil {

    /**
     * Uses the specified {@code createAndAdder} to produce a new node and sets
     * it as a delegate in the specified groovy closure. The produced node is
     * then returned.
     *
     * @param <S> the type of the node to produce
     * @param closure the groovy closure
     * @param createAndAdder the method to use for creating and adding new node
     * instances
     * @return the produced instance
     */
    public static <S> S groovyDelegatorHelper(Closure<?> closure, Supplier<S> createAndAdder) {
        requireNonNull(closure);
        requireNonNull(createAndAdder);

        final S result = createAndAdder.get();
        closure.setDelegate(result);
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.call();
        return result;
    }

    /**
     * Creates a supplier that can be called to create
     * {@link SpeedmentException} that explains that there is no nodes of one
     * type for the other type by the specified name.
     *
     * @param nodeType the node type
     * @param parentType the parent type
     * @param name the name to look for
     * @return the exception supplier
     */
    public static Supplier<SpeedmentException> thereIsNo(Class<?> nodeType, Class<?> parentType, String name) {
        requireNonNull(nodeType);
        requireNonNull(parentType);
        requireNonNull(name);
        
        return () -> new SpeedmentException(
            "There is no "
            + nodeType.getSimpleName()
            + " for the "
            + parentType.getSimpleName()
            + " named "
            + name
        );
    }

    /**
     * Utility classes should not be instantiated.
     */
    private ConfigUtil() {
        instanceNotAllowed(getClass());
    }
}
