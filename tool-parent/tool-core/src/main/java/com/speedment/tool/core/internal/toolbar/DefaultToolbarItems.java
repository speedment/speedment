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
package com.speedment.tool.core.internal.toolbar;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.generator.core.component.EventComponent;
import com.speedment.tool.core.brand.Brand;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.resource.FontAwesome;
import com.speedment.tool.core.toolbar.ToolbarComponent;

import static com.speedment.common.injector.State.INITIALIZED;

/**
 * @author Emil Forslund
 * @since  3.1.5
 */
@InjectKey(DefaultToolbarItems.class)
public final class DefaultToolbarItems {

    @ExecuteBefore(INITIALIZED)
    public void install(
            @WithState(INITIALIZED) ToolbarComponent toolbar,
            @WithState(INITIALIZED) UserInterfaceComponent ui,
            @WithState(INITIALIZED) Brand brand,
            @WithState(INITIALIZED) EventComponent events
    ) {

        toolbar.install("reload", new SimpleToolbarItemImpl(
            "Reload", FontAwesome.REFRESH, ev -> ui.reload(),
            "Reload the metadata from the database and merge any changes " +
            "with the existing configuration."
        ));

        toolbar.install("generate", new SimpleToolbarItemImpl(
            "Generate", FontAwesome.PLAY_CIRCLE, ev -> ui.generate(),
            "Generate code using the current configuration. Automatically " +
                "save the configuration before generation."
        ));

        toolbar.install("brand", new BrandToolbarItem(brand));
        toolbar.install("progress", new GenerationProgressToolbarItem(events));
    }
}
