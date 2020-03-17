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
package com.speedment.tool.core;

import com.speedment.common.injector.InjectBundle;
import com.speedment.tool.actions.ToolActionsBundle;
import com.speedment.tool.core.internal.component.RuleComponentImpl;
import com.speedment.tool.core.internal.component.UserInterfaceComponentImpl;
import com.speedment.tool.core.internal.component.VersionComponentImpl;
import com.speedment.tool.core.internal.menubar.DefaultMenuItemFactories;
import com.speedment.tool.core.internal.menubar.MenuBarComponentImpl;
import com.speedment.tool.core.internal.toolbar.DefaultToolbarItems;
import com.speedment.tool.core.internal.toolbar.ToolbarComponentImpl;
import com.speedment.tool.core.internal.util.ConfigFileHelper;
import com.speedment.tool.core.internal.util.InjectionLoaderImpl;
import com.speedment.tool.core.provider.DelegateUserInterfaceComponent;

import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class ToolBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return InjectBundle.of(UserInterfaceComponentImpl.class)
            .withBundle(DelegateUserInterfaceComponent.include())
            .withBundle(new ToolActionsBundle())
            .withComponent(VersionComponentImpl.class)
            .withComponent(RuleComponentImpl.class)
            .withComponent(ConfigFileHelper.class)
            .withComponent(InjectionLoaderImpl.class)
            .withComponent(ToolbarComponentImpl.class)
            .withComponent(DefaultToolbarItems.class)
            .withComponent(DefaultMenuItemFactories.class)
            .withComponent(MenuBarComponentImpl.class)
            .injectables();
    }
    
}
