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
package com.speedment.plugins.enums.internal;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @author  Simon Jonasson
 * @since   3.0.0
 */
public final class GeneratedEnumType implements Type {
    
    private final String typeName;
    private final List<String> constants;

    public GeneratedEnumType(String typeName, List<String> constants) {
        this.typeName  = requireNonNull(typeName);
        this.constants = unmodifiableList(new ArrayList<>(constants));
    }

    public List<String> getEnumConstants() {
        return constants;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Type)) {
            return false;
        }

        if (o instanceof GeneratedEnumType) {
            final GeneratedEnumType that = (GeneratedEnumType) o;
            return typeName.equals(that.typeName)
                && constants.equals(that.constants);
        } else {
            return typeName.equals(((Type) o).getTypeName());
        }
    }

    @Override
    public int hashCode() {
        int result = getTypeName().hashCode();
        result = 31 * result + constants.hashCode();
        return result;
    }
}
