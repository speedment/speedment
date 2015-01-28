package com.speedment.codegen.model.statement.expression.trinary;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;
import com.speedment.codegen.model.statement.expression.DefaultExpression;

/**
 *
 * @author pemi
 * @param <T>
 */
public class TrinaryExpression<T extends TrinaryExpression<T>> extends DefaultExpression<T> {

    private Expression first, second, third;

    public TrinaryExpression(Operator_ operator) {
        super(operator);
    }

    public TrinaryExpression(Operator_ operator, Expression first, Expression second, Expression third) {
        super(operator);
        this.first = first;
        this.second = third;
    }

    public Expression getFirst() {
        return first;
    }

    public T setFirst(Expression first) {
        return set(first, f -> this.first = f);
    }

    public Expression getSecond() {
        return second;
    }

    public T setSecond(Expression second) {
        return set(second, s -> this.second = s);
    }

    public Expression getThird() {
        return third;
    }

    public T setThird(Expression third) {
        return set(third, t -> this.third = t);
    }

}
