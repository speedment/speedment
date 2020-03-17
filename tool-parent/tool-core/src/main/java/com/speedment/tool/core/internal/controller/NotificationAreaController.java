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
package com.speedment.tool.core.internal.controller;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.notification.Notification;
import com.speedment.tool.core.util.LayoutAnimator;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author  Emil Forslund
 * @since   2.3.0
 */
public final class NotificationAreaController implements Initializable {
    
    private final LayoutAnimator animator;
    
    public @Inject UserInterfaceComponent userInterfaceComponent;
    private @FXML FlowPane notificationArea;
    
    public NotificationAreaController() {
        this.animator = new LayoutAnimator();
    }

    /**
     * Initializes the controller class.
     * 
     * @param url  the url
     * @param rb   the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animator.observe(notificationArea.getChildren());
        
        final ObservableList<Notification> notifications = userInterfaceComponent.notifications();
        
        notifications.addListener((ListChangeListener.Change<? extends Notification> change) -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(n -> {
                        NotificationController.showNotification(
                            notificationArea,
                            n.text(),
                            n.icon(),
                            n.palette(),
                            n.onClose()
                        );
                        
                        notifications.remove(n);
                    });
                }
            }
        });
        
        while (!notifications.isEmpty()) {
            final Notification n = notifications.get(0);
            NotificationController.showNotification(
                notificationArea,
                n.text(),
                n.icon(),
                n.palette(),
                n.onClose()
            );
            notifications.remove(0);
        }
    }
}