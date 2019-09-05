/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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

import com.speedment.runtime.core.exception.SpeedmentException;

import static java.lang.String.format;

/**
 * Enumeration of the readiness of a particular feature. This is used internally
 * by Speedment to print an appropriate message on launch.
 *
 * @author Per Minborg
 * @since  3.0.21
 */
public enum FeatureReadinessLevel {
    /**
     * The feature is pure experimental whereby the API is likely to change,
     * there are no or limited test coverage and the feature is likely to
     * exhibit unpredictable behavior including erratic results, memory leaks,
     * lingering resource allocations and complete JVM crashes. The feature may
     * also be removed at any time without prior notice.
     * <p>
     * THE USE OF THE FEATURE IN PRODUCTION SYSTEM IS DISALLOWED.
     */
    EXPERIMENTAL,
    /**
     * The feature has undergone limited testing and the API is likely to change
     * but likely not in a material way. The feature may exhibit unpredictable
     * behavior including erratic results, memory leaks, lingering resource
     * allocations.
     * <p>
     * THE USE OF THE FEATURE IN PRODUCTION SYSTEM IS HIGHLY DISCOURAGED.
     */
    PREVIEW,
    /**
     * The feature has undergone testing and the API is unlikely to undergo
     * anything else than minor changes. The feature might exhibit unpredictable
     * behavior including erratic results, memory leaks, lingering resource
     * allocations.
     * <p>
     * THE USE OF THE FEATURE IN PRODUCTION SYSTEM IS DISCOURAGED.
     */
    API_STABLE,
    /**
     * The feature has undergone regular testing and the API is stable. The
     * feature is unlikely to exhibit unpredictable behavior such as erratic
     * results, memory leaks, lingering resource allocations.
     * <p>
     * THE USE OF THE FEATURE IN PRODUCTION SYSTEM IS NOT RECOMMENDED.
     */
    RELEASE_CANDIDATE;

    /**
     * Throw a {@link SpeedmentException} if the required
     * {@link FeatureReadinessLevel} has not been met.
     *
     * @param requiredLevel       the required readiness level
     * @throws SpeedmentException if not met
     */
    public void assertAtLeast(FeatureReadinessLevel requiredLevel)
    throws SpeedmentException {
        if (this.compareTo(requiredLevel) < 0) {
            throw new SpeedmentException(format(
                "The required %s is %s but this feature is only at level %s",
                FeatureReadinessLevel.class.getSimpleName(),
                requiredLevel.name(), name()
            ));
        }
    }
}