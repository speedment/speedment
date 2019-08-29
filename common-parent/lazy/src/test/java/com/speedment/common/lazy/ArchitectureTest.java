package com.speedment.common.lazy;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = "com.speedment.common.lazy", importOptions = {DoNotIncludeTests.class})
final class ArchitectureTest {

    @ArchTest
    void publicClassesShouldBeFinal(JavaClasses classes) {
        ArchRuleDefinition.classes()
            .that().resideOutsideOfPackage("..internal..")
            .and().areNotInterfaces()
            .should().haveModifier(JavaModifier.FINAL)
            .check(classes);
    }

    @ArchTest
    void constructorsShouldBePrivate(JavaClasses classes) {
        ArchRuleDefinition.constructors()
            .that().areDeclaredInClassesThat().resideOutsideOfPackages("..internal..")
            .should().haveModifier(JavaModifier.PRIVATE)
            .check(classes);
    }

}
