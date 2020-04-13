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
package com.speedment.common.codegen.internal.util;

import com.speedment.common.codegen.model.File;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.trait.HasClasses;
import com.speedment.common.codegen.model.trait.HasConstructors;
import com.speedment.common.codegen.model.trait.HasFields;
import com.speedment.common.codegen.model.trait.HasImports;
import com.speedment.common.codegen.model.trait.HasInitializers;
import com.speedment.common.codegen.model.trait.HasJavadoc;
import com.speedment.common.codegen.model.trait.HasMethods;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for operating on a tree of code models.
 *
 * @author Emil Forslund
 * @since  2.5
 */
public final class ModelTreeUtil {

    /**
     * Returns a list of all the imports of the specified model by traversing
     * the entire tree.
     *
     * @param root  the root to start at (usually the {@link File})
     * @return      the list of imports
     */
    public static List<Import> importsOf(HasImports<?> root) {
        final List<Import> found = new ArrayList<>(root.getImports());

        if (root instanceof HasClasses) {
            final HasClasses<?> classes = (HasClasses<?>) root;
            classes.getClasses().forEach(clazz -> found.addAll(importsOf(clazz)));
        }

        if (root instanceof HasFields) {
            final HasFields<?> classes = (HasFields<?>) root;
            classes.getFields().forEach(field -> found.addAll(importsOf(field)));
        }

        if (root instanceof HasMethods) {
            final HasMethods<?> classes = (HasMethods<?>) root;
            classes.getMethods().forEach(field -> found.addAll(importsOf(field)));
        }

        if (root instanceof HasConstructors) {
            final HasConstructors<?> classes = (HasConstructors<?>) root;
            classes.getConstructors().forEach(constr -> found.addAll(importsOf(constr)));
        }

        if (root instanceof HasInitializers) {
            final HasInitializers<?> classes = (HasInitializers<?>) root;
            classes.getInitializers().forEach(init -> found.addAll(importsOf(init)));
        }

        if (root instanceof HasJavadoc) {
            final HasJavadoc<?> classes = (HasJavadoc<?>) root;
            classes.getJavadoc().ifPresent(doc -> found.addAll(importsOf(doc)));
        }

        return found;
    }

    /**
     * Utility classes should not be instantiated.
     */
    private ModelTreeUtil() {}
}
