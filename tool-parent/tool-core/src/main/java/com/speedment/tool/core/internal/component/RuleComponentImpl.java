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
package com.speedment.tool.core.internal.component;

import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.tool.core.component.IssueComponent;
import com.speedment.tool.core.component.RuleComponent;
import com.speedment.tool.core.internal.rule.ProtectedNameRule;
import com.speedment.tool.core.internal.rule.ReferencesEnabledRule;
import com.speedment.tool.core.internal.util.CompletableFutureUtil;
import com.speedment.tool.core.rule.Rule;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Simon Jonasson
 * @since 3.0.0
 */
public final class RuleComponentImpl implements RuleComponent {

    private final List<Rule> rules;

    public RuleComponentImpl(
        final ProjectComponent projectComponent,
        final IssueComponent issues
    ) {
        this.rules = new LinkedList<>();
        install(new ProtectedNameRule(projectComponent, issues));
        install(new ReferencesEnabledRule(projectComponent, issues));
    }

    @Override
    public void install(Rule rule) {
        rules.add(rule);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture<Boolean> verify() {
        final CompletableFuture<Boolean>[] futures;
        futures = rules.stream().parallel()
            .map(Rule::verify)
            .toArray(CompletableFuture[]::new);

        return CompletableFutureUtil.allOf(Boolean.TRUE, Boolean::logicalAnd, futures);
    }
}
