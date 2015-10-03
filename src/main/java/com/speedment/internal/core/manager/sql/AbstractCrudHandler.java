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
package com.speedment.internal.core.manager.sql;

import com.speedment.Speedment;
import com.speedment.config.Dbms;
import com.speedment.db.CrudHandler;
import static java.util.Objects.requireNonNull;

/**
 * Abstract base class for different CrudHandlers.
 * 
 * @author Emil Forslund
 */
public abstract class AbstractCrudHandler implements CrudHandler {
    
    private final Speedment speedment;
    private final Dbms dbms;

    /**
     * Initiates this AbstractCrudHandler.
     * @param speedment  the speedment instance to use.
     */
    protected AbstractCrudHandler(Speedment speedment, Dbms dbms) {
        this.speedment = requireNonNull(speedment);
        this.dbms      = requireNonNull(dbms);
    }

    /**
     * Returns the current speedment instance.
     * 
     * @return  the speedment instance
     */
    protected final Speedment speedment() {
        return speedment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dbms getDbms() {
        return dbms;
    }
}