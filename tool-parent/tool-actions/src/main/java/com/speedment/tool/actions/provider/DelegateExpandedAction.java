package com.speedment.tool.actions.provider;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.tool.actions.ProjectTreeComponent;
import com.speedment.tool.actions.internal.menues.ToggleColumnsEnabledActionImpl;
import com.speedment.tool.actions.internal.menues.ToggleExpandedActionImpl;
import com.speedment.tool.actions.menues.ToggleExpandedAction;
import com.speedment.tool.actions.menues.ToggleTablesEnabledAction;

import static com.speedment.common.injector.State.RESOLVED;

/**
 * Delegated implementation of the {@link ToggleTablesEnabledAction}-interface.
 *
 * @author Per
 * @since  3.2.0
 */
public final class DelegateExpandedAction implements ToggleExpandedAction {

    private final ToggleExpandedActionImpl inner;

    public DelegateExpandedAction() {
        this.inner = new ToggleExpandedActionImpl();
    }

    @ExecuteBefore(RESOLVED)
    public void installMenuItems(ProjectTreeComponent projectTree) {
        inner.installMenuItems(projectTree);
    }
}