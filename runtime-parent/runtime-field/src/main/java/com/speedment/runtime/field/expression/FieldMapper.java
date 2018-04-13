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
     * the mapping. The returned object must implement one of the following
     * interfaces:
     * <ul>
     *     <li>{@link com.speedment.common.function.ToByteFunction}
     *     <li>{@link com.speedment.common.function.ToShortFunction}
     *     <li>{@link java.util.function.ToIntFunction}
     *     <li>{@link java.util.function.ToLongFunction}
     *     <li>{@link java.util.function.ToDoubleFunction}
     *     <li>{@link com.speedment.common.function.ToFloatFunction}
     *     <li>{@link com.speedment.common.function.ToCharFunction}
     *     <li>{@link com.speedment.common.function.ToBooleanFunction}
     *     <li>{@link java.util.function.Function}
     * </ul>
     * The first generic type should be the boxed type returned by the getters
     * of the {@link #getField() field}. If the mapper implements
     * {@link java.util.function.Function}, then the returning type must be
     * either {@link java.lang.String}, {@link java.lang.Enum} or
     * {@link java.math.BigDecimal}.
     *
     * @return  the mapper to use
     */
    MAPPER getMapper();

}
