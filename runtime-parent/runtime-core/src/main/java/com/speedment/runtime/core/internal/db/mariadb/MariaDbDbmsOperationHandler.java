/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.db.mariadb;

import com.speedment.runtime.core.internal.db.AbstractDbmsOperationHandler;
import com.speedment.runtime.core.internal.manager.sql.SqlInsertStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Emil Forslund
 * @since 3.0.0
 */
public final class MariaDbDbmsOperationHandler extends AbstractDbmsOperationHandler {

    @Override
    public void configureSelect(PreparedStatement statement) throws SQLException {
        statement.setFetchSize(Integer.MIN_VALUE); // Enable streaming ResultSet
    }

//    @Override
//    public <ENTITY> void handleGeneratedKeys(PreparedStatement ps, SqlInsertStatement<ENTITY> sqlStatement) throws SQLException {
//        try (final ResultSet generatedKeys = ps.getGeneratedKeys()) {
//
////            // Work-around for MariaDb Bug that returns a SINGLETON of no keys are generated! See #408
////            if (!generatedKeys.isClosed()) {
//                while (generatedKeys.next()) {
//                    sqlStatement.addGeneratedKey(generatedKeys.getLong(1));
//                }
////            }
//
//        }
//    }

}
