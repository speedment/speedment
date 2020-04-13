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
package com.speedment.plugins.json.internal;

import com.speedment.plugins.json.JsonComponent;
import com.speedment.plugins.json.JsonEncoder;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.Field;

import java.util.Set;
import java.util.stream.Stream;

import static com.speedment.common.invariant.NullUtil.requireNonNullElements;
import static com.speedment.plugins.json.internal.JsonUtil.jsonField;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class JsonComponentImpl implements JsonComponent {
    
    private final ProjectComponent projectComponent;

    public JsonComponentImpl(ProjectComponent projectComponent) {
        this.projectComponent = requireNonNull(projectComponent);
    }

    @Override
    public <ENTITY> JsonEncoder<ENTITY> noneOf(Manager<ENTITY> manager) {
        return new JsonEncoderImpl<>(projectComponent.getProject(), manager);
    }

    @Override
    public <ENTITY> JsonEncoder<ENTITY> allOf(Manager<ENTITY> manager) {
        requireNonNull(manager);

        final JsonEncoder<ENTITY> formatter = noneOf(manager);

        manager.fields()
            .forEachOrdered(f ->
                formatter.put(jsonField(projectComponent.getProject(), f.identifier()),
                    f.getter()::apply
                )
            );

        return formatter;
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    @Override
    public final <ENTITY> JsonEncoder<ENTITY> of(Manager<ENTITY> manager, Field<ENTITY>... fields) {
        requireNonNull(manager);
        requireNonNullElements(fields);
        final JsonEncoder<ENTITY> formatter = noneOf(manager);

        final Set<String> fieldNames = Stream.of(fields)
            .map(Field::identifier)
            .map(ColumnIdentifier::getColumnId)
            .collect(toSet());

        manager.fields()
            .filter(f -> fieldNames.contains(f.identifier().getColumnId()))
            .forEachOrdered(f
                -> formatter.put(jsonField(projectComponent.getProject(), f.identifier()),
                    f.getter()::apply
                )
            );

        return formatter;
    }
}