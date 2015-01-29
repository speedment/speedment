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
package com.speedment.codegen.model.modifier;

import static com.speedment.codegen.CodeUtil.lcfirst;
import com.speedment.codegen.model.CodeModel;
import com.speedment.util.java.JavaLanguage;

/**
 *
 * @author pemi
 * @param <T>
 */
public interface Modifier_<T extends Modifier_<T>> extends CodeModel {

    String name();

    int getValue();

    public static boolean valuesContains(int values, int value) {
        return ((values & values) != 0);
    }

    public static int requireInValues(int value, int values) {
        if (!valuesContains(values, value)) {
            throw new IllegalArgumentException(value + " is not a class modifier value in " + values);
        }
        return value;
    }

    @Override
    default Type getModelType() {
        return Type.MODIFIER;
    }

    default CharSequence toJavaCode() {
        return lcfirst(JavaLanguage.getJavaNameFromSqlName(name()));
    }

}
