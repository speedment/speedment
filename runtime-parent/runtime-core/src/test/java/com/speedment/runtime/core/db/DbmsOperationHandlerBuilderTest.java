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

package com.speedment.runtime.core.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import org.junit.jupiter.api.Test;

final class DbmsOperationHandlerBuilderTest {

    @Test
    void create() {
        final ConnectionPoolComponent connectionPoolComponent = mock(ConnectionPoolComponent.class);
        final TransactionComponent transactionComponent = mock(TransactionComponent.class);

        assertNotNull(DbmsOperationalHandlerBuilder.create(connectionPoolComponent, transactionComponent));
    }
}
