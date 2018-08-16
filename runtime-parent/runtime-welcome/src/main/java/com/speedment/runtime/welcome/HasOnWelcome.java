package com.speedment.runtime.welcome;

@FunctionalInterface
public interface HasOnWelcome {

    /**
     * This method is invoked by Speedment upon start.
     */
    void onWelcome();

}
