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
package com.speedment.runtime.typemapper.internal.largeobject;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */

public final class StringClob implements Clob {

    private String inner;

    public StringClob(String inner) {
        this.inner = requireNonNull(inner);
    }

    @Override
    public long length() throws SQLException {
        assertNotFreeNotCalled();
        return inner.length();
    }

    @Override
    public String getSubString(final long ordinalPosition, final int len) throws SQLException {
        assertNotFreeNotCalled();
        if (inner.isEmpty()) {
            return inner;
        }
        assertInRange(ordinalPosition);
        final int effectiveLen;
        if (len > inner.length() - ordinalPosition + 1) {
            // Truncate length
            effectiveLen = (int) (inner.length() - ordinalPosition + 1);
        } else {
            effectiveLen = len;
        }
        return inner.substring((int) ordinalPosition - 1, effectiveLen);
    }

    @Override
    public Reader getCharacterStream() throws SQLException {
        assertNotFreeNotCalled();
        return new StringReader(inner);
    }

    @Override
    public InputStream getAsciiStream() throws SQLException {
        assertNotFreeNotCalled();
        return new ByteArrayInputStream(inner.getBytes(StandardCharsets.US_ASCII));
    }

    @Override
    public long position(final String str, final long startOrdinalPosition) throws SQLException {
        assertNotFreeNotCalled();
        assertInRange(startOrdinalPosition);
        final int index = inner.indexOf(str, (int) startOrdinalPosition - 1);
        return (index == -1) ? (-1) : (index + 1);
    }

    @Override
    public long position(final Clob clob, final long start) throws SQLException {
        requireNonNull(clob);
        assertNotFreeNotCalled();
        return position(clob.getSubString(1, (int) clob.length()), start);
    }

    @Override
    public int setString(final long pos, final String str) throws SQLException {
        assertNotFreeNotCalled();
        throw newSQLFeatureNotSupportedException();
    }

    @Override
    public int setString(long pos, String str, int offset, int len) throws SQLException {
        assertNotFreeNotCalled();
        throw newSQLFeatureNotSupportedException();
    }

    @Override
    public OutputStream setAsciiStream(long pos) throws SQLException {
        assertNotFreeNotCalled();
        throw newSQLFeatureNotSupportedException();
    }

    @Override
    public Writer setCharacterStream(long ordinalPosition) throws SQLException {
        assertNotFreeNotCalled();
        assertInRange(ordinalPosition);
        throw newSQLFeatureNotSupportedException();
    }

    @Override
    public void truncate(long len) throws SQLException {
        assertNotFreeNotCalled();
        throw newSQLFeatureNotSupportedException();
    }

    private SQLFeatureNotSupportedException newSQLFeatureNotSupportedException() {
        return new SQLFeatureNotSupportedException("Not supported for an unmodifiable Clob.");
    }

    @Override
    public void free() throws SQLException {
        inner = null;
    }

    @Override
    public Reader getCharacterStream(long pos, long length) throws SQLException {
        assertNotFreeNotCalled();
        if (length > Integer.MAX_VALUE) {
            throw new SQLException("Length cannot be >" + Integer.MAX_VALUE);
        }
        return new StringReader(getSubString(pos, (int) length));
    }

    private void assertNotFreeNotCalled() throws SQLException {
        if (inner == null) {
            throw new SQLException("The method free() was already called on this Clob");
        }
    }

    private void assertInRange(long ordinalPosition) throws SQLException {
        if (ordinalPosition > inner.length() || ordinalPosition < 1) {
            throw new SQLException("Starting position cannot be < 1 or >" + inner.length());
        }
    }

}
