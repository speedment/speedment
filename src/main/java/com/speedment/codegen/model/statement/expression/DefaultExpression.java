package com.speedment.codegen.model.statement.expression;

import com.speedment.codegen.model.AbstractCodeModel;
import com.speedment.codegen.model.Operator_;

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
        return set(operator, o -> this.operator = o);
    }

}
