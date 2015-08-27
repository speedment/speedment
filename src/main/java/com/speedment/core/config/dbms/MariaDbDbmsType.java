package com.speedment.core.config.dbms;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author pemi
 */
public final class MariaDbDbmsType extends AbstractDbmsType {

    public MariaDbDbmsType() {

        super(
            "MariaDB",
            "MariaDB JDBC Driver",
            3305,
            ".",
            "Just a name",
            "com.mysql.jdbc.Driver",
            "useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true&useCursorFetch=true&zeroDateTimeBehavior=convertToNull",
            "mariadb",
            "`",
            "`",
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList("MySQL", "information_schema"))),
            MySqlDbmsHandler::new
        );
    }

}
