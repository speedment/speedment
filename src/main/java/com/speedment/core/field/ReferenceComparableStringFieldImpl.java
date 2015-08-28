package com.speedment.core.field;

import com.speedment.api.field.ReferenceComparableStringField;
import com.speedment.api.field.methods.Getter;
import com.speedment.api.field.methods.Setter;
import com.speedment.api.field.operators.StandardStringOperator;
import com.speedment.core.field.builders.StringPredicateBuilderImpl;
import com.speedment.api.field.builders.StringPredicateBuilder;

/**
 *
 * @author pemi
 * @param <ENTITY>
 */
public class ReferenceComparableStringFieldImpl<ENTITY>
    extends ReferenceComparableFieldImpl<ENTITY, String> 
    implements ReferenceComparableStringField<ENTITY> {

    public ReferenceComparableStringFieldImpl(String columnName, Getter<ENTITY, String> getter, Setter<ENTITY, String> setter) {
        super(columnName, getter, setter);
    }

    @Override
    public StringPredicateBuilder<ENTITY> equalIgnoreCase(String value) {
        return newBinary(value, StandardStringOperator.EQUAL_IGNORE_CASE);
    }

    @Override
    public StringPredicateBuilder<ENTITY> notEqualIgnoreCase(String value) {
        return newBinary(value, StandardStringOperator.NOT_EQUAL_IGNORE_CASE);
    }

    @Override
    public StringPredicateBuilder<ENTITY> startsWith(String value) {
        return newBinary(value, StandardStringOperator.STARTS_WITH);
    }

    @Override
    public StringPredicateBuilder<ENTITY> endsWith(String value) {
        return newBinary(value, StandardStringOperator.ENDS_WITH);
    }

    @Override
    public StringPredicateBuilder<ENTITY> contains(String value) {
        return newBinary(value, StandardStringOperator.CONTAINS);
    }

    protected StringPredicateBuilder<ENTITY> newBinary(String value, StandardStringOperator binaryOperator) {
        return new StringPredicateBuilderImpl<>(this, value, binaryOperator);
    }
}