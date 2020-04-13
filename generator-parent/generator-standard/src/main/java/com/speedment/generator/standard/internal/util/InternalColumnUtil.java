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
package com.speedment.generator.standard.internal.util;

import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.trait.HasNullable;

import java.lang.reflect.Type;
import java.util.Optional;

public final class InternalColumnUtil {

    private InternalColumnUtil() {}

    public static boolean usesOptional(Column col) {
        return col.isNullable()
            && HasNullable.ImplementAs.OPTIONAL
            == col.getNullableImplementation();
    }

    public static Optional<String> optionalGetterName(TypeMapperComponent typeMappers, Column column) {
        final Type colType = typeMappers.get(column).getJavaType(column);
        final String getterName;

        if (usesOptional(column)) {
            if (Double.class.getName().equals(colType.getTypeName())) {
                getterName = ".getAsDouble()";
            } else if (Integer.class.getName().equals(colType.getTypeName())) {
                getterName = ".getAsInt()";
            } else if (Long.class.getName().equals(colType.getTypeName())) {
                getterName = ".getAsLong()";
            } else {
                getterName = ".get()";
            }
        } else {
            return Optional.empty();
        }

        return Optional.of(getterName);
    }


}
