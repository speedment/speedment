package com.speedment.runtime.field.expression;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.field.ReferenceField;

/**
 * Specialized expression that takes the value of a particular Speedment field
 * and maps it to a specific type.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface FieldMapper<ENTITY, T, MAPPER> extends Expression {

    /**
     * The field that is being mapped.
     *
     * @return  the field
     */
    ReferenceField<ENTITY, ?, T> getField();

    /**
     * Returns the functional interface implementation that is used when doing
     * the mapping. This type must correspond with the type that is returned by
     * {@link #getMapperType()}.
     *
     * @return  the mapper to use
     */
    MAPPER getMapper();

    /**
     * The type of mapping that is performed.
     *
     * @return  the mapping type
     */
    MapperType getMapperType();

}
