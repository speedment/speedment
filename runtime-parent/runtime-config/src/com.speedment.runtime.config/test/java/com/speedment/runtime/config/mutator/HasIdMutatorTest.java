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
package com.speedment.runtime.config.mutator;

import com.speedment.runtime.config.mutator.trait.HasIdMutator;
import com.speedment.runtime.config.mutator.trait.HasNameMutator;
import com.speedment.runtime.config.util.*;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public final class HasIdMutatorTest extends AbstractDocumentTest {

    private static final String ID = "TryggveArneOchSven";

    @Test
    public void testIdMutator() {
        tableA.mutator().setId(ID);
        assertEquals(ID, tableA.getId());
    }

    @Test
    public void testIdMutatorTrait() {

        final Set<Class<?>> hasNameMutator = mutatorClasses()
            .filter(c -> HasNameMutator.class.isAssignableFrom(c))
            .collect(toSet());

        final Set<Class<?>> hasIdMutator = mutatorClasses()
            .filter(c -> HasIdMutator.class.isAssignableFrom(c))
            .collect(toSet());

        assertEquals(hasNameMutator, hasIdMutator);
    }

    private Stream<Class<? extends DocumentMutatorImpl>> mutatorClasses() {
        return Stream.of(
            ColumnMutator.class,
            DbmsMutator.class,
            ForeignKeyColumnMutator.class,
            ForeignKeyMutator.class,
            IndexColumnMutator.class,
            IndexMutator.class,
            PrimaryKeyColumnMutator.class,
            ProjectMutator.class,
            SchemaMutator.class,
            TableMutator.class
        );
    }

}
