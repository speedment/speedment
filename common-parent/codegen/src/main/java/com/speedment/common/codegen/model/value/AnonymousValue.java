package com.speedment.common.codegen.model.value;

import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.trait.HasClasses;
import com.speedment.common.codegen.model.trait.HasFields;
import com.speedment.common.codegen.model.trait.HasInitializers;
import com.speedment.common.codegen.model.trait.HasMethods;
import com.speedment.common.codegen.model.trait.HasValues;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A value representing a reference to an abstract implementation used
 * anonymously to set a field.
 *
 * @author Emil Forslund
 * @since  2.4.6
 */
public interface AnonymousValue
extends Value<Type>,
        HasValues<AnonymousValue>,
        HasMethods<AnonymousValue>,
        HasFields<AnonymousValue>,
        HasInitializers<AnonymousValue>,
        HasClasses<AnonymousValue> {

    @Override
    AnonymousValue setValue(Type value);

    /**
     * Returns a list of type parameters to set when instantiating the anonymous
     * type.
     * <p>
     * The returned list is mutable.
     *
     * @return list of type parameters
     */
    List<Type> getTypeParameters();

    /**
     * Adds the specified type parameter to the end of the
     * {@link #getTypeParameters} list.
     *
     * @param typeParameter  the type parameter to add
     * @return               a reference to this
     */
    default AnonymousValue add(Type typeParameter) {
        getTypeParameters().add(typeParameter);
        return this;
    }
}