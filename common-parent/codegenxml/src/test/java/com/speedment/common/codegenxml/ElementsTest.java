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
package com.speedment.common.codegenxml;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

final class ElementsTest {

    @Test
    void rows() {
        assertNotNull(Elements.rows("string"));
    }

    @Test
    void a() {
        assertNotNull(Elements.a());
    }

    @Test
    void body() {
        assertNotNull(Elements.body());
    }

    @Test
    void br() {
        assertNotNull(Elements.br());
    }

    @Test
    void div() {
        assertNotNull(Elements.div());
    }

    @Test
    void form() {
        assertNotNull(Elements.form());
    }

    @Test
    void input() {
        assertNotNull(Elements.input());
    }

    @Test
    void html() {
        assertNotNull(Elements.html());
    }

    @Test
    void head() {
        assertNotNull(Elements.head());
    }

    @Test
    void h1() {
        assertNotNull(Elements.h1());
    }

    @Test
    void h2() {
        assertNotNull(Elements.h2());
    }

    @Test
    void h3() {
        assertNotNull(Elements.h3());
    }

    @Test
    void h4() {
        assertNotNull(Elements.h4());
    }

    @Test
    void h5() {
        assertNotNull(Elements.h5());
    }

    @Test
    void img() {
        assertNotNull(Elements.img());
    }

    @Test
    void meta() {
        assertNotNull(Elements.meta());
    }

    @Test
    void p() {
        assertNotNull(Elements.p());
    }

    @Test
    void span() {
        assertNotNull(Elements.span());
    }

    @Test
    void table() {
        assertNotNull(Elements.table());
    }

    @Test
    void tbody() {
        assertNotNull(Elements.tbody());
    }

    @Test
    void td() {
        assertNotNull(Elements.td());
    }

    @Test
    void tfoot() {
        assertNotNull(Elements.tfoot());
    }

    @Test
    void title() {
        assertNotNull(Elements.title());
    }

    @Test
    void th() {
        assertNotNull(Elements.th());
    }

    @Test
    void tr() {
        assertNotNull(Elements.tr());
    }
}
