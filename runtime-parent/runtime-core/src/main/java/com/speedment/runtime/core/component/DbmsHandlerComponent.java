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
package com.speedment.runtime.core.component;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.db.DbmsType;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class holds the various {@link DbmsType DbmsTypes} 
 * currently installed in the Speedment platform.
 *
 * @author  Per Minborg
 * @since   2.0.0
 */
@InjectKey(DbmsHandlerComponent.class)
public interface DbmsHandlerComponent  {

    /**
     * Installs a new {@link DbmsType} so that handlers can be located using the
     * {@link #findByName(String)} method.
     * <p>
     * The type will be indexed by its name as returned by
     * {@link DbmsType#getName()}. If multiple {@code DbmsTypes} share name,
     * only the most recently installed will be saved.
     *
     * @param dbmsType the type to install
     */
    void install(DbmsType dbmsType);

    /**
     * Returns a stream of all {@link DbmsType DbmsTypes} that has been
     * installed in this component.
     *
     * @return a stream of installed DbmsTypes.
     */
    Stream<DbmsType> supportedDbmsTypes();

    /**
     * Searches for the specified {@link DbmsType} by its name as defined by
     * {@link DbmsType#getName()}. If none is found, an {@code empty} is
     * returned.
     *
     * @param dbmsTypeName the name to search for
     * @return the {@link DbmsType} found or {@code empty}
     */
    Optional<DbmsType> findByName(String dbmsTypeName);
}
