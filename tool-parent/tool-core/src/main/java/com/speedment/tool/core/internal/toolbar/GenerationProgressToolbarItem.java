package com.speedment.tool.core.internal.toolbar;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.generator.core.component.EventComponent;
import com.speedment.generator.core.event.AfterGenerate;
import com.speedment.generator.core.event.BeforeGenerate;
import com.speedment.generator.core.event.FileGenerated;
import com.speedment.tool.core.toolbar.ToolbarItem;
import com.speedment.tool.core.toolbar.ToolbarSide;
import javafx.scene.control.Label;

import static javafx.application.Platform.runLater;

/**
 * Displays the generation progress in the toolbar.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public final class GenerationProgressToolbarItem implements ToolbarItem<Label> {

    @Inject
    private EventComponent events;

    @Override
    public Label createNode() {
        final Label label = new Label("");
        events.on(BeforeGenerate.class, bg -> runLater(() -> label.setText("Generating...")));
        events.on(FileGenerated.class, fg -> runLater(() -> label.setText(fg.meta().getModel().getName())));
        events.on(AfterGenerate.class, ag -> runLater(() -> label.setText("")));
        return label;
    }

    @Override
    public ToolbarSide getSide() {
        return ToolbarSide.LEFT;
    }
}
