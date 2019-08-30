package com.speedment.common.archtest.internal;

import com.speedment.common.archtest.ArchitectureRules;
import com.speedment.common.archtest.ArchitectureRules.RuleType;
import com.tngtech.archunit.core.domain.JavaClasses;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class ArchitectureRulesUtil {

    private ArchitectureRulesUtil() {}

    public static Stream<Consumer<JavaClasses>> rules(Collection<ArchitectureRules.RuleType> set) {
        final Stream.Builder<Consumer<JavaClasses>> builder = Stream.builder();
        if (set.contains(RuleType.UTIL)) {
            builder
                .add(UtilClassRuleUtil::methodIsStatic)
                .add(UtilClassRuleUtil::classHasPrivateConstructor)
                .add(UtilClassRuleUtil::classIsFinal);
        }
        if (set.contains(RuleType.INTERNAL)) {
            builder
                .add(OpenClassesRuleUtil::openClassesShouldNotImplementInternalClasses)
                .add(OpenClassesRuleUtil::openClassMethodsShouldNotReturnInternalClasses)
                .add(OpenClassesRuleUtil::openClassMethodsShouldNotThrowInternalClasses);
        }
        return builder.build();
    }

}
