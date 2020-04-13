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
package com.speedment.runtime.core.internal.stream;

import com.speedment.runtime.core.db.SqlFunction;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Emil Forslund
 * @since 3.0.11
 */
final class ResultSetIteratorTest {

    private static final SqlFunction<ResultSet, Integer> RS_MAPPER = rs -> rs.getInt(1);
    private static final int SIZE = 33;

    @Test
    void testZero() {
        final AtomicInteger counter = new AtomicInteger(0);
        final ResultSet rs = new MockResultSet(0);
        final InternalStreamUtil.ResultSetIterator<?> it
            = new InternalStreamUtil.ResultSetIterator<>(rs, $ -> counter.incrementAndGet());

        assertFalse(it.hasNext());
        assertEquals(0, counter.get());
        assertFalse(it.hasNext());
        assertEquals(0, counter.get());
    }

    @Test
    void testOne() {
        final AtomicInteger counter = new AtomicInteger(0);
        final ResultSet rs = new MockResultSet(1);
        final InternalStreamUtil.ResultSetIterator<?> it
            = new InternalStreamUtil.ResultSetIterator<>(rs, $ -> counter.incrementAndGet());

        assertTrue(it.hasNext());
        assertEquals(0, counter.get());
        assertEquals(1, it.next());
        assertFalse(it.hasNext());
        assertEquals(1, counter.get());
    }

    @Test
    void testTwo() {
        final AtomicInteger counter = new AtomicInteger(0);
        final ResultSet rs = new MockResultSet(2);
        final InternalStreamUtil.ResultSetIterator<?> it
            = new InternalStreamUtil.ResultSetIterator<>(rs, $ -> counter.incrementAndGet());

        assertTrue(it.hasNext());
        assertEquals(0, counter.get());
        assertEquals(1, it.next());
        assertTrue(it.hasNext());
        assertEquals(1, counter.get());
        assertEquals(2, it.next());
        assertFalse(it.hasNext());
        assertEquals(2, counter.get());
    }

    @Test
    void testIterate() {
        final ResultSet rs = new MockResultSet(SIZE);
        final InternalStreamUtil.ResultSetIterator<Integer> it
            = new InternalStreamUtil.ResultSetIterator<>(rs, RS_MAPPER);

        for (int i = 0; i < SIZE; i++) {
            assertTrue(it.hasNext());
            assertEquals((Integer) (i + 1), it.next());
        }
        assertFalse(it.hasNext());
    }

    @Test
    void testForEachRemaining() {
        for (int initialNext = 0; initialNext < SIZE; initialNext++) {
            final ResultSet rs = new MockResultSet(SIZE);
            final InternalStreamUtil.ResultSetIterator<Integer> it
                = new InternalStreamUtil.ResultSetIterator<>(rs, RS_MAPPER);

            AtomicInteger expected = new AtomicInteger(0);
            for (int i = 0; i < initialNext; i++) {
                assertTrue(it.hasNext());
                assertEquals((Integer) expected.incrementAndGet(), it.next());
            }

            it.forEachRemaining(rsInt -> {
                assertEquals((Integer) expected.incrementAndGet(), rsInt);
            });

            assertFalse(it.hasNext());

        }
    }

    @Test
    void testEmpty() {
        final ResultSet rs = new MockResultSet(0);
        final InternalStreamUtil.ResultSetIterator<Integer> it
            = new InternalStreamUtil.ResultSetIterator<>(rs, RS_MAPPER);

        assertThrows(NoSuchElementException.class, () -> {
            it.next();
        });
    }

    @Test
    void testRemove() {
        final ResultSet rs = new MockResultSet(1);
        final InternalStreamUtil.ResultSetIterator<Integer> it
            = new InternalStreamUtil.ResultSetIterator<>(rs, RS_MAPPER);

        assertThrows(UnsupportedOperationException.class, it::remove);
    }


        private static final class MockResultSet implements ResultSet {

        private int itemsLeft;
        private int itemsConsumed;
        private boolean closed;

        MockResultSet(int itemsLeft) {
            this.itemsLeft = itemsLeft;
        }

