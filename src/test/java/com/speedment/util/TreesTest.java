/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.util;

import static com.speedment.util.Trees.TraversalOrder.BREADTH_FIRST;
import static com.speedment.util.Trees.TraversalOrder.DEPTH_FIRST_POST;
import static com.speedment.util.Trees.TraversalOrder.DEPTH_FIRST_PRE;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pemi
 */
public class TreesTest {

    public TreesTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of traverse method, of class Trees.
     */
    @Test
    public void testTraverse() {
        System.out.println("traverse");

        // http://en.wikipedia.org/wiki/Tree_traversal
        final Node f = new Node("F",
                new Node("B",
                        new Node("A"),
                        new Node("D",
                                new Node("C"),
                                new Node("E")
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

        Stream.of(Trees.TraversalOrder.values()).forEach(to -> {
            test(null, to, "");
        });

    }

    private static void test(Node f, Trees.TraversalOrder traversalOrder, String expectedResult) {
        final String result = Trees.traverse(f, Node::stream, traversalOrder).map(Node::getName).collect(JOINING_COMAS);
        System.out.printf("%-20s: %s\n", traversalOrder, result);
        assertEquals(expectedResult, result);
    }

    public class Node {

        private final Node left;
        private final Node right;
        private final String name;

        public Node(final String name, final Node left, final Node right) {
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
