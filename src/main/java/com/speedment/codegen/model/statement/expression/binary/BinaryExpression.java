package com.speedment.codegen.model.statement.expression.binary;

import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.Expression;
import com.speedment.codegen.model.statement.expression.DefaultExpression;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 */
public class BinaryExpression<T extends BinaryExpression<T>> extends DefaultExpression<T> {

    private Expression left, right;

    public BinaryExpression(Operator_ operator) {
        super(operator);
    }

    public BinaryExpression(Operator_ operator, Expression left, Expression right) {
        super(operator);
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public T setLeft(Expression left) {
        return set(left, l -> this.left = l);
    }

    public Expression getRight() {
        return right;
    }

    public T setRight(Expression right) {
        return set(right, r -> this.right = r);
    }

    @Override
    public Stream<? extends Expression> stream() {
        return Stream.of(left, right);
    }

    @Override
    public String toString() {
        return left.toString() + " " + getOperator() + " " + right.toString();
    }

}
