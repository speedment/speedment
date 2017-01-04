/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.field;

/**
 *
 * @author pemi
 */
public class EntityImpl implements Entity {

    private Integer id;
    private String name;

    public EntityImpl(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Entity setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public Entity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return EntityImpl.class.getSimpleName() + " { id: " + id + ", name: \"" + name + "\"}";
    }

}
