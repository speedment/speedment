package com.speedment.orm.field.doubles;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.field.Field;
import com.speedment.orm.field.StandardBinaryOperator;
import com.speedment.orm.field.StandardUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

/**
 *
 * @author pemi
 * @param <ENTITY>
 */
public class DoubleField<ENTITY> implements Field<ENTITY> {

    private final Supplier<Column> columnSupplier;
    private final ToDoubleFunction<ENTITY> getter;

    public DoubleField(Supplier<Column> columnSupplier, ToDoubleFunction<ENTITY> getter) {
        this.getter = getter;
        this.columnSupplier = columnSupplier;
    }

    public DoubleBinaryPredicateBuilder<ENTITY> equal(double value) {
        return newBinary(value, StandardBinaryOperator.EQUAL);
    }

    public DoubleBinaryPredicateBuilder<ENTITY> notEqual(double value) {
        return newBinary(value, StandardBinaryOperator.NOT_EQUAL);
    }

    public DoubleBinaryPredicateBuilder<ENTITY> lessThan(double value) {
        return newBinary(value, StandardBinaryOperator.LESS_THAN);
    }

    public DoubleBinaryPredicateBuilder<ENTITY> lessOrEqual(double value) {
        return newBinary(value, StandardBinaryOperator.LESS_OR_EQUAL);
    }

    public DoubleBinaryPredicateBuilder<ENTITY> greaterThan(double value) {
        return newBinary(value, StandardBinaryOperator.GREATER_THAN);
    }

    public DoubleBinaryPredicateBuilder<ENTITY> greaterOrEqual(double value) {
        return newBinary(value, StandardBinaryOperator.GREATER_OR_EQUAL);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return false;
    }

    public double getFrom(ENTITY entity) {
        return getter.applyAsDouble(entity);
    }

    @Override
    public Column getColumn() {
        return columnSupplier.get();
    }

    public DoubleBinaryPredicateBuilder<ENTITY> newBinary(double value, StandardBinaryOperator binaryOperator) {
        return new DoubleBinaryPredicateBuilder<>(this, value, binaryOperator);
    }

    public DoubleUnaryPredicateBuilder<ENTITY> newUnary(StandardUnaryOperator unaryOperator) {
        return new DoubleUnaryPredicateBuilder<>(this, unaryOperator);
    }

}
