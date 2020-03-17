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
package com.speedment.tool.core.component;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.tool.core.rule.Issue;
import com.speedment.tool.core.rule.Rule;

import java.util.concurrent.CompletableFuture;

/**
 * A component for assigning and verifying rules, which will be checked
 * before code generation.
 * <p>
 * The rules will be checked after a user issues the Generate command via the
 * Speedment UI, but before code generation is initiated
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
@InjectKey(RuleComponent.class)
public interface RuleComponent {
        
    /**
     * Installs a new {@link Rule}, which will be checked before code generation
     * occurs.
     * <p>
     * Implementors of new Rules may have their rules post an {@link Issue} using
     * the {@link IssueComponent} in case their Rule is not fulfilled during
     * verification.
     * 
     * @param rule  to add
     */
    void install(Rule rule);

    /**
     * Checks the rule against the current Speedment configuration. The CompletableFuture
     * will return once all rules have had their .verify() method executed.
     * 
     * @return  a CompletableFuture which will return once all rules have executed
     */
    CompletableFuture<Boolean> verify();
}
