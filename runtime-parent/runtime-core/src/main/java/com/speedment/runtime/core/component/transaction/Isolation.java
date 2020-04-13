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
package com.speedment.runtime.core.component.transaction;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Per Minborg
 */
public enum Isolation {

//    /**
//     * An enum indicating that transactions are not supported.
//     */
//    NONE(Connection.TRANSACTION_NONE),
    /**
     * An Enum indicating that the default level of isolation for the
     * transaction domain shall be used.
     */
    DEFAULT(-1),
    /**
     * An Enum indicating that dirty reads, non-repeatable reads and phantom
     * reads can occur. This level allows a row changed by one transaction to be
     * read by another transaction before any changes in that row have been
     * committed (a "dirty read"). If any of the changes are rolled back, the
     * second transaction will have retrieved an invalid row.
     */
    READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),
    /**
     * An Enum indicating that dirty reads are prevented; non-repeatable reads
     * and phantom reads can occur. This level only prohibits a transaction from
     * reading a row with uncommitted changes in it.
     */
    READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),
    /**
     * An Enum indicating that dirty reads and non-repeatable reads are
     * prevented; phantom reads can occur. This level prohibits a transaction
     * from reading a row with uncommitted changes in it, and it also prohibits
     * the situation where one transaction reads a row, a second transaction
     * alters the row, and the first transaction rereads the row, getting
     * different values the second time (a "non-repeatable read").
     */
    REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),
    /**
     * An Enum indicating that dirty reads, non-repeatable reads and phantom
     * reads are prevented. This level includes the prohibitions in
     * <code>TRANSACTION_REPEATABLE_READ</code> and further prohibits the
     * situation where one transaction reads all rows that satisfy a
     * <code>WHERE</code> condition, a second transaction inserts a row that
     * satisfies that <code>WHERE</code> condition, and the first transaction
     * rereads for the same condition, retrieving the additional "phantom" row
     * in the second read.
     */
    SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE);

    private final int sqlIsolationLevel;

    Isolation(int sqlIsolationlevel) {
        this.sqlIsolationLevel = sqlIsolationlevel;
    }

    public int getSqlIsolationLevel() {
        if (DEFAULT == this) {
            throw new IllegalArgumentException("The DEFAULT isolation level does not have a hard coded value.");
        }
        return sqlIsolationLevel;
    }

    private static final Map<Integer, Isolation> LEVEL_TO_ISOLATION_MAP = new HashMap<>();
    static {
        LEVEL_TO_ISOLATION_MAP.put(Connection.TRANSACTION_READ_UNCOMMITTED, READ_UNCOMMITTED);
        LEVEL_TO_ISOLATION_MAP.put(Connection.TRANSACTION_READ_COMMITTED, READ_COMMITTED);
        LEVEL_TO_ISOLATION_MAP.put(Connection.TRANSACTION_REPEATABLE_READ, REPEATABLE_READ);
        LEVEL_TO_ISOLATION_MAP.put(Connection.TRANSACTION_SERIALIZABLE, SERIALIZABLE);
    }

    public static Isolation fromSqlIsolationLevel(int level) {
        final Isolation isolation= LEVEL_TO_ISOLATION_MAP.get(level);
        if (isolation == null) {
            throw new IllegalArgumentException("No Isolation exists for " + level);
        }
        return isolation;
    }

}
