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
package com.speedment.runtime.config.immutable;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.internal.immutable.ImmutableColumn;
import com.speedment.runtime.config.internal.immutable.ImmutableDbms;
import com.speedment.runtime.config.internal.immutable.ImmutableDocument;
import com.speedment.runtime.config.internal.immutable.ImmutableForeignKey;
import com.speedment.runtime.config.internal.immutable.ImmutableForeignKeyColumn;
import com.speedment.runtime.config.internal.immutable.ImmutableIndex;
import com.speedment.runtime.config.internal.immutable.ImmutableIndexColumn;
import com.speedment.runtime.config.internal.immutable.ImmutablePrimaryKeyColumn;
import com.speedment.runtime.config.internal.immutable.ImmutableProject;
import com.speedment.runtime.config.internal.immutable.ImmutableSchema;
import com.speedment.runtime.config.internal.immutable.ImmutableTable;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.trait.HasName;
import com.speedment.runtime.config.util.*;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

public final class ImmutableHasIdTest extends AbstractDocumentTest {

    private static final String ID = "TryggveArneOchSven";

    @Test
    public void testIdMutator() {
        dbmsA.mutator().setId(ID);
        final Project immutableProject = ImmutableProject.wrap(project);
        final Dbms immutableDbms = immutableProject.dbmses().findFirst().get();

        assertEquals(ID, immutableDbms.getId());

        try {
            immutableDbms.mutator().setId(ID);
            fail("It shall not be possible to set the id");
        } catch (Exception e) {
            // ok
        }

    }

    @Test
    public void testImmutableIdTrait() {

        final Set<Class<?>> hasImmutableName = immutableClasses()
            .filter(c -> HasName.class.isAssignableFrom(c))
            .collect(toSet());

        final Set<Class<?>> hasImmutableId = immutableClasses()
            .filter(c -> HasId.class.isAssignableFrom(c))
            .collect(toSet());

        assertEquals(hasImmutableName, hasImmutableId);
    }

    private Stream<Class<? extends ImmutableDocument>> immutableClasses() {
        return Stream.of(
            ImmutableColumn.class,
            ImmutableDbms.class,
            ImmutableForeignKeyColumn.class,
            ImmutableForeignKey.class,
            ImmutableIndexColumn.class,
            ImmutableIndex.class,
            ImmutablePrimaryKeyColumn.class,
            ImmutableProject.class,
            ImmutableSchema.class,
            ImmutableTable.class
        );
    }

}
