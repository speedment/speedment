package com.speedment.runtime.core.manager.sql;

import java.util.List;

/**
 * Statement that can be sent to a SQL Database.
 *
 * @author Emil Forslund
 * @since  3.1.4
 */
public interface SqlStatement {

    /**
     * The type of statement.
     */
    enum Type {
        INSERT, UPDATE, DELETE
    }

    /**
     * The type of SQL Statement that this is.
     *
     * @return  the type
     */
    Type getType();

    /**
     * Returns the parameterized SQL string that will be sent to the database.
     *
     * @return  the parameterized SQL string
     */
    String getSql();

    /**
     * Returns the parameters of the statement.
     *
     * @return  the parameters
     */
    List<?> getValues();
}