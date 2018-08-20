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
