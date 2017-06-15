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
package com.speedment.runtime.core.internal.db;

import com.speedment.common.injector.InjectBundle;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.db.mariadb.MariaDbDbmsType;
import com.speedment.runtime.core.internal.db.mysql.MySqlDbmsType;
import com.speedment.runtime.core.internal.db.postgresql.PostgresqlDbmsType;

import java.util.stream.Stream;

import static com.speedment.common.injector.InjectBundle.of;

/**
 *
 * @author Per Minborg
 * @author Emil Forslund
 */
public final class StandardDbmsTypes {

    public static InjectBundle include() {
        return of(MySqlDbmsType.class, MariaDbDbmsType.class, PostgresqlDbmsType.class);
    }
    
    private @Inject MySqlDbmsType mysql;
    private @Inject MariaDbDbmsType mariadb;
    private @Inject PostgresqlDbmsType postgresql;

    public Stream<DbmsType> stream() {
        return Stream.of(mysql, mariadb, postgresql);
    }

    public DbmsType defaultType() {
        return mysql;
    }
}
