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
package com.speedment.codegen.model.statement.expression;

import com.speedment.codegen.model.AbstractCodeModel;
import com.speedment.codegen.model.Operator_;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 */
public class DefaultExpression<T extends DefaultExpression<T>> extends AbstractCodeModel<T> implements Expression {

    private Operator_ operator;

    public DefaultExpression() {
    }

    public DefaultExpression(Operator_ operator) {
        this.operator = operator;
    }

    @Override
    public Operator_ getOperator() {
        return operator;
    }

    public T setOperator(Operator_ operator) {
        return with(operator, o -> this.operator = o);
    }

    @Override
    public Stream<? extends Expression> stream() {
        return (Stream<? extends Expression>) stream();
    }

    @Override
    public boolean isConstant() {
        return stream().noneMatch(Expression::isConstant);
    }

}
