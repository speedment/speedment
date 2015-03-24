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
package com.speedment.orm.runtime.typemapping;

import com.speedment.orm.config.model.Dbms;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;

/**
 *
 * @author pemi
 */
public enum StandardJavaTypeMapping implements JavaTypeMapping {

    OBJECT(Object.class, "Object"),
    BOOLEAN(Boolean.class, "Boolean"),
    BYTE(Byte.class, "Byte"),
    SHORT(Short.class, "Short"),
    INTEGER(Integer.class, "Int"),
    LONG(Long.class, "Long"),
    FLOAT(Float.class, "Float"),
    DOUBLE(Double.class, "Double"),
    STRING(String.class, "String"),
    Date(Date.class, "Date"),
    TIME(Time.class, "Time"),
    TIMESTAMP(Timestamp.class, "Timestamp"),
    BIG_DECIMAL(Timestamp.class, "BigDecimal"),
    BLOB(Blob.class, "Blob"),
    CLOB(Clob.class, "Clob"),
    ARRAY(Array.class, "Array"),
    REF(Ref.class, "Ref"),
    URL(URL.class, "URL"),
    ROW_ID(RowId.class, "RowId"),
    N_CLOB(NClob.class, "NClob"),
    SQLXML(SQLXML.class, "SQLXML");

    private StandardJavaTypeMapping(Class<?> clazz, String resultSetMethodName) {
        this.clazz = clazz;
        this.resultSetMethodName = resultSetMethodName;
    }

    private final Class<?> clazz;
    private final String resultSetMethodName;

    @Override
    public Class<?> getJavaClass() {
        return clazz;
    }

    @Override
    public String getResultSetMethodName(Dbms dbms) {
        return resultSetMethodName;
    }

}
