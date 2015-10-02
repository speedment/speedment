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
package com.speedment.db.crud;

import com.speedment.annotation.Api;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;

/**
 * @author Emil Forslund
 * @since 2.2
 */
@Api(version = "2.2")
public interface Result {

    Array getArray(String columnName);
    BigDecimal getBigDecimal(String columnName);
    Blob getBlob(String columnName);
    Clob getClob(String columnName);
    NClob getNClob(String columnName);
    Ref getRef(String columnName);
    Object getObject(String columnName);
    double getDouble(String columnName);
    long getLong(String columnName);
    float getFloat(String columnName);
    int getInt(String columnName);
    short getShort(String columnName);
    byte getByte(String columnName);
    boolean getBoolean(String columnName);
    String getString(String columnName);
    URL getURL(String columnName);
    SQLXML getSQLXML(String columnName);
    Timestamp getTimestamp(String columnName);
    Time getTime(String columnName);
    Date getDate(String columnName);
    
    @SuppressWarnings("unchecked")
    default <T> T get(String columnName, Class<T> mapping) {
        final T result;
        
        if (Array.class.isAssignableFrom(mapping)) {
            result = (T) getArray(columnName);
        } else if (BigDecimal.class.isAssignableFrom(mapping)) {
            result = (T) getBigDecimal(columnName);
        } else if (Blob.class.isAssignableFrom(mapping)) {
            result = (T) getBlob(columnName);
        } else if (Clob.class.isAssignableFrom(mapping)) {
            result = (T) getClob(columnName);
        } else if (NClob.class.isAssignableFrom(mapping)) {
            result = (T) getNClob(columnName);
        } else if (Ref.class.isAssignableFrom(mapping)) {
            result = (T) getRef(columnName);
        } else if (String.class.isAssignableFrom(mapping)) {
            result = (T) getString(columnName);
        } else if (URL.class.isAssignableFrom(mapping)) {
            result = (T) getURL(columnName);
        } else if (SQLXML.class.isAssignableFrom(mapping)) {
            result = (T) getSQLXML(columnName);
        } else if (Timestamp.class.isAssignableFrom(mapping)) {
            result = (T) getTimestamp(columnName);
        } else if (Time.class.isAssignableFrom(mapping)) {
            result = (T) getTime(columnName);
        } else if (Date.class.isAssignableFrom(mapping)) {
            result = (T) getDate(columnName);
        } else if (Double.class.isAssignableFrom(mapping)) {
            result = (T) (Double) getDouble(columnName);
        } else if (Long.class.isAssignableFrom(mapping)) {
            result = (T) (Long) getLong(columnName);
        } else if (Float.class.isAssignableFrom(mapping)) {
            result = (T) (Float) getFloat(columnName);
        } else if (Integer.class.isAssignableFrom(mapping)) {
            result = (T) (Integer) getInt(columnName);
        } else if (Short.class.isAssignableFrom(mapping)) {
            result = (T) (Short) getShort(columnName);
        } else if (Byte.class.isAssignableFrom(mapping)) {
            result = (T) (Byte) getByte(columnName);
        } else if (Boolean.class.isAssignableFrom(mapping)) {
            result = (T) (Boolean) getBoolean(columnName);
        } else {
            throw new UnsupportedOperationException(
                "Unknown wrapper mapping type: " + mapping.getSimpleName()
            );
        }
        
        return result;
    }

}