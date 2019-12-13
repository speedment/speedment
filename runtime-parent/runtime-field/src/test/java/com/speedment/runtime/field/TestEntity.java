/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.field;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @author pemi
 */
public interface TestEntity {
    
    enum Identifier implements ColumnIdentifier<TestEntity> {
        ID("id"), NAME("name");
        
        private final String columnName;
        
        Identifier(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String getColumnId() {
            return columnName;
        }

        @Override
        public String getDbmsId() {
            return "my_dbms";
        }

        @Override
        public String getSchemaId() {
            return "my_schema";
        }

        @Override
        public String getTableId() {
            return "my_table";
        }
    }

    ComparableField<TestEntity, Integer, Integer> ID = ComparableField.create(Identifier.ID, TestEntity::getId, TestEntity::setId, TypeMapper.identity(), true);

    StringField<TestEntity, String> NAME = StringField.create(
        Identifier.NAME, 
        TestEntity::getName,
        TestEntity::setName,
        TypeMapper.identity(),
        false
    );

    StringField<TestEntity, String> FK_NAME = StringForeignKeyField.create(
        Identifier.NAME,
        TestEntity::getName,
        TestEntity::setName,
        NAME,
        TypeMapper.identity(),
        false
    );


    Integer getId();

    String getName();

    TestEntity setId(Integer id);

    TestEntity setName(String name);

}
