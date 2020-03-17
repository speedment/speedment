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
package com.speedment.plugins.enums;

import com.speedment.common.injector.Injector;
import com.speedment.common.json.Json;
import com.speedment.generator.translator.provider.StandardJavaLanguageNamer;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.ProjectUtil;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.config.util.DocumentTranscoder;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class StringToEnumTypeMapperTest {

    private static final String COLUMN_NAME = "name";

    private Column column;
    private Injector injector;
    private Project project;

    private StringToEnumTypeMapper<User.Name> instance;

    public static final class User {
        public enum Name {
            ONE {
                public String toDatabase() {
                    return "One";
                }
            },
            TWO,
            THREE {
                private String toDatabase() {
                    return "WillNotBeInvokedBecausePrivate";
                }
            };

            public static Name fromDatabase(final String databaseName) {
                if (databaseName == null) {
                    return null;
                }
                switch (databaseName) {
                    case "ONE":
                        return Name.ONE;
                    case "TWO":
                        return Name.TWO;
                    case "THREE":
                        return Name.THREE;
                    default:
                        throw new UnsupportedOperationException();
                }
            }
        }
    }

    public static final class A {

        // Used to simulate enum without a "fromDatabase" methods
        public enum NamE {
            ONE
        }
    }

    public static final class B {
        // Used to simulate enum with a "fromDatabase" method that cannot be accessed
        public enum NaMe {
            ONE;

            public static NaMe fromDatabase(final String databaseName) throws IllegalAccessException {
                throw new IllegalAccessException("Can't run me!");
            }

        }
    }

    @BeforeEach
    void setup() throws InstantiationException {
        project = TestUtil.project();
        column = DocumentDbUtil.referencedColumn(project,"speedment_test","speedment_test", "user", COLUMN_NAME);

        injector = Injector.builder().withComponent(StandardJavaLanguageNamer.class).build();
        instance = new StringToEnumTypeMapper<>();
        injector.inject(instance);
    }

    @Test
    void getLabel() {
        assertTrue(instance.getLabel().toLowerCase().contains("enum"));
    }

    @Test
    void isToolApplicable() {
        assertTrue(instance.isToolApplicable());
    }

    @Test
    void getJavaType() {
        final Type type = instance.getJavaType(column);
        assertTrue(type.getTypeName().toLowerCase().contains(COLUMN_NAME.toLowerCase()));
    }

    @Test
    void getJavaTypeNull() {
        assertThrows(NullPointerException.class, () ->  instance.getJavaType(null));
    }

    @Test
    void getJavaTypeCategory() {
        assertEquals(TypeMapper.Category.ENUM, instance.getJavaTypeCategory(column));
    }

    @Test
    void toJavaType() {
        final User.Name actual = instance.toJavaType(column, User.class, "ONE");
        assertEquals(User.Name.ONE,  actual);
        // Test again if cached
        assertEquals(User.Name.ONE,  instance.toJavaType(column, User.class, "ONE"));
    }

    @Test
    void toJavaTypeNull() {
        assertNull(instance.toJavaType(column, User.class, null));
    }

    @Test
    void toJavaTypeNoToDatabaseMethod() {
        final StringToEnumTypeMapper<A.NamE> tm = new StringToEnumTypeMapper<>();
        injector.inject(tm);
        assertThrows(NoSuchElementException.class, () -> tm.toJavaType(column, A.class, "ONE"));
    }

    @Test
    void toJavaTypeUnableToAccessToDatabaseMethod() {
        final StringToEnumTypeMapper<B.NaMe> tm = new StringToEnumTypeMapper<>();
        injector.inject(tm);
        assertThrows(IllegalArgumentException.class, () -> tm.toJavaType(column, B.class, "ONE"));
    }

    @Test
    void toDatabaseType() {
        assertEquals("One", instance.toDatabaseType(User.Name.ONE));
    }

    @Test
    void toDatabaseTypeNull() {
        assertNull(instance.toDatabaseType(null));
    }

    @Test
    void toDatabaseTypeNoSuchMethod() {
        assertThrows(IllegalArgumentException.class, () -> instance.toDatabaseType(User.Name.TWO));
    }

    @Test
    void toDatabaseTypeIllegalAccess() {
        assertThrows(IllegalArgumentException.class, () -> instance.toDatabaseType(User.Name.THREE));
    }

}