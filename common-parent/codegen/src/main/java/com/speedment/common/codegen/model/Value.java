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
package com.speedment.common.codegen.model;

import com.speedment.common.codegen.internal.model.value.AnonymousValueImpl;
import com.speedment.common.codegen.internal.model.value.ArrayValueImpl;
import com.speedment.common.codegen.internal.model.value.BooleanValueImpl;
import com.speedment.common.codegen.internal.model.value.EnumValueImpl;
import com.speedment.common.codegen.internal.model.value.InvocationValueImpl;
import com.speedment.common.codegen.internal.model.value.NullValueImpl;
import com.speedment.common.codegen.internal.model.value.NumberValueImpl;
import com.speedment.common.codegen.internal.model.value.ReferenceValueImpl;
import com.speedment.common.codegen.internal.model.value.TextValueImpl;
import com.speedment.common.codegen.model.trait.HasCopy;
import com.speedment.common.codegen.model.value.AnonymousValue;
import com.speedment.common.codegen.model.value.ArrayValue;
import com.speedment.common.codegen.model.value.BooleanValue;
import com.speedment.common.codegen.model.value.EnumValue;
import com.speedment.common.codegen.model.value.InvocationValue;
import com.speedment.common.codegen.model.value.NullValue;
import com.speedment.common.codegen.model.value.NumberValue;
import com.speedment.common.codegen.model.value.ReferenceValue;
import com.speedment.common.codegen.model.value.TextValue;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;

/**
 * A model that represents any kind of value declared in code.
 *
 * @param <V> the value type
 *
 * @author Emil Forslund
 * @since  2.0.0
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

    /**
     * Returns a new {@link ArrayValue} with no values set.
     *
     * @return an {@code ArrayValue}
     */
    static ArrayValue ofArray() {
        return new ArrayValueImpl();
    }

    /**
     * Returns a new {@link ArrayValue} with the specified values set.
     *
     * @param values  the values to initialize the array with
     * @return        an {@code ArrayValue}
     */
    static ArrayValue ofArray(List<Value<?>> values) {
        return new ArrayValueImpl(values);
    }

    /**
     * Returns a new {@link BooleanValue} with the specified value.
     *
     * @param val  the boolean
     * @return     the boolean value
     */
    static BooleanValue ofBoolean(Boolean val) {
        return new BooleanValueImpl(val);
    }

    /**
     * Returns a new {@link EnumValue} with the specified constant selected.
     *
     * @param type      the enum type
     * @param constant  the selected constant
     * @return          the boolean value
     */
    static EnumValue ofEnum(Type type, String constant) {
        return new EnumValueImpl(type, constant);
    }

    /**
     * Returns a new {@code NullValue} representing {@code null}.
     *
     * @return  a {@code NullValue}
     */
    static NullValue ofNull() {
        return new NullValueImpl();
    }

    /**
     * Returns a new {@link NumberValue}.
     *
     * @param num  the represented number
     * @return     the created number value
     */
    static NumberValue ofNumber(Number num) {
        return new NumberValueImpl(num);
    }

    /**
     * Returns a new {@link ReferenceValue} representing a reference to an
     * object.
     *
     * @param reference  the code to show
     * @return           the reference value
     */
    static ReferenceValue ofReference(String reference) {
        return new ReferenceValueImpl(reference);
    }

    /**
     * Returns a new {@link TextValue} representing a string text.
     *
     * @param text  the text
     * @return      the text value
     */
    static TextValue ofText(String text) {
        return new TextValueImpl(text);
    }

    /**
     * Returns a new {@link AnonymousValue} representing the anonymous
     * implementation of a class or interface as the value of a field.
     *
     * @param implementedType  the class or interface to implement
     * @return                 the anonymous value
     */
    static AnonymousValue ofAnonymous(Type implementedType) {
        return new AnonymousValueImpl()
            .setValue(implementedType);
    }

    /**
     * Returns a new {@link InvocationValue} representing a call to the
     * specified local method with the specified arguments.
     *
     * @param method  the local method to invoke
     * @param args    the arguments to pass to it
     * @return        the created invocation
     */
    static InvocationValue ofInvocation(String method, Value<?>... args) {
        final InvocationValue result = new InvocationValueImpl()
            .setValue(method);

        Stream.of(args).forEachOrdered(result::add);

        return result;
    }

    /**
     * Returns a new {@link InvocationValue} representing a call to the
     * specified static method in the specified class with the specified
     * arguments.
     *
     * @param owner   the class where the static method is located
     * @param method  the static method to invoke
     * @param args    the arguments to pass to it
     * @return        the created invocation
     */
    static InvocationValue ofInvocation(Type owner,
                                        String method,
                                        Value<?>... args) {

        final InvocationValue result = new InvocationValueImpl()
            .set(owner)
            .setValue(method);

        Stream.of(args).forEachOrdered(result::add);

        return result;
    }
}