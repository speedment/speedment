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
package com.speedment.common.codegen.model.trait;

import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.value.BooleanValue;
import com.speedment.common.codegen.model.value.NumberValue;
import com.speedment.common.codegen.model.value.TextValue;

import java.util.Optional;

import static com.speedment.common.codegen.model.Value.ofBoolean;
import static com.speedment.common.codegen.model.Value.ofNumber;
import static com.speedment.common.codegen.model.Value.ofText;

/**
 * A trait for models that has a {@link Value}.
 *
 * @param <T>  the extending type
 *
 * @author Emil Forslund
 * @since  2.0.0
 */
public interface HasValue<T extends HasValue<T>> {
    
    /**
     * Sets the value of this model.
     * 
     * @param val  the new value
     * @return     a reference to this
     */
	T set(final Value<?> val);

    /**
     * Sets the value of this model to a new {@link TextValue} holding the
     * specified {@code String}.
     *
     * @param val  the new value
     * @return     a reference to this
     */
	default T set(final String val) {
        return set(ofText(val));
    }

    /**
     * Sets the value of this model to a new {@link NumberValue} holding the
     * specified {@code Number}.
     *
     * @param val  the new value
     * @return     a reference to this
     */
    default T set(final Number val) {
        return set(ofNumber(val));
    }

    /**
     * Sets the value of this model to a new {@link BooleanValue} holding the
     * specified {@code Boolean}.
     *
     * @param val  the new value
     * @return     a reference to this
     */
    default T set(final Boolean val) {
        return set(ofBoolean(val));
    }
    
    /**
     * Returns the value of this model.
     * 
     * @return  the value
     */
	Optional<Value<?>> getValue();
}