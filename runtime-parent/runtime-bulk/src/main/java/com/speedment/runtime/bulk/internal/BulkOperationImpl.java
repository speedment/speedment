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
package com.speedment.runtime.bulk.internal;

import com.speedment.runtime.bulk.BulkOperation;
import com.speedment.runtime.bulk.Operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public class BulkOperationImpl implements BulkOperation {

    private final Collection<? extends Operation<?>> operations;

    public BulkOperationImpl(Collection<? extends Operation<?>> operations) {
        this.operations = new ArrayList<>(requireNonNull(operations));
    }

    @Override
    public Stream<? extends Operation<?>> operations() {
        return operations.stream();
    }

}
