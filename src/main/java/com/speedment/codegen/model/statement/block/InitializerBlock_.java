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
package com.speedment.codegen.model.statement.block;

import com.speedment.codegen.model.modifier.InitializerModifier_;
import com.speedment.codegen.model.modifier.Modifiable;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class InitializerBlock_ extends Block_<InitializerBlock_> implements Modifiable<InitializerModifier_> {

    private final Set<InitializerModifier_> modifiers;

    public InitializerBlock_() {
        super();
        this.modifiers = EnumSet.noneOf(InitializerModifier_.class);
    }

    @Override
    public Set<InitializerModifier_> getModifiers() {
        return modifiers;
    }

    @Override
    public InitializerBlock_ add(final InitializerModifier_ firstClassModifier_m, final InitializerModifier_... restClassModifiers) {
        return with(firstClassModifier_m, restClassModifiers, (f, r) -> {
            getModifiers().add(firstClassModifier_m);
            Stream.of(restClassModifiers).forEach(getModifiers()::add);
        });
    }

    @Override
    public InitializerBlock_ set(final Set<InitializerModifier_> newSet) {
        return with(newSet, s -> {
            getModifiers().clear();
            getModifiers().addAll(newSet);
        });
    }

    @Override
    public boolean is(InitializerModifier_ modifier) {
        return getModifiers().contains(modifier);
    }

    public InitializerBlock_ static_() {
        return with(InitializerModifier_.STATIC, getModifiers()::add);
    }

}
