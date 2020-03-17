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
package com.speedment.runtime.core.util;

import com.speedment.runtime.core.internal.util.ProgressMeasureImpl;

import java.util.function.Consumer;

/**
 * Measures the progress of a task.
 * 
 * @author  Per Minborg
 * @since   2.3.0
 */
public interface ProgressMeasure {

    /**
     * Sets the progress ratio. A negative value for progress indicates that the
     * progress is indeterminate. A positive value between 0 and 1 indicates the
     * percentage of progress where 0 is 0% and 1 is 100%. Any value greater
     * than 1 is interpreted as 100%.
     *
     * @param value  the new progress value
     */
    void setProgress(double value);

    /**
     * Gets the progress ratio. A negative value for progress indicates that the
     * progress is indeterminate. A positive value between 0 and 1 indicates the
     * percentage of progress where 0 is 0% and 1 is 100%. Any value greater
     * than 1 is interpreted as 100%.
     *
     * @return  the progress ratio
     */
    double getProgress();
    
    /**
     * Returns {@code true} if this task is done.
     * 
     * @return  {@code true} if done, else {@code false}
     */
    default boolean isDone() {
        return getProgress() >= ProgressMeasureUtil.DONE;
    }

    /**
     * Sets the current action.
     *
     * @param action  what is going on
     */
    void setCurrentAction(String action);

    /**
     * Gets the current action.
     *
     * @return  what is going on
     */
    String getCurrentAction();

    /**
     * Adds a listener that will be called each time a value changes.
     *
     * @param listener  to add
     * @return          an instance of a {@link ProgressMeasure} that has the 
     *                  provided listener
     */
    ProgressMeasure addListener(Consumer<ProgressMeasure> listener);

    static ProgressMeasure create() {
        return new ProgressMeasureImpl();
    }
}