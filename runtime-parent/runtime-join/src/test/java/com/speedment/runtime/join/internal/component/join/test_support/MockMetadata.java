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
package com.speedment.runtime.join.internal.component.join.test_support;

import com.speedment.runtime.config.*;
import com.speedment.runtime.config.trait.HasNameUtil;
import com.speedment.runtime.config.trait.HasTypeMapperUtil;
import com.speedment.runtime.core.testsupport.AbstractTestApplicationMetadata;

import java.util.Optional;
import static java.util.stream.Collectors.joining;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class MockMetadata extends AbstractTestApplicationMetadata {

    public static final String DBMS_NAME = "dbms";
    public static final String SCHEMA_NAME = "schema";

    public static final String T0_NAME = "t0";
    public static final String T1_NAME = "t1";
    public static final String T2_NAME = "t2";
    public static final String T3_NAME = "t3";
    public static final String T4_NAME = "t4";
    public static final String T5_NAME = "t5";
    public static final String T6_NAME = "t6";
    public static final String T7_NAME = "t7";
    public static final String T8_NAME = "t8";
    public static final String T9_NAME = "t9";

    public static final String T0_ID_NAME = "t0_id";
    public static final String T1_ID_NAME = "t1_id";
    public static final String T2_ID_NAME = "t2_id";
    public static final String T3_ID_NAME = "t3_id";
    public static final String T4_ID_NAME = "t4_id";
    public static final String T5_ID_NAME = "t5_id";
    public static final String T6_ID_NAME = "t6_id";
    public static final String T7_ID_NAME = "t7_id";
    public static final String T8_ID_NAME = "t8_id";
    public static final String T9_ID_NAME = "t9_id";

    private String quote(String s) {
        return "\"" + s + "\"";
    }

    private String name(String s) {
        return quote(HasNameUtil.NAME) + " : " + quote(s);
    }

    private String dbmsTypeName(String dbmsTypeName) {
        return quote(DbmsUtil.TYPE_NAME) + " : " + quote(dbmsTypeName);
    }

    private String columnDatabaseType(String typeName) {
        return quote(HasTypeMapperUtil.DATABASE_TYPE) + " : " + quote(typeName);
    }

    private String array(String name, String... s) {
        return quote(name) + " : [\n" + Stream.of(s)
            .map(line -> line.replace("\n", "\n    "))
            .collect(joining(",\n    ")
            ) + "\n]";
    }

    private String objectWithKey(String name, String... s) {
        return quote(name) + " : " + object(s);
    }

    private String object(String... s) {
        return "{\n" + Stream.of(s)
            .map(line -> line.replace("\n", "\n    "))
            .collect(joining(",\n    ")) + "\n}";
    }

    @Override
    public Optional<String> getMetadata() {
        return Optional.of("{"
            + objectWithKey("config",
                name("myProject"),
                array(ProjectUtil.DBMSES,
                    object(name(DBMS_NAME),
                        dbmsTypeName("MySQL"),
                        array(DbmsUtil.SCHEMAS,
                            object(name(SCHEMA_NAME),
                                array(SchemaUtil.TABLES,
                                    object(name(T0_NAME),
                                        array(TableUtil.COLUMNS,
                                            object(name(T0_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(TableUtil.PRIMARY_KEY_COLUMNS,
                                            object(name(T0_ID_NAME)
                                            )
                                        )
                                    ),
                                    object(name(T1_NAME),
                                        array(TableUtil.COLUMNS,
                                            object(name(T1_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(TableUtil.PRIMARY_KEY_COLUMNS,
                                            object(name(T1_ID_NAME)
                                            )
                                        )
                                    ),
                                    object(name(T2_NAME),
                                        array(TableUtil.COLUMNS,
                                            object(name(T2_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(TableUtil.PRIMARY_KEY_COLUMNS,
                                            object(name(T2_ID_NAME)
                                            )
                                        )
                                    ),
                                    object(name(T3_NAME),
                                        array(TableUtil.COLUMNS,
                                            object(name(T3_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(TableUtil.PRIMARY_KEY_COLUMNS,
                                            object(name(T3_ID_NAME)
                                            )
                                        )
                                    ),
                                    object(name(T4_NAME),
                                        array(TableUtil.COLUMNS,
                                            object(name(T4_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(TableUtil.PRIMARY_KEY_COLUMNS,
                                            object(name(T4_ID_NAME)
                                            )
                                        )
                                    ),
                                    object(name(T5_NAME),
                                        array(TableUtil.COLUMNS,
                                            object(name(T5_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(TableUtil.PRIMARY_KEY_COLUMNS,
                                            object(name(T5_ID_NAME)
                                            )
                                        )
                                    )
                                        ,
                                        object(name(T6_NAME),
                                                array(TableUtil.COLUMNS,
                                                        object(name(T6_ID_NAME),
                                                                columnDatabaseType(Integer.class.getName())
                                                        )
                                                ),
                                                array(TableUtil.PRIMARY_KEY_COLUMNS,
                                                        object(name(T6_ID_NAME)
                                                        )
                                                )
                                        )
                                        ,
                                        object(name(T7_NAME),
                                                array(TableUtil.COLUMNS,
                                                        object(name(T7_ID_NAME),
                                                                columnDatabaseType(Integer.class.getName())
                                                        )
                                                ),
                                                array(TableUtil.PRIMARY_KEY_COLUMNS,
                                                        object(name(T7_ID_NAME)
                                                        )
                                                )
                                        )
                                        ,
                                        object(name(T8_NAME),
                                                array(TableUtil.COLUMNS,
                                                        object(name(T8_ID_NAME),
                                                                columnDatabaseType(Integer.class.getName())
                                                        )
                                                ),
                                                array(TableUtil.PRIMARY_KEY_COLUMNS,
                                                        object(name(T8_ID_NAME)
                                                        )
                                                )
                                        )
                                        ,
                                        object(name(T9_NAME),
                                                array(TableUtil.COLUMNS,
                                                        object(name(T9_ID_NAME),
                                                                columnDatabaseType(Integer.class.getName())
                                                        )
                                                ),
                                                array(TableUtil.PRIMARY_KEY_COLUMNS,
                                                        object(name(T9_ID_NAME)
                                                        )
                                                )
                                        )
                                )
                            )
                        )
                    )
                )
            )
            + "}"
        );
    }
}
