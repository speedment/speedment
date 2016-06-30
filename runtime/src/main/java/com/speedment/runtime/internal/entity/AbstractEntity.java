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
package com.speedment.runtime.internal.entity;

import com.speedment.runtime.entity.Entity;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.manager.Manager;

/**
 * Abstract base class that makes it easier to implements the {@link Entity}
 * interface.
 * 
 * @param <ENTITY> the entity type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.1.0
 */
public abstract class AbstractEntity<ENTITY> implements Entity<ENTITY> {

    /**
     * The main interface for this entity type.
     * 
     * @return  the main interface
     */
    protected abstract Class<ENTITY> entityClass();

    @Override
    public ENTITY persist(Manager<ENTITY> manager) throws SpeedmentException {
        return manager.persist(selfAsEntity());
    }

    @Override
    public ENTITY update(Manager<ENTITY> manager) throws SpeedmentException {
        return manager.update(selfAsEntity());
    }

    @Override
    public ENTITY remove(Manager<ENTITY> manager) throws SpeedmentException {
        return manager.remove(selfAsEntity());
    }
    
    private ENTITY selfAsEntity() {
        @SuppressWarnings("unchecked")
        final ENTITY self = (ENTITY) this;
        return self;
    }
}