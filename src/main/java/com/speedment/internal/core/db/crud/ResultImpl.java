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
package com.speedment.internal.core.db.crud;

import com.speedment.db.crud.Result;
import com.speedment.exception.SpeedmentException;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;

/**
 * @author Emil Forslund
 */
public final class ResultImpl implements Result {

    private final ResultSet result;

    public ResultImpl(ResultSet result) {
        this.result = result;
    }

    @Override
    public Array getArray(String column) {
        return get(result::getArray, column);
    }

    @Override
    public BigDecimal getBigDecimal(String column) {
        return get(result::getBigDecimal, column);
    }

    @Override
    public Blob getBlob(String column) {
        return get(result::getBlob, column);
    }

    @Override
    public Clob getClob(String column) {
        return get(result::getClob, column);
    }

    @Override
    public NClob getNClob(String column) {
        return get(result::getNClob, column);
    }

    @Override
    public Ref getRef(String column) {
        return get(result::getRef, column);
    }

    @Override
    public Object getObject(String column) {
        return get(result::getObject, column);
    }

    @Override
    public double getDouble(String column) {
        return get(result::getDouble, column);
    }

    @Override
    public long getLong(String column) {
        return get(result::getLong, column);
    }

    @Override
    public float getFloat(String column) {
        return get(result::getFloat, column);
    }

    @Override
    public int getInt(String column) {
        return get(result::getInt, column);
    }

    @Override
    public short getShort(String column) {
        return get(result::getShort, column);
    }

    @Override
    public byte getByte(String column) {
        return get(result::getByte, column);
    }

    @Override
    public boolean getBoolean(String column) {
        return get(result::getBoolean, column);
    }

    @Override
    public String getString(String column) {
        return get(result::getString, column);
    }

    @Override
    public URL getURL(String column) {
        return get(result::getURL, column);
    }

    @Override
    public SQLXML getSQLXML(String column) {
        return get(result::getSQLXML, column);
    }

    @Override
    public Timestamp getTimestamp(String column) {
        return get(result::getTimestamp, column);
    }

    @Override
    public Time getTime(String column) {
        return get(result::getTime, column);
    }

    @Override
    public Date getDate(String column) {
        return get(result::getDate, column);
    }

    private static <T> T get(SqlGetter<T> getter, String column) {
        try {
            return getter.apply(column);
        } catch (SQLException ex) {
            throw new SpeedmentException("Error mapping ResultSet to SqlResult on column " + column + ".", ex);
        }
    }

    @FunctionalInterface
    private interface SqlGetter<T> {
        T apply(String columnName) throws SQLException;
    }
}