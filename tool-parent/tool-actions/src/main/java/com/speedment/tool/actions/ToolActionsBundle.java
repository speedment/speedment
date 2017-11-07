package com.speedment.tool.actions;

import com.speedment.common.injector.InjectBundle;
import com.speedment.tool.actions.internal.ProjectTreeComponentImpl;
import com.speedment.tool.actions.internal.menues.ToggleExpandedActionImpl;
import com.speedment.tool.actions.internal.menues.ToggleTablesEnabledActionImpl;

import java.util.stream.Stream;

/**
 * Dependency Injection Bundle that installs additional actions in the Speedment
 * tool.
 *
 * @author Emil Forslund
 * @since  3.0.16
 */
public final class ToolActionsBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(
            ProjectTreeComponentImpl.class,
            ToggleExpandedActionImpl.class,
            ToggleTablesEnabledActionImpl.class
        );
    }
}
