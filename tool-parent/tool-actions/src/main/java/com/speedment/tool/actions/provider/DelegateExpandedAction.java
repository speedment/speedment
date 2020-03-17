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
package com.speedment.tool.actions.provider;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.tool.actions.ProjectTreeComponent;
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