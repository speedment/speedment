package com.speedment.orm.field.reference.string;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.field.StandardStringBinaryOperator;
import com.speedment.orm.field.reference.ComparableReferenceField;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 * @param <ENTITY>
 */
public class StringReferenceField<ENTITY> extends ComparableReferenceField<ENTITY, String> {

    public StringReferenceField(final Supplier<Column> columnSupplier, Function<ENTITY, String> getter) {
        super(columnSupplier, getter);
    }

    public StringBinaryPredicateBuilder<ENTITY> equalIgnoreCase(String value) {
        return newBinary(value, StandardStringBinaryOperator.EQUAL_IGNORE_CASE);
    }

    public StringBinaryPredicateBuilder<ENTITY> notEqualIgnoreCase(String value) {
        return newBinary(value, StandardStringBinaryOperator.NOT_EQUAL_IGNORE_CASE);
    }

    public StringBinaryPredicateBuilder<ENTITY> startsWith(String value) {
        return newBinary(value, StandardStringBinaryOperator.STARTS_WITH);
    }

    public StringBinaryPredicateBuilder<ENTITY> endsWith(String value) {
        return newBinary(value, StandardStringBinaryOperator.ENDS_WITH);
    }

    public StringBinaryPredicateBuilder<ENTITY> contains(String value) {
        return newBinary(value, StandardStringBinaryOperator.CONTAINS);
    }

    public StringBinaryPredicateBuilder<ENTITY> newBinary(String value, StandardStringBinaryOperator binaryOperator) {
        return new StringBinaryPredicateBuilder<>(this, value, binaryOperator);
    }

}
