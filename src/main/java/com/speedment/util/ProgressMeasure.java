package com.speedment.util;

import com.speedment.internal.util.ProgressMeasurerImpl;
import java.util.function.Consumer;

/**
 *
 * @author Per Minborg
 */
public interface ProgressMeasure {

    /**
     * Sets the progress ratio. A negative value for progress indicates that the
     * progress is indeterminate. A positive value between 0 and 1 indicates the
     * percentage of progress where 0 is 0% and 1 is 100%. Any value greater
     * than 1 is interpreted as 100%.
     *
     * @param value
     */
    void setProgress(double value);

    /**
     * Gets the progress ratio. A negative value for progress indicates that the
     * progress is indeterminate. A positive value between 0 and 1 indicates the
     * percentage of progress where 0 is 0% and 1 is 100%. Any value greater
     * than 1 is interpreted as 100%.
     *
     * @return the progress ratio
     */
    double getProgress();

    /**
     * Sets the current action.
     *
     * @param action what is going on
     */
    void setCurrentAction(String action);

    /**
     * Gets the current action.
     *
     * @return what is going on
     */
    String getCurrentAction(String action);

    /**
     * Adds a listener that will be called each time a value changes.
     *
     * @param listener to add
     * @return an instance of a ProgressListener that has the provided listener
     */
    ProgressMeasure addListener(Consumer<ProgressMeasure> listener);

    static ProgressMeasure create() {
        return new ProgressMeasurerImpl();
    }

}
