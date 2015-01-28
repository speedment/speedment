package com.speedment.codegen.model.statement.expression.binary;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;

/**
 *
 * @author pemi
 */
public class Equals extends BinaryExpression<Equals> {

    public Equals() {
        this(null, null);
    }

    public Equals(Expression left, Expression right) {
        super(Operator_.EQUALS, left, right);
    }

}
