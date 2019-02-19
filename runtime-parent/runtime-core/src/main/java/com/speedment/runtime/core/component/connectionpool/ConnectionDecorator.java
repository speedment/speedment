package com.speedment.runtime.core.component.connectionpool;

import com.speedment.common.injector.annotation.InjectKey;

import java.sql.Connection;
import java.sql.SQLException;

@InjectKey(ConnectionDecorator.class)
public interface ConnectionDecorator {

    /**
     * Configures a newly allocated connection before use.
     *
     * @param connection to apply configuration to
     * @throws NullPointerException if the provided {@code connection}
     * is {@code null}
     */
    void configure(Connection connection) throws SQLException;

    /**
     * Cleans up a used connection before it is returned to a connection pool.
     *
     * @param connection to apply configuration to
     * @throws NullPointerException if the provided {@code connection}
     * is {@code null}
     */
    default void cleanup(Connection connection) throws SQLException {
        // Do nothing by default.
    };


}
