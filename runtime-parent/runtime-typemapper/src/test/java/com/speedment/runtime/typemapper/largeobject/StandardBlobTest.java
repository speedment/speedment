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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;

class StandardBlobTest {

    private static final byte[] BYTES = "array content".getBytes();

    private StandardBlob standardBlob;

    @BeforeEach
    void createBlob() {
        standardBlob = new StandardBlob(BYTES);
    }

    @Test
    void getBlobLength() throws SQLException {
        assertEquals(BYTES.length, standardBlob.length());
    }

    @Test
    void getAllBytes() throws SQLException {
        assertArrayEquals(BYTES, standardBlob.getBytes(1, (int) standardBlob.length()));
    }

    @Test
    void getPartialBytes() throws SQLException {
        final int startIndex = 2;
        final int endIndex = BYTES.length - 4;

        byte[] expected = Arrays.copyOfRange(BYTES, startIndex, endIndex);
        byte[] actual = standardBlob.getBytes(startIndex + 1, endIndex - startIndex);

        assertArrayEquals(expected, actual);
    }

    @Test
    void getBlobStartPosition() throws SQLException {
        assertEquals(1, standardBlob.position(standardBlob, 1));
    }

    @Test
    void getSliceStartPosition() throws SQLException {
        final int startIndex = 2;
        final int endIndex = BYTES.length - 4;

        byte[] arraySlice = Arrays.copyOfRange(BYTES, startIndex, endIndex);

        long sliceStartIndex = standardBlob.position(arraySlice, 1);

        assertEquals(startIndex, sliceStartIndex - 1);
    }

    @Test
    void overrideBlobContents() throws SQLException {
        byte[] originalBytes = standardBlob.getBytes(1, (int) standardBlob.length());

        byte[] newBytes = new byte[BYTES.length];
        standardBlob.setBytes(1, newBytes);

        newBytes = standardBlob.getBytes(1, (int) standardBlob.length());

        assertFalse(Arrays.equals(originalBytes, newBytes));
    }

    @Test
    void truncateBlob() throws SQLException {
        long originalLength = standardBlob.length();

        standardBlob.truncate(originalLength - 5);

        long newLength = standardBlob.length();

        assertNotEquals(originalLength, newLength);
    }

    @Test()
    void freeBlob() throws SQLException {
        assertThrows(IllegalStateException.class, () -> {
            standardBlob.free();
            standardBlob.getBytes(1, 1);
        });
    }

}
