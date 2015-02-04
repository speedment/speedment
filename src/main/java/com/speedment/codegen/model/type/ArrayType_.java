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
package com.speedment.codegen.model.type;

/**
 *
 * @author pemi
 */
public class ArrayType_ extends Type_<ArrayType_> {

    private int arrayDimension;

    public ArrayType_(Class<?> typeClass, int arrayDimension) {
        super(typeClass);
        this.arrayDimension = arrayDimension;
    }

    public ArrayType_(CharSequence typeName, int arrayDimension) {
        super(typeName);
        this.arrayDimension = arrayDimension;
    }

    public boolean isArray() {
        return getArrayDimension() == 0;
    }

    public ArrayType_ setArrayDimension(int arrayDimension) {
        return with(arrayDimension, ad -> this.arrayDimension = ad);
    }

    public int getArrayDimension() {
        return arrayDimension;
    }
}
