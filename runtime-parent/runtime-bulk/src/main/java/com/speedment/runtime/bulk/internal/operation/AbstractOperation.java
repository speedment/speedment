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
package com.speedment.runtime.bulk.internal.operation;

import com.speedment.runtime.bulk.Operation.Type;
import com.speedment.runtime.core.manager.Manager;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public abstract class AbstractOperation<ENTITY> {

    private final Type type;
    private final Manager<ENTITY> manager;

    protected AbstractOperation(Type type, Manager<ENTITY> manager) {
        this.type = requireNonNull(type);
        this.manager = requireNonNull(manager);
    }

    public Manager<ENTITY> manager() {
        return manager;
    }

    public Type type() {
        return type;
    }

}
