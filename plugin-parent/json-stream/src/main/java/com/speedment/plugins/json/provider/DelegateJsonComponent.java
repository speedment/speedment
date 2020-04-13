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
package com.speedment.plugins.json.provider;

import com.speedment.plugins.json.JsonComponent;
import com.speedment.plugins.json.JsonEncoder;
import com.speedment.plugins.json.internal.JsonComponentImpl;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.Field;

public final class DelegateJsonComponent implements JsonComponent {

    private final JsonComponentImpl inner;

    public DelegateJsonComponent(ProjectComponent projectComponent) {
        this.inner = new JsonComponentImpl(projectComponent);
    }

    @Override
    public <ENTITY> JsonEncoder<ENTITY> noneOf(Manager<ENTITY> manager) {
        return inner.noneOf(manager);
    }

    @Override
    public <ENTITY> JsonEncoder<ENTITY> allOf(Manager<ENTITY> manager) {
        return inner.allOf(manager);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    @Override
    public final <ENTITY> JsonEncoder<ENTITY> of(Manager<ENTITY> manager, Field<ENTITY>... fields) {
        return inner.of(manager, fields);
    }

}
