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
package com.speedment.codegen.model.statement.expression.binary;

import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;
import com.speedment.codegen.model.statement.expression.DefaultExpression;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 */
public class BinaryExpression<T extends BinaryExpression<T>> extends DefaultExpression<T> {

    private Expression left, right;

    public BinaryExpression(Operator_ operator) {
        super(operator);
    }

    public BinaryExpression(Operator_ operator, Expression left, Expression right) {
        super(operator);
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public T setLeft(Expression left) {
        return with(left, l -> this.left = l);
    }

    public Expression getRight() {
        return right;
    }

    public T setRight(Expression right) {
        return with(right, r -> this.right = r);
    }

    @Override
    public Stream<? extends Expression> stream() {
        return Stream.of(left, right);
    }

    @Override
    public String toString() {
        return left.toString() + " " + getOperator() + " " + right.toString();
    }

}
