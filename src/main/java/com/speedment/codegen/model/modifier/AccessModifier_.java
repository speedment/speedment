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
public enum AccessModifier_ implements Modifier_<AccessModifier_> {

    PUBLIC(Modifier.PUBLIC),
    PROTECTED(Modifier.PROTECTED),
    PRIVATE(Modifier.PRIVATE);

    private final static StaticSupport<AccessModifier_> staticSupport = new StaticSupport<>(values());

    private final int value;

    private AccessModifier_(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    public static AccessModifier_ by(final String text) {
        return staticSupport.by(text);
    }

    public static Set<AccessModifier_> of(final String text) {
        return staticSupport.of(text);
    }

    public static Set<AccessModifier_> of(final int code) {
        return staticSupport.of(code);
    }

    public static Set<AccessModifier_> of(final AccessModifier_... classModifiers) {
        return staticSupport.of(classModifiers);
    }

}
