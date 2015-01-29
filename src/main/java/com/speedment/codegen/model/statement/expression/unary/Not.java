package com.speedment.codegen.model.statement.expression.unary;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;

/**
 *
 * @author pemi
 */
public class Not extends UnaryExpression<Not> {

    public Not() {
        this(null);
    }

    public Not(Expression statement) {
        super(Operator_.NOT, statement);
    }

}
