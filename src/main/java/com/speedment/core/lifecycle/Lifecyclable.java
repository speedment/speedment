/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.core.lifecycle;

/**
 *
 * @author pemi
 * @param <T> Return type
 */
public interface Lifecyclable<T> {

    /**
     * Initialize this Lifecyclable. This method is used to set initial
     * dependencies and internal settings.
     * 
     * @return this
     */
    T initialize();

    /**
     * Resolve is called when all Lifecyclables has been initialized. This
     * method can be used to link to other Lifecyclables.
     * 
     * @return this
     */
    T resolve();

    /**
     * Starts this Lifecyclable. This method can be used to link to other
     * Lifecyclables.
     * 
     * @return this
     */
    T start();

    /**
     * Stops this Lifecyclable. The method shall also deallocate any resources
     * allocated to this Lifecyclable.
     * 
     * @return this
     */
    T stop();

    public enum State {

        INIT, INIITIALIZED, RESOLVED, STARTED, STOPPED;

        public void checkNextState(State nextState) {
            if (ordinal() + 1 != nextState.ordinal()) {
                throw new IllegalStateException("Cannot move from " + this + " to " + nextState);
            }
        }

        public State nextState() {
            final State[] states = State.values();
            if (states.length >= ordinal()) {
                throw new IllegalStateException("No next state for " + this);
            }
            return states[ordinal() + 1];
        }

        public boolean is(State compareToState) {
            return ordinal() >= compareToState.ordinal();
        }

    }

}
