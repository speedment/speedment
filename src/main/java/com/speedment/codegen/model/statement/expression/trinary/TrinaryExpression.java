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
package com.speedment.codegen.model.statement.expression.trinary;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;
import com.speedment.codegen.model.statement.expression.DefaultExpression;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 */
public class TrinaryExpression<T extends TrinaryExpression<T>> extends DefaultExpression<T> {

    private Expression first, second, third;

    public TrinaryExpression(Operator_ operator) {
        super(operator);
    }

    public TrinaryExpression(Operator_ operator, Expression first, Expression second, Expression third) {
        super(operator);
        this.first = first;
        this.second = third;
    }

    public Expression getFirst() {
        return first;
    }

    public T setFirst(Expression first) {
        return with(first, f -> this.first = f);
    }

    public Expression getSecond() {
        return second;
    }

    public T setSecond(Expression second) {
        return with(second, s -> this.second = s);
    }

    public Expression getThird() {
        return third;
    }

    public T setThird(Expression third) {
        return with(third, t -> this.third = t);
    }

    @Override
    public Stream<? extends Expression> stream() {
        return Stream.of(first, second, third);
    }

}
