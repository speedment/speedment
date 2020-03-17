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
package com.speedment.runtime.bulk.internal.operation;

import com.speedment.runtime.bulk.PersistOperation;
import com.speedment.runtime.core.manager.Manager;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> type
 */
public final class PersistOperationImpl<ENTITY> extends AbstractOperation<ENTITY> implements PersistOperation<ENTITY> {

    private final List<Supplier<Stream<? extends ENTITY>>> generatorSuppliers;

    public PersistOperationImpl(
        final Manager<ENTITY> manager,
        final List<Supplier<Stream<? extends ENTITY>>> generatorSuppliers
    ) {
        super(Type.PERSIST, manager);
        this.generatorSuppliers = new ArrayList<>(requireNonNull(generatorSuppliers));
    }

    @Override
    public Stream<Supplier<Stream<? extends ENTITY>>> generatorSuppliers() {
        return generatorSuppliers.stream();
    }

}
