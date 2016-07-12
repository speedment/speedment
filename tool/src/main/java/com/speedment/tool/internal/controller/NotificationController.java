/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.tool.internal.controller;

import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.tool.brand.Palette;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;
import static javafx.util.Duration.millis;

/**
 * FXML Controller class
 *
 * @author  Emil Forslund
 * @since   2.3
 */
public final class NotificationController implements Initializable {

    private final String message;
    private final FontAwesomeIcon icon;
    private final Palette palette;
    private final Runnable onClose;
    private final AtomicBoolean destroyed;
    
    private @FXML Label notification;
    
    private NotificationController(String message, FontAwesomeIcon icon, Palette palette, Runnable onClose) {
        this.message = requireNonNull(message);
        this.icon    = requireNonNull(icon);
        this.palette = requireNonNull(palette);
        this.onClose = requireNonNull(onClose);
        
        this.destroyed = new AtomicBoolean(false);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        notification.setText(message);
        notification.setGraphic(createGlyph());
        notification.getStyleClass().add(palette.name().toLowerCase());
    }
    
    public @FXML void close() {
        if (destroyed.compareAndSet(false, true)) {
            onClose.run();
            remove(notification);
        }
    }
    
    private void fadeAway() {
        final FadeTransition fade = new FadeTransition(millis(EXIT_SPEED), notification);
        fade.setFromValue(1);
        fade.setToValue(0);

        final TranslateTransition trans = new TranslateTransition(millis(EXIT_SPEED), notification);
        trans.setFromY(0);
        trans.setToY(ENTER_Y);

        final SequentialTransition seq = new SequentialTransition(fade, trans);
        seq.setOnFinished(ev -> {
            if (destroyed.compareAndSet(false, true)) {
                remove(notification);
            }
        });
        seq.play();
    }

    static void showNotification(FlowPane area, String message, FontAwesomeIcon icon, Palette palette, Runnable onClose) {

        final FXMLLoader loader = new FXMLLoader(NotificationController.class.getResource(NOTIFICATION_FXML));
        final AtomicReference<NotificationController> ref = new AtomicReference<>();
        loader.setControllerFactory(clazz -> {
            ref.set(new NotificationController(message, icon, palette, onClose));
            return ref.get();
        });
        final Node notification;
        
        try {
            notification = loader.load();
        } catch (final IOException ex) {
            throw new SpeedmentException(
                "Failed to load FXML-file: '" + NOTIFICATION_FXML + "'.", ex
            );
        }
        
        notification.setOpacity(0);
        notification.setLayoutX(area.getWidth());
        notification.setLayoutY(area.getHeight());
        
        final FadeTransition fade = new FadeTransition(millis(ENTER_SPEED), notification);
        fade.setFromValue(0);
        fade.setToValue(1);

        childrenOf(area).add(notification);
        fade.play();
        
        final Timeline timeline = new Timeline(new KeyFrame(
            TIMER, ev -> ref.get().fadeAway()
        ));
        timeline.play();
    }
    
    private FontAwesomeIconView createGlyph() {
        final FontAwesomeIconView view = new FontAwesomeIconView(icon);
        view.setSize(ICON_SIZE);
        return view;
    }
    
    private static void remove(Node node) {
        siblingsOf(node).remove(node);
    }
    
    private static ObservableList<Node> siblingsOf(Node node) {
        final Parent parent = node.getParent();
        
        if (parent == null) {
            throw new NullPointerException(
                "Can't delete node from 'null' parent."
            );
        }
        
        return childrenOf(parent);
    }
    
    private static ObservableList<Node> childrenOf(Node parent) {
        final Method getChildren;
        try {
            getChildren = parent.getClass().getMethod("getChildren");
        } catch (final NoSuchMethodException | SecurityException ex) {
            throw new SpeedmentException(
                "Could not find public method 'getChildren()' in class '" + 
                parent.getClass() + "'.", ex
            );
        }
        
        try {
            @SuppressWarnings("unchecked")
            final ObservableList<Node> siblings = (ObservableList<Node>) getChildren.invoke(parent);
            return siblings;
        } catch (final IllegalAccessException 
                     | IllegalArgumentException 
                     | InvocationTargetException 
                     | ClassCastException ex) {
            throw new SpeedmentException(
                "Error executing 'getChildren()' in class '" + 
                parent.getClass() + "'."
            );
        }
    }
    
    private final static int 
        ENTER_SPEED = 350, 
        EXIT_SPEED = 350,
        ENTER_Y = 100;
    
    private final static String ICON_SIZE = "24";
    private final static String NOTIFICATION_FXML = "/fxml/Notification.fxml";
    private final static Duration TIMER = Duration.seconds(10);
}