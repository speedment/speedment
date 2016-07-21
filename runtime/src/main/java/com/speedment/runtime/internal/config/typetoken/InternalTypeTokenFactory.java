/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.internal.config.typetoken;

import com.speedment.runtime.config.typetoken.TypeToken;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;
import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 * A factory that can create type tokens using the most fitting implementation.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class InternalTypeTokenFactory {
    
    /**
     * Produces an instance of {@link TypeToken} for any non-generic type.
     * 
     * @param type  a non-generic type
     * @return      a type token representing that type
     */
    public static TypeToken create(Class<?> type) {
        if (type == byte.class) {
            return PrimitiveTypeTokenImpl.BYTE;
        } else if (type == short.class) {
            return PrimitiveTypeTokenImpl.SHORT;
        } else if (type == int.class) {
            return PrimitiveTypeTokenImpl.INT;
        } else if (type == long.class) {
            return PrimitiveTypeTokenImpl.LONG;
        } else if (type == float.class) {
            return PrimitiveTypeTokenImpl.FLOAT;
        } else if (type == double.class) {
            return PrimitiveTypeTokenImpl.DOUBLE;
        } else if (type == boolean.class) {
            return PrimitiveTypeTokenImpl.BOOLEAN;
        } else if (type == char.class) {
            return PrimitiveTypeTokenImpl.CHAR;
        } else {
            Class<?> inner = type;

            int dimension = 0;
            while (inner.isArray()) {
                dimension++;
                inner = inner.getComponentType();
            }
            
            final List<String> constants;
            if (inner.isEnum()) {
                @SuppressWarnings("unchecked")
                final Enum<?>[] obj = (Enum<?>[]) inner.getEnumConstants();
                constants = Stream.of(obj).map(Enum::name).collect(toList());
            } else {
                constants = Collections.emptyList();
            }
            
            if (dimension > 0) {
                if (constants.isEmpty()) {
                    return new ArrayTypeTokenImpl(inner.getName(), dimension);
                } else {
                    return new EnumArrayTypeTokenImpl(inner.getName(), constants, dimension);
                }
            } else {
                if (constants.isEmpty()) {
                    return new DefaultTypeToken<>(inner);
                } else {
                    return new EnumTypeTokenImpl(inner.getName(), constants);
                }
            }
        }
    }
    
    /**
     * Utility classes should not be instantiated.
     */
    private InternalTypeTokenFactory() {
        instanceNotAllowed(getClass());
    }
}
