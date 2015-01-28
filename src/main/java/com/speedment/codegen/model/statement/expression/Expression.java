package com.speedment.codegen.model.statement.expression;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.Statement_;

/**
 *
 * @author pemi
 */
public interface Expression extends Statement_ {

    Operator_ getOperator();

    static Expression of(CharSequence text) {
        return new SimpleExpression(text);
    }

}
