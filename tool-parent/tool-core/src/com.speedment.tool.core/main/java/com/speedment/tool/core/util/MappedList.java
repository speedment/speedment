/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.core.util;


import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 * A list mapped to a transformation list.
 * 
 * @author        Emil Forslund
 * @param <FROM>  the type to map from
 * @param <TO>    the type to map to
 */
public final class MappedList<FROM, TO> extends TransformationList<TO, FROM> {

    private final Function<FROM, TO> mapper;

    public MappedList(ObservableList<? extends FROM> source, Function<FROM, TO> mapper) {
        super(source);
        this.mapper = requireNonNull(mapper);
    }

    @Override
    public int getSourceIndex(int index) {
        return index;
    }

    @Override
    public TO get(int index) {
        return mapper.apply(getSource().get(index));
    }

    // Java 9 hack
    public int getViewIndex(int index) {
        return index;
    }
    
    @Override
    public int size() {
        return getSource().size();
    }

    @Override
    protected void sourceChanged(Change<? extends FROM> c) {
        fireChange(new Change<TO>(this) {

            @Override
            public boolean wasAdded() {
                return c.wasAdded();
            }

            @Override
            public boolean wasRemoved() {
                return c.wasRemoved();
            }

            @Override
            public boolean wasReplaced() {
                return c.wasReplaced();
            }

            @Override
            public boolean wasUpdated() {
                return c.wasUpdated();
            }

            @Override
            public boolean wasPermutated() {
                return c.wasPermutated();
            }

            @Override
            public int getPermutation(int i) {
                return c.getPermutation(i);
            }

            @Override
            protected int[] getPermutation() {
                // This method is only called by the superclass methods
                // wasPermutated() and getPermutation(int), which are
                // both overriden by this class. There is no other way
                // this method can be called.
                throw new AssertionError("Unreachable code");
            }

            @Override
            public List<TO> getRemoved() {
                ArrayList<TO> res = new ArrayList<>(c.getRemovedSize());
                for (final FROM e : c.getRemoved()) {
                    res.add(mapper.apply(e));
                }
                return res;
            }

            @Override
            public int getFrom() {
                return c.getFrom();
            }

            @Override
            public int getTo() {
                return c.getTo();
            }

            @Override
            public boolean next() {
                return c.next();
            }

            @Override
            public void reset() {
                c.reset();
            }
        });
    }
}