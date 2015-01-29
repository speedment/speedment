package com.speedment.codegen.model.statement.expression.unary;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;

/**
 *
 * @author pemi
 */
public class Complement extends UnaryExpression<Complement> {

    public Complement() {
        this(null);
    }

    public Complement(Expression statement) {
        super(Operator_.COMPLEMENT, statement);
    }

}
