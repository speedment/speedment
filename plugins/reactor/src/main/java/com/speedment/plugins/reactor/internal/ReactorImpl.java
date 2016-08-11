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
package com.speedment.plugins.reactor.internal;

import com.speedment.plugins.reactor.Reactor;
import com.speedment.runtime.exception.SpeedmentException;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The default implementation of the {@link Reactor} interface.
 * 
 * @author  Emil Forslund
 * @since   1.1.0
 */
public final class ReactorImpl implements Reactor {
    
    private final ScheduledExecutorService scheduler;
    
    /**
     * Constructs a new Reactor. This should only be called as part of the
     * builder pattern for this class and never directly.
     * 
     * @param scheduler  the scheduler that is polling the database
     */
    public ReactorImpl(ScheduledExecutorService scheduler) {
        this.scheduler = requireNonNull(scheduler);
    }
    
    /**
     * Stops the reactor from polling the database.
     */
    @Override
    public void stop() {
        scheduler.shutdownNow();
        
        try {
            if (!scheduler.awaitTermination(2, TimeUnit.SECONDS)) {
                throw new SpeedmentException(
                    "Scheduler took too long to terminate."
                );
            }
        } catch (final InterruptedException ex) {
            throw new SpeedmentException(
                "Scheduler was interrupted during termination.", ex
            );
        }
    }
}
