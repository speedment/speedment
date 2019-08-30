package com.speedment.common.archtest.internal;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

final class UtilClassRuleUtil {

    private UtilClassRuleUtil() {}

    private static final String UTIL = "Util";
    private static final DescribedPredicate<JavaClass> NONISTANTIABLE_CLASS = JavaClass.Predicates.simpleNameStartingWith(UTIL);

    static void classIsFinal(JavaClasses classes) {
        ArchRuleDefinition.classes()
            .that().haveSimpleNameEndingWith(UTIL)
            .should().haveModifier(JavaModifier.FINAL)
            .check(classes);
    }

    static void classHasPrivateConstructor(JavaClasses classes) {
        ArchRuleDefinition.constructors()
            .that().areDeclaredInClassesThat().haveSimpleNameEndingWith(UTIL)
            .should().haveModifier(JavaModifier.PRIVATE)
            .check(classes);
    }

    static void methodIsStatic(JavaClasses classes) {
        ArchRuleDefinition.methods()
            .that().areDeclaredInClassesThat().haveSimpleNameEndingWith(UTIL)
            .should().haveModifier(JavaModifier.STATIC)
            .check(classes);
    }

}
