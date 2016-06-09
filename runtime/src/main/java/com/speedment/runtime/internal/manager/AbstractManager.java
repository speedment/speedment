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
package com.speedment.runtime.internal.manager;

import static com.speedment.common.injector.State.RESOLVED;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.ManagerComponent;
import com.speedment.runtime.component.StreamSupplierComponent;
import com.speedment.runtime.field.trait.ComparableFieldTrait;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.manager.Manager;
import com.speedment.runtime.stream.StreamDecorator;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author          Emil Forslund
 * @param <ENTITY>  entity type for this Manager
 */
public abstract class AbstractManager<ENTITY> implements Manager<ENTITY> {

    private @Inject StreamSupplierComponent streamSupplierComponent;

    protected AbstractManager() {}
    
    @ExecuteBefore(RESOLVED)
    void install(@Inject ManagerComponent managerComponent) {
        managerComponent.put(this);
    }

    @Override
    public Stream<ENTITY> stream(StreamDecorator decorator) {
        return streamSupplierComponent
                .stream(getEntityClass(), decorator);
    }

    @Override
    public <D, V extends Comparable<? super V>, 
    F extends FieldTrait & ReferenceFieldTrait<ENTITY, D, V> & ComparableFieldTrait<ENTITY, D, V>> 
    Optional<ENTITY> findAny(F field, V value) {
        
        requireNonNull(field);
        return streamSupplierComponent
                .findAny(getEntityClass(), field, value);
    }
}