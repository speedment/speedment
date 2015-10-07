/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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

import com.speedment.Speedment;
import com.speedment.config.Dbms;
import com.speedment.db.DbmsHandler;
import com.speedment.internal.core.db.MySqlDbmsHandler;
import java.util.Collections;
import java.util.function.BiFunction;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public final class MariaDbDbmsType extends AbstractDbmsType {

    private static final BiFunction<Speedment, Dbms, DbmsHandler> DBMS_MAPPER = MySqlDbmsHandler::new; // JAVA8 bug: Cannot use method ref in this() or super()

    public MariaDbDbmsType() {

        super(
            "MariaDB",
            "MariaDB JDBC Driver",
            3305,
            ".",
            "Just a name",
            "com.mysql.jdbc.Driver",
            "useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true&useCursorFetch=true&zeroDateTimeBehavior=convertToNull",
            "mariadb",
            "`",
            "`",
            Stream.of("MySQL", "information_schema").collect(collectingAndThen(toSet(), Collections::unmodifiableSet)),
            DBMS_MAPPER
        );
    }

}
