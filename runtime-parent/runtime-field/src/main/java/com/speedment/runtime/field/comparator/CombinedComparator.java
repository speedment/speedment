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
package com.speedment.runtime.field.comparator;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * A combined {@link Comparator} that compares a number of
 * {@link FieldComparator FieldComparators} in sequence.
 *
 * @param <ENTITY>  the entity type
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public interface CombinedComparator<ENTITY> extends Comparator<ENTITY>  {

    @Override
    CombinedComparator<ENTITY> reversed();

    /**
     * Returns the comparators ordered so that the first comparator is
     * the most significant, and if that evaluates to {@code 0}, continue on to
     * the next one.
     *
     * @return  list of comparators
     */
    Stream<FieldComparator<? super ENTITY>> stream();

    /**
     * The number of comparators in the {@link #stream()}.
     *
     * @return  the number of comparators
     */
    int size();
}