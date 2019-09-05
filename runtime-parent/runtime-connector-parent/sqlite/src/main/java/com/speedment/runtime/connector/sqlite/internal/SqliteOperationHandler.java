/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.internal.db.AbstractDbmsOperationHandler;

/**
 * The implementation of {@link DbmsOperationHandler} for SQLite databases.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class SqliteOperationHandler extends AbstractDbmsOperationHandler {

    protected SqliteOperationHandler(
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final TransactionComponent transactionComponent
    ) {
        super(connectionPoolComponent, dbmsHandlerComponent, transactionComponent);
    }

}