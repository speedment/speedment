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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.orm.db.impl;

import com.speedment.orm.db.AsynchronousQueryResult;
import com.speedment.orm.exception.SpeedmentException;
import com.speedment.util.stream.StreamUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pemi
 * @param <T>
 */
public class AsynchronousQueryResultImpl<T> implements AsynchronousQueryResult<T> {

    private static final Logger LOGGER = LogManager.getLogger(AsynchronousQueryResultImpl.class);

    private final String sql;
    private final Function<ResultSet, T> rsMapper;
    private final Supplier<Connection> connectionSupplier;
    private Connection connection;
    private Statement statement;
    private ResultSet rs;

    public AsynchronousQueryResultImpl(final String sql, final Function<ResultSet, T> rsMapper, Supplier<Connection> connectionSupplier) {
        this.sql = Objects.requireNonNull(sql);
        this.rsMapper = Objects.requireNonNull(rsMapper);
        this.connectionSupplier = Objects.requireNonNull(connectionSupplier);
    }

    @Override
    public Stream<T> stream() {
        try {
            connection = connectionSupplier.get();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
        } catch (SQLException sqle) {
            LOGGER.error("Error executing " + sql, sqle);
            throw new SpeedmentException(sqle);
        }
        return StreamUtil.asStream(rs, rsMapper);
    }

    @Override
    public void close() {
        closeSilently(rs);
        closeSilently(statement);
        closeSilently(connection);
    }

    protected void closeSilently(final AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            LOGGER.error("Error closing " + closeable, e);
            // Just log the error. No re-throw
        }
    }

}
