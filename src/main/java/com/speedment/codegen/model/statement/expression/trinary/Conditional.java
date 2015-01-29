package com.speedment.codegen.model.statement.expression.trinary;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;

/**
 *
 * @author pemi
 */
public class Conditional extends TrinaryExpression<Conditional> {

    public Conditional() {
        this(null, null, null);
    }

    public Conditional(Expression first, Expression second, Expression third) {
        super(Operator_.CONDITIONAL, first, second, third);
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
