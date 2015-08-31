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
package com.speedment.internal.codegen.base;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The default {@link RenderStack} implementation.
 * 
 * @author Emil Forslund
 */
public class DefaultRenderStack implements RenderStack {
    
    private final Deque<Object> stack;
    
    /**
     * Constructs the stack.
     */
    public DefaultRenderStack() {
        stack = new ArrayDeque<>();
    }
    
    /**
     * Constructs the stack using an existing stack as a prototype. This creates
     * a shallow copy of that stack.
     * 
     * @param prototype  the prototype
     */
    public DefaultRenderStack(DefaultRenderStack prototype) {
        stack = new ArrayDeque<>(requireNonNull(prototype).stack);
    }
    
    /**
     * Put the specified object on the stack.
     * 
     * @param obj  the object to push
     */
    public void push(Object obj) {
        stack.push(requireNonNull(obj));
    }
    
    /**
     * Returns the latest element from the stack, removes it.
     * 
     * @return  the latest added element
     */
    public Object pop() {
        return stack.pop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Stream<T> fromBottom(Class<T> type) {
        return all(requireNonNull(type), stack.descendingIterator());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Stream<T> fromTop(Class<T> type) {
        return all(requireNonNull(type), stack.iterator());
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
    
    /**
     * Creates a <code>Stream</code> over all the elements of the stack using an
     * <code>Iterator</code>. Only elements of the specified type is included.
     * 
     * @param <T>   the type to look for
     * @param type  the type to look for
     * @param i     the iterator
     * @return      a stream of all the models in the stack of that type
     */
    @SuppressWarnings("unchecked")
    private static <T> Stream<T> all(Class<T> type, Iterator<Object> i) {
        requireNonNull(type);
        requireNonNull(i);
        
        return all(i).filter(o -> type.isAssignableFrom(o.getClass()))
            .map(o -> (T) o);
    }
    
    /**
     * Creates a <code>Stream</code> over all the elements of the stack using an
     * <code>Iterator</code>.
     * 
     * @param i     the iterator
     * @return      a stream of all the models in the stack
     */
    private static Stream<?> all(Iterator<Object> i) {
        requireNonNull(i);
        final Iterable<Object> it = () -> i;
        return StreamSupport.stream(it.spliterator(), false);
    }
}