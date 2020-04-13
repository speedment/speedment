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

import com.speedment.tool.core.resource.FontAwesome;
import com.speedment.tool.core.toolbar.ToolbarItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.text.TextAlignment;

/**
 * Default implementation of the {@link ToolbarItem}-interface.
 *
 * @author Emil Forslund
 * @since  3.1.5
 */
public final class SimpleToolbarItemImpl implements ToolbarItem<Button> {

    private final String text;
    private final FontAwesome icon;
    private final EventHandler<ActionEvent> handler;
    private final String tooltip;

    public SimpleToolbarItemImpl(
            String text,
            FontAwesome icon,
            EventHandler<ActionEvent> handler,
            String tooltip) {
        this.text    = text;
        this.icon    = icon;
        this.handler = handler;
        this.tooltip = tooltip;
    }

    @Override
    public Button createNode() {
        final Button btn = new Button(text, icon.view());
        btn.setTextAlignment(TextAlignment.CENTER);
        btn.setAlignment(Pos.CENTER);
        btn.setMnemonicParsing(false);
        btn.setLayoutX(10);
        btn.setLayoutY(10);
        btn.setPadding(new Insets(8, 12, 8, 12));
        btn.setOnAction(handler);
        btn.setTooltip(new Tooltip(tooltip));
        return btn;
    }
}
