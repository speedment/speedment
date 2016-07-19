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

import com.speedment.runtime.config.typetoken.ArrayTypeToken;
import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link ArrayTypeToken} interface.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class ArrayTypeTokenImpl implements ArrayTypeToken {
    
    private final String name;
    private final int dimension;
    
    public ArrayTypeTokenImpl(String name, int dimension) {
        this.name      = requireNonNull(name);
        this.dimension = dimension;
    }

    @Override
    public String getTypeName() {
        return name;
    }

    @Override
    public int getArrayDimension() {
        return dimension;
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }

    @Override
    public boolean isGeneric() {
        return false;
    }

    @Override
    public boolean isEnum() {
        return false;
    }

    @Override
    public boolean isComparable() {
        return false;
    }
}