/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.codegen.model;

import com.speedment.codegen.model.modifier.Modifiable;
import com.speedment.codegen.model.modifier.Modifier_;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T>
 * @param <M>
 */
public abstract class AbstractModifiableCodeModel<T extends AbstractModifiableCodeModel<T, M>, M extends Modifier_<M>> extends AbstractCodeModel<T> implements Modifiable<M> {

    @Override
    public T add(final M firstClassModifier_m, final M... restClassModifiers) {
        return with(firstClassModifier_m, restClassModifiers, (f, s) -> {
            getModifiers().add(f);
            Stream.of(s).forEach(getModifiers()::add);
        });
    }

    @Override
    public T set(final Set<M> newSet) {
        return with(newSet, s -> {
            getModifiers().clear();
            getModifiers().addAll(s);
        });
    }

    @Override
    public boolean is(M modifier) {
        return getModifiers().contains(modifier);
    }

}
