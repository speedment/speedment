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
package com.speedment.core.config.model.aspects;

import com.speedment.util.Trees;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 * The base interface for all nodes in the database model. Nodes can be anything
 * from an index to an entire schema. If a node can have children it should
 * also inherit the {@link Parent} interface and if it has a parent it should
 * inherit the {@link Child} interface.
 * 
 * All nodes are both {@link Nameable} and {@link Enableable}.
 * 
 * @author  Emil Forslund
 * @see     Parent
 * @see     Child
 */
public interface Node extends Nameable, Enableable {
    
    /**
     * If this node implements the {@link Child} interface, it is returned
     * wrapped in an <code>Optional</code>. If not, empty is returned. The 
     * method does not take into consideration if the parent is set or not, only 
     * if it is possible for it to have a parent.
     * 
     * @return       this entity if it is a child.
     * @see          Child
     */
    default Optional<? extends Child<?>> asChild() {
        return Optional.empty();
    }

    /**
     * If this node implements the {@link Parent} interface, it is returned
     * wrapped in an <code>Optional</code>. If not, empty is returned. The 
     * method does not take into consideration if any children exists or not, only
     * if it is possible for it to have children.
     * 
     * @return       this entity if it is a parent.
     * @see          Parent
     */
    default Optional<? extends Parent<?>> asParent() {
        return Optional.empty();
    }

    /**
     * Returns <code>true</code> if this class implements the {@link Parent} 
     * interface.
     * 
     * @return       <code>true</code> if this is a {@link Parent}.
     * @see          Parent
     */
    default boolean isParentInterface() {
        return false;
    }

    /**
     * Returns <code>true</code> if this class implements the {@link Child} 
     * interface.
     * 
     * @return       <code>true</code> if this is a {@link Child}.
     * @see          Child
     */
    default boolean isChildInterface() {
        return false;
    }

    /**
     * Returns <code>true</code> if this class implements the {@link Ordinable} 
     * interface.
     * 
     * @return       <code>true</code> if this is a {@link Ordinable}.
     * @see          Ordinable
     */
    default boolean isOrdinable() {
        return false;
    }

    /**
     * Returns a <code>Stream</code> with all the nodes above this node in the
     * database model tree.
     * 
     * @return       a stream of all ancestors.
     */
    @SuppressWarnings("unchecked")
    default Stream<? extends Parent<?>> ancestors() {
        return asChild()
            .flatMap(c -> c.getParent())
            .map(p -> (Parent<?>) p)
            .map(parent -> Trees.walkOptional(
                parent, (Parent<?> p) -> p.asChild()
                    .flatMap(c -> c.getParent())
                    .map(p2 -> (Parent<?>) p2),
                Trees.WalkingOrder.BACKWARD
            )).orElse(Stream.empty());
    }

    /**
     * Looks for an ancestor of the specified type above this node in the
     * database model tree. If such an ancestor exists, it is returned. Else
     * the result will be <code>empty</code>.
     * 
     * @param <E>    the type of the ancestor to return
     * @param clazz  the class of the ancestor to search for
     * @return       the ancestor
     */
    @SuppressWarnings("unchecked")
    default <E extends Node> Optional<E> ancestor(final Class<E> clazz) {
        return ancestors()
            .filter(p -> clazz.isAssignableFrom(p.getClass()))
            .map(p -> (E) p)
            .findFirst();
    }

    /**
     * 
     * 
     * @param <T> sddasd
     * @param from zsdzsd
     * @return       asd
     */
    default <T extends Parent<?>> String getRelativeName(final Class<T> from) {
        return getRelativeName(from, Function.identity());
    }

    default <T extends Parent<?>> String getRelativeName(final Class<T> from, Function<String, String> childMapper) {
        Objects.requireNonNull(from);
        final StringJoiner sj = new StringJoiner(".", "", ".").setEmptyValue("");
        final List<Parent<?>> ancestors = ancestors().map(p -> (Parent<?>) p).collect(toList());
        boolean add = false;
        for (final Parent<?> parent : ancestors) {
            if (from.isAssignableFrom(parent.getClass())) {
                add = true;
            }
            if (add) {
                sj.add(childMapper.apply(parent.getName()));
            }
        }
        return sj.toString() + childMapper.apply(getName());
    }

    /**
     * Returns the {@code Class} of the interface of this node. 
     * <p>
     * This should <b>not</b> be overridden by implementing classes!
     * 
     * @return  the main interface class
     */
    Class<?> getInterfaceMainClass();
}