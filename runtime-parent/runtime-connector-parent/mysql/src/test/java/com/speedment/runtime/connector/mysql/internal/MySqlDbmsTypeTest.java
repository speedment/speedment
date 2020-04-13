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

package com.speedment.runtime.connector.mysql.internal;

import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.DriverComponent;
import com.speedment.test.connector.DbmsTypeMixin;
import org.mockito.Mockito;

final class MySqlDbmsTypeTest implements DbmsTypeMixin {

    @Override
    public DbmsType getDbmsTypeInstance() {
        final DriverComponent driverComponent = Mockito.mock(DriverComponent.class);
        final ConnectionPoolComponent connectionPoolComponent = Mockito.mock(ConnectionPoolComponent.class);
        final DbmsHandlerComponent dbmsHandlerComponent = Mockito.mock(DbmsHandlerComponent.class);
        final ProjectComponent projectComponent = Mockito.mock(ProjectComponent.class);
        final TransactionComponent transactionComponent = Mockito.mock(TransactionComponent.class);

        return new MySqlDbmsTypeImpl(
            driverComponent,
            "utf8_bin",
            "utf8_general_ci",
            connectionPoolComponent,
            dbmsHandlerComponent,
            projectComponent,
            transactionComponent);
    }
}
