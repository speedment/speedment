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
package com.speedment.runtime.test_support;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @author Per Minborg
 */
public class MockEntity {

    /**
     * This Field corresponds to the {@link Country} field that can be obtained
     * using the {@link Country#getId()} method.
     */
    public static final IntField<MockEntity, Integer> ID = IntField.create(
        Identifier.ID,
        MockEntity::getId,
        MockEntity::setId,
        TypeMapper.primitive(),
        true
    );

    public static final StringField<MockEntity, String> NAME = StringField.create(
        Identifier.NAME,
        MockEntity::getName,
        MockEntity::setName,
        TypeMapper.identity(),
        true
    );

    private int id;
    private String name;

    public MockEntity(int id) {
        this.id = id;
        this.name = "Name" + id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MockEntity setId(int id) {
        this.id = id;
        return this;
    }

    public MockEntity setName(String name) {
        this.name = name;
        return this;
    }

    enum Identifier implements ColumnIdentifier<MockEntity> {

        ID("id"),
        NAME("name");

        private final String columnName;
        private final TableIdentifier<MockEntity> tableIdentifier;

        Identifier(String columnName) {
            this.columnName = columnName;
            this.tableIdentifier = TableIdentifier.of(getDbmsId(),
                getSchemaId(),
                getTableId());
        }

        @Override
        public String getDbmsId() {
            return "db0";
        }

        @Override
        public String getSchemaId() {
            return "speedment_test";
        }

        @Override
        public String getTableId() {
            return "mock_entity";
        }

        @Override
        public String getColumnId() {
            return this.columnName;
        }

        @Override
        public TableIdentifier<MockEntity> asTableIdentifier() {
            return this.tableIdentifier;
        }
    }

}
