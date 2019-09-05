/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.example.typemapper;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.typemapper.TypeMapper;
import java.lang.reflect.Type;

/**
 *
 * @author Per Minborg
 */
public final class IntegerZeroOneToYesNoTypeMapper
    implements TypeMapper<Integer, String> {

    @Override
    public String getLabel() {
        return "Integer (0|1) to String Yes/No";
    }

    @Override
    public Type getJavaType(Column column) {
        return String.class;
    }

    @Override
    public String toJavaType(Column column, Class<?> entityType, Integer value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case 0:
                return "No";
            case 1:
                return "Yes";
            default:
                throw new IllegalArgumentException("Value must be either 0 or 1. Was " + value);
        }
    }

    @Override
    public Integer toDatabaseType(String value) {
        switch (value) {
            case "No":
                return 0;
            case "Yes":
                return 1;
            default:
                throw new IllegalArgumentException("Value must be either Yes or No. Was " + value);
        }
    }

    @Override
    public Ordering getOrdering() {
        return Ordering.RETAIN;
    }

}
