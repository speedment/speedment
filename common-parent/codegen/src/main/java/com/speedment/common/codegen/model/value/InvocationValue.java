package com.speedment.common.codegen.model.value;

import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.trait.HasType;
import com.speedment.common.codegen.model.trait.HasValues;

/**
 * A value calculated by invoking a method.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public interface InvocationValue
extends Value<String>,
        HasType<InvocationValue>,
        HasValues<InvocationValue> {

    @Override
    InvocationValue setValue(String value);

}
