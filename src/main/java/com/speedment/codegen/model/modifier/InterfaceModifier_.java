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
package com.speedment.codegen.model.modifier;

import java.lang.reflect.Modifier;
import java.util.Set;

/**
 *
 * @author pemi
 */
public enum InterfaceModifier_ implements Modifier_<InterfaceModifier_> {

    PUBLIC(Modifier.PUBLIC),
    PROTECTED(Modifier.PROTECTED),
    PRIVATE(Modifier.PRIVATE),
    ABSTRACT(Modifier.ABSTRACT),
    STATIC(Modifier.STATIC),
    STRICTFP(Modifier.STRICT);

    private final static StaticSupport<InterfaceModifier_> support = new StaticSupport<>(values());

    private final int value;

    private InterfaceModifier_(int value) {
        this.value = Modifier_.requireInValues(value, Modifier.interfaceModifiers());
    }

    @Override
    public int getValue() {
        return value;
    }

    public static InterfaceModifier_ by(final String text) {
        return support.by(text);
    }

    public static Set<InterfaceModifier_> of(final String text) {
        return support.of(text);
    }

    public static Set<InterfaceModifier_> of(final int code) {
        return support.of(code);
    }

    public static Set<InterfaceModifier_> of(final InterfaceModifier_... classModifiers) {
        return support.of(classModifiers);
    }

}
