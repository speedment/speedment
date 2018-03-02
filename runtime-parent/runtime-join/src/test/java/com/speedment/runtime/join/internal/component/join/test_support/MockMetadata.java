package com.speedment.runtime.join.internal.component.join.test_support;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.Schema;
import com.speedment.runtime.config.Table;
import com.speedment.runtime.config.trait.HasName;
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

    public static final String T1_NAME = "t1";
    public static final String T2_NAME = "t2";
    public static final String T3_NAME = "t3";
    public static final String T4_NAME = "t4";
    public static final String T5_NAME = "t5";
    public static final String T6_NAME = "t6";

    public static final String T1_ID_NAME = "t1_id";
    public static final String T2_ID_NAME = "t2_id";
    public static final String T3_ID_NAME = "t3_id";
    public static final String T4_ID_NAME = "t4_id";
    public static final String T5_ID_NAME = "t5_id";
    public static final String T6_ID_NAME = "t6_id";

    private String quote(String s) {
        return "\"" + s + "\"";
    }

    private String name(String s) {
        return quote(HasName.NAME) + " : " + quote(s);
    }

    private String dbmsTypeName(String dbmsTypeName) {
        return quote(Dbms.TYPE_NAME) + " : " + quote(dbmsTypeName);
    }

    private String columnDatabaseType(String typeName) {
        return quote(Column.DATABASE_TYPE) + " : " + quote(typeName);
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
                array(Project.DBMSES,
                    object(name(DBMS_NAME),
                        dbmsTypeName("MySQL"),
                        array(Dbms.SCHEMAS,
                            object(name(SCHEMA_NAME),
                                array(Schema.TABLES,
                                    object(name(T1_NAME),
                                        array(Table.COLUMNS,
                                            object(name(T1_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(Table.PRIMARY_KEY_COLUMNS,
                                            object(name(T1_ID_NAME)
                                            )
                                        )
                                    ),
                                    object(name(T2_NAME),
                                        array(Table.COLUMNS,
                                            object(name(T2_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(Table.PRIMARY_KEY_COLUMNS,
                                            object(name(T2_ID_NAME)
                                            )
                                        )
                                    ),
                                    object(name(T3_NAME),
                                        array(Table.COLUMNS,
                                            object(name(T3_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(Table.PRIMARY_KEY_COLUMNS,
                                            object(name(T3_ID_NAME)
                                            )
                                        )
                                    ),
                                    object(name(T4_NAME),
                                        array(Table.COLUMNS,
                                            object(name(T4_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(Table.PRIMARY_KEY_COLUMNS,
                                            object(name(T4_ID_NAME)
                                            )
                                        )
                                    ),
                                    object(name(T5_NAME),
                                        array(Table.COLUMNS,
                                            object(name(T5_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(Table.PRIMARY_KEY_COLUMNS,
                                            object(name(T5_ID_NAME)
                                            )
                                        )
                                    ),
                                    object(name(T6_NAME),
                                        array(Table.COLUMNS,
                                            object(name(T6_ID_NAME),
                                                columnDatabaseType(Integer.class.getName())
                                            )
                                        ),
                                        array(Table.PRIMARY_KEY_COLUMNS,
                                            object(name(T6_ID_NAME)
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
