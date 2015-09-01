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
package com.speedment.internal.codegen.java.views;

import com.speedment.internal.codegen.base.DependencyManager;
import com.speedment.internal.codegen.base.Generator;
import com.speedment.internal.codegen.base.Transform;
import com.speedment.internal.codegen.lang.models.Import;
import com.speedment.internal.codegen.lang.models.Type;

import java.util.Optional;

import static com.speedment.internal.core.stream.CollectorUtil.joinIfNotEmpty;
import static com.speedment.internal.codegen.util.Formatting.*;
import static java.util.Objects.requireNonNull;

/**
 * Transforms from an {@link Import} to java code.
 *
 * @author Emil Forslund
 */
public final class ImportView implements Transform<Import, String> {

    private final static String IMPORT_STRING = "import ";

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> transform(Generator gen, Import model) {
        requireNonNull(gen);
        requireNonNull(model);

        if (shouldImport(gen, model.getType())) {
            return Optional.of(
                IMPORT_STRING
                + gen.onEach(model.getModifiers()).collect(joinIfNotEmpty(SPACE, EMPTY, SPACE))
                + model.getType().getName()
                + model.getStaticMember().map(str -> DOT + str).orElse(EMPTY)
                + SC
            ).filter(x -> {
                gen.getDependencyMgr().load(model.getType().getName());
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

        if (mgr.isIgnored(type.getName())) {
            return false;
        }

        if (mgr.isLoaded(type.getName())) {
            return false;
        }

        final Optional<String> current = mgr.getCurrentPackage();
        final Optional<String> suggested = packageName(type.getName());
        
        // TODO: Inner classes might still be imported explicitly.

        if (current.isPresent()
        &&  suggested.isPresent()
        &&  current.get().equals(suggested.get())) {
            return false;
        }

        return true;
    }
}
