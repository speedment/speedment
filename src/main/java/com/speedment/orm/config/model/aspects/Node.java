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
    default <P extends Parent<?, ?>> Optional<P> getParent() {
        return Optional.empty();
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
    
    default Optional<? extends Child<?, ?>> asChild() {
        return Optional.empty();
    }
    
    default Optional<? extends Parent<?, ?>> asParent() {
        return Optional.empty();
    }
    
    @SuppressWarnings("unchecked")
    default Stream<? extends Parent<?, ?>> ancestors() {
        return Trees.walkOptional(
            getParent().map(p -> (Parent<?, ?>) p).get(), 
            p -> Optional.of(p).flatMap(p2 -> p2.getParent()),
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
    
    <T extends Node> Class<T> getInterfaceMainClass();
}