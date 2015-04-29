package com.speedment.orm.field.reference.string;

import com.speedment.orm.field.reference.*;
import com.speedment.orm.field.BasePredicate;
import com.speedment.orm.field.Operator;
import com.speedment.orm.field.PredicateBuilder;
import com.speedment.orm.field.StandardStringBinaryOperator;
import java.util.Objects;

/**
 *
 * @author pemi
 * @param <ENTITY> Entity type
 *
 */
public class StringBinaryPredicateBuilder<ENTITY> extends BasePredicate<ENTITY> implements PredicateBuilder {

    private final ReferenceField<ENTITY, String> field;
    private final String value;
    private final StandardStringBinaryOperator binaryOperator;

    public StringBinaryPredicateBuilder(
            ReferenceField<ENTITY, String> field,
            String value,
            StandardStringBinaryOperator binaryOperator
    ) {
        this.field = Objects.requireNonNull(field);
        this.value = value;
        this.binaryOperator = Objects.requireNonNull(binaryOperator);
    }

    @Override
    public boolean test(ENTITY entity) {
        final String columnValue = field.getFrom(entity);
        if (columnValue == null || value == null) {
            return false;
        }
        return binaryOperator.getComparator().test(columnValue, value);
    }

    @Override
    public ReferenceField<ENTITY, String> getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Operator getOperator() {
        return binaryOperator;
    }

}
