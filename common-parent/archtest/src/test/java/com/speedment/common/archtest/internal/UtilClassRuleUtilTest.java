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
package com.speedment.common.archtest.internal;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author Per Minborg
 */
@AnalyzeClasses(packages = "com.speedment.common.archtest.negative")
final class UtilClassRuleUtilTest {

    @ArchTest
    static void classIsFinal(JavaClasses classes) {
        assertThrows(AssertionError.class, () -> {
            UtilClassRuleUtil.classIsFinal(classes);
        });
    }

    @ArchTest
    static void methodIsStatic(JavaClasses classes) {
        assertThrows(AssertionError.class, () -> {
            UtilClassRuleUtil.methodIsStatic(classes);
        });
    }

    @ArchTest
    static void classHasPrivateConstructor(JavaClasses classes) {
        assertThrows(AssertionError.class, () -> {
            UtilClassRuleUtil.classHasPrivateConstructor(classes);
        });
    }


}
