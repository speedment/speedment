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
package com.speedment.codegen.model.statement.expression.constant;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.DefaultExpression;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author pemi
 * @param <T> The extending class type
 * @param <V> The type of the Constant to hold
 */
public class Constant<T extends Constant<T, V>, V> extends DefaultExpression<T> {

    private Optional<V> value;

    public Constant() {
        this(null);
    }

    public Constant(V value) {
        super(Operator_.CONSTANT);
        set(value);
    }

    public Optional<V> get() {
        return value;
    }

    public T set(V value) {
        return with(value, v -> this.value = Optional.ofNullable(v));
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public String toString() {
        if (get().isPresent()) {
            return labelOpen() + get().get() + labelClose();
        }
        return labelNull();
    }

    protected String labelOpen() {
        return "";
    }

    protected String labelClose() {
        return "";
    }

    protected String labelNull() {
        return "null";
    }
}
