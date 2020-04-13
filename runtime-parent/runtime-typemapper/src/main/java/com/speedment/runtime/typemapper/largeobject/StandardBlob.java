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
package com.speedment.runtime.typemapper.largeobject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * A standard implementation of the Blob interface.
 * <p>
 * By having an implementation, we do not have to
 * require usage of the java.sql.rowset module.
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class StandardBlob implements Blob {

    private final long origLen;
    private byte[] buf;
    private long len;

    public StandardBlob(byte[] buf) {
        this.origLen = buf.length;
        this.buf = Arrays.copyOf(buf, buf.length);
        this.len = buf.length;
    }

    @Override
    public long length()  {
        assertNotFreed();
        return len;
    }

    @Override
    public byte[] getBytes(final long posOrdinal, final int length) throws SQLException {
        assertNotFreed();
        final int actualLength = Math.min(length, (int) len);
        if (posOrdinal < 1 || len - posOrdinal < 0 ) {
            throw newIllegalArgumentExceptionRange("posOrdinal", 1, len,  posOrdinal);
        }

        final byte[] bytes = new byte[actualLength];
        long pos = posOrdinal -1;
        for (int i = 0; i < actualLength; i++) {
            bytes[i] = buf[(int) pos];
            pos++;
        }
        return bytes;
    }

    @Override
    public InputStream getBinaryStream() {
        assertNotFreed();
        return new ByteArrayInputStream(buf);
    }

    @Override
    public long position(byte[] pattern, long startOrdinal) {
        assertNotFreed();
        if (startOrdinal < 1 || startOrdinal > len) {
            return -1;
        }

        final long patternLength = pattern.length;
        int pos = (int) startOrdinal - 1;
        int i = 0;

        while (pos < len) {
            if (pattern[i] == buf[pos]) {
                if (i + 1 == patternLength) {
                    return (pos + 1) - (patternLength - 1);
                }
                i++; pos++;
            } else if (pattern[i] != buf[pos]) {
                pos++;
            }
        }
        return -1;
    }

    @Override
    public long position(Blob pattern, long start) throws SQLException {
        assertNotFreed();
        return position(pattern.getBytes(1, (int) (pattern.length())), start);
    }

    @Override
    public int setBytes(long pos, byte[] bytes) throws SQLException {
        assertNotFreed();
        return setBytes(pos, bytes, 0, bytes.length);
    }

    @Override
    public int setBytes(long posOrdinal, byte[] bytes, int offset, int length) throws SQLException {
        assertNotFreed();
        if (offset < 0 || offset > bytes.length) {
            throw newIllegalArgumentExceptionRange("offset", 0, bytes.length, offset);
        }

        if (posOrdinal < 1 || posOrdinal > len) {
            throw newIllegalArgumentExceptionRange("pos", 1, length(), posOrdinal);
        }

        if ((long) (length) > origLen) {
            throw new IllegalArgumentException("Buffer is not sufficient to hold the value");
        }

        if ((length + offset) > bytes.length) {
            throw new IllegalArgumentException("Cannot have combined offset and length that is greater that the Blob buffer");
        }

        final long pos = posOrdinal -1;
        int i = 0;
        while ( i < length || (offset + i +1) < (bytes.length-offset) ) {
            buf[(int) pos + i] = bytes[offset + i];
            i++;
        }
        return i;
    }

    @Override
    public OutputStream setBinaryStream(long pos) throws SQLException {
        throw new UnsupportedOperationException("This operation is not supported. Us a SerialBlob instead.");
    }

    @Override
    public void truncate(long length) throws SQLException {
        assertNotFreed();
        if (length > len) {
            throw newIllegalArgumentExceptionRange("Length", 0, len, length);
        } else if ((int) length == 0) {
            buf = new byte[0];
            len = length;
        } else {
            len = length;
            buf = getBytes(1, (int) len);
        }
    }

    @Override
    public void free() throws SQLException {
        // Free up the backing array
        buf = null;
    }

    @Override
    public InputStream getBinaryStream(long pos, long length) throws SQLException {
        assertNotFreed();
        if (pos < 1 || pos > length) {
            throw newIllegalArgumentExceptionRange("pos",1, length, pos );
        }
        if (length > len - pos + 1) {
            throw newIllegalArgumentExceptionRange("length", 1, (len - pos + 1), length);
        }
        return new ByteArrayInputStream(buf, (int) pos - 1, (int) length);
    }

    private void assertNotFreed() {
        if (buf == null) {
            throw new IllegalStateException("Cannot call a method after free() has been called.");
        }
    }

    private IllegalArgumentException newIllegalArgumentExceptionRange(String name, long startInclusive, long endInclusive, long actual) {
        return new IllegalArgumentException(
            "The " + name + " must be in the range [" + startInclusive + ", " + endInclusive + "] but was " + actual + "."
        );
    }

}
