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
package com.speedment.common.archtest;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

/**
 *
 * @author Per Minborg
 */
@AnalyzeClasses(packages = "com.speedment.common.archtest", importOptions = {DoNotIncludeTests.class})
final class ArchitectureTest {

    @ArchTest
    void util(JavaClasses classes) {
        ArchitectureRules.check(classes, RuleType.UTIL);
    }

    @ArchTest
    void internal(JavaClasses classes) {
        ArchitectureRules.check(classes, RuleType.INTERNAL);
    }

}
