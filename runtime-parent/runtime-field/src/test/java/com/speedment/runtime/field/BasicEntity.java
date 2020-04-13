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
package com.speedment.runtime.field;

import com.speedment.runtime.config.identifier.ColumnIdentifier;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class BasicEntity {
    
    public enum Identifier implements ColumnIdentifier<BasicEntity> {

        VAR_BYTE    ("test_byte"),
        VAR_SHORT   ("test_short"),
        VAR_INT     ("test_int"),
        VAR_LONG    ("test_long"),
        VAR_FLOAT   ("test_float"),
        VAR_DOUBLE  ("test_double"),
        VAR_CHAR    ("test_char"),
        VAR_BOOLEAN ("test_boolean"),
        VAR_STRING  ("test_string");
        
        private final String columnName;
        
        Identifier(String columnName) {
            this.columnName = columnName;
        }

        @Override
        public String getDbmsId() {
            return "test_dbms";
        }

        @Override
        public String getSchemaId() {
            return "test_schema";
        }

        @Override
        public String getTableId() {
            return "test_table";
        }

        @Override
        public String getColumnId() {
            return columnName;
        }
    }

    private byte varByte;
    private short varShort;
    private int varInt;
    private long varLong;
    private float varFloat;
    private double varDouble;
    private char varChar;
    private boolean varBoolean;
    private String varString;

    public BasicEntity() {}

    public byte getVarByte() {
        return varByte;
    }

    public BasicEntity setVarByte(byte varByte) {
        this.varByte = varByte;
        return this;
    }

    public short getVarShort() {
        return varShort;
    }

    public BasicEntity setVarShort(short varShort) {
        this.varShort = varShort;
        return this;
    }

    public int getVarInt() {
        return varInt;
    }

    public BasicEntity setVarInt(int varInt) {
        this.varInt = varInt;
        return this;
    }

    public long getVarLong() {
        return varLong;
    }

    public BasicEntity setVarLong(long varLong) {
        this.varLong = varLong;
        return this;
    }

    public float getVarFloat() {
        return varFloat;
    }

    public BasicEntity setVarFloat(float varFloat) {
        this.varFloat = varFloat;
        return this;
    }

    public double getVarDouble() {
        return varDouble;
    }

    public BasicEntity setVarDouble(double varDouble) {
        this.varDouble = varDouble;
        return this;
    }

    public char getVarChar() {
        return varChar;
    }

    public BasicEntity setVarChar(char varChar) {
        this.varChar = varChar;
        return this;
    }

    public boolean isVarBoolean() {
        return varBoolean;
    }

    public BasicEntity setVarBoolean(boolean varBoolean) {
        this.varBoolean = varBoolean;
        return this;
    }

    public String getVarString() {
        return varString;
    }

    public BasicEntity setVarString(String varString) {
        this.varString = varString;
        return this;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BasicEntity) {
            return this == obj;
        } else return false;
    }
}