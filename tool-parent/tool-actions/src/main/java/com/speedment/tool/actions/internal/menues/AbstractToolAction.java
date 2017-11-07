package com.speedment.tool.actions.internal.menues;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.tool.actions.ProjectTreeComponent;

import static com.speedment.common.injector.State.INITIALIZED;
import static com.speedment.common.injector.State.RESOLVED;

/**
 * Abstract base implementation of a tool action. The purpose of this class is
 * to standardize the dependency injection phases used in different actions.
 *
 * @author Emil Forslund
 * @since  3.0.17
 */
abstract class AbstractToolAction {

    /**
     * This method will be invoked before the {@link State#RESOLVED}-phase, but
     * before the {@link ProjectTreeComponent} is {@link State#INITIALIZED}.
     *
     * @param projectTree  the project tree component
     */
    @ExecuteBefore(RESOLVED)
    abstract void installMenuItems(
            @WithState(INITIALIZED) ProjectTreeComponent projectTree);

}