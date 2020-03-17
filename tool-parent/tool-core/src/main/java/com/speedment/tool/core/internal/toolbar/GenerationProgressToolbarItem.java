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

import com.speedment.generator.core.component.EventComponent;
import com.speedment.generator.core.event.AfterGenerate;
import com.speedment.generator.core.event.BeforeGenerate;
import com.speedment.generator.core.event.FileGenerated;
import com.speedment.tool.core.toolbar.ToolbarItem;
import com.speedment.tool.core.toolbar.ToolbarSide;
import javafx.scene.control.Label;

import static java.util.Objects.requireNonNull;
import static javafx.application.Platform.runLater;

/**
 * Displays the generation progress in the toolbar.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public final class GenerationProgressToolbarItem implements ToolbarItem<Label> {

    private final EventComponent events;

    public GenerationProgressToolbarItem(EventComponent events) {
        this.events = requireNonNull(events);
    }

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
