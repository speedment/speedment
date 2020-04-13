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
package com.speedment.tool.actions;

import com.speedment.common.injector.InjectBundle;
import com.speedment.tool.actions.provider.DelegateColumnsEnabledAction;
import com.speedment.tool.actions.provider.DelegateExpandedAction;
import com.speedment.tool.actions.provider.DelegateProjectTreeComponent;
import com.speedment.tool.actions.provider.DelegateTablesEnabledAction;

import java.util.stream.Stream;

/**
 * Dependency Injection Bundle that installs additional actions in the Speedment
 * tool.
 *
 * @author Emil Forslund
 * @since  3.0.17
 */
public final class ToolActionsBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(
            DelegateProjectTreeComponent.class,
            DelegateExpandedAction.class,
            DelegateColumnsEnabledAction.class,
            DelegateTablesEnabledAction.class
        );
    }
}
