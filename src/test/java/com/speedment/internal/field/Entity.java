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
package com.speedment.internal.field;

import com.speedment.field2.ComparableField;
import com.speedment.field2.StringField;
import com.speedment.internal.core.field2.ComparableFieldImpl;
import com.speedment.internal.core.field2.StringFieldImpl;

/**
 *
 * @author pemi
 */
public interface Entity {

    public final static ComparableField<Entity, Integer> ID = new ComparableFieldImpl<>("id", Entity::getId, Entity::setId);
    public final static StringField<Entity> NAME = new StringFieldImpl<>("name", Entity::getName, Entity::setName);

    public Integer getId();

    public String getName();

    public Entity setId(Integer id);

    public Entity setName(String name);

}
