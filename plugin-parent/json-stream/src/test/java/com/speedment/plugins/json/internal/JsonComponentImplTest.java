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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.plugins.json.internal;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.exception.NoDefaultConstructorException;
import com.speedment.plugins.json.JsonBundle;
import com.speedment.plugins.json.JsonComponent;
import com.speedment.plugins.json.JsonEncoder;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.manager.Manager;
import com.speedment.runtime.field.IntField;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Objects.requireNonNull;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Per Minborg
 */
//TODO: Figure out if the tests here are actually testing for the correct values and fix the disabled test
    // Oh, and don't just comment non functioning tests, actually fix them.
@ExtendWith(MockitoExtension.class)
final class JsonComponentImplTest {

    @Mock
    private Project project;
    @Mock
    private Manager<Person> persons;
    @Mock
    private ProjectComponent projectComponent;
    @InjectMocks
    private JsonComponentImpl jsonComponent;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void init() {
        final Stream<Person> personStream = IntStream.range(0, 8).mapToObj(Person::new);
        when(persons.stream()).thenReturn(personStream);
        when(projectComponent.getProject()).thenReturn(project);
    }

    @Test
    void testNoneOf() {
        final JsonEncoder<Person> result = jsonComponent.noneOf(persons);
        final String json = persons.stream().collect(result.collector());
        assertEquals("[{},{},{},{},{},{},{},{}]", json);
    }

//    @Test
//    public void testAllOf() {
//        when(persons.fields()).thenReturn(Stream.of(Person.ID, Person.NAME));
//        final JsonEncoder<Person> result = jsonComponent.allOf(persons);
//        final String json = persons.stream().collect(result.collector());
//        System.out.println(json);
//    }
//
//    @Test
//    public void testOf() {
//        System.out.println("of");
//        @SuppressWarnings("unchecked")
//        final JsonEncoder<Person> result = jsonComponent.of(persons, Person.ID, Person.NAME);
//        final String json = persons.stream().collect(result.collector());
//        System.out.println(json);
//    }

    private static final class Person {

        public static IntField<Person, Integer> ID = IntField.create(
            Identifier.ID,
            Person::getId,
            Person::setId,
            TypeMapper.primitive(),
            true
        );
        /**
         * This Field corresponds to the {@link Person} field that can be
         * obtained using the {@link Person#getName()} method.
         */
        public static StringField<Person, String> NAME = StringField.create(
            Identifier.NAME,
            Person::getName,
            Person::setName,
            TypeMapper.identity(),
            false
        );

        private int id;
        private String name;

        public Person(int id) {
            this.id = id;
            this.name = "name" + id;
        }

        public int getId() {
            return id;
        }

        public Person setId(int id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public Person setName(String name) {
            this.name = name;
            return this;
        }

        enum Identifier implements ColumnIdentifier<Person> {

            ID("id"),
            NAME("name");

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
                return "schema";
            }

            @Override
            public String getTableId() {
                return "person";
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

    private JsonComponent newComponent() {
        try {
            Injector injector = Injector.builder()
                .withComponent(MyProjectComponent.class)
                .withBundle(JsonBundle.class)
                .build();

            return injector.getOrThrow(JsonComponent.class);

        } catch (InstantiationException | NoDefaultConstructorException e) {
            throw new RuntimeException(e);
        }
    }

    private static final class MyProjectComponent implements ProjectComponent {

        private Project project;

        public MyProjectComponent() {
            final Project project = mock(Project.class);
            setProject(project);
        }

        @Override
        public Project getProject() {
            return project;
        }

        @Override
        public void setProject(Project project) {
            this.project = requireNonNull(project);
        }

    }

}
