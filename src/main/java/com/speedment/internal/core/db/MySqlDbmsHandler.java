/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.db;

import com.speedment.Speedment;
import com.speedment.config.db.Dbms;
import com.speedment.db.metadata.ColumnMetaData;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Specific MySQL implementation of a DbmsHandler. Currently, there are no
 * specific implementations for MySQL.
 *
 * @author pemi
 * @since 2.0
 */
public final class MySqlDbmsHandler extends AbstractRelationalDbmsHandler {

    private static final String YEAR = "YEAR";
    private static final Class<?> YEAR_MAPPING = Integer.class;

    public MySqlDbmsHandler(Speedment speedment, final Dbms dbms) {
        super(speedment, dbms);
    }

    @Override
    protected Map<String, Class<?>> readTypeMapFromDB(Dbms dbms) throws SQLException {
        final Map<String, Class<?>> result = super.readTypeMapFromDB(dbms);
        result.put(YEAR, YEAR_MAPPING);
        return result;
    }

    @Override
    protected void addCustomJavaTypeMap() {
        addMySqlCustomJavaTypeMap(javaTypeMap);
    }

    @Override
    protected Class<?> lookupJdbcClass(Map<String, Class<?>> sqlTypeMapping, ColumnMetaData md) {
        if ("BIT".equals(md.getTypeName()) && md.getColumnSize() == 1) {
            // Map a BIT(1) to boolean
            return Boolean.class;
        }
        return super.lookupJdbcClass(sqlTypeMapping, md);
    }

    /**
     * Common to MySQL and MariaDB
     *
     * @param map to add to
     */
    static void addMySqlCustomJavaTypeMap(Map<String, Class<?>> map) {
        map.put(YEAR, YEAR_MAPPING);
        Stream.of("LONG", "MEDIUM", "TINY").forEach(key -> {
            map.put(key + "BLOB", Blob.class);
        });
        map.put("JSON", String.class);
    }

}
