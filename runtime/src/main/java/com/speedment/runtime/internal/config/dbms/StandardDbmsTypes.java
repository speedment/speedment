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
package com.speedment.runtime.internal.config.dbms;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.RequiresInjectable;
import com.speedment.runtime.config.parameter.DbmsType;
import java.util.stream.Stream;

/**
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 */
@RequiresInjectable({
    MySqlDbmsType.class,
    MariaDbDbmsType.class,
    PostgresDbmsType.class
})
public final class StandardDbmsTypes {
    
    private @Inject MySqlDbmsType mysql;
    private @Inject MariaDbDbmsType mariadb;
    private @Inject PostgresDbmsType postgresql;
    
    public Stream<DbmsType> stream() {
        return Stream.of(mysql, mariadb, postgresql);
    }

    public DbmsType defaultType() {
        return mysql; 
    }
}