/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.connector.postgres.internal;

import static com.speedment.common.injector.State.INITIALIZED;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.connector.postgres.PostgresComponent;
import com.speedment.runtime.core.component.DbmsHandlerComponent;

/**
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since 1.0.0
 */
public final class PostgresComponentImpl implements PostgresComponent {

    protected PostgresComponentImpl() {}

    @ExecuteBefore(INITIALIZED)
    void onInitialize(
        final @WithState(INITIALIZED) DbmsHandlerComponent dbmsHandlerComponent,
        final @WithState(INITIALIZED) PostgresDbmsType postgresDbmsType
    ) {
        dbmsHandlerComponent.install(postgresDbmsType);
    }

}
