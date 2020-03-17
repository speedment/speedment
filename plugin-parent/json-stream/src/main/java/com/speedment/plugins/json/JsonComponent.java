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
package com.speedment.plugins.json;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.Field;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
@InjectKey(JsonComponent.class)
public interface JsonComponent {

    /**
     * Creates and return a new JsonEncoder with no fields added to the
     * renderer.
     *
     * @param <ENTITY> the Entity type
     * @param manager of the entity
     * @return a new JsonEncoder with no fields added to the renderer
     */
    <ENTITY> JsonEncoder<ENTITY> noneOf(Manager<ENTITY> manager);

    /**
     * Creates and return a new JsonEncoder with all the Entity fields added to
     * the renderer. The field(s) will be rendered using their default class
     * renderer.
     *
     * @param <ENTITY> the Entity type
     * @param manager of the entity
     * @return a new JsonEncoder with all the Entity fields added to the
     * renderer
     */
    <ENTITY> JsonEncoder<ENTITY> allOf(Manager<ENTITY> manager);

    /**
     * Creates and return a new JsonEncoder with the provided Entity field(s)
     * added to the renderer. The field(s) will be rendered using their default
     * class renderer.
     *
     * @param <ENTITY> the Entity type
     * @param manager of the ENTITY
     * @param fields to add to the output renderer
     * @return a new JsonEncoder with the specified fields added to the renderer
     */
    @SuppressWarnings ({"unchecked", "varargs"})
    <ENTITY> JsonEncoder<ENTITY> of(Manager<ENTITY> manager, Field<ENTITY>... fields);
    
}