/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.internal.component;

import com.speedment.internal.common.injector.Injector;
import com.speedment.internal.common.injector.State;
import com.speedment.internal.common.injector.annotation.ExecuteBefore;
import com.speedment.internal.common.injector.annotation.Inject;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.internal.component.InternalOpenSourceComponent;
import com.speedment.tool.component.RuleComponent;
import com.speedment.tool.internal.rule.ProtectedNameRule;
import com.speedment.tool.internal.rule.ReferencesEnabledRule;
import com.speedment.tool.internal.util.CompletableFutureUtil;
import com.speedment.tool.rule.Rule;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */
public class RuleComponentImpl extends InternalOpenSourceComponent implements  RuleComponent{
    
    private @Inject Injector injector;
    private final List<Rule> rules;
    
    public RuleComponentImpl(){
        this.rules = new LinkedList<>();
    }
    
    @ExecuteBefore(State.RESOLVED)
    private void installRules(){
        install(ProtectedNameRule::new);
        install(ReferencesEnabledRule::new);
    }
    
    @Override
    public void install(Supplier<Rule> rule){
        rules.add( injector.inject(rule.get()) );
    }

    @Override
    @SuppressWarnings("unchecked")
    public CompletableFuture<Boolean> verify() {
        final CompletableFuture<Boolean>[] futures;
        futures =  rules.stream().parallel()
                        .map( Rule::verify )
                        .toArray( CompletableFuture[]::new );

        return CompletableFutureUtil.allOf(Boolean.TRUE, Boolean::logicalAnd, futures);
    }
    
    @Override
    protected String getDescription() {
        return "Component responsible for validating all installed rules, and generating issues if said rules aren't followed.";
    }

    @Override
    public Class<? extends Component> getComponentClass() {
        return RuleComponent.class;
    }
}
