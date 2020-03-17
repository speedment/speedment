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

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import com.speedment.runtime.typemapper.exception.SpeedmentTypeMapperException;
import com.speedment.runtime.typemapper.internal.largeobject.StringClob;

import java.lang.reflect.Type;
import java.sql.Clob;
import java.sql.SQLException;

/**
 *
 * @author Per Minborg
 */
public final class ClobToStringMapper implements TypeMapper<Clob, String> {

    @Override
    public String getLabel() {
        return "Clob to String";
    }
    
    @Override
    public Type getJavaType(Column column) {
        return String.class;
    }

    @Override
    public String toJavaType(Column column, Class<?> entityType, Clob value) {
        if (value == null) {
            return null;
        } else try {
            if (value.length() < Integer.MAX_VALUE) {
                return value.getSubString(1, (int) value.length());
            } else {
                throw new SpeedmentTypeMapperException(
                    "The provided Clob contains too many characters >" + Integer.MAX_VALUE
                );
            }
        } catch (final SQLException sqle) {
            throw new SpeedmentTypeMapperException("Unable to convert Clob to String.", sqle);
        }
    }

    @Override
    public Clob toDatabaseType(String value) {
        if (value == null) {
            return null;
        }
        return new StringClob(value);
    }
}