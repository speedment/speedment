package com.speedment.core.config.dbms;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author pemi
 */
public final class MySqlDbmsType extends AbstractDbmsType {

    public MySqlDbmsType() {

        super(
            "MySQL",
            "MySQL-AB JDBC Driver",
            3306,
            ".",
            "Just a name",
            "com.mysql.jdbc.Driver",
            "useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true&useCursorFetch=true&zeroDateTimeBehavior=convertToNull",
            "mysql",
            "`",
            "`",
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList("MySQL", "information_schema"))),
            MySqlDbmsHandler::new
        );
    }

}
