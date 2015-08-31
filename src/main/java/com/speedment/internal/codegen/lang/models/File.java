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
package com.speedment.internal.codegen.lang.models;

import com.speedment.internal.codegen.lang.interfaces.Callable;
import com.speedment.internal.codegen.lang.interfaces.HasClasses;
import com.speedment.internal.codegen.lang.interfaces.Copyable;
import com.speedment.internal.codegen.lang.interfaces.HasImports;
import com.speedment.internal.codegen.lang.interfaces.HasJavadoc;
import com.speedment.internal.codegen.lang.interfaces.HasName;
import com.speedment.internal.codegen.lang.models.implementation.FileImpl;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 * A model that represents an entire code file.
 *
 * @author Emil Forslund
 */
public interface File extends Copyable<File>, HasName<File>,
    HasJavadoc<File>, HasImports<File>, HasClasses<File>, Callable<File> {

    /**
     * Factory holder.
     */
    enum Factory {
        INST;
        private Function<String, File> mapper = FileImpl::new;
    }

    /**
     * Creates a new instance implementing this interface by using the class
     * supplied by the default factory. To change implementation, please use the
     * {@link #setSupplier(java.util.function.Supplier) setSupplier} method.
     *
     * @param name the name
     * @return the new instance
     */
    static File of(String name) {
        return Factory.INST.mapper.apply(name);
    }

    /**
     * Sets the instantiation method used to create new instances of this
     * interface.
     *
     * @param mapper the new constructor
     */
    static void setSupplier(Function<String, File> mapper) {
        Factory.INST.mapper = requireNonNull(mapper);
    }
}
