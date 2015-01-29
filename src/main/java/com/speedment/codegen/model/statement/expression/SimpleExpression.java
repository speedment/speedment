package com.speedment.codegen.model.statement.expression;

import com.speedment.codegen.model.Operator_;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class SimpleExpression implements Expression {

    private CharSequence text;

    public SimpleExpression() {
    }

    public SimpleExpression(CharSequence text) {
        this.text = text;
    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
    }

    @Override
    public Operator_ getOperator() {
        return Operator_.NONE;
    }

    @Override
    public Stream<? extends Expression> stream() {
        return Stream.empty();
    }

}
