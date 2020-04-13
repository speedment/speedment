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
package com.speedment.runtime.field.trait;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.method.FindFromNullable;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Trait for fields that in addition to {@link HasFinder}, also implement a
 * {@link #nullableFinder(TableIdentifier, Supplier)}-method.
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public interface HasNullableFinder<ENTITY, FK_ENTITY>
extends HasFinder<ENTITY, FK_ENTITY> {

    /**
     * Returns a function that can be used to find referenced entities using the
     * specified manager. This method is different from
     * {@link #finder(TableIdentifier, Supplier)} in that the returned value is
     * wrapped in a singleton stream.
     *
     * @param identifier      the table identifier
     * @param streamSupplier  the stream supplier
     * @return                finder method
     */
    FindFromNullable<ENTITY, FK_ENTITY> nullableFinder(
        TableIdentifier<FK_ENTITY> identifier,
        Supplier<Stream<FK_ENTITY>> streamSupplier);

}
