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
package com.speedment.internal.core.config.dbms;

import com.speedment.config.db.parameters.DbmsType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class StandardDbmsType {

    private static final DbmsType MYSQL    = MySqlDbmsType.INSTANCE;
    private static final DbmsType MARIADB  = MariaDbDbmsType.INSTANCE;
    private static final DbmsType POSTGRES = PostgresDbmsType.INSTANCE;
    private static final DbmsType SQLITE = SqliteDbmsType.INSTANCE;

    private final static List<DbmsType> STANDARD_TYPES = Stream.of(
        MYSQL,
        MARIADB,
        POSTGRES,
        SQLITE
    ).collect(Collectors.toList());

    public static Stream<DbmsType> stream() {
        return STANDARD_TYPES.stream();
    }

    public static DbmsType defaultType() {
        return MYSQL; 
    }
}