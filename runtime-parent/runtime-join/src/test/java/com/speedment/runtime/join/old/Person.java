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
package com.speedment.runtime.join.old;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 *
 * @author Per Minborg
 */
public class Person {

    private int id;
    private int age;

    private static final IntField<Person, Integer> ID
        = IntField.create(
            Identifier.ID,
            Person::getId,
            Person::setId,
            TypeMapper.primitive(),
            true
        );

    private static final IntField<Person, Integer> AGE
        = IntField.create(
            Identifier.AGE,
            Person::getAge,
            Person::setAge,
            TypeMapper.primitive(),
            true
        );

    int getId() {
        return id;
    }

    Person setId(int id) {
        this.id = id;
        return this;
    }

    int getAge() {
        return age;
    }

    Person setAge(int age) {
        this.age = age;
        return this;
    }

    enum Identifier implements ColumnIdentifier<Person> {
        ID("id"),
        AGE("age");

        private final String columnName;
        private final TableIdentifier<Person> tableIdentifier;

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
            return "SPEEDMENT";
        }

        @Override
        public String getTableId() {
            return "PERSON";
        }

        @Override
        public String getColumnId() {
            return this.columnName;
        }

        @Override
        public TableIdentifier<Person> asTableIdentifier() {
            return this.tableIdentifier;
        }

    }

}
