package com.speedment.tool.core.internal.toolbar;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.common.injector.annotation.WithState;
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
    void install(
            @WithState(INITIALIZED) ToolbarComponent toolbar,
            @WithState(INITIALIZED) UserInterfaceComponent ui) {

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

        toolbar.install("progress", new GenerationProgressToolbarItem());
        toolbar.install("brand", new BrandToolbarItem());
    }
}
