/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
        return withSeveral(statements, getTrueStatements()::add);
    }

    public If addFalse(Statement_... statements) {
        return withSeveral(statements, getFalseStatements()::add);
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
        return with(expression, e -> this.expression = e);
    }

    public List<Statement_> getTrueStatements() {
        return trueStatements;
    }

    public List<Statement_> getFalseStatements() {
        return falseStatements;
    }

}
