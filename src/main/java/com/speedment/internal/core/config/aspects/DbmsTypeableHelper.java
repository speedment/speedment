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
package com.speedment.internal.core.config.aspects;

import com.speedment.annotation.External;
import com.speedment.config.aspects.DbmsTypeable;
import com.speedment.internal.core.platform.component.DbmsHandlerComponent;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public interface DbmsTypeableHelper extends DbmsTypeable {

    @Override
    default String getTypeName() {
        return getType().getName();
    }

    /**
     *
     * @param name the type name of the dbms
     * @throws IllegalArgumentException if a DbmsType for the given dbmsTypeName
     * could not be found
     */
    @Override
    default void setTypeName(String name) {
        requireNonNull(name);
        setType(getSpeedment()
            .get(DbmsHandlerComponent.class)
            .findByName(name)
            .orElseThrow(IllegalArgumentException::new)
        );
    }
}
