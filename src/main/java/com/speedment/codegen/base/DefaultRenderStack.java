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
package com.speedment.codegen.base;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 * @author Emil Forslund
 */
public class DefaultRenderStack implements RenderStack {
    
    private final Deque<Object> stack;
    
    public DefaultRenderStack() {
        stack = new ArrayDeque<>();
    }
    
    public DefaultRenderStack(DefaultRenderStack prototype) {
        stack = new ArrayDeque<>(prototype.stack);
    }
    
    public void push(Object obj) {
        stack.push(obj);
    }
    
    public Object pop() {
        return stack.pop();
    }

    @Override
    public <T> Stream<T> fromBottom(Class<T> type) {
        return all(type, stack.descendingIterator());
    }

    @Override
    public <T> Stream<T> fromTop(Class<T> type) {
        return all(type, stack.iterator());
    }
    
    @SuppressWarnings("unchecked")
    private static <T> Stream<T> all(Class<T> type, Iterator<Object> i) {
        return all(i).filter(o -> type.isAssignableFrom(o.getClass()))
            .map(o -> (T) o);
    }
    
    private static Stream<?> all(Iterator<Object> i) {
        final Iterable<Object> it = () -> i;
        return StreamSupport.stream(it.spliterator(), false);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
