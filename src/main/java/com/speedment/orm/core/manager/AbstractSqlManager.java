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
package com.speedment.orm.core.manager;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.config.model.Schema;
import com.speedment.orm.config.model.Table;
import com.speedment.orm.core.Buildable;
import com.speedment.orm.db.DbmsHandler;
import com.speedment.orm.platform.Platform;
import com.speedment.orm.platform.component.DbmsHandlerComponent;
import java.sql.ResultSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pemi
 *
 * @param <PK> PrimaryKey type for this Manager
 * @param <ENTITY> Entity type for this Manager
 * @param <BUILDER> Builder type for this Manager
 */
public abstract class AbstractSqlManager<PK, ENTITY, BUILDER extends Buildable<ENTITY>> extends AbstractManager<PK, ENTITY, BUILDER> implements SqlManager<PK, ENTITY, BUILDER> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractSqlManager.class);

    private Function<ResultSet, ENTITY> sqlEntityMapper;

    @Override
    public Stream<ENTITY> stream() {
        final Table table = getTable();
        final String columns = table.streamOf(Column.class).map(Column::getName).collect(Collectors.joining(","));
        final String sql = "select " + columns + " from " + table.getRelativeName(Schema.class);
        LOGGER.debug(sql);
        return streamOf(sql);
    }

    protected Stream<ENTITY> streamOf(final String sql) {
        final Table table = getTable();
        final Dbms dbms = table.ancestor(Dbms.class).get();
        final DbmsHandler dbmsHandler = Platform.get().get(DbmsHandlerComponent.class).get(dbms);
        return dbmsHandler.executeQuery(sql, getSqlEntityMapper());
    }

    @Override
    public Function<ResultSet, ENTITY> getSqlEntityMapper() {
        return sqlEntityMapper;
    }

    @Override
    public void setSqlEntityMapper(Function<ResultSet, ENTITY> sqlEntityMapper) {
        this.sqlEntityMapper = sqlEntityMapper;
    }

}
