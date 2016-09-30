/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.field.streamer;

import com.speedment.runtime.core.field.Field;
import com.speedment.runtime.core.field.method.BackwardFinder;
import com.speedment.runtime.core.field.trait.HasComparableOperators;
import com.speedment.runtime.core.field.trait.HasFinder;
import com.speedment.runtime.core.manager.Manager;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>     the source entity
 * @param <FK_ENTITY>  the target entity
 * @param <T>          the column type
 * 
 * @author  Emil Forslund
 * @since   3.0.0
 */
public class BackwardFinderImpl<ENTITY, FK_ENTITY, T extends Comparable<? super T>, FIELD extends Field<FK_ENTITY> & HasComparableOperators<FK_ENTITY, T> & HasFinder<FK_ENTITY, ENTITY>>
    implements BackwardFinder<ENTITY, FK_ENTITY> {

    private final FIELD target;
    private final Manager<FK_ENTITY> manager;
    
    public BackwardFinderImpl(FIELD target, Manager<FK_ENTITY> manager) {
        this.target  = requireNonNull(target);
        this.manager = requireNonNull(manager);
    }
    
    @Override
    public final FIELD getField() {
        return target;
    }

    @Override
    public final Manager<FK_ENTITY> getTargetManager() {
        return manager;
    }

    @Override
    public Stream<FK_ENTITY> apply(ENTITY entity) {
        @SuppressWarnings("unchecked")
        final T value = (T) getField().getReferencedField().getter().apply(entity);
        if (value == null) {
            return null;
        } else {
            return getTargetManager().stream().filter(getField().equal(value));
        }
    }
}