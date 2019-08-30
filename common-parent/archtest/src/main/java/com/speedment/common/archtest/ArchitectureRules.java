package com.speedment.common.archtest;

import com.speedment.common.archtest.internal.ArchitectureRulesUtil;
import com.tngtech.archunit.core.domain.JavaClasses;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ArchitectureRules {

    public enum RuleType {
        UTIL, INTERNAL;
    }

    public static void check(JavaClasses classes, Collection<RuleType> set) {
        ArchitectureRulesUtil.rules(set).forEach(c -> c.accept(classes));
    }

    public static void check(JavaClasses classes, RuleType... set) {
         check(classes, Stream.of(set).collect(Collectors.toList()));
    }

}
