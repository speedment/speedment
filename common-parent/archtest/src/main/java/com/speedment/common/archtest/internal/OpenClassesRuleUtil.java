package com.speedment.common.archtest.internal;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

final class OpenClassesRuleUtil {

    private OpenClassesRuleUtil() {}

    private static final String INTERNAL = "..internal..";
    private static final DescribedPredicate<JavaClass> INTERNAL_CLASS = JavaClass.Predicates.resideInAnyPackage("..internal..");

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
