package com.speedment.codegen.model.statement;

import java.util.Optional;

/**
 *
 * @author pemi
 * @param <T> The type of SimpleStatement
 */
public class SimpleStatement<T extends SimpleStatement<T>> extends BaseStatement<T> {

    private CharSequence statement;

    public SimpleStatement() {
    }

    public SimpleStatement(CharSequence statement) {
        this.statement = statement;
    }

    public Optional<CharSequence> get() {
        return Optional.ofNullable(statement);
    }

    public T set(CharSequence statement) {
        return set(statement, s -> this.statement = s);
    }

}
