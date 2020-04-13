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

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public final class Elements {

    public static ContentElement[] rows(String... rows) {
        return Stream.of(rows)
            .map(ContentElement::of)
            .toArray(ContentElement[]::new);
    }

    public static TagElement a() {
        return TagElement.of("a");
    }

    public static TagElement body() {
        return TagElement.of("body");
    }

    public static TagElement br() {
        return TagElement.of("br");
    }

    public static TagElement div() {
        return TagElement.of("div");
    }

    public static TagElement form() {
        return TagElement.of("form");
    }

    public static TagElement input() {
        return TagElement.of("input");
    }

    public static TagElement html() {
        return TagElement.of("html");
    }

    public static TagElement head() {
        return TagElement.of("head");
    }

    public static TagElement h1() {
        return TagElement.of("h1");
    }

    public static TagElement h2() {
        return TagElement.of("h2");
    }

    public static TagElement h3() {
        return TagElement.of("h3");
    }

    public static TagElement h4() {
        return TagElement.of("h4");
    }

    public static TagElement h5() {
        return TagElement.of("h5");
    }

    public static TagElement img() {
        return TagElement.of("img");
    }

    public static TagElement meta() {
        return TagElement.of("meta");
    }

    public static TagElement p() {
        return TagElement.of("p");
    }

    public static TagElement span() {
        return TagElement.of("span");
    }

    public static TagElement table() {
        return TagElement.of("table");
    }

    public static TagElement tbody() {
        return TagElement.of("tbody");
    }

    public static TagElement td() {
        return TagElement.of("td");
    }

    public static TagElement tfoot() {
        return TagElement.of("tfoot");
    }

    public static TagElement title() {
        return TagElement.of("title");
    }

    public static TagElement th() {
        return TagElement.of("th");
    }

    public static TagElement tr() {
        return TagElement.of("tr");
    }

    private Elements() {
    }

}