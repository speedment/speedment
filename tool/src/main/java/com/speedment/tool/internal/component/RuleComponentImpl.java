package com.speedment.tool.internal.component;

import com.speedment.common.injector.Injector;
import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.component.Component;
import com.speedment.runtime.internal.component.InternalOpenSourceComponent;
import com.speedment.tool.component.RuleComponent;
import com.speedment.tool.internal.rule.ProtectedNameRule;
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
    }
    
    @Override
    public void install(Supplier<Rule> rule){
        rules.add( injector.inject(rule.get()) );
    }

    @Override
    public CompletableFuture<Void> verify() {
        final CompletableFuture<Void>[] futures;
        futures = rules.stream().parallel()
                        .map( Rule::verify )
                        .toArray( CompletableFuture[]::new );
        return CompletableFuture.allOf(futures);
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
