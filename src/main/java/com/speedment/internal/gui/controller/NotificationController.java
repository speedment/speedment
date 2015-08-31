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
package com.speedment.internal.gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.speedment.internal.util.NullUtil.requireNonNulls;
import static com.speedment.internal.gui.util.FadeAnimation.fadeOut;
import static com.speedment.internal.gui.util.TransitionAnimation.enterFromRight;
import static java.util.Objects.requireNonNull;

/**
 * FXML Controller class for the notification messages that can be triggered in the bottom right part of the window.
 *
 * @author Emil Forslund
 */
public final class NotificationController implements Initializable {
    
    @FXML private VBox container;
    @FXML private Button closeButton;
    @FXML private Label message;

    /**
     * The notification type and the relevant background color.
     */
    public enum Notification {
        INFO ("#e5e5e5"), 
        WARNING ("#ffe5aa"), 
        ERROR ("#ffaaaa"), 
        SUCCESS ("#99ff99");
        
        private final String color;
        
        Notification(String hexColor) {
            color = hexColor;
        }
        
        public String getBackgroundColor() {
            return color;
        }
    }
    
    private final String text;
    private final Notification type;

    /**
     * Initializes the controller using the text to show and the notification type.
     *
     * @param text  the text to show
     * @param type  the notification type
     */
    private NotificationController(String text, Notification type) {
        this.text   = requireNonNull(text);
        this.type   = requireNonNull(type);
    }

    /**
     * Initializes the controller class.
     *
     * @param url  the url to use
     * @param rb   the resource bundle to use
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requireNonNull(url);
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

    /**
     * Creates and configures a new Notification-component in the specified stage.
     *
     * @param parent   the parent to show it in
     * @param message  the message to display
     * @param type     the type of notification
     */
    public static void showNotification(Pane parent, String message, Notification type) {
        requireNonNulls(parent, message, type);
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