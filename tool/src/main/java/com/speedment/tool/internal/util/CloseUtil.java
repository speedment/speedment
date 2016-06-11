package com.speedment.tool.internal.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 *
 * @author Emil Forslund
 * @since  2.4.0
 */
public final class CloseUtil {
    
    public static EventHandler<ActionEvent> newCloseHandler() {
        return event -> {
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        };
    }
    
    private CloseUtil() {}
}
