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
package com.speedment.internal.gui.config;

import com.speedment.Speedment;
import com.speedment.config.Node;
import com.speedment.config.aspects.Child;
import com.speedment.config.aspects.Parent;
import com.speedment.internal.core.config.ChildHolder;
import com.speedment.internal.util.Trees;
import static com.speedment.internal.util.Trees.TraversalOrder.BREADTH_FIRST;
import com.speedment.stream.MapStream;
import static java.util.Objects.requireNonNull;
import java.util.function.BiFunction;
import java.util.function.Function;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import static javafx.collections.FXCollections.observableMap;
import javafx.collections.ObservableSet;

/**
 *
 * @author Emil Forslund
 * @param <THIS>  the type of this node
 * @param <CHILD> the child type
 */
public abstract class AbstractParentProperty<THIS extends Node, CHILD extends Child<? super THIS>> extends AbstractNodeProperty implements Parent<CHILD> {

    public AbstractParentProperty(Speedment speedment) {
        super(speedment);
    }
    
    public AbstractParentProperty(Speedment speedment, THIS prototype) {
        super(speedment, prototype);
    }

    @Override
    public final ChildHolder<CHILD> getChildren() {
        throw new UnsupportedOperationException("This method should not be used.");
    }
    
    public final CHILD prepare(CHILD child) {
        child.setParent(this);
        child.setName(child.getInterfaceMainClass().getSimpleName() + "_" + child.hashCode());
        return child;
    }

    @Override
    public Stream<Node> traverse() {
        final Function<Node, Stream<Node>> traverse = n -> n.asParent()
            .map(Parent::stream)
            .orElse(Stream.empty())
            .map(Node.class::cast);

        return Trees.traverse(this, traverse, BREADTH_FIRST).map(Node.class::cast);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Node> Stream<T> traverseOver(Class<T> childType) {
        requireNonNull(childType);
        
        return traverse()
            .filter(t -> childType.isAssignableFrom(t.getClass()))
            .map(t -> (T) t);
    }
    
    protected IllegalArgumentException wrongChildTypeException(Class<?> childType) {
        return new IllegalArgumentException(
            getClass().getSimpleName() + 
            " can't accept a child of type " + 
            childType.getSimpleName() + "."
        );
    }
    
    protected IllegalArgumentException noChildWithNameException(Class<?> childType, String name) {
        return new IllegalArgumentException(
            getClass().getSimpleName() + 
            " does not have any " + childType.getSimpleName() + 
            " with name '" + name + "'."
        );
    }
    
    protected <CHILD extends Child<?>, THIS extends Node & Parent<? super CHILD>> ObservableSet<CHILD> copyChildrenFrom(THIS prototype, Class<CHILD> childType, BiFunction<Speedment, CHILD, CHILD> wrapper) {
        return observableSet(
            prototype.streamOf(childType)
                .map(child -> {
                    final CHILD newChild = wrapper.apply(getSpeedment(), child);
                    newChild.setParent(this);
                    return newChild;
                })
                .collect(toSet())
        );
    }
}