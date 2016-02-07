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

import java.util.function.Function;

import com.speedment.config.db.Dbms;
import com.speedment.config.db.parameters.DbmsType;
import com.speedment.internal.core.db.MySqlDbmsHandler;
import com.speedment.internal.core.manager.sql.MySqlSpeedmentPredicateView;
import com.speedment.internal.core.manager.sql.SpeedmentPredicateView;

/**
 *
 * @author pemi
 */
public final class MariaDbDbmsType {

    private static final String QUOTE = "`";

    private static final Function<Dbms, String> CONNECTION_URL_GENERATOR = dbms -> {
        final StringBuilder result = new StringBuilder()
                .append("jdbc:mariadb://")
                .append(dbms.getIpAddress().orElse(""));
        dbms.getPort().ifPresent(p -> result.append(":").append(p));
        result.append("/?useUnicode=true&characterEncoding=UTF-8&useServerPrepStmts=true&useCursorFetch=true&zeroDateTimeBehavior=convertToNull");
        return result.toString();
    };

    private static final SpeedmentPredicateView VIEW = new MySqlSpeedmentPredicateView(QUOTE, QUOTE);

    public static final DbmsType INSTANCE = DefaultDbmsType.builder()
            // Mandatory parameters
            .withName("MariaDB")
            .withDriverManagerName("MariaDB JDBC Driver")
            .withDefaultPort(3305)
            .withDbmsNameMeaning("Just a name")
            .withDriverName("com.mysql.jdbc.Driver")
            .withFieldEncloserStart(QUOTE)
            .withFieldEncloserEnd(QUOTE)
            .withDbmsMapper(MySqlDbmsHandler::new)
            .withConnectionUrlGenerator(CONNECTION_URL_GENERATOR)
            .withSpeedmentPredicateView(VIEW)
            // Optional parameters
            .build();

}
