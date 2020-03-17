/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.codegen.model.trait;

import java.lang.reflect.Type;
import java.util.List;

import static com.speedment.common.codegen.constant.DefaultType.genericType;

/**
 * A trait for models that have interfaces as supertypes.
 *
 * @param <T> the extending type
 *
 * @author Emil Forslund
 * @since  2.0
 */
public interface HasImplements<T extends HasImplements<T>> {
    
    /**
     * Adds the specified interface to the list of implemented interfaces of
     * this model. The type should represent an interface.
     * 
     * @param interf  the implemented interface
     * @return        a reference to this
     */
    @SuppressWarnings("unchecked")
    default T add(final Type interf) {
        getInterfaces().add(interf);
        return (T) this;
    }

    /**
     * Adds the specified interface to the list of implemented interfaces of
     * this model. The type should represent an interface. This method is a
     * synonym for {@link #add(Type)}.
     *
     * @param interf  the implemented interface
     * @return        a reference to this
     *
     * @since 2.5
     */
    default T implement(final Type interf) {
        return add(interf);
    }

    /**
     * Adds the specified interface to the list of implemented interfaces of
     * this model. This method will construct a parameterized type with all the
     * specified type parameters as generic arguments. The type should represent
     * an interface.
     *
     * @param interf  the implemented interface
     * @param params  the generic types of that interface
     * @return        a reference to this
     *
     * @since 2.5
     */
    default T implement(final Type interf, final Type... params) {
        return add(genericType(interf, params));
    }

    /**
     * Adds the specified interface to the list of implemented interfaces of
     * this model. This method will construct a parameterized type with all the
     * specified type parameters as generic arguments. The type should represent
     * an interface.
     *
     * @param interf  the implemented interface
     * @param params  the generic types of that interface
     * @return        a reference to this
     *
     * @since 2.5
     */
    default T implement(final Type interf, final String... params) {
        return add(genericType(interf, params));
    }
    
    /**
     * Returns a list of all the interfaces implemented by this model.
     * <p>
     * The list returned must be mutable for changes!
     * 
     * @return  the interfaces
     */
    List<Type> getInterfaces();
}