        @Override
        public boolean next() throws SQLException {
            if (itemsLeft > 0) {
                itemsLeft--;
                itemsConsumed++;
                return true;
            } else if (itemsLeft == 0) {
                itemsLeft--;
                return false;
            } else {
                fail("Next called on empty ResultSet.");
                return false;
            }
        }

        @Override
        public boolean wasNull() throws SQLException {
            return false;
        }

        @Override
        public String getString(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public boolean getBoolean(int columnIndex) throws SQLException {
            return false;
        }

        @Override
        public byte getByte(int columnIndex) throws SQLException {
            return (byte) itemsConsumed;
        }

        @Override
        public short getShort(int columnIndex) throws SQLException {
            return (short) itemsConsumed;
        }

        @Override
        public int getInt(int columnIndex) throws SQLException {
            return itemsConsumed;
        }

        @Override
        public long getLong(int columnIndex) throws SQLException {
            return itemsConsumed;
        }

        @Override
        public float getFloat(int columnIndex) throws SQLException {
            return (float) itemsConsumed;
        }

        @Override
        public double getDouble(int columnIndex) throws SQLException {
            return itemsConsumed;
        }

        @Override
        @SuppressWarnings("deprecation")
        public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
            return null;
        }

        @Override
        public byte[] getBytes(int columnIndex) throws SQLException {
            return new byte[0];
        }

        @Override
        public Date getDate(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public Time getTime(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public Timestamp getTimestamp(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public InputStream getAsciiStream(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        @SuppressWarnings("deprecation")
        public InputStream getUnicodeStream(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public InputStream getBinaryStream(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return Integer.toString(itemsConsumed);
        }

        @Override
        public boolean getBoolean(String columnLabel) throws SQLException {
            return false;
        }

        @Override
        public byte getByte(String columnLabel) throws SQLException {
            return 0;
        }

        @Override
        public short getShort(String columnLabel) throws SQLException {
            return 0;
        }

        @Override
        public int getInt(String columnLabel) throws SQLException {
            return 0;
        }

        @Override
        public long getLong(String columnLabel) throws SQLException {
            return 0;
        }

        @Override
        public float getFloat(String columnLabel) throws SQLException {
            return 0;
        }

        @Override
        public double getDouble(String columnLabel) throws SQLException {
            return 0;
        }

        @Override
        @SuppressWarnings("deprecation")
        public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
            return null;
        }

        @Override
        public byte[] getBytes(String columnLabel) throws SQLException {
            return new byte[0];
        }

        @Override
        public Date getDate(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public Time getTime(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public Timestamp getTimestamp(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public InputStream getAsciiStream(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        @SuppressWarnings("deprecation")
        public InputStream getUnicodeStream(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public InputStream getBinaryStream(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return null;
        }

        @Override
        public void clearWarnings() throws SQLException {

        }

        @Override
        public String getCursorName() throws SQLException {
            return null;
        }

        @Override
        public ResultSetMetaData getMetaData() throws SQLException {
            return null;
        }

        @Override
        public Object getObject(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public Object getObject(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public int findColumn(String columnLabel) throws SQLException {
            return 0;
        }

        @Override
        public Reader getCharacterStream(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public Reader getCharacterStream(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public boolean isBeforeFirst() throws SQLException {
            return itemsConsumed == 0;
        }

        @Override
        public boolean isAfterLast() throws SQLException {
            return false;
        }

        @Override
        public boolean isFirst() throws SQLException {
            return itemsConsumed == 1;
        }

        @Override
        public boolean isLast() throws SQLException {
            return false;
        }

        @Override
        public void beforeFirst() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void afterLast() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean first() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean last() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getRow() throws SQLException {
            return 0;
        }

        @Override
        public boolean absolute(int row) throws SQLException {
            return false;
        }

        @Override
        public boolean relative(int rows) throws SQLException {
            return false;
        }

        @Override
        public boolean previous() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setFetchDirection(int direction) throws SQLException {

        }

        @Override
        public int getFetchDirection() throws SQLException {
            return 0;
        }

        @Override
        public void setFetchSize(int rows) throws SQLException {

        }

        @Override
        public int getFetchSize() throws SQLException {
            return 0;
        }

        @Override
        public int getType() throws SQLException {
            return 0;
        }

        @Override
        public int getConcurrency() throws SQLException {
            return 0;
        }

        @Override
        public boolean rowUpdated() throws SQLException {
            return false;
        }

        @Override
        public boolean rowInserted() throws SQLException {
            return false;
        }

        @Override
        public boolean rowDeleted() throws SQLException {
            return false;
        }

        @Override
        public void updateNull(int columnIndex) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBoolean(int columnIndex, boolean x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateByte(int columnIndex, byte x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateShort(int columnIndex, short x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateInt(int columnIndex, int x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateLong(int columnIndex, long x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateFloat(int columnIndex, float x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateDouble(int columnIndex, double x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateString(int columnIndex, String x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBytes(int columnIndex, byte[] x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateDate(int columnIndex, Date x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateTime(int columnIndex, Time x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateObject(int columnIndex, Object x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateNull(String columnLabel) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBoolean(String columnLabel, boolean x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateByte(String columnLabel, byte x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateShort(String columnLabel, short x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateInt(String columnLabel, int x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateLong(String columnLabel, long x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateFloat(String columnLabel, float x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateDouble(String columnLabel, double x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateString(String columnLabel, String x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBytes(String columnLabel, byte[] x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateDate(String columnLabel, Date x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateTime(String columnLabel, Time x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateObject(String columnLabel, Object x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void insertRow() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateRow() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteRow() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void refreshRow() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void cancelRowUpdates() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void moveToInsertRow() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void moveToCurrentRow() throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Statement getStatement() throws SQLException {
            return null;
        }

        @Override
        public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
            return itemsConsumed;
        }

        @Override
        public Ref getRef(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public Blob getBlob(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public Clob getClob(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public Array getArray(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
            return null;
        }

        @Override
        public Ref getRef(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public Blob getBlob(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public Clob getClob(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public Array getArray(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public Date getDate(int columnIndex, Calendar cal) throws SQLException {
            return null;
        }

        @Override
        public Date getDate(String columnLabel, Calendar cal) throws SQLException {
            return null;
        }

        @Override
        public Time getTime(int columnIndex, Calendar cal) throws SQLException {
            return null;
        }

        @Override
        public Time getTime(String columnLabel, Calendar cal) throws SQLException {
            return null;
        }

        @Override
        public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
            return null;
        }

        @Override
        public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
            return null;
        }

        @Override
        public URL getURL(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public URL getURL(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public void updateRef(int columnIndex, Ref x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateRef(String columnLabel, Ref x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBlob(int columnIndex, Blob x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBlob(String columnLabel, Blob x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateClob(int columnIndex, Clob x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateClob(String columnLabel, Clob x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateArray(int columnIndex, Array x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateArray(String columnLabel, Array x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public RowId getRowId(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public RowId getRowId(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public void updateRowId(int columnIndex, RowId x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateRowId(String columnLabel, RowId x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getHoldability() throws SQLException {
            return 0;
        }

        @Override
        public boolean isClosed() throws SQLException {
            return closed;
        }

        @Override
        public void updateNString(int columnIndex, String nString) throws SQLException {

        }

        @Override
        public void updateNString(String columnLabel, String nString) throws SQLException {

        }

        @Override
        public void updateNClob(int columnIndex, NClob nClob) throws SQLException {

        }

        @Override
        public void updateNClob(String columnLabel, NClob nClob) throws SQLException {

        }

        @Override
        public NClob getNClob(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public NClob getNClob(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public SQLXML getSQLXML(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public SQLXML getSQLXML(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {

        }

        @Override
        public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {

        }

        @Override
        public String getNString(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public String getNString(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public Reader getNCharacterStream(int columnIndex) throws SQLException {
            return null;
        }

        @Override
        public Reader getNCharacterStream(String columnLabel) throws SQLException {
            return null;
        }

        @Override
        public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateClob(int columnIndex, Reader reader) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateClob(String columnLabel, Reader reader) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateNClob(int columnIndex, Reader reader) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateNClob(String columnLabel, Reader reader) throws SQLException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
            return null;
        }

        @Override
        public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
            return null;
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }

        @Override
        public void close() throws SQLException {
            this.closed = true;
        }
    }

}
