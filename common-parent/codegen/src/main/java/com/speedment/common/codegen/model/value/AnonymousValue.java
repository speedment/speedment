package com.speedment.common.codegen.model.value;

import com.speedment.common.codegen.model.ClassOrInterface;
import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.trait.HasGenerics;
import com.speedment.common.codegen.model.trait.HasValues;

/**
 * A value representing a reference to an abstract implementation used
 * anonymously to set a field.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public interface AnonymousValue<T extends ClassOrInterface<T>>
extends Value<T>,
        HasGenerics<AnonymousValue<T>>,
        HasValues<AnonymousValue<T>> {

    @Override
    AnonymousValue<T> setValue(T value);
}