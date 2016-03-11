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
package com.speedment.internal.core.code;

import com.speedment.exception.SpeedmentException;
import com.speedment.encoder.JsonEncoder;
import com.speedment.manager.Manager;
import com.speedment.db.MetaResult;
import java.util.function.Consumer;
import com.speedment.Entity;
import com.speedment.Speedment;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public abstract class AbstractBaseEntity<ENTITY> implements Entity<ENTITY> {

    /**
     * Returns the Speedment instance. This method will be implemented by the
     * instantiating class using an anonymous class and should therefore not be
     * implemented in named child classes.
     * 
     * @return  the {@link Speedment} instance
     */
    protected abstract Speedment speedment();
   
    @Override
    public String toJson(JsonEncoder<ENTITY> encoder) {
        return requireNonNull(encoder).apply((selfAsEntity()));
    }

    @Override
    public String toJson() {
        return manager_().toJson(selfAsEntity());
    }

    @Override
    public ENTITY persist() throws SpeedmentException {
        return manager_().persist(selfAsEntity());
    }

    @Override
    public ENTITY update() throws SpeedmentException {
        return manager_().update(selfAsEntity());
    }

    @Override
    public ENTITY remove() throws SpeedmentException {
        return manager_().remove(selfAsEntity());
    }

    @Override
    public ENTITY persist(Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException {
        return manager_().persist(selfAsEntity(), consumer);
    }

    @Override
    public ENTITY update(Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException {
        return manager_().update(selfAsEntity(), consumer);
    }

    @Override
    public ENTITY remove(Consumer<MetaResult<ENTITY>> consumer) throws SpeedmentException {
        return manager_().remove(selfAsEntity(), consumer);
    }

    protected abstract Class<ENTITY> entityClass();

    @SuppressWarnings("unchecked")
    private ENTITY selfAsEntity() {
        return (ENTITY) this;
    }

    protected Manager<ENTITY> manager_() {
        return managerOf_(entityClass());
    }

    protected <T> Manager<T> managerOf_(Class<T> entityClass) {
        return speedment().managerOf(requireNonNull(entityClass));
    }
        
}
