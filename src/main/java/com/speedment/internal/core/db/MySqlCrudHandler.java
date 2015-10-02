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
package com.speedment.internal.core.db;

import com.speedment.Speedment;
import com.speedment.component.ConnectionPoolComponent;
import com.speedment.config.Dbms;
import com.speedment.config.parameters.DbmsType;
import com.speedment.db.crud.Create;
import com.speedment.db.crud.CrudOperation;
import com.speedment.db.crud.Delete;
import com.speedment.db.crud.Read;
import com.speedment.db.crud.Result;
import com.speedment.db.crud.Update;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.core.db.crud.ResultImpl;
import com.speedment.internal.core.manager.sql.AbstractCrudHandler;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.internal.util.sql.SqlWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

/**
 * A handler for MySQL databases.
 * 
 * @author Emil Forslund
 */
public final class MySqlCrudHandler extends AbstractCrudHandler {

    private static final Logger LOGGER = LoggerManager.getLogger(MySqlCrudHandler.class);
    private final Dbms dbms;
    
    public MySqlCrudHandler(Speedment speedment, Dbms dbms) {
        super(speedment);
        this.dbms = requireNonNull(dbms);
    }

    @Override
    protected <T> T create(Create create, Function<Result, T> mapper) throws SpeedmentException {
        return executeUpdate(create, mapper);
    }

    @Override
    protected <T> T update(Update update, Function<Result, T> mapper) throws SpeedmentException {
        return executeUpdate(update, mapper);
    }

    @Override
    protected void delete(Delete delete) throws SpeedmentException {
        final PreparedStatement statement = SqlWriter.prepare(getConnection(), delete);
        
        try {
            statement.execute();
        } catch (SQLException ex) {
            throw new SpeedmentException(
                "Could not delete entity", ex
            );
        }
    }

    @Override
    protected <T> Stream<T> read(Read read, Function<Result, T> mapper) throws SpeedmentException {
        final PreparedStatement statement = SqlWriter.prepare(getConnection(), read);
        try (final ResultSet set = statement.executeQuery()) {
            return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                    new Iterator<Result>() {
                        @Override
                        public boolean hasNext() {
                            try {
                                return set.next();
                            } catch (SQLException ex) {
                                throw new SpeedmentException("Failed to iterate over ResultSet.", ex);
                            }
                        }

                        @Override
                        public Result next() {
                            return new ResultImpl(set);
                        }
                    }, 0
                ), false
            ).map(mapper);
            
        } catch (SQLException ex) {
            throw new SpeedmentException(
                "Could not delete entity", ex
            );
        }
    }
    
    private <T> T executeUpdate(CrudOperation operation, Function<Result, T> mapper) {
        final PreparedStatement statement = SqlWriter.prepare(getConnection(), operation);
        final T entity;
        
        try {
            final int rows = statement.executeUpdate();
            if (rows > 0) {
                try (final ResultSet result = statement.getGeneratedKeys()) {
                    entity = mapper.apply(new ResultImpl(result));
                }
            } else {
                throw new SpeedmentException(
                    operation.getType().name() + " operation did not affect any rows in the database."
                );
            }
        } catch (SQLException ex) {
            throw new SpeedmentException(
                "Could not " + operation.getType().name().toLowerCase() + " entity", ex
            );
        }
        
        return entity;
    }
    
    private Connection getConnection() {
        final Connection conn;
        
        final String url      = getUrl();
        final String user     = dbms.getUsername().orElse(null);
        final String password = dbms.getPassword().orElse(null);
        
        try {
            conn = speedment().get(ConnectionPoolComponent.class)
                .getConnection(url, user, password);
            
        } catch (SQLException sqle) {
            final String msg = "Unable to get connection for " + dbms + " using url \"" + url + "\", user = " + user + ".";
            LOGGER.error(sqle, msg);
            throw new SpeedmentException(msg, sqle);
        }
        
        return conn;
    }

    private String getUrl() {
        final DbmsType dbmsType    = dbms.getType();
        final StringBuilder result = new StringBuilder();
        
        result.append("jdbc:");
        result.append(dbmsType.getJdbcConnectorName());
        result.append("://");
        
        dbms.getIpAddress().ifPresent(ip -> result.append(ip));
        dbms.getPort().ifPresent(port -> result.append(":").append(port));
        
        result.append("/");

        dbmsType.getDefaultConnectorParameters()
            .ifPresent(params -> result.append("?").append(params));

        return result.toString();
    }
}