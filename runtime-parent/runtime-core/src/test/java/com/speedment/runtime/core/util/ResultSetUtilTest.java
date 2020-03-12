/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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

package com.speedment.runtime.core.util;

import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_ARRAY;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_BIG_DECIMAL;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_BLOB;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_BOOLEAN;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_BYTE;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_BYTES;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_CLOB;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_DATE;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_DOUBLE;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_FLOAT;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_INT;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_LONG;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_NCLOB;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_OBJECT;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_REF;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_ROW_ID;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_SHORT;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_SQLXML;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_STRING;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_TIME;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_TIMESTAMP;
import static com.speedment.runtime.core.util.ResultSetUtilTest.ResultSetAccessHelper.RS_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

final class ResultSetUtilTest {

    private static final ResultSet RESULT_SET = mockResultSet();
    private static final ResultSet NULL_RESULT_SET = mockNullResultSet();

    @Test
    void getObject() throws SQLException {
        assertEquals(RS_OBJECT.getValue(), ResultSetUtil.getObject(RESULT_SET, RS_OBJECT.getLabel()));
        assertEquals(RS_OBJECT.getValue(), ResultSetUtil.getObject(RESULT_SET, RS_OBJECT.getIndex()));

        assertNull(ResultSetUtil.getObject(NULL_RESULT_SET, RS_OBJECT.getLabel()));
        assertNull(ResultSetUtil.getObject(NULL_RESULT_SET, RS_OBJECT.getIndex()));
    }

    @Test
    void getBoolean() throws SQLException {
        assertEquals(RS_BOOLEAN.getValue(), ResultSetUtil.getBoolean(RESULT_SET, RS_BOOLEAN.getLabel()));
        assertEquals(RS_BOOLEAN.getValue(), ResultSetUtil.getBoolean(RESULT_SET, RS_BOOLEAN.getIndex()));

        assertNull(ResultSetUtil.getBoolean(NULL_RESULT_SET, RS_BOOLEAN.getLabel()));
        assertNull(ResultSetUtil.getBoolean(NULL_RESULT_SET, RS_BOOLEAN.getIndex()));
    }

    @Test
    void getByte() throws SQLException {
        assertEquals(RS_BYTE.getValue(), ResultSetUtil.getByte(RESULT_SET, RS_BYTE.getLabel()));
        assertEquals(RS_BYTE.getValue(), ResultSetUtil.getByte(RESULT_SET, RS_BYTE.getIndex()));

        assertNull(ResultSetUtil.getByte(NULL_RESULT_SET, RS_BYTE.getLabel()));
        assertNull(ResultSetUtil.getByte(NULL_RESULT_SET, RS_BYTE.getIndex()));
    }

    @Test
    void getShort() throws SQLException {
        assertEquals(RS_SHORT.getValue(), ResultSetUtil.getShort(RESULT_SET, RS_SHORT.getLabel()));
        assertEquals(RS_SHORT.getValue(), ResultSetUtil.getShort(RESULT_SET, RS_SHORT.getIndex()));

        assertNull(ResultSetUtil.getShort(NULL_RESULT_SET, RS_SHORT.getLabel()));
        assertNull(ResultSetUtil.getShort(NULL_RESULT_SET, RS_SHORT.getIndex()));
    }

    @Test
    void getInt() throws SQLException {
        assertEquals(RS_INT.getValue(), ResultSetUtil.getInt(RESULT_SET, RS_INT.getLabel()));
        assertEquals(RS_INT.getValue(), ResultSetUtil.getInt(RESULT_SET, RS_INT.getIndex()));

        assertNull(ResultSetUtil.getInt(NULL_RESULT_SET, RS_INT.getLabel()));
        assertNull(ResultSetUtil.getInt(NULL_RESULT_SET, RS_INT.getIndex()));
    }

    @Test
    void getLong() throws SQLException {
        assertEquals(RS_LONG.getValue(), ResultSetUtil.getLong(RESULT_SET, RS_LONG.getLabel()));
        assertEquals(RS_LONG.getValue(), ResultSetUtil.getLong(RESULT_SET, RS_LONG.getIndex()));

        assertNull(ResultSetUtil.getLong(NULL_RESULT_SET, RS_LONG.getLabel()));
        assertNull(ResultSetUtil.getLong(NULL_RESULT_SET, RS_LONG.getIndex()));
    }

    @Test
    void getFloat() throws SQLException {
        assertEquals(RS_FLOAT.getValue(), ResultSetUtil.getFloat(RESULT_SET, RS_FLOAT.getLabel()));
        assertEquals(RS_FLOAT.getValue(), ResultSetUtil.getFloat(RESULT_SET, RS_FLOAT.getIndex()));

        assertNull(ResultSetUtil.getFloat(NULL_RESULT_SET, RS_FLOAT.getLabel()));
        assertNull(ResultSetUtil.getFloat(NULL_RESULT_SET, RS_FLOAT.getIndex()));
    }

    @Test
    void getDouble() throws SQLException {
        assertEquals(RS_DOUBLE.getValue(), ResultSetUtil.getDouble(RESULT_SET, RS_DOUBLE.getLabel()));
        assertEquals(RS_DOUBLE.getValue(), ResultSetUtil.getDouble(RESULT_SET, RS_DOUBLE.getIndex()));

        assertNull(ResultSetUtil.getDouble(NULL_RESULT_SET, RS_DOUBLE.getLabel()));
        assertNull(ResultSetUtil.getDouble(NULL_RESULT_SET, RS_DOUBLE.getIndex()));
    }

    @Test
    void getString() throws SQLException {
        assertEquals(RS_STRING.getValue(), ResultSetUtil.getString(RESULT_SET, RS_STRING.getLabel()));
        assertEquals(RS_STRING.getValue(), ResultSetUtil.getString(RESULT_SET, RS_STRING.getIndex()));

        assertNull(ResultSetUtil.getString(NULL_RESULT_SET, RS_STRING.getLabel()));
        assertNull(ResultSetUtil.getString(NULL_RESULT_SET, RS_STRING.getIndex()));
    }

    @Test
    void getDate() throws SQLException {
        assertEquals(RS_DATE.getValue(), ResultSetUtil.getDate(RESULT_SET, RS_DATE.getLabel()));
        assertEquals(RS_DATE.getValue(), ResultSetUtil.getDate(RESULT_SET, RS_DATE.getIndex()));

        assertNull(ResultSetUtil.getDate(NULL_RESULT_SET, RS_DATE.getLabel()));
        assertNull(ResultSetUtil.getDate(NULL_RESULT_SET, RS_DATE.getIndex()));
    }

    @Test
    void getTime() throws SQLException {
        assertEquals(RS_TIME.getValue(), ResultSetUtil.getTime(RESULT_SET, RS_TIME.getLabel()));
        assertEquals(RS_TIME.getValue(), ResultSetUtil.getTime(RESULT_SET, RS_TIME.getIndex()));

        assertNull(ResultSetUtil.getTime(NULL_RESULT_SET, RS_TIME.getLabel()));
        assertNull(ResultSetUtil.getTime(NULL_RESULT_SET, RS_TIME.getIndex()));
    }

    @Test
    void getTimestamp() throws SQLException {
        assertEquals(RS_TIMESTAMP.getValue(), ResultSetUtil.getTimestamp(RESULT_SET, RS_TIMESTAMP.getLabel()));
        assertEquals(RS_TIMESTAMP.getValue(), ResultSetUtil.getTimestamp(RESULT_SET, RS_TIMESTAMP.getIndex()));

        assertNull(ResultSetUtil.getTimestamp(NULL_RESULT_SET, RS_TIMESTAMP.getLabel()));
        assertNull(ResultSetUtil.getTimestamp(NULL_RESULT_SET, RS_TIMESTAMP.getIndex()));
    }

    @Test
    void getBigDecimal() throws SQLException {
        assertEquals(RS_BIG_DECIMAL.getValue(), ResultSetUtil.getBigDecimal(RESULT_SET, RS_BIG_DECIMAL.getLabel()));
        assertEquals(RS_BIG_DECIMAL.getValue(), ResultSetUtil.getBigDecimal(RESULT_SET, RS_BIG_DECIMAL.getIndex()));

        assertNull(ResultSetUtil.getBigDecimal(NULL_RESULT_SET, RS_BIG_DECIMAL.getLabel()));
        assertNull(ResultSetUtil.getBigDecimal(NULL_RESULT_SET, RS_BIG_DECIMAL.getIndex()));
    }

    @Test
    void getBigInteger() throws SQLException {
        assertEquals(RS_BIG_DECIMAL.getValueAs(BigDecimal.class).toBigInteger(), ResultSetUtil.getBigInteger(RESULT_SET, RS_BIG_DECIMAL.getIndex()));

        assertNull(ResultSetUtil.getBigInteger(NULL_RESULT_SET, RS_BIG_DECIMAL.getIndex()));
    }

    @Test
    void getBlob() throws SQLException {
        assertEquals(RS_BLOB.getValue(), ResultSetUtil.getBlob(RESULT_SET, RS_BLOB.getLabel()));
        assertEquals(RS_BLOB.getValue(), ResultSetUtil.getBlob(RESULT_SET, RS_BLOB.getIndex()));

        assertNull(ResultSetUtil.getBlob(NULL_RESULT_SET, RS_BLOB.getLabel()));
        assertNull(ResultSetUtil.getBlob(NULL_RESULT_SET, RS_BLOB.getIndex()));
    }

    @Test
    void getClob() throws SQLException {
        assertEquals(RS_CLOB.getValue(), ResultSetUtil.getClob(RESULT_SET, RS_CLOB.getLabel()));
        assertEquals(RS_CLOB.getValue(), ResultSetUtil.getClob(RESULT_SET, RS_CLOB.getIndex()));

        assertNull(ResultSetUtil.getClob(NULL_RESULT_SET, RS_CLOB.getLabel()));
        assertNull(ResultSetUtil.getClob(NULL_RESULT_SET, RS_CLOB.getIndex()));
    }

    @Test
    void getArray() throws SQLException {
        assertEquals(RS_ARRAY.getValue(), ResultSetUtil.getArray(RESULT_SET, RS_ARRAY.getLabel()));
        assertEquals(RS_ARRAY.getValue(), ResultSetUtil.getArray(RESULT_SET, RS_ARRAY.getIndex()));

        assertNull(ResultSetUtil.getArray(NULL_RESULT_SET, RS_ARRAY.getLabel()));
        assertNull(ResultSetUtil.getArray(NULL_RESULT_SET, RS_ARRAY.getIndex()));
    }

    @Test
    void getRef() throws SQLException {
        assertEquals(RS_REF.getValue(), ResultSetUtil.getRef(RESULT_SET, RS_REF.getLabel()));
        assertEquals(RS_REF.getValue(), ResultSetUtil.getRef(RESULT_SET, RS_REF.getIndex()));

        assertNull(ResultSetUtil.getRef(NULL_RESULT_SET, RS_REF.getLabel()));
        assertNull(ResultSetUtil.getRef(NULL_RESULT_SET, RS_REF.getIndex()));
    }

    @Test
    void getURL() throws SQLException {
        assertEquals(RS_URL.getValue(), ResultSetUtil.getURL(RESULT_SET, RS_URL.getLabel()));
        assertEquals(RS_URL.getValue(), ResultSetUtil.getURL(RESULT_SET, RS_URL.getIndex()));

        assertNull(ResultSetUtil.getURL(NULL_RESULT_SET, RS_URL.getLabel()));
        assertNull(ResultSetUtil.getURL(NULL_RESULT_SET, RS_URL.getIndex()));
    }

    @Test
    void getRowId() throws SQLException {
        assertEquals(RS_ROW_ID.getValue(), ResultSetUtil.getRowId(RESULT_SET, RS_ROW_ID.getLabel()));
        assertEquals(RS_ROW_ID.getValue(), ResultSetUtil.getRowId(RESULT_SET, RS_ROW_ID.getIndex()));

        assertNull(ResultSetUtil.getRowId(NULL_RESULT_SET, RS_ROW_ID.getLabel()));
        assertNull(ResultSetUtil.getRowId(NULL_RESULT_SET, RS_ROW_ID.getIndex()));
    }

    @Test
    void getNClob() throws SQLException {
        assertEquals(RS_NCLOB.getValue(), ResultSetUtil.getNClob(RESULT_SET, RS_NCLOB.getLabel()));
        assertEquals(RS_NCLOB.getValue(), ResultSetUtil.getNClob(RESULT_SET, RS_NCLOB.getIndex()));

        assertNull(ResultSetUtil.getNClob(NULL_RESULT_SET, RS_NCLOB.getLabel()));
        assertNull(ResultSetUtil.getNClob(NULL_RESULT_SET, RS_NCLOB.getIndex()));
    }

    @Test
    void getSQLXML() throws SQLException {
        assertEquals(RS_SQLXML.getValue(), ResultSetUtil.getSQLXML(RESULT_SET, RS_SQLXML.getLabel()));
        assertEquals(RS_SQLXML.getValue(), ResultSetUtil.getSQLXML(RESULT_SET, RS_SQLXML.getIndex()));

        assertNull(ResultSetUtil.getSQLXML(NULL_RESULT_SET, RS_SQLXML.getLabel()));
        assertNull(ResultSetUtil.getSQLXML(NULL_RESULT_SET, RS_SQLXML.getIndex()));
    }

    @Test
    void getBytes() throws SQLException {
        assertEquals(RS_BYTES.getValue(), ResultSetUtil.getBytes(RESULT_SET, RS_BYTES.getIndex()));

        assertNull(ResultSetUtil.getBytes(NULL_RESULT_SET, RS_BYTES.getIndex()));
    }

    @Test
    void getUUID() throws SQLException {
        assertEquals(RS_OBJECT.getValue(), ResultSetUtil.getUUID(RESULT_SET, RS_OBJECT.getLabel()));
        assertEquals(RS_OBJECT.getValue(), ResultSetUtil.getUUID(RESULT_SET, RS_OBJECT.getIndex()));

        assertNull(ResultSetUtil.getUUID(NULL_RESULT_SET, RS_OBJECT.getLabel()));
        assertNull(ResultSetUtil.getUUID(NULL_RESULT_SET, RS_OBJECT.getIndex()));
    }

    private static ResultSet mockResultSet() {
        final ResultSet resultSet = mock(ResultSet.class);
        try {
            when(resultSet.getObject(RS_OBJECT.getLabel())).thenReturn(RS_OBJECT.getValue());
            when(resultSet.getObject(RS_OBJECT.getLabel(), UUID.class)).thenReturn(RS_OBJECT.getValueAs(UUID.class));
            when(resultSet.getBoolean(RS_BOOLEAN.getLabel())).thenReturn(RS_BOOLEAN.getValueAs(Boolean.class));
            when(resultSet.getByte(RS_BYTE.getLabel())).thenReturn(RS_BYTE.getValueAs(Byte.class));
            when(resultSet.getShort(RS_SHORT.getLabel())).thenReturn(RS_SHORT.getValueAs(Short.class));
            when(resultSet.getInt(RS_INT.getLabel())).thenReturn(RS_INT.getValueAs(Integer.class));
            when(resultSet.getLong(RS_LONG.getLabel())).thenReturn(RS_LONG.getValueAs(Long.class));
            when(resultSet.getFloat(RS_FLOAT.getLabel())).thenReturn(RS_FLOAT.getValueAs(Float.class));
            when(resultSet.getDouble(RS_DOUBLE.getLabel())).thenReturn(RS_DOUBLE.getValueAs(Double.class));
            when(resultSet.getString(RS_STRING.getLabel())).thenReturn(RS_STRING.getValueAs(String.class));
            when(resultSet.getDate(RS_DATE.getLabel())).thenReturn(RS_DATE.getValueAs(Date.class));
            when(resultSet.getTime(RS_TIME.getLabel())).thenReturn(RS_TIME.getValueAs(Time.class));
            when(resultSet.getTimestamp(RS_TIMESTAMP.getLabel())).thenReturn(RS_TIMESTAMP.getValueAs(Timestamp.class));
            when(resultSet.getBigDecimal(RS_BIG_DECIMAL.getLabel())).thenReturn(RS_BIG_DECIMAL.getValueAs(BigDecimal.class));
            when(resultSet.getBlob(RS_BLOB.getLabel())).thenReturn(RS_BLOB.getValueAs(Blob.class));
            when(resultSet.getClob(RS_CLOB.getLabel())).thenReturn(RS_CLOB.getValueAs(Clob.class));
            when(resultSet.getArray(RS_ARRAY.getLabel())).thenReturn(RS_ARRAY.getValueAs(Array.class));
            when(resultSet.getRef(RS_REF.getLabel())).thenReturn(RS_REF.getValueAs(Ref.class));
            when(resultSet.getURL(RS_URL.getLabel())).thenReturn(RS_URL.getValueAs(URL.class));
            when(resultSet.getNClob(RS_NCLOB.getLabel())).thenReturn(RS_NCLOB.getValueAs(NClob.class));
            when(resultSet.getRowId(RS_ROW_ID.getLabel())).thenReturn(RS_ROW_ID.getValueAs(RowId.class));
            when(resultSet.getSQLXML(RS_SQLXML.getLabel())).thenReturn(RS_SQLXML.getValueAs(SQLXML.class));
            when(resultSet.getBytes(RS_BYTES.getLabel())).thenReturn(RS_BYTES.getValueAs(byte[].class));

            when(resultSet.getObject(RS_OBJECT.getIndex())).thenReturn(RS_OBJECT.getValue());
            when(resultSet.getBoolean(RS_BOOLEAN.getIndex())).thenReturn(RS_BOOLEAN.getValueAs(Boolean.class));
            when(resultSet.getByte(RS_BYTE.getIndex())).thenReturn(RS_BYTE.getValueAs(Byte.class));
            when(resultSet.getShort(RS_SHORT.getIndex())).thenReturn(RS_SHORT.getValueAs(Short.class));
            when(resultSet.getInt(RS_INT.getIndex())).thenReturn(RS_INT.getValueAs(Integer.class));
            when(resultSet.getLong(RS_LONG.getIndex())).thenReturn(RS_LONG.getValueAs(Long.class));
            when(resultSet.getFloat(RS_FLOAT.getIndex())).thenReturn(RS_FLOAT.getValueAs(Float.class));
            when(resultSet.getDouble(RS_DOUBLE.getIndex())).thenReturn(RS_DOUBLE.getValueAs(Double.class));
            when(resultSet.getString(RS_STRING.getIndex())).thenReturn(RS_STRING.getValueAs(String.class));
            when(resultSet.getDate(RS_DATE.getIndex())).thenReturn(RS_DATE.getValueAs(Date.class));
            when(resultSet.getTime(RS_TIME.getIndex())).thenReturn(RS_TIME.getValueAs(Time.class));
            when(resultSet.getTimestamp(RS_TIMESTAMP.getIndex())).thenReturn(RS_TIMESTAMP.getValueAs(Timestamp.class));
            when(resultSet.getBigDecimal(RS_BIG_DECIMAL.getIndex())).thenReturn(RS_BIG_DECIMAL.getValueAs(BigDecimal.class));
            when(resultSet.getBlob(RS_BLOB.getIndex())).thenReturn(RS_BLOB.getValueAs(Blob.class));
            when(resultSet.getClob(RS_CLOB.getIndex())).thenReturn(RS_CLOB.getValueAs(Clob.class));
            when(resultSet.getArray(RS_ARRAY.getIndex())).thenReturn(RS_ARRAY.getValueAs(Array.class));
            when(resultSet.getRef(RS_REF.getIndex())).thenReturn(RS_REF.getValueAs(Ref.class));
            when(resultSet.getURL(RS_URL.getIndex())).thenReturn(RS_URL.getValueAs(URL.class));
            when(resultSet.getNClob(RS_NCLOB.getIndex())).thenReturn(RS_NCLOB.getValueAs(NClob.class));
            when(resultSet.getRowId(RS_ROW_ID.getIndex())).thenReturn(RS_ROW_ID.getValueAs(RowId.class));
            when(resultSet.getSQLXML(RS_SQLXML.getIndex())).thenReturn(RS_SQLXML.getValueAs(SQLXML.class));
            when(resultSet.getBytes(RS_BYTES.getIndex())).thenReturn(RS_BYTES.getValueAs(byte[].class));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    private static ResultSet mockNullResultSet() {
        final ResultSet resultSet = mock(ResultSet.class);
        try {
            when(resultSet.wasNull()).thenReturn(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    enum ResultSetAccessHelper {
        RS_OBJECT(UUID.randomUUID()),
        RS_BOOLEAN(true),
        RS_BYTE((byte) 1),
        RS_SHORT((short) 1),
        RS_INT(1),
        RS_LONG(1L),
        RS_FLOAT(1.0f),
        RS_DOUBLE(1.0d),
        RS_STRING("string"),
        RS_DATE(Date.valueOf(LocalDate.now())),
        RS_TIME(Time.valueOf(LocalTime.now())),
        RS_TIMESTAMP(Timestamp.valueOf(LocalDateTime.now())),
        RS_BIG_DECIMAL(BigDecimal.valueOf(1.0d)),
        RS_BLOB(newBlob()),
        RS_CLOB(newClob()),
        RS_ARRAY(newArray()),
        RS_REF(newRef()),
        RS_URL(newUrl()),
        RS_ROW_ID(newRowId()),
        RS_NCLOB(newNClob()),
        RS_SQLXML(newSQLXML()),
        RS_BYTES("string".getBytes())
        ;

        private final String label;
        private final int index;
        private final Object value;

        ResultSetAccessHelper(Object value) {
            this.label = name().toLowerCase();
            this.index = ordinal() + 1;
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public int getIndex() {
            return index;
        }

        public Object getValue() {
            return value;
        }

        public <T> T getValueAs(Class<T> clazz) {
            return clazz.cast(value);
        }

        private static Blob newBlob() {
            return mock(Blob.class);
        }

        private static Clob newClob() {
            return mock(Clob.class);
        }

        private static NClob newNClob() {
            return mock(NClob.class);
        }

        private static Array newArray() {
            return mock(Array.class);
        }

        private static Ref newRef() {
            return mock(Ref.class);
        }

        private static RowId newRowId() {
            return mock(RowId.class);
        }

        private static SQLXML newSQLXML() {
            return mock(SQLXML.class);
        }

        private static URL newUrl() {
            try {
                return new URL("url");
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }
}
