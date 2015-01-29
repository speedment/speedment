package com.speedment.codegen.model.statement.expression.binary;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;

/**
 *
 * @author pemi
 */
public class Assign extends BinaryExpression<Assign> {

    public Assign() {
        this(null, null);
    }

    public Assign(Expression left, Expression right) {
        super(Operator_.ASSIGN, left, right);
    }

}
