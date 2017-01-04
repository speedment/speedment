/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.model;


import com.speedment.common.codegen.internal.model.value.*;
import com.speedment.common.codegen.model.trait.HasCopy;
import com.speedment.common.codegen.model.value.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A model that represents any kind of value declared in code.
 * 
 * @author Emil Forslund
 * @param <V> the value type
 * @since  2.0
 */
public interface Value<V> extends HasCopy<Value<V>> {
    
    /**
     * Sets the inner value of this.
     * 
     * @param value  the new value
     * @return       a reference to this model
     */
    Value<V> setValue(V value);
    
    /**
     * Returns the inner value of this model.
     * 
     * @return  the inner value 
     */
	V getValue();
    
    static ArrayValue ofArray() {
        return new ArrayValueImpl();
    }
    
    static ArrayValue ofArray(List<Value<?>> arrayValue) {
        return new ArrayValueImpl(arrayValue);
    }
    
    static BooleanValue ofBoolean(Boolean val) {
        return new BooleanValueImpl(val);
    }
    
    static EnumValue ofEnum(Type type, String value) {
        return new EnumValueImpl(type, value);
    }
    
//    static EnumValue ofEnum(EnumValue prototype) {
//        return new EnumValueImpl(prototype);
//    }
    
    static NullValue ofNull() {
        return new NullValueImpl();
    }
    
    static NumberValue ofNumber(Number num) {
        return new NumberValueImpl(num);
    }
    
    static ReferenceValue ofReference(String reference) {
        return new ReferenceValueImpl(reference);
    }
    
    static TextValue ofText(String text) {
        return new TextValueImpl(text);
    }

}