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
package com.speedment.runtime.internal.field;

import com.speedment.runtime.config.mapper.identity.IntegerIdentityMapper;
import com.speedment.runtime.config.mapper.identity.StringIdentityMapper;
import com.speedment.runtime.field.ComparableField;
import com.speedment.runtime.config.identifier.FieldIdentifier;
import com.speedment.runtime.field.StringField;

/**
 *
 * @author pemi
 */
public interface Entity {
    
    enum Identifier implements FieldIdentifier<Entity> {
        ID("id"), NAME("name");
        
        private final String columnName;
        
        Identifier(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String columnName() {
            return columnName;
        }

        @Override
        public String dbmsName() {
            return "my_dbms";
        }

        @Override
        public String schemaName() {
            return "my_schema";
        }

        @Override
        public String tableName() {
            return "my_table";
        }
    }

    public final static ComparableField<Entity, Integer, Integer> ID = new ComparableFieldImpl<>(Identifier.ID, Entity::getId, Entity::setId, new IntegerIdentityMapper(), true);
    public final static StringField<Entity, String> NAME = new StringFieldImpl<>(Identifier.NAME, Entity::getName, Entity::setName, new StringIdentityMapper(), false);

    public Integer getId();

    public String getName();

    public Entity setId(Integer id);

    public Entity setName(String name);

}
