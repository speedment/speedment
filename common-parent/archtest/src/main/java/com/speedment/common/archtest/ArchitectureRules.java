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

import com.speedment.common.archtest.internal.ArchitectureRulesUtil;
import com.tngtech.archunit.core.domain.JavaClasses;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ArchitectureRules {

    private ArchitectureRules() {}

    public static void check(JavaClasses classes, Collection<RuleType> set) {
        ArchitectureRulesUtil.rules(set).forEach(c -> c.accept(classes));
    }

    public static void check(JavaClasses classes, RuleType... set) {
         check(classes, Stream.of(set).collect(Collectors.toList()));
    }

}
