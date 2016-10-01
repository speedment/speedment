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
package com.speedment.runtime.core.internal.component;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.SqlStreamSupplierComponent;
import com.speedment.runtime.core.stream.StreamDecorator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class SqlStreamSupplierComponentImpl implements SqlStreamSupplierComponent {

    private final Map<TableIdentifier, SqlStreamSupplierComponent.Support> supportMap;

    public SqlStreamSupplierComponentImpl() {
        this.supportMap = new ConcurrentHashMap<>();
    }

    @Override
    public <ENTITY> Stream<ENTITY> stream(TableIdentifier<ENTITY> tableIdentifier, StreamDecorator decorator) {
        final SqlStreamSupplierComponent.Support support = supportMap.computeIfAbsent(tableIdentifier, this::makeSupport);

        return Stream.empty(); // Todo: Generate stream.
    }

    private SqlStreamSupplierComponent.Support makeSupport(TableIdentifier<?> tableIdentifier) {
        // Calculate stuff from the TableIdentifier and some other components...
        return null;
    }

}