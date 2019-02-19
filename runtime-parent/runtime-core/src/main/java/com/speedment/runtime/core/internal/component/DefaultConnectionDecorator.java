package com.speedment.runtime.core.internal.component;

import com.speedment.runtime.core.component.connectionpool.ConnectionDecorator;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This {@link ConnectionDecorator} does nothing with the connection and
 * is used as the default ConnectionDecorator.
 */
public class DefaultConnectionDecorator implements ConnectionDecorator {

    @Override
    public void configure(Connection connection) throws SQLException {
       // Do nothing
    }

    @Override
    public void cleanup(Connection connection) throws SQLException {
        // Do nothing
    }

}
