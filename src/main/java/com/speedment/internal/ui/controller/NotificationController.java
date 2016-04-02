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
package com.speedment.internal.ui.controller;

import com.speedment.exception.SpeedmentException;
import com.speedment.internal.ui.UISession;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import static javafx.util.Duration.millis;

/**
 * FXML Controller class
 *
 * @author  Emil Forslund
 * @since   2.3
 */
public final class NotificationController implements Initializable {
    
    public final static Color 
        GREEN = Color.web("#1ce1b0"),
        BLUE  = Color.web("#45a6fc"),
        RED   = Color.web("#ff5555");
    
    private final String message;
    private final FontAwesomeIcon icon;
    private final Runnable onClose;
    private final Color color;
    private final AtomicBoolean destroyed;
    
    private @FXML Label notification;
    
    
    private NotificationController(String message, FontAwesomeIcon icon, Color color, Runnable onClose) {
        this.message = requireNonNull(message);
        this.icon    = requireNonNull(icon);
        this.color   = requireNonNull(color);
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
        notification.setStyle("-fx-border-color:" + toRGBCode(color));
    }
    
    public @FXML void close() {
        if (destroyed.compareAndSet(false, true)) {
            onClose.run();

            final FadeTransition fade = new FadeTransition(millis(EXIT_SPEED), notification);
            fade.setFromValue(1);
            fade.setToValue(0);

            final TranslateTransition trans = new TranslateTransition(millis(EXIT_SPEED), notification);
            trans.setFromY(0);
            trans.setToY(ENTER_Y);

            final SequentialTransition seq = new SequentialTransition(fade, trans);
            seq.setOnFinished(ev -> remove(notification));
            seq.play();
        }
    }
    
    public static void showNotification(UISession session, String message) {
        showNotification(session, message, DEFAULT_ICON);
    }
    
    public static void showNotification(UISession session, String message, Color color) {
        showNotification(session, message, DEFAULT_ICON, color, () -> {});
    }
    
    public static void showNotification(UISession session, String message, FontAwesomeIcon icon) {
        showNotification(session, message, icon, DEFAULT_COLOR, () -> {});
    }
    
    public static void showNotification(UISession session, String message, Runnable onClose) {
        showNotification(session, message, DEFAULT_ICON, DEFAULT_COLOR, onClose);
    }
    
    public static void showNotification(UISession session, String message, FontAwesomeIcon icon, Color color, Runnable onClose) {
        final FlowPane area = (FlowPane) session.getStage().getScene().lookup(NOTIFICATION_AREA_ID);
        
        if (area == null) {
            throw new SpeedmentException(
                "Could not find the '" + NOTIFICATION_AREA_ID + 
                "' node in the JavaFX scene."
            );
        }
        
        final FXMLLoader loader = new FXMLLoader(NotificationController.class.getResource(NOTIFICATION_FXML));
        final AtomicReference<NotificationController> ref = new AtomicReference<>();
        loader.setControllerFactory(clazz -> {
            ref.set(new NotificationController(message, icon, color, onClose));
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
            TIMER, ev -> ref.get().close()
        ));
        timeline.play();
    }
    
    private static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
    }
    
    private FontAwesomeIconView createGlyph() {
        final FontAwesomeIconView view = new FontAwesomeIconView(icon);
        view.setSize(ICON_SIZE);
        view.setFill(color);
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
        
        final ObservableList<Node> siblings;
        try {
            siblings = (ObservableList<Node>) getChildren.invoke(parent);
        } catch (final IllegalAccessException 
                     | IllegalArgumentException 
                     | InvocationTargetException 
                     | ClassCastException ex) {
            throw new SpeedmentException(
                "Error executing 'getChildren()' in class '" + 
                parent.getClass() + "'."
            );
        }
        
        return siblings;
    }
    
    private final static int 
        ENTER_SPEED = 350, 
        EXIT_SPEED = 350,
        ENTER_Y = 100;
    
    private final static String ICON_SIZE = "24";
    private final static FontAwesomeIcon DEFAULT_ICON = FontAwesomeIcon.EXCLAMATION;
    private final static Color DEFAULT_COLOR = BLUE;
    
    private final static String NOTIFICATION_FXML = "/fxml/Notification.fxml";
    private final static String NOTIFICATION_AREA_ID = "#notificationArea";
    
    private final static Duration TIMER = Duration.seconds(30);
}