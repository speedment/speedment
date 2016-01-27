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

import com.speedment.Speedment;
import com.speedment.config.db.Dbms;
import com.speedment.db.DbmsHandler;
import com.speedment.internal.core.db.MySqlDbmsHandler;
import com.speedment.internal.core.manager.sql.MySqlSpeedmentPredicateView;
import com.speedment.internal.core.manager.sql.SpeedmentPredicateView;
import com.speedment.internal.util.sql.SqlTypeInfo;

import static com.speedment.internal.core.stream.OptionalUtil.unwrap;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import java.util.function.Function;
import java.util.stream.Stream;

import static com.speedment.internal.core.stream.OptionalUtil.unwrap;

/**
 *
 * @author pemi
 */
public final class MariaDbDbmsType extends AbstractDbmsType {

    private static final String QUOTE = "`";
    private static final BiFunction<Speedment, Dbms, DbmsHandler> DBMS_MAPPER = MySqlDbmsHandler::new; // JAVA8 bug: Cannot use method ref in this() or super()
    private static final String RESULTSET_TABLE_SCHEMA = "TABLE_SCHEMA";
    private static final String JDBC_CONNECTOR_NAME = "mariadb";
    private static final Optional<String> DEFAULT_CONNECTOR_PARAMS = Optional.of("useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true&useCursorFetch=true&zeroDateTimeBehavior=convertToNull");
    private static final Function<Dbms, String> CONNECTION_URL_GENERATOR = dbms -> {
        final StringBuilder result = new StringBuilder();
        result.append("jdbc:").append(JDBC_CONNECTOR_NAME).append("://");
        dbms.getIpAddress().ifPresent(ip -> result.append(ip));
        dbms.getPort().ifPresent(p -> result.append(":").append(p));
        result.append("/");
        DEFAULT_CONNECTOR_PARAMS.ifPresent(d -> result.append("?").append(d));
        return result.toString();
    };
    private static final Set<SqlTypeInfo> DATA_TYPES = new HashSet<>();

    public MariaDbDbmsType() {

        super(
                "MariaDB",
                "MariaDB JDBC Driver",
                3305,
                ".",
                "Just a name",
                "com.mysql.jdbc.Driver",
                unwrap(DEFAULT_CONNECTOR_PARAMS),
                JDBC_CONNECTOR_NAME,
                QUOTE,
                QUOTE,
                Stream.of("MySQL", "information_schema").collect(collectingAndThen(toSet(), Collections::unmodifiableSet)),
                DBMS_MAPPER,
                RESULTSET_TABLE_SCHEMA,
                CONNECTION_URL_GENERATOR,
                DATA_TYPES
        );
    }
    private static final MySqlSpeedmentPredicateView VIEW = new MySqlSpeedmentPredicateView(QUOTE, QUOTE);

    @Override
    public MySqlSpeedmentPredicateView getSpeedmentPredicateView() {
        return VIEW;
    }
}
