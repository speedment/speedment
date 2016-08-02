package com.speedment.tool.component;

import com.speedment.common.injector.annotation.InjectorKey;
import com.speedment.runtime.annotation.Api;
import com.speedment.runtime.component.Component;
import com.speedment.tool.rule.Issue;
import com.speedment.tool.rule.Rule;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

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
@Api(version="3.0")
@InjectorKey(RuleComponent.class)
public interface RuleComponent extends Component{
        
    /**
     * Installs a new {@link Rule}, which will be checked before code generation
     * occurs.
     * <p>
     * Implementors of new Rules may have their rules post an {@link Issue} using
     * the {@link IssueComponent} in case their Rule is not fulfilled during
     * verification.
     * 
     * @param ruleFactory  supplier for the rule
     */
    void install(Supplier<Rule> ruleFactory);

    /**
     * Checks the rule against the current Speedment configuration. The CompletableFuture
     * will return once all rules have had their .verify() method executed.
     * 
     * @return  a CompletableFuture which will return once all rules have executed
     */
    CompletableFuture<Void> verify();
}
