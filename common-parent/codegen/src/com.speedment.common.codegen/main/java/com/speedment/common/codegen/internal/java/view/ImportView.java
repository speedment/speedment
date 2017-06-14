/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.codegen.internal.java.view;

import com.speedment.common.codegen.DependencyManager;
import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import static com.speedment.common.codegen.internal.util.CollectorUtil.joinIfNotEmpty;
import static com.speedment.common.codegen.internal.util.NullUtil.requireNonNulls;
import com.speedment.common.codegen.model.Import;
import static com.speedment.common.codegen.util.Formatting.packageName;
import static com.speedment.common.codegen.util.Formatting.stripGenerics;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Transforms from an {@link Import} to java code.
 *
 * @author Emil Forslund
 */
public final class ImportView implements Transform<Import, String> {

    @Override
    public Optional<String> transform(Generator gen, Import model) {
        requireNonNulls(gen, model);
        final String name = stripGenerics(model.getType().getTypeName())
            .replace('$', '.');

        if (!model.getModifiers().isEmpty()
        ||   shouldImport(gen, model.getType())) {
            return Optional.of(
                "import "
                + gen.onEach(model.getModifiers()).collect(joinIfNotEmpty(" ", "", " "))
                + name
                + model.getStaticMember().map(str -> "." + str).orElse("")
                + ";"
            ).filter(x -> {
                gen.getDependencyMgr().load(name);
                return true;
            });
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns <code>true</code> if the specified type requires an explicit
     * import. If the type has already been imported or is part of a package
     * that does not need to be imported, <code>false</code> is returned.
     *
     * @param gen the generator
     * @param type the type to import
     * @return      <code>true</code> if it should be imported explicitly
     */
    private boolean shouldImport(Generator gen, Type type) {
        final DependencyManager mgr = gen.getDependencyMgr();
        final String typeName = stripGenerics(type.getTypeName());
        
        if (mgr.isIgnored(typeName)) {
            return false;
        }

        if (mgr.isLoaded(typeName)) {
            return false;
        }

        final Optional<String> current = mgr.getCurrentPackage();
        final Optional<String> suggested = packageName(typeName);

        // TODO: Inner classes might still be imported explicitly.
            
        return !(current.isPresent()
            && suggested.isPresent()
            && current.get().equals(suggested.get()));
    }
}