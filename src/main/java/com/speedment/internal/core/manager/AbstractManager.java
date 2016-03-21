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
package com.speedment.internal.core.manager;

import com.speedment.manager.Manager;
import com.speedment.Speedment;
import com.speedment.encoder.JsonEncoder;
import com.speedment.field.trait.ComparableFieldTrait;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.core.runtime.AbstractLifecycle;
import com.speedment.stream.StreamDecorator;
import java.util.stream.Stream;
import java.util.Optional;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author          Emil Forslund
 * @param <ENTITY>  entity type for this Manager
 */
public abstract class AbstractManager<ENTITY> extends AbstractLifecycle<Manager<ENTITY>> implements Manager<ENTITY> {

    protected final Speedment speedment;

    private final JsonEncoder<ENTITY> sharedJasonFormatter;

    protected AbstractManager(Speedment speedment) {
        this.speedment = requireNonNull(speedment);
        sharedJasonFormatter = JsonEncoder.allOf(this);
    }

    @Override
    public String toJson(ENTITY entity) {
        requireNonNull(entity);
        return sharedJasonFormatter.apply(entity);
    }

    @Override
    public Stream<ENTITY> stream(StreamDecorator decorator) {
        return speedment.getStreamSupplierComponent()
                .stream(getEntityClass(), decorator);
    }

    @Override
    public <D, V extends Comparable<? super V>, 
    F extends FieldTrait & ReferenceFieldTrait<ENTITY, D, V> & ComparableFieldTrait<ENTITY, D, V>> 
    Optional<ENTITY> findAny(F field, V value) {
        
        requireNonNull(field);
        return speedment.getStreamSupplierComponent()
                .findAny(getEntityClass(), field, value);
    }

    @Override
    public Speedment speedment() {
        return speedment;
    }
}