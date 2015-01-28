package com.speedment.codegen.model.statement;

import com.speedment.codegen.model.statement.expression.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class If extends BaseStatement<If> {

    private Expression expression;
    private List<Statement_> trueStatements;
    private List<Statement_> falseStatements;

    public If() {
        this(null);
    }

    public If(Expression expression) {
        trueStatements = new ArrayList<>();
        falseStatements = new ArrayList<>();
        this.expression = expression;
    }

    public If addTrue(Statement_... statements) {
        return addSeveral(statements, getTrueStatements()::add);
    }

    public If addFalse(Statement_... statements) {
        return addSeveral(statements, getFalseStatements()::add);
    }

    public If addTrue(CharSequence... statements) {
        Stream.of(statements).forEach(s -> {
            getTrueStatements().add(Statement_.of(s));
        });
        return this;
    }

    public If addFalse(CharSequence statements) {
        Stream.of(statements).forEach(s -> {
            getFalseStatements().add(Statement_.of(s));
        });
        return this;
    }

    public Expression getExpression() {
        return expression;
    }

    public If setExpression(Expression expression) {
        return set(expression, e -> this.expression = e);
    }

    public List<Statement_> getTrueStatements() {
        return trueStatements;
    }

    public List<Statement_> getFalseStatements() {
        return falseStatements;
    }

}
