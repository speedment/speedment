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

import com.speedment.common.archtest.RuleType;
import com.tngtech.archunit.core.domain.JavaClasses;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class ArchitectureRulesUtil {

    private ArchitectureRulesUtil() {}

    public static Stream<Consumer<JavaClasses>> rules(Collection<RuleType> set) {
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
