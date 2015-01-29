package com.speedment.codegen.model.statement.expression.constant;

import com.speedment.codegen.model.Operator_;
import com.speedment.codegen.model.statement.expression.DefaultExpression;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author pemi
 * @param <T> The extending class type
 * @param <V> The type of the Constant to hold
 */
public class Constant<T extends Constant<T, V>, V> extends DefaultExpression<T> {

    private Optional<V> value;

    public Constant() {
        this(null);
    }

    public Constant(V value) {
        super(Operator_.CONSTANT);
        set(value);
    }

    public Optional<V> get() {
        return value;
    }

    public T set(V value) {
        return set(value, v -> this.value = Optional.ofNullable(v));
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public String toString() {
        if (get().isPresent()) {
            return labelOpen() + get().get() + labelClose();
        }
        return labelNull();
    }

    protected String labelOpen() {
        return "";
    }

    protected String labelClose() {
        return "";
    }

    protected String labelNull() {
        return "null";
    }
}
