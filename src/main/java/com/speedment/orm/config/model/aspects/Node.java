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

import com.speedment.util.Trees;
import java.util.Optional;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 */
public interface Node extends Nameable {
    
    @SuppressWarnings("unchecked")
    default <P extends Parent<?>> Optional<P> getParent(Class<P> parentClass) {
        return Optional.of(this)
            .filter(e -> e.isChildInterface())
            .map(e -> (Child<?>) e)
            .flatMap(e -> e.getParent())
            .filter(e -> parentClass.isAssignableFrom(e.getClass()))
            .map(e -> (P) e);
    }
    
    default boolean isParentInterface() {
        return false;
    }

    default boolean isChildInterface() {
        return false;
    }
    
    default boolean isOrdinable() {
        return false;
    }
    
    default Optional<? extends Child<?>> asChild() {
        return Optional.empty();
    }
    
    default Optional<? extends Parent<?>> asParent() {
        return Optional.empty();
    }
    
    @SuppressWarnings("unchecked")
    default Stream<? extends Parent<?>> ancestors() {
        return Trees.walkOptional(
            getParent(Parent.class).get(), 
            (Parent<?> p) -> p.getParent(Parent.class).map(p2 -> (Parent<?>) p2), 
            Trees.WalkingOrder.BACKWARD
        );
    }
    
    @SuppressWarnings("unchecked")
    default <E extends Node> Optional<E> ancestor(final Class<E> clazz) {
        return ancestors()
            .filter(p -> clazz.isAssignableFrom(p.getClass()))
            .map(p -> (E) p)
            .findFirst();
    }
    
    default String getRelativeName(Node from) {
        return ancestors().map(Nameable::getName).collect(joining("."));
    }
    
    Class<?> getInterfaceMainClass();
    
    default boolean is(Class<?> clazz) {
        final Class<?> first = getInterfaceMainClass();
        return clazz.isAssignableFrom(first);
    }
}