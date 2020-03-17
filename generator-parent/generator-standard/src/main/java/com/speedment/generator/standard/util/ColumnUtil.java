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
package com.speedment.generator.standard.util;

import com.speedment.generator.standard.internal.util.InternalColumnUtil;
import com.speedment.runtime.typemapper.TypeMapperComponent;
import com.speedment.runtime.config.Column;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Emil Forslund
 */
public final class ColumnUtil {

    private ColumnUtil() {}

    public static boolean usesOptional(Column col) {
        requireNonNull(col);
        return InternalColumnUtil.usesOptional(col);
    }
    
    public static Optional<String> optionalGetterName(TypeMapperComponent typeMappers, Column column) {
        requireNonNull(typeMappers);
        requireNonNull(column);
        return InternalColumnUtil.optionalGetterName(typeMappers, column);
    }
    
}
