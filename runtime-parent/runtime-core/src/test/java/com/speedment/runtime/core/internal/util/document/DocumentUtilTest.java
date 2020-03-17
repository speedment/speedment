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
package com.speedment.runtime.core.internal.util.document;

import com.speedment.runtime.config.*;
import com.speedment.runtime.config.trait.HasId;
import com.speedment.runtime.config.util.DocumentUtil;
import com.speedment.runtime.core.util.TestUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Emil Forslund
 */
final class DocumentUtilTest extends AbstractDocumentTest {

    @Test
    void testTraverseOver() {

        final List<Document> visited = DocumentUtil.traverseOver(schemaA).collect(toList());

        // Make sure there are no duplicate visits        
        // This test may fail spuriously because hash may collide
        final Set<Integer> set = visited.stream()
            .map(d -> System.identityHashCode(d.getData()))
            .collect(toSet());
        assertEquals(visited.size(), set.size(), "Duplicates in " + idsOf(visited));

        assertThatFirstIsBeforeOthers(visited, schemaA,
            tableA, columnA1, columnA2, primaryKeyColumnA1, indexA2, indexColumnA2, foreignKeyA2_C1, foreignKeyColumnA2_C1,
            tableC, columnC1, columnC2, primaryKeyColumnC1
        );

        assertThatFirstIsBeforeOthers(visited, tableA,
            columnA1, columnA2, primaryKeyColumnA1, indexA2, indexColumnA2, foreignKeyA2_C1, foreignKeyColumnA2_C1
        );

        assertThatFirstIsBeforeOthers(visited, tableC,
            columnC1, columnC2, primaryKeyColumnC1
        );

        assertThatFirstIsBeforeOthers(visited, indexA2,
            indexColumnA2
        );
        assertThatFirstIsBeforeOthers(visited, foreignKeyA2_C1,
            foreignKeyColumnA2_C1
        );

        for (final Document document : Arrays.asList(tableB, columnB1, columnB2)) {
            assertTrue(index(visited, document) == -1, "Illegal node traversed " + idOf(document));
        }

    }

    @Test
    void testAncestor() {
        assertEquals(Optional.empty(), DocumentUtil.ancestor(project, Project.class));
        stream().filter(d -> !(d instanceof Project)).forEach(d -> {
            assertEquals(Optional.of(project), DocumentUtil.ancestor(d, Project.class));
        });

        testAncestorHelper(Dbms.class, dbmsA);
        testAncestorHelper(Dbms.class, dbmsB);
        testAncestorHelper(Schema.class, schemaA);
        testAncestorHelper(Schema.class, schemaB);
        testAncestorHelper(Table.class, tableA);
        testAncestorHelper(Table.class, tableB);
        testAncestorHelper(Table.class, tableC);
        testAncestorHelper(Table.class, tableD);
        testAncestorHelper(Column.class, columnA1);
        testAncestorHelper(Column.class, columnA2);
        testAncestorHelper(Column.class, columnB1);
        testAncestorHelper(Column.class, columnB2);
        testAncestorHelper(Column.class, columnC1);
        testAncestorHelper(Column.class, columnC2);
        testAncestorHelper(Column.class, columnD1);
        testAncestorHelper(Column.class, columnD2);

        testAncestorHelper(Index.class, indexA2);
        testAncestorHelper(IndexColumn.class, indexColumnA2);
        testAncestorHelper(ForeignKey.class, foreignKeyA2_C1);
        testAncestorHelper(ForeignKeyColumn.class, foreignKeyColumnA2_C1);
        testAncestorHelper(PrimaryKeyColumn.class, primaryKeyColumnA1);

    }

    @Test
    @Disabled
    void testRelativeName3Arg() {
        {
            final String expected = "Dbms A.Schema A.Table A.Column A";
            final String result = DocumentUtil.relativeName(columnA1, Dbms.class, DocumentUtil.Name.DATABASE_NAME);
            assertEquals(expected, result);
        }
        {
            final String expected = "Dbms A.Schema A.Table A.ForeignKey A2 to C1.Column A2";
            final String result = DocumentUtil.relativeName(foreignKeyColumnA2_C1, Dbms.class, DocumentUtil.Name.DATABASE_NAME);
            assertEquals(expected, result);
        }
    }

    @Test
    @Disabled
    void testRelativeName4Arg() {
        final String expected = "#Dbms A.#Schema A.#Table A.#Column A";
        final String result = DocumentUtil.relativeName(columnA1, Dbms.class, DocumentUtil.Name.DATABASE_NAME, s -> "#" + s);
        assertEquals(expected, result);
    }

    @Test
    @Disabled
    void testRelativeName5Arg() {
        final String expected = "#Dbms A|#Schema A|#Table A|#Column A";
        final String result = DocumentUtil.relativeName(columnA1, Dbms.class, DocumentUtil.Name.DATABASE_NAME, "|", s -> "#" + s);
        assertEquals(expected, result);
    }


    @Test
    void createInstance() {
        TestUtil.assertNonInstansiable(DocumentUtil.class);
    }

    private <T extends Document> void testAncestorHelper(Class<T> parentClass, T parent) {
        DocumentUtil.traverseOver(parent)
            .filter(d -> !parentClass.isInstance(d))
            .forEach(d -> {
                assertEquals(Optional.of(parent), DocumentUtil.ancestor(d, parentClass));
            });
    }

    private void assertThatFirstIsBeforeOthers(List<Document> list, Document first, Document... others) {
        final int firstIndex = index(list, first);
        if (firstIndex == -1) {
            fail("First document " + idOf(first) + " is not in the list " + idsOf(list));
        }
        for (final Document other : others) {
            final int secondIndex = index(list, other);
            if (secondIndex == -1) {
                fail("There is no " + idOf(other) + " in the list " + idsOf(list));
            }
            if (firstIndex >= secondIndex) {
                fail(idOf(first) + " is after or same as " + idOf(other) + " in the list " + idsOf(list));
            }
        }
    }

    private int index(List<Document> list, Document document) {
        int index = 0;
        for (final Document doc : list) {
            if (identical(document, doc)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private String idOf(Document doc) {
        return HasId.of(doc).getId();
    }

    private String idsOf(Collection<Document> docs) {
        return docs.stream()
            .map(this::idOf)
            .collect(toList()).toString();
    }

    private boolean identical(Document dis, Document dat) {
        return dis.getData() == dat.getData();
    }
}
