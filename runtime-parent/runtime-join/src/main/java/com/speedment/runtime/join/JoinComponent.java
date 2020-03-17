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
package com.speedment.runtime.join;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.join.builder.JoinBuilder1;

/**
 * a JoinComponent can be used to create builders for creating Join objects.
 * Join objects, in turn, can be used to stream over joined tables.
 * <p>
 * It is unspecified if objects returned by builder-type of objects return
 * itself or a new builder. Users should make no assumption of either case and
 * use a fluent API style of programming.
 * <p>
 * All methods that takes objects will throw a {@code NullPointerException} if
 * provided with one or several {@code null} values.
 * <p>
 * Intermediate builder methods are not thread safe.
 * <p>
 * Currently, this component can build joins of grade 2, 3, 4, 5, 6, 7, 8, 9 or
 * 10.
 *
 * @author Per Minborg
 * @since  3.1.0
 */
@InjectKey(JoinComponent.class)
public interface JoinComponent {

    /**
     * Adds a provided {@code firstTableIdentifier} to the collection of joined
     * managers. Rows are joined from the provided {@code firstTableIdentifier}
     * depending on how subsequent managers are added to the builder.
     *
     * @param <T0> type of entities for the first manager
     * @param firstTableIdentifier to use
     * @return a builder where the provided {@code firstTableIdentifier} is added
     *
     * @throws NullPointerException if the provided {@code firstTableIdentifier} is
     * {@code null}
     */
    <T0> JoinBuilder1<T0> from(TableIdentifier<T0> firstTableIdentifier);

}
