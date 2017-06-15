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
package com.speedment.runtime.core;

import com.speedment.common.injector.InjectBundle;
import com.speedment.runtime.core.internal.AbstractSpeedment;
import com.speedment.runtime.core.internal.db.StandardDbmsTypes;
import com.speedment.runtime.core.internal.db.mariadb.MariaDbDbmsType;
import com.speedment.runtime.core.internal.db.mysql.MySqlDbmsType;
import com.speedment.runtime.core.internal.db.postgresql.PostgresqlDbmsType;

import java.util.stream.Stream;

/**
 * The {@link InjectBundle} for the "runtime"-module.
 * 
 * @author  Per Minborg
 * @since   3.0.1
 */
public class RuntimeBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return AbstractSpeedment.include()
            .withBundle(StandardDbmsTypes.include())
            .withBundle(MariaDbDbmsType.include())
            .withBundle(MySqlDbmsType.include())
            .withBundle(PostgresqlDbmsType.include())
            .injectables();
    }

}