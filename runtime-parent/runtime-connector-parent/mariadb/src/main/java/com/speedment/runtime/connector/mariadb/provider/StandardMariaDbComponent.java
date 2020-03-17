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
package com.speedment.runtime.connector.mariadb.provider;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.connector.mariadb.MariaDbComponent;
import com.speedment.runtime.connector.mariadb.MariaDbDbmsType;
import com.speedment.runtime.core.component.DbmsHandlerComponent;

import static com.speedment.common.injector.State.INITIALIZED;

/**
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since 1.0.0
 */
public final class StandardMariaDbComponent implements MariaDbComponent {

    @ExecuteBefore(INITIALIZED)
    public void onInitialize(
        final @WithState(INITIALIZED) DbmsHandlerComponent dbmsHandlerComponent,
        final @WithState(INITIALIZED) MariaDbDbmsType mariaDbDbmsType
    ) {
        dbmsHandlerComponent.install(mariaDbDbmsType);
    }

}
