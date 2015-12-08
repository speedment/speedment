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
package com.speedment.internal.core.config.immutable;

import com.speedment.internal.core.config.aspects.*;
import com.speedment.config.aspects.Child;
import com.speedment.internal.core.config.ChildHolder;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static com.speedment.internal.core.config.immutable.ImmutableUtil.throwNewUnsupportedOperationExceptionImmutable;

/**
 *
 * @author pemi
 * @param <C> the type of the children
 */
public interface ImmutableParentHelper<C extends Child<?>> extends ParentHelper<C> {

    @Override
    default Optional<C> add(final C child) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }
    
    @Override
    default Optional<C> remove(final C child) {
        return throwNewUnsupportedOperationExceptionImmutable();
    }

    default ChildHolder<C> childHolderOf(Class<C> childClass, Stream<C> childs) {
        return ImmutableChildHolder.of(childClass, childs.collect(toList()));
    }
}