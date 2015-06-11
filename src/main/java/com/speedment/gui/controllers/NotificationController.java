/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.gui.controllers;

import static com.speedment.gui.util.FadeAnimation.fadeOut;
import static com.speedment.gui.util.TransitionAnimation.enterFromRight;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Emil Forslund
 */
public final class NotificationController implements Initializable {
    
    @FXML private VBox container;
    @FXML private Button closeButton;
    @FXML private Label message;
    
    public static enum Notification {
        INFO ("#e5e5e5"), 
        WARNING ("#ffe5aa"), 
        ERROR ("#ffaaaa"), 
        SUCCESS ("#99ff99");
        
        private final String color;
        
        private Notification(String hexColor) {
            color = hexColor;
        }
        
        public String getBackgroundColor() {
            return color;
        }
    }
    
    private final String text;
    private final Notification type;
    
    private NotificationController(String text, Notification type) {
        this.text   = text;
        this.type   = type;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        message.setText(text);
        message.setStyle("background:" + type.getBackgroundColor());
        
        final Runnable closer = () -> {
            fadeOut(container, ev -> {
                @SuppressWarnings("unchecked")
                final Pane pane = (Pane) container.getParent();
                pane.getChildren().remove(container);
            });
        };
        
        closeButton.setOnAction(ev -> closer.run());
        container.setOnMouseReleased(ev -> closer.run());
    }
    
    public static void showNotification(Pane parent, String message, Notification type) {
		final FXMLLoader loader = new FXMLLoader(AlertController.class.getResource("/fxml/Notification.fxml"));
		final NotificationController control = new NotificationController(message, type);
        loader.setController(control);

        try {
            final VBox notification = (VBox) loader.load();
            parent.getChildren().add(notification);
            enterFromRight(notification);
            
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
	}
}