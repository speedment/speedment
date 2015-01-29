package com.speedment.codegen.model.statement;

import com.speedment.codegen.model.statement.expression.Expression;

/**
 *
 * @author pemi
 */
public class Return extends BaseStatement<Return> implements Statement_ {

    private Expression expression;

    public Return() {
    }

    public Return(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Return setExpression(Expression expression) {
        return set(expression, e -> this.expression = e);
    }

}
