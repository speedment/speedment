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

import com.speedment.internal.gui.MainApp;
import com.speedment.internal.gui.icon.SpeedmentIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.speedment.internal.gui.util.FadeAnimation.fadeIn;
import static com.speedment.internal.gui.util.FadeAnimation.fadeOut;
import static java.util.Objects.requireNonNull;

/**
 * FXML Controller class for the big popup window that is displayed when the GUI is started to let the user choose
 * between generating code directly or configuring the settings first.
 *
 * @author Emil Forslund
 */
public final class ActionChoiceController implements Initializable {
    
    @FXML private StackPane background;
    @FXML private ImageView imgGenerate;
    @FXML private ImageView imgOptions;
    @FXML private Label labelGenerate;
    @FXML private Label labelOptions;
    
    private final Pane parent;
    private final Runnable onGenerate, onConfigure;
    
    private final static Image
        GENERATE_ORIGINAL = SpeedmentIcon.BIG_GENERATE.load(),
        OPTIONS_ORIGINAL  = SpeedmentIcon.BIG_CONFIGURE.load(),
        GENERATE_HOVER    = SpeedmentIcon.BIG_GENERATE_HOVER.load(),
        OPTIONS_HOVER     = SpeedmentIcon.BIG_CONFIGURE_HOVER.load();

    /**
     * Initializes the controller by specifying the parent component and actions to be executed when the two big buttons
     * are pressed. The parent is required so that this popup can close itself.
     *
     * @param parent       the parent
     * @param onGenerate   action to be performed when "generate" is pressed
     * @param onConfigure  action to be performed when "configure" is pressed
     */
    private ActionChoiceController(Pane parent, Runnable onGenerate, Runnable onConfigure) {
        this.parent      = requireNonNull(parent);
        this.onGenerate  = requireNonNull(onGenerate);
        this.onConfigure = requireNonNull(onConfigure);
    }

    /**
     * Initializes the controller class.
     *
     * @param url  the url
     * @param rb   the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requireNonNull(url);
        createHoverEffect(imgGenerate, labelGenerate, GENERATE_ORIGINAL, 
            GENERATE_HOVER, onGenerate);
        
        createHoverEffect(imgOptions, labelOptions, OPTIONS_ORIGINAL, 
            OPTIONS_HOVER, onConfigure);
    }

    /**
     * Creates the effect shown when a button in the popup is hovered.
     *
     * @param view      the image component to display the effect on
     * @param label     the label component to display the effect on
     * @param original  the original image (without hovering)
     * @param hover     the hover image
     * @param onClick   action to be performed when the button is pressed
     */
    private void createHoverEffect(ImageView view, Label label, Image original, Image hover, Runnable onClick) {
        requireNonNull(view);
        requireNonNull(label);
        requireNonNull(original);
        requireNonNull(hover);
        requireNonNull(onClick);
        view.setImage(original);
        view.setCursor(Cursor.HAND);
        view.setOnMouseEntered(ev -> view.setImage(hover));
        view.setOnMouseExited(ev -> view.setImage(original));
        
        final EventHandler<MouseEvent> handler = ev -> {
            onClick.run();
            fadeOut(background, e -> 
                parent.getChildren().remove(background)
            );
        };
        
        view.setOnMouseReleased(handler);
        label.setOnMouseReleased(handler);
    }

    /**
     * Creates and configures a new ActionChoice-component in the specified stage.
     *
     * @param parent       the parent pane to display the popup in
     * @param onGenerate   action to be performed if the "generate" button is pressed
     * @param onConfigure  action to be performed if the "configure" button is pressed
     */
    public static void showActionChoice(Pane parent, Runnable onGenerate, Runnable onConfigure) {
        requireNonNull(parent);
        requireNonNull(onGenerate);
        requireNonNull(onConfigure);
        final FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/ActionChoice.fxml"));
        final ActionChoiceController control = new ActionChoiceController(parent, onGenerate, onConfigure);
        loader.setController(control);

        try {
            final StackPane root = (StackPane) loader.load();
            root.setOpacity(0.0);
            parent.getChildren().add(root);
            root.prefWidthProperty().bind(parent.widthProperty());
            root.prefHeightProperty().bind(parent.heightProperty());
            fadeIn(root);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}