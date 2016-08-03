package com.speedment.tool.rule;

import com.speedment.runtime.annotation.Api;
import com.speedment.tool.rule.Issue.Level;
import java.util.concurrent.CompletableFuture;

/**
 * A rule is a set of conditions which should/must be met before code generation.
 * <p>
 * Rules can post {@link Issue} during their verification phase. If one
 * or more {@link Level.ERROR} has been posted, generation cannot be performed. If one or
 * more {@link Level.WARNING} has been issued, generation can still be performed, but at the
 * users own risk.
 * 
 * @author Simon Jonasson
 *  * @since 3.0.0
 */
@Api(version="3.0")
public interface Rule {
    
    /**
     * Verifies this rule. 
     * <p>
     * When the rule has performed all of its verifications, the CompletableFuture
     * should be set. {@code true} if no issues were found, {@code false} if issues
     * were found.
     * 
     * @return  a CompletableFuture, that will take {@code true} if no issues were found
     */
    CompletableFuture<Boolean> verify(); 
}
