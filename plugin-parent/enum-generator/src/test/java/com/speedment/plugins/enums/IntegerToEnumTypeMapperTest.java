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
import com.speedment.generator.translator.provider.StandardJavaLanguageNamer;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.util.DocumentDbUtil;
import com.speedment.runtime.typemapper.TypeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

final class IntegerToEnumTypeMapperTest {

    private static final String COLUMN_NAME = "name";

    private Column column;
    private Injector injector;
    private Project project;

    private IntegerToEnumTypeMapper<User.Name> instance;

    public static final class User {
        public enum Name {
            ZERO {
                public Integer toDatabase() {
                    return 0;
                }
            },
            ONE,
            TWO {
                private Integer toDatabase() {
                    return -999;
                }
            };

            public static User.Name fromDatabase(final Integer databaseName) {
                if (databaseName == null) {
                    return null;
                }
                switch (databaseName) {
                    case 0:
                        return Name.ZERO;
                    case 1:
                        return Name.ONE;
                    case 2:
                        return Name.TWO;
                    default:
                        throw new UnsupportedOperationException();
                }
            }
        }
    }


    @BeforeEach
    void setup() throws InstantiationException {
        project = TestUtil.project();
        column = DocumentDbUtil.referencedColumn(project,"speedment_test","speedment_test", "user", COLUMN_NAME);

        injector = Injector.builder().withComponent(StandardJavaLanguageNamer.class).build();
        instance = new IntegerToEnumTypeMapper<>();
        injector.inject(instance);
    }

    @Test
    void getLabel() {
        assertTrue(instance.getLabel().toLowerCase().contains("enum"));
    }

    @Test
    @Disabled("Why is this mapper not applicable to the tool?")
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
        final User.Name actual = instance.toJavaType(column, User.class, 0);
        assertEquals(User.Name.ZERO,  actual);
        // Test again if cached
        assertEquals(User.Name.ZERO,  instance.toJavaType(column, User.class, 0));
    }

    @Test
    void toJavaTypeNull() {
        assertNull(instance.toJavaType(column, User.class, null));
    }

    @Test
    void toJavaTypeOutOfBounds() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> instance.toJavaType(column, User.class, 43));
    }

    @Test
    void toDatabaseType() {
        assertEquals(0, instance.toDatabaseType(User.Name.ZERO));
    }

    @Test
    void toDatabaseTypeNull() {
        assertNull(instance.toDatabaseType(null));
    }


}