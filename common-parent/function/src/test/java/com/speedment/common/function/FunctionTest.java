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
package com.speedment.common.function;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class FunctionTest {

    @Test
    void triFunction() {
        final TriFunction<Integer, Integer, Integer, Integer> fn =
            (a, b, c) -> a + b + c;
        final int expected = 1 + 2 + 3;
        final int actual = fn.apply(1, 2, 3);
        assertEquals(expected, actual);
    }

    @Test
    void quadFunction() {
        final QuadFunction<Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d) -> a + b + c + d;
        final int expected = 1 + 2 + 3 + 4;
        final int actual = fn.apply(1, 2, 3, 4);
        assertEquals(expected, actual);
    }

    @Test
    void function5() {
        final Function5<Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e) -> a + b + c + d + e;
        final int expected = 1 + 2 + 3 + 4 + 5;
        final int actual = fn.apply(1, 2, 3, 4, 5);
        assertEquals(expected, actual);
    }

    @Test
    void function6() {
        final Function6<Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f) -> a + b + c + d + e + f;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6);
        assertEquals(expected, actual);
    }

    @Test
    void function7() {
        final Function7<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g) -> a + b + c + d + e + f + g;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7);
        assertEquals(expected, actual);
    }

    @Test
    void function8() {
        final Function8<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h) -> a + b + c + d + e + f + g + h;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8);
        assertEquals(expected, actual);
    }

    @Test
    void function9() {
        final Function9<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i) -> a + b + c + d + e + f + g + h + i;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9);
        assertEquals(expected, actual);
    }

    @Test
    void function10() {
        final Function10<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j) -> a + b + c + d + e + f + g + h + i + j;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertEquals(expected, actual);
    }

    @Test
    void function11() {
        final Function11<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k) -> a + b + c + d + e + f + g + h + i + j + k;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        assertEquals(expected, actual);
    }

    @Test
    void function12() {
        final Function12<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l) -> a + b + c + d + e + f + g + h + i + j + k + l;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        assertEquals(expected, actual);
    }

    @Test
    void function13() {
        final Function13<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m) -> a + b + c + d + e + f + g + h + i + j + k + l + m;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        assertEquals(expected, actual);
    }

    @Test
    void function14() {
        final Function14<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m, n) -> a + b + c + d + e + f + g + h + i + j + k + l + m + n;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13 + 14;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        assertEquals(expected, actual);
    }

    @Test
    void function15() {
        final Function15<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o) -> a + b + c + d + e + f + g + h + i + j + k + l + m + n + o;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13 + 14 + 15;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        assertEquals(expected, actual);
    }

    @Test
    void function16() {
        final Function16<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p) -> a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13 + 14 + 15 + 16;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        assertEquals(expected, actual);
    }

    @Test
    void function17() {
        final Function17<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q) -> a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13 + 14 + 15 + 16 + 17;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        assertEquals(expected, actual);
    }

    @Test
    void function18() {
        final Function18<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r) -> a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13 + 14 + 15 + 16 + 17 + 18;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
        assertEquals(expected, actual);
    }

    @Test
    void function19() {
        final Function19<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s) -> a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13 + 14 + 15 + 16 + 17 +18 + 19;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        assertEquals(expected, actual);
    }

    @Test
    void function20() {
        final Function20<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t) -> a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s + t;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13 + 14 + 15 + 16 + 17 +18 + 19 + 20;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        assertEquals(expected, actual);
    }

    @Test
    void function21() {
        final Function21<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u) -> a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s + t + u;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13 + 14 + 15 + 16 + 17 +18 + 19 + 20 + 21;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21);
        assertEquals(expected, actual);
    }

    @Test
    void function22() {
        final Function22<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v) -> a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s + t + u + v;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13 + 14 + 15 + 16 + 17 +18 + 19 + 20 + 21 + 22;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
        assertEquals(expected, actual);
    }

    @Test
    void function23() {
        final Function23<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fn =
            (a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, x) -> a + b + c + d + e + f + g + h + i + j + k + l + m + n + o + p + q + r + s + t + u + v + x;
        final int expected = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 + 11 + 12 + 13 + 14 + 15 + 16 + 17 +18 + 19 + 20 + 21 + 22 + 23;
        final int actual = fn.apply(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23);
        assertEquals(expected, actual);
    }

}
