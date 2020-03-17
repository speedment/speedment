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
package com.speedment.tool.core.rule;

import java.util.concurrent.CompletableFuture;

/**
 * A rule is a set of conditions which should/must be met before code generation.
 * <p>
 * Rules can post {@link Issue} during their verification phase. If one
 * or more {@link Issue.Level#ERROR} has been posted, generation cannot be performed. If one or
 * more {@link Issue.Level#WARNING} has been issued, generation can still be performed, but at the
 * users own risk.
 * 
 * @author Simon Jonasson
 * @since 3.0.0
 */
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
