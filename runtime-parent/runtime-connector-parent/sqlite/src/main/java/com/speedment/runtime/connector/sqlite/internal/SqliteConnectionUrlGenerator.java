package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.db.ConnectionUrlGenerator;

/**
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class SqliteConnectionUrlGenerator implements ConnectionUrlGenerator {

    @Override
    public String from(Dbms dbms) {
        return "jdbc:sqlite:" + dbms.getLocalPath().orElse(":memory:");
    }
}
