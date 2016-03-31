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
import com.speedment.config.db.Column;
import com.speedment.config.db.Dbms;
import java.sql.SQLException;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import com.speedment.db.metadata.ColumnMetaData2;
import static java.util.Objects.requireNonNull;

/**
 * Created by fdirlikl on 11/15/2015.
 */
public class PostgresDbmsHandler extends AbstractRelationalDbmsHandler {

    public PostgresDbmsHandler(Speedment speedment, final Dbms dbms) {
        super(speedment, dbms);
    }

    @Override
    protected Class<?> lookupJdbcClass(Map<String, Class<?>> sqlTypeMapping, ColumnMetaData2 md) {
        requireNonNull(sqlTypeMapping);
        requireNonNull(md);
        final String typeName = md.getTypeName().toUpperCase();
        final int columnSize = md.getColumnSize();
      
        switch (typeName) {
            case "BIT": {
                if (columnSize == 1) {
                    return Boolean.class;
                }
                break;
            }
        }

        return super.lookupJdbcClass(sqlTypeMapping, md);
    }

    @Override
    protected void setAutoIncrement(Column column, ColumnMetaData2 md) throws SQLException {
        final String defaultValue = md.getColumnDef();
        if (defaultValue != null && defaultValue.startsWith("nextval(")) {
            column.mutator().setAutoIncrement(true);
        }
    }

//    @Override
//    protected void addCustomJavaTypeMap() {
//        final Optional<Class<?>> pgLineClass = PostgresDbmsType.pgLineClass();
//        pgLineClass.ifPresent(c -> {
//            javaTypeMap.put("line", c);
//        });
//    }
    

}
