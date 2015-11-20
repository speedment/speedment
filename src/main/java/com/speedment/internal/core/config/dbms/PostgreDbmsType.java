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
import com.speedment.internal.core.db.PostgreDbmsHandler;
import com.speedment.internal.core.manager.sql.PostgresSpeedmentPredicateView;
import com.speedment.internal.core.manager.sql.SpeedmentPredicateView;

import java.util.Collections;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Created by fdirlikl on 11/13/2015.
 */
public final class PostgreDbmsType extends AbstractDbmsType {
    private static final BiFunction<Speedment, Dbms, DbmsHandler> DBMS_MAPPER = PostgreDbmsHandler::new; // JAVA8 bug: Cannot use method ref in this() or super()
    private static final String RESULTSET_TABLE_SCHEMA = "TABLE_SCHEM";
    private static final String JDBC_CONNECTOR_NAME = "postgresql";
    private static final Optional<String> DEFAULT_CONNECTOR_PARAMS = Optional.of("useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true&useCursorFetch=true&zeroDateTimeBehavior=convertToNull");
    private static final Function<Dbms,String> CONNECTION_URL_GENERATOR = dbms -> {
        final StringBuilder result = new StringBuilder();
        result.append("jdbc:").append(JDBC_CONNECTOR_NAME).append("://");
        dbms.getIpAddress().ifPresent(ip -> result.append(ip));
        dbms.getPort().ifPresent(p -> result.append(":").append(p));
        result.append("/").append(dbms.getName());
        DEFAULT_CONNECTOR_PARAMS.ifPresent(d -> result.append("?").append(d));
        return result.toString();
    };

    public PostgreDbmsType() {

        super(
                "Postgres",
                "Postgres-AB JDBC Driver",
                5432,
                ".",
                "Just a name",
                "org.postgresql.Driver",
                "useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true&useCursorFetch=true&zeroDateTimeBehavior=convertToNull",
                "postgresql",
                "\"",
                "\"",
                Stream.of("pg_catalog", "information_schema").collect(Collectors.collectingAndThen(toSet(), Collections::unmodifiableSet)),
                DBMS_MAPPER,
                RESULTSET_TABLE_SCHEMA,
                CONNECTION_URL_GENERATOR
        );
    }


    @Override
    public SpeedmentPredicateView getSpeedmentPredicateView() {
        return new PostgresSpeedmentPredicateView();
    }
}