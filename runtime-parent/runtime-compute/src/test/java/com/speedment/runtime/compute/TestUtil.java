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
package com.speedment.runtime.compute;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
final class TestUtil {

    static final double EPSILON = 10e-10;
    static final String STRING = "Tryggve";

    private TestUtil() {
    }

    public static Stream<String> strings() {
        return Stream.of(
            "Tryggve",
            "Arne",
            "Sven",
            "Glenn",
            "Pippilotta Viktualia Rullgardina Krusmynta Efraimsdotter Långstrump"
        );

    }

}
