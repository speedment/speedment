package com.speedment.orm.field.ints;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.field.Field;
import com.speedment.orm.field.StandardBinaryOperator;
import com.speedment.orm.field.StandardUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/**
 *
 * @author pemi
 * @param <ENTITY>
 */
public class IntField<ENTITY> implements Field<ENTITY> {

    private final Supplier<Column> columnSupplier;
    private final ToIntFunction<ENTITY> getter;

    public IntField(Supplier<Column> columnSupplier, ToIntFunction<ENTITY> getter) {
        this.getter = getter;
        this.columnSupplier = columnSupplier;
    }

    public IntBinaryPredicateBuilder<ENTITY> equal(int value) {
        return newBinary(value, StandardBinaryOperator.EQUAL);
    }

    public IntBinaryPredicateBuilder<ENTITY> notEqual(int value) {
        return newBinary(value, StandardBinaryOperator.NOT_EQUAL);
    }

    public IntBinaryPredicateBuilder<ENTITY> lessThan(int value) {
        return newBinary(value, StandardBinaryOperator.LESS_THAN);
    }

    public IntBinaryPredicateBuilder<ENTITY> lessOrEqual(int value) {
        return newBinary(value, StandardBinaryOperator.LESS_OR_EQUAL);
    }

    public IntBinaryPredicateBuilder<ENTITY> greaterThan(int value) {
        return newBinary(value, StandardBinaryOperator.GREATER_THAN);
    }

    public IntBinaryPredicateBuilder<ENTITY> greaterOrEqual(int value) {
        return newBinary(value, StandardBinaryOperator.GREATER_OR_EQUAL);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return false;
    }

    public int getFrom(ENTITY entity) {
        return getter.applyAsInt(entity);
    }

    @Override
    public Column getColumn() {
        return columnSupplier.get();
    }

    public IntBinaryPredicateBuilder<ENTITY> newBinary(int value, StandardBinaryOperator binaryOperator) {
        return new IntBinaryPredicateBuilder<>(this, value, binaryOperator);
    }

    public IntUnaryPredicateBuilder<ENTITY> newUnary(StandardUnaryOperator unaryOperator) {
        return new IntUnaryPredicateBuilder<>(this, unaryOperator);
    }

}
