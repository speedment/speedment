package com.speedment.codegen.model.statement.expression.binary;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;

/**
 *
 * @author pemi
 */
public class Minus extends BinaryExpression<Minus> {

    public Minus() {
        this(null, null);
    }

    public Minus(Expression left, Expression right) {
        super(Operator_.MINUS, left, right);
    }

}
