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
package com.speedment.runtime.bulk;

import com.speedment.runtime.core.manager.Manager;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> tyep
 */
public interface Operation<ENTITY> {

    enum Type {
        PERSIST, UPDATE, REMOVE
    }

    /**
     * Returns the Manager for this Operation.
     *
     * @return the Manager for this Operation
     */
    Manager<ENTITY> manager();

    /**
     * Returns the type of Operation.
     *
     * @return the type of Operation
     */
    Type type();

}
