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
package com.speedment.tool.propertyeditor.item;


import com.speedment.runtime.config.Document;
import com.speedment.tool.config.trait.HasNameProtectedProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Simon Jonasson
 * @since 3.0.0
 */

public final class ItemUtil {

    private ItemUtil() {}

    public static final String DATABASE_RELATION_TOOLTIP =
        "This field should ONLY be changed to reflect changes made in the underlying database.\nEnable editing by by right clicking on the field.";

    /**
     * Makes a node disabled by default, and requires that the user right-clicks
     * the node to enable it.
     * <p>
     * This decorator is intended to use on fields that can be edited, but
     * should only be edited under certain conditions, such as the Table.Name
     * field
     *
     * @param node         the node to decorate
     * @param doc          the document
     * @param tooltipText  the tooltip text displayed while the node is disabled
     * @return             the decorated node, which will be wrapped in a StackPane
     */
    public static Node lockDecorator(Node node, Document doc, String tooltipText) {
        if (doc instanceof HasNameProtectedProperty
        && !((HasNameProtectedProperty) doc).isNameProtected()) return node;

        node.setDisable(true);

        final StackPane pane = new StackPane();
        final ContextMenu menu = new ContextMenu();
        final MenuItem item = new MenuItem("Enable editing");
        final Tooltip tooltip = new Tooltip(tooltipText);
        
        Tooltip.install(pane, tooltip);
        menu.getItems().add(item);

        final EventHandler<MouseEvent> contextMenuToggle = (MouseEvent event) -> {
            if (event.isSecondaryButtonDown() && !menu.isShowing()) {
                menu.show(pane, event.getScreenX(), event.getScreenY());
            } else if (menu.isShowing()) {
                menu.hide();
            }
            event.consume();
        };
        final EventHandler<ActionEvent> menuItemClicked = (ActionEvent event) -> {
            Tooltip.uninstall(pane, tooltip);
            pane.removeEventHandler(MouseEvent.MOUSE_PRESSED, contextMenuToggle);
            node.setDisable(false);
            if (doc instanceof HasNameProtectedProperty) {
                ((HasNameProtectedProperty) doc)
                    .nameProtectedProperty().set(false);
            }
        };

        item.setOnAction(menuItemClicked);

        pane.setOnMousePressed(contextMenuToggle);
        pane.getChildren().add(node);
        return pane;
    }

}
