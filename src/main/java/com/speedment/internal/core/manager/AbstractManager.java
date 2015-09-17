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
package com.speedment.internal.core.manager;

import com.speedment.Manager;
import com.speedment.Speedment;
import com.speedment.config.Column;
import com.speedment.config.ForeignKey;
import com.speedment.config.Table;
import com.speedment.internal.core.field.encoder.JsonEncoder;
import com.speedment.internal.core.runtime.Lifecyclable;
import com.speedment.component.ManagerComponent;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author Emil Forslund
 *
 * @param <ENTITY> Entity type for this Manager
 */
public abstract class AbstractManager<ENTITY> implements Manager<ENTITY> {

    protected final Speedment speedment;

    private Lifecyclable.State state;

    private final JsonEncoder<ENTITY> sharedJasonFormatter;

    public AbstractManager(Speedment speedment) {
        this.speedment = requireNonNull(speedment);
        state = Lifecyclable.State.CREATED;
        sharedJasonFormatter = JsonEncoder.allOf(this);
    }

    @Override
    public String toJson(ENTITY entity) {
        requireNonNull(entity);
        return sharedJasonFormatter.apply(entity);
    }

//    @Override
//    @SuppressWarnings("unchecked")
//    public Optional<Object> find(ENTITY entity, Column column) {
//        requireNonNull(entity);
//        requireNonNull(column);
//        return getTable()
//            .streamOf(ForeignKey.class)
//            .flatMap(fk -> fk.stream().filter(fkc -> fkc.getColumn().equals(column)))
//            .map(oFkc -> {
//                Table fkTable = oFkc.getForeignTable();
//                Column fkColumn = oFkc.getForeignColumn();
//
//                @SuppressWarnings("rawtypes")
//                final Manager fkManager = speedment.get(ManagerComponent.class).findByTable(fkTable);
//
//                Object key = get(entity, column);
//
//                // This is an O(n) operation. We must use our short curcuit Fields...
//                return fkManager.stream().filter(e -> fkManager.get(e, fkColumn).equals(key)).findAny();
//            }).filter(o -> o.isPresent()).map(i -> i.get()).findAny();
//    }

    @Override
    public Manager<ENTITY> initialize() {
        state = State.INIITIALIZED;
        return this;
    }

    @Override
    public Manager<ENTITY> resolve() {
        state = State.RESOLVED;
        return this;
    }

    @Override
    public Manager<ENTITY> start() {
        state = State.STARTED;
        return this;
    }

    @Override
    public Manager<ENTITY> stop() {
        state = State.STOPPED;
        return this;
    }

    @Override
    public Lifecyclable.State getState() {
        return state;
    }

}
