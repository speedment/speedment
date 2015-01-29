package com.speedment.codegen.model.statement.expression;

import com.speedment.codegen.model.CodeModel;
import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.Statement_;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface Expression extends Statement_ {

    Operator_ getOperator();

    static Expression of(CharSequence text) {
        return new SimpleExpression(text);
    }

    /**
     * Returns if this Expression can be evaluated to a constant value. Example
     * of constant values are:
     *
     * <P>
     * 1, "John Smith", 1+1, sin(0+0)
     *
     * @return if this Expression can be evaluated to a constant value.
     */
    default boolean isConstant() {
        return false;
    }

    @Override
    public Stream<? extends Expression> stream();

}
