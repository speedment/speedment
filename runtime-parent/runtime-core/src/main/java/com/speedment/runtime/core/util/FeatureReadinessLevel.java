package com.speedment.runtime.core.util;

import com.speedment.runtime.core.exception.SpeedmentException;

/**
 *
 * @author Per Minborg
 */
public enum FeatureReadinessLevel {
    /**
     * The feature is pure experimental whereby the API is likely to change,
     * there are no or limited test coverage and the feature is likely to
     * exhibit unpredictable behavior including erratic results, memory leaks,
     * lingering resource allocations and complete JVM crashes. The feature may
     * also be removed at any time without prior notice.
     * <p>
     * THE USE OF THE FEATURE IN PRODUCTION SYSTEM IS DISSALOWED.
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

    public void assertAtLeast(FeatureReadinessLevel requiredLevel) {
        if (this.compareTo(requiredLevel) < 0) {
            throw new SpeedmentException(
                "The requred " + FeatureReadinessLevel.class.getSimpleName() + " was " + requiredLevel
                + " but this feature is only at level " + this
            );
        }
    }

}
