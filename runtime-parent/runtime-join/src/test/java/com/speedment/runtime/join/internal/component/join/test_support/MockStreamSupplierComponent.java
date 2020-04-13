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
package com.speedment.runtime.join.internal.component.join.test_support;

import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.StreamSupplierComponent;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class MockStreamSupplierComponent implements StreamSupplierComponent {

    private static final int ENTITIES_PER_CARDINALITY = 2;

    @Override
    @SuppressWarnings("unchecked")
    public <ENTITY> Stream<ENTITY> stream(TableIdentifier<ENTITY> tableIdentifier, ParallelStrategy strategy) {
        final int table;
        final Supplier<ENTITY> constructor;
        if (JoinTestUtil.E0Manager.IDENTIFIER.equals(tableIdentifier)) {
            table = 0;
            constructor = () -> (ENTITY) new JoinTestUtil.E0Impl();
        } else if (JoinTestUtil.E1Manager.IDENTIFIER.equals(tableIdentifier)) {
            table = 1;
            constructor = () -> (ENTITY) new JoinTestUtil.E1Impl();
        } else if (JoinTestUtil.E2Manager.IDENTIFIER.equals(tableIdentifier)) {
            table = 2;
            constructor = () -> (ENTITY) new JoinTestUtil.E2Impl();
        } else if (JoinTestUtil.E3Manager.IDENTIFIER.equals(tableIdentifier)) {
            table = 3;
            constructor = () -> (ENTITY) new JoinTestUtil.E3Impl();
        } else if (JoinTestUtil.E4Manager.IDENTIFIER.equals(tableIdentifier)) {
            table = 4;
            constructor = () -> (ENTITY) new JoinTestUtil.E4Impl();
        } else if (JoinTestUtil.E5Manager.IDENTIFIER.equals(tableIdentifier)) {
            table = 5;
            constructor = () -> (ENTITY) new JoinTestUtil.E5Impl();
        } else {
            throw new UnsupportedOperationException("The table " + tableIdentifier + " is not known");
        }
        int mask = 1 << (table);

        return IntStream.range(0, (1 << 6) * ENTITIES_PER_CARDINALITY)
            .filter(i -> (i & mask) != 0)
            .mapToObj(i -> ((JoinTestUtil.HasId<?>) constructor.get()).setId(i))
            .map(e -> (ENTITY) e);
    }

}
