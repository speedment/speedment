package com.speedment.orm.field.longs;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.field.Field;
import com.speedment.orm.field.StandardBinaryOperator;
import com.speedment.orm.field.StandardUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;

/**
 *
 * @author pemi
 * @param <ENTITY>
 */
public class LongField<ENTITY> implements Field<ENTITY> {

    private final Supplier<Column> columnSupplier;
    private final ToLongFunction<ENTITY> getter;

    public LongField(Supplier<Column> columnSupplier, ToLongFunction<ENTITY> getter) {
        this.getter = getter;
        this.columnSupplier = columnSupplier;
    }

    public LongBinaryPredicateBuilder<ENTITY> equal(long value) {
        return newBinary(value, StandardBinaryOperator.EQUAL);
    }

    public LongBinaryPredicateBuilder<ENTITY> notEqual(long value) {
        return newBinary(value, StandardBinaryOperator.NOT_EQUAL);
    }

    public LongBinaryPredicateBuilder<ENTITY> lessThan(long value) {
        return newBinary(value, StandardBinaryOperator.LESS_THAN);
    }

    public LongBinaryPredicateBuilder<ENTITY> lessOrEqual(long value) {
        return newBinary(value, StandardBinaryOperator.LESS_OR_EQUAL);
    }

    public LongBinaryPredicateBuilder<ENTITY> greaterThan(long value) {
        return newBinary(value, StandardBinaryOperator.GREATER_THAN);
    }

    public LongBinaryPredicateBuilder<ENTITY> greaterOrEqual(long value) {
        return newBinary(value, StandardBinaryOperator.GREATER_OR_EQUAL);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return false;
    }

    public long getFrom(ENTITY entity) {
        return getter.applyAsLong(entity);
    }

    @Override
    public Column getColumn() {
        return columnSupplier.get();
    }

    public LongBinaryPredicateBuilder<ENTITY> newBinary(long value, StandardBinaryOperator binaryOperator) {
        return new LongBinaryPredicateBuilder<>(this, value, binaryOperator);
    }

    public LongUnaryPredicateBuilder<ENTITY> newUnary(StandardUnaryOperator unaryOperator) {
        return new LongUnaryPredicateBuilder<>(this, unaryOperator);
    }

}
