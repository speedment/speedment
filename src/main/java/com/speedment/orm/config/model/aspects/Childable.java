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
package com.speedment.orm.config.model.aspects;

import com.speedment.orm.config.model.impl.ChildHolder;
import com.speedment.util.Trees;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface Childable<C extends Parentable> extends Node {
    ChildHolder<C> getChildren();
    
    default Optional<C> add(final C child) {
        return getChildren().put(this, child);
    }

    default Optional<C> remove(final C child) {
        return getChildren().remove(this, child);
    }

    default boolean contains(final C child) {
        return getChildren().contains(child);
    }

    default Stream<? extends C> stream() {
        return getChildren().stream();
    }
    
    default <T extends C> Stream<T> streamOf(Class<T> childClass) {
        return getChildren().streamOf(childClass);
    }

    default <T extends Childable> Stream<T> traversalOf(Class<T> childClass) {
        final Stream<? extends Childable> s = Trees.traverse(
            (Childable) this, 
            t -> t.stream(), 
            Trees.TraversalOrder.BREADTH_FIRST
        );
        
        return s.filter(t -> childClass.isAssignableFrom(
            t.getInterfaceMainClass()
        )).map(t -> (T) t);
    }

    default boolean isEmpty() {
        return getChildren().isEmpty();
    }
    
    default long size() {
        return getChildren().size();
    }
    
    @Override
    default boolean isChildable() {
        return true;
    }
}