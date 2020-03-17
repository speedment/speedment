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
package com.speedment.runtime.config.util;

import com.speedment.runtime.config.internal.util.Trees;
import org.junit.jupiter.api.Test;

import static com.speedment.runtime.config.internal.util.Trees.TraversalOrder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public class TreesTest {

    /**
     * Test of traverse method, of class Trees.
     */
    @Test
    void testTraverse() {

        final Node nodeE = new Node("E");
        
        // http://en.wikipedia.org/wiki/Tree_traversal
        final Node f = new Node("F",
                new Node("B",
                        new Node("A"),
                        new Node("D",
                                new Node("C"),
                                nodeE
                        )
                ),
                new Node("G",
                        null,
                        new Node("I",
                                new Node("H"),
                                null))
        );
        test(f, DEPTH_FIRST_PRE, "F,B,A,D,C,E,G,I,H");
        test(f, DEPTH_FIRST_POST, "A,C,E,D,B,H,I,G,F");
        test(f, BREADTH_FIRST, "F,B,G,A,D,I,C,E,H");

        Stream.of(Trees.TraversalOrder.values()).forEach(traversalOrder -> {
            test(nodeE, traversalOrder, "E");
        });

    }

    private static void test(Node f, Trees.TraversalOrder traversalOrder, String expectedResult) {
        final String result = Trees.traverse(f, Node::stream, traversalOrder).map(Node::getName).collect(JOINING_COMAS);
        //System.out.printf("%-20s: %s\n", traversalOrder, result);
        assertEquals(expectedResult, result);
    }

    static class Node {

        private final Node left;
        private final Node right;
        private final String name;

        Node(final String name, final Node left, final Node right) {
            this.left = left;
            this.right = right;
            this.name = name;
        }

        public Node(String name) {
            this(name, null, null);
        }

        @Override
        public String toString() {
            return getName();
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public String getName() {
            return name;
        }

        public Stream<Node> stream() {
            return Stream.of(getLeft(), getRight()).filter(Objects::nonNull);
        }

    }

    public static final Collector<CharSequence, ?, String> JOINING_COMAS = Collectors.joining(",");

}
