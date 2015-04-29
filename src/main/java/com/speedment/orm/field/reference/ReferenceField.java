package com.speedment.orm.field.reference;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.field.Field;
import com.speedment.orm.field.StandardUnaryOperator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <V>
 */
public class ReferenceField<ENTITY, V> implements Field<ENTITY> {

    private final Supplier<Column> columnSupplier;
    private final Function<ENTITY, V> getter;

    public ReferenceField(Supplier<Column> columnSupplier, Function<ENTITY, V> getter) {
        this.columnSupplier = Objects.requireNonNull(columnSupplier);
        this.getter = Objects.requireNonNull(getter);
    }

    public UnaryPredicateBuilder<ENTITY, V> isNull() {
        return newUnary(StandardUnaryOperator.IS_NULL);
    }

    public UnaryPredicateBuilder<ENTITY, V> isNotNull() {
        return newUnary(StandardUnaryOperator.IS_NOT_NULL);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return getFrom(entity) == null;
    }

    public V getFrom(ENTITY entity) {
        return getter.apply(entity);
    }

    @Override
    public Column getColumn() {
        return columnSupplier.get();
    }

    public UnaryPredicateBuilder<ENTITY, V> newUnary(StandardUnaryOperator unaryOperator) {
        return new UnaryPredicateBuilder<>(this, unaryOperator);
    }

}
