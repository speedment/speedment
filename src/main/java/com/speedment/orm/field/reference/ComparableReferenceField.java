package com.speedment.orm.field.reference;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.field.StandardBinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <V>
 */
public class ComparableReferenceField<ENTITY, V extends Comparable<V>> extends ReferenceField<ENTITY, V> {

    public ComparableReferenceField(Supplier<Column> columnSupplier, Function<ENTITY, V> getter) {
        super(columnSupplier, getter);
    }

    public BinaryPredicateBuilder<ENTITY, V> equal(V value) {
        return newBinary(value, StandardBinaryOperator.EQUAL);
    }

    public BinaryPredicateBuilder<ENTITY, V> notEqual(V value) {
        return newBinary(value, StandardBinaryOperator.NOT_EQUAL);
    }

    public BinaryPredicateBuilder<ENTITY, V> lessThan(V value) {
        return newBinary(value, StandardBinaryOperator.LESS_THAN);
    }

    public BinaryPredicateBuilder<ENTITY, V> lessOrEqual(V value) {
        return newBinary(value, StandardBinaryOperator.LESS_OR_EQUAL);
    }

    public BinaryPredicateBuilder<ENTITY, V> greaterThan(V value) {
        return newBinary(value, StandardBinaryOperator.GREATER_THAN);
    }

    public BinaryPredicateBuilder<ENTITY, V> greaterOrEqual(V value) {
        return newBinary(value, StandardBinaryOperator.GREATER_OR_EQUAL);
    }

    public BinaryPredicateBuilder<ENTITY, V> newBinary(V value, StandardBinaryOperator binaryOperator) {
        return new BinaryPredicateBuilder<>(this, value, binaryOperator);
    }

}
