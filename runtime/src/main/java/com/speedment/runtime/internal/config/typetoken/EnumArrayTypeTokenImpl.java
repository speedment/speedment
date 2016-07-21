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
import com.speedment.runtime.config.typetoken.EnumTypeToken;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

/**
 * The default implementation of the {@link ArrayTypeToken} interface.
 * 
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
final class EnumArrayTypeTokenImpl implements EnumTypeToken, ArrayTypeToken {

    private final String name;
    private final List<String> constants;
    private final int dimension;

    EnumArrayTypeTokenImpl(String name, List<String> constants, int dimension) {
        this.name      = requireNonNull(name);
        this.constants = unmodifiableList(new ArrayList<>(constants));
        this.dimension = dimension;
    }
    
    @Override
    public List<String> getEnumConstants() {
        return constants;
    }

    @Override
    public String getTypeName() {
        return name;
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
    public boolean isComparable() {
        return false;
    }

    @Override
    public int getArrayDimension() {
        return dimension;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.constants);
        hash = 79 * hash + this.dimension;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (obj == null) return false;
        else if (!(obj instanceof EnumTypeToken)) return false;
        else if (!(obj instanceof ArrayTypeToken)) return false;
        
        final EnumTypeToken asEnum = (EnumTypeToken) obj;
        final ArrayTypeToken asArray = (ArrayTypeToken) obj;
        
        return getArrayDimension() == asArray.getArrayDimension()
            && Objects.equals(this.getTypeName(), asEnum.getTypeName())
            && Objects.deepEquals(this.getEnumConstants(), asEnum.getEnumConstants());
    }
}