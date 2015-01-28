package com.speedment.codegen.model.statement.expression.binary;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;

/**
 *
 * @author pemi
 */
public class Plus extends BinaryExpression<Plus> {

    public Plus() {
        this(null, null);
    }

    public Plus(Expression left, Expression right) {
        super(Operator_.PLUS, left, right);
    }

}
