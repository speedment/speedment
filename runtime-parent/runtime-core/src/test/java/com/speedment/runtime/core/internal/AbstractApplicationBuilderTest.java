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
package com.speedment.runtime.core.internal;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.core.Speedment;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.junit.Assert;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class AbstractApplicationBuilderTest {

    private static final String COLUMN_NAME = "column_name";
    private static final String TABLE_NAME = "table_name";
    private static final String SCHEMA_NAME = "schema_name";
    private static final String DBMS_NAME = "dbms_name";
    private static final ColumnIdentifier<Object> COLUMN_IDENTITY = new ColumnIdentifier<Object>() {
        @Override
        public String getDbmsName() {
            return DBMS_NAME;
        }

        @Override
        public String getSchemaName() {
            return SCHEMA_NAME;
        }

        @Override
        public String getTableName() {
            return TABLE_NAME;
        }

        @Override
        public String getColumnName() {
            return COLUMN_NAME;
        }

    };

    private AbstractApplicationBuilder<Speedment, DefaultApplicationBuilder> instance;

    @Before
    public void setUp() {
        instance = new DefaultApplicationBuilder(TestApplicationMetadata.class)
            .withSkipCheckDatabaseConnectivity()
            .withSkipLogoPrintout()
            .withSkipValidateRuntimeConfig();
        //instance = new AbstractApplicationBuilderImpl<>(Injector.builder());
    }

    @Test
    public void checkVersion() {
        final Map<String, Optional<Boolean>> testVector = new LinkedHashMap<>();

        testVector.put("1.8.0_39", Optional.of(Boolean.FALSE));
        testVector.put("1.8.0_40", Optional.of(Boolean.TRUE));
        testVector.put("1.8.0_101", Optional.of(Boolean.TRUE));
        testVector.put("1.7.0_40", Optional.of(Boolean.FALSE));
        testVector.put("0.8.0_40", Optional.of(Boolean.FALSE));
        testVector.put("Arne", Optional.empty());

        testVector.entrySet().forEach((e) -> {
            final Optional<Boolean> expected = e.getValue();
            final Optional<Boolean> result = instance.isVersionOk(e.getKey());
            assertEquals(e.getKey(), expected, result);
        });

    }

    @Test
    public void withColumnTest() {
        withDocTest((ref) -> instance.withColumn(COLUMN_IDENTITY, ref::set));
    }

    @Test
    public void withTableTest() {
        withDocTest((ref) -> instance.withTable(COLUMN_IDENTITY, ref::set));
    }

    @Test
    public void withSchemaTest() {
        withDocTest((ref) -> instance.withSchema(COLUMN_IDENTITY, ref::set));
    }

    @Test
    public void withDbmsTest() {
        withDocTest((ref) -> instance.withDbms(COLUMN_IDENTITY, ref::set));
    }

    @Test
    public void withPasswordCharArrayTest() {
        final char[] expected = "Olle".toCharArray();
        instance.withPassword(COLUMN_IDENTITY, expected);
        final char[] actual = instance.build().getOrThrow(PasswordComponent.class).get(DBMS_NAME).get();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void withPasswordStringTest() {
        final String expected = "Olle";
        instance.withPassword(COLUMN_IDENTITY, expected);
        final String actual = String.valueOf(instance.build().getOrThrow(PasswordComponent.class).get(DBMS_NAME).get());
        assertEquals(expected, actual);
    }

    @Test
    public void withUsername() {
        final String expected = "SvenGlenn";
        instance.withUsername(COLUMN_IDENTITY, expected);
        final String actual = instance.build().getOrThrow(ProjectComponent.class).getProject().dbmses().findFirst().get().getUsername().get();
        assertEquals(expected, actual);
    }

    @Test
    public void withIpAddressTest() {
        final String expected = "Olle";
        instance.withIpAddress(COLUMN_IDENTITY, expected);
        final String actual = instance.build().getOrThrow(ProjectComponent.class).getProject().dbmses().findFirst().get().getIpAddress().get();
        assertEquals(expected, actual);
    }

    @Test
    public void withPortTest() {
        final int expected = 42;
        instance.withPort(COLUMN_IDENTITY, expected);
        final int actual = instance.build().getOrThrow(ProjectComponent.class).getProject().dbmses().findFirst().get().getPort().getAsInt();
        assertEquals(expected, actual);
    }

    private void withDocTest(Consumer<AtomicReference<Object>> consumer) {
        final AtomicReference<Object> tableRef = new AtomicReference<>();
        consumer.accept(tableRef);
        instance.build();
        Assert.assertNotNull(tableRef.get());
    }

    private static class TestApplicationMetadata extends AbstractApplicationMetadata {

        @Override
        protected Optional<String> getMetadata() {
            return Optional.of("{\n"
                + "  \"config\" : {\n"
                + "    \"companyName\" : \"company\",\n"
                + "    \"name\" : \"speedment_test\",\n"
                + "    \"packageLocation\" : \"src/main/java/\",\n"
                + "    \"dbmses\" : [\n"
                + "      {\n"
                + "        \"expanded\" : true,\n"
                + "        \"port\" : 3306,\n"
                + "        \"schemas\" : [\n"
                + "          {\n"
                + "            \"tables\" : [\n"
                + "              {\n"
                + "                \"expanded\" : true,\n"
                + "                \"columns\" : [\n"
                + "                  {\n"
                + "                    \"databaseType\" : \"java.lang.Integer\",\n"
                + "                    \"typeMapper\" : \"com.speedment.runtime.typemapper.primitive.PrimitiveTypeMapper\",\n"
                + "                    \"expanded\" : true,\n"
                + "                    \"nullable\" : false,\n"
                + "                    \"name\" : \"" + COLUMN_NAME + "\",\n"
                + "                    \"ordinalPosition\" : 1,\n"
                + "                    \"enabled\" : true\n"
                + "                  },\n"
                + "                  {\n"
                + "                    \"databaseType\" : \"java.lang.String\",\n"
                + "                    \"expanded\" : true,\n"
                + "                    \"nullable\" : true,\n"
                + "                    \"name\" : \"other_column_name\",\n"
                + "                    \"ordinalPosition\" : 2,\n"
                + "                    \"enabled\" : true\n"
                + "                  }\n"
                + "                ],\n"
                + "                \"name\" : \"" + TABLE_NAME + "\",\n"
                + "                \"enabled\" : true\n"
                + "              }\n"
                + "              ],\n"
                + "            \"expanded\" : true,\n"
                + "            \"name\" : \"" + SCHEMA_NAME + "\",\n"
                + "            \"enabled\" : true\n"
                + "          }\n"
                + "        ],\n"
                + "        \"name\" : \"" + DBMS_NAME + "\",\n"
                + "        \"typeName\" : \"MySQL\",\n"
                + "        \"ipAddress\" : \"localhost\",\n"
                + "        \"enabled\" : true,\n"
                + "        \"username\" : \"speedment_test\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"enabled\" : true\n"
                + "  }\n"
                + "}"
            );
        }

    }

//
//    public class AbstractApplicationBuilderImpl<
//        APP extends Speedment, BUILDER extends AbstractApplicationBuilder<APP, BUILDER>> extends AbstractApplicationBuilder<APP, BUILDER> {
//
//        public AbstractApplicationBuilderImpl(Injector.Builder injector) {
//            super(injector);
//        }
//
//        @Override
//        protected APP build(Injector injector) {
//            throw new UnsupportedOperationException("Should never be called.");
//        }
//    }
}
