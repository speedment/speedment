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

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

final class OpenClassesRuleUtil {

    private OpenClassesRuleUtil() {}

    private static final String INTERNAL = "..internal..";
    private static final DescribedPredicate<JavaClass> INTERNAL_CLASS = JavaClass.Predicates.resideInAnyPackage(INTERNAL);

    static void openClassesShouldNotImplementInternalClasses(JavaClasses classes) {
        ArchRuleDefinition.classes()
            .that().resideOutsideOfPackage(INTERNAL)
            .should().notImplement(INTERNAL_CLASS)
            .check(classes);
    }

    static void openClassMethodsShouldNotThrowInternalClasses(JavaClasses classes) {
        ArchRuleDefinition.noMethods()
            .that().areDeclaredInClassesThat().resideOutsideOfPackages(INTERNAL)
            .should().declareThrowableOfType(INTERNAL_CLASS)
            .check(classes);
    }

    static  void openClassMethodsShouldNotReturnInternalClasses(JavaClasses classes) {
        ArchRuleDefinition.noMethods()
            .that().areDeclaredInClassesThat().resideOutsideOfPackages(INTERNAL)
            .should().haveRawReturnType(INTERNAL_CLASS)
            .check(classes);
    }

}
