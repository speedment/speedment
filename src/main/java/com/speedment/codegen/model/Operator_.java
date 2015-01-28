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
package com.speedment.codegen.model;

import com.speedment.codegen.model.statement.expression.Expression;
import com.speedment.codegen.model.statement.expression.SimpleExpression;
import com.speedment.codegen.model.statement.expression.binary.Equals;
import com.speedment.codegen.model.statement.expression.binary.Minus;
import com.speedment.codegen.model.statement.expression.binary.Plus;
import com.speedment.codegen.model.statement.expression.trinary.Conditional;
import com.speedment.codegen.model.statement.expression.unary.Not;
import java.util.function.Supplier;

/**
 * Java Operators (3.12 Operators).
 *
 * @author pemi
 */
public enum Operator_ implements CodeModel {

    NONE(""),
    ASSIGN("="), GREATER_THAN(">"), LESS_THAN("<"), NOT("!", Not::new), COMPLEMENT("~"), CONDITIONAL("?", Conditional::new), COLON(":"), ARROW("->"),
    EQUALS("==", Equals::new), GREATER_OR_EQAL(">="), LESS_OR_EQUAL("<="), NOT_EQUAL("!="),
    AND_LOGICAL("&&"), OR_LOGICAL("||"), INCREMENT("++"), DECREMENT("--"),
    PLUS("+", Plus::new), MINUS("-", Minus::new), MULTIPLY("*"), DIVIDE("/"), AND_BINARY("&"), OR_BINARY("|"), XOR_BINARY("^"), MODULO("%"),
    LEFT_SHIFT("<<"), RIGHT_SHIFT(">>"), RIGTH_SHIFT_ZEROIN(">>>"),
    ADD_AND_ASSIGN("+="), MINUS_AND_ASSINGN("-="), MULTIPLY_AND_ASSIGN("*="), DIVIDE_AND_ASSIGN("/="),
    MODULO_AND_ASSING("%="), LEFT_SHIFT_AND_ASSIGN("<<="), RIGHT_SHIFT_AND_ASSIGN(">>="), RIGHT_SHIFT_ZEROIN_AND_ASSIGN(">>>=");

    /*
     = > < ! ~ ? : ->
     == >= <= != && || ++ --
     + - * / & | ^ % << >> >>>
     += -= *= /= &= |= ^= %= <<= >>= >>>=
     */
    private final String text;
    private final Supplier<Expression> supplier;

    private Operator_(String text) {
        this.text = text;
        supplier = SimpleExpression::new;
    }

    private Operator_(String text, Supplier<Expression> supplier) {
        this.text = text;
        this.supplier = supplier;
    }

    public String getText() {
        return text;
    }

    @Override
    public Type getModelType() {
        return Type.OPERATOR;
    }

    Expression newExpression() {
        return supplier.get();
    }

}
