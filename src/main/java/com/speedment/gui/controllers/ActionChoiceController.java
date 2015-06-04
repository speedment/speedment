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

import com.speedment.gui.MainApp;
import com.speedment.gui.icons.Icons;
import static com.speedment.gui.util.FadeAnimation.fadeIn;
import static com.speedment.gui.util.FadeAnimation.fadeOut;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author Emil Forslund
 */
public class ActionChoiceController implements Initializable {
    
    @FXML private StackPane background;
    @FXML private ImageView imgGenerate;
    @FXML private ImageView imgOptions;
    @FXML private Label labelGenerate;
    @FXML private Label labelOptions;
    
    private final Pane parent;
    private final Runnable onGenerate, onConfigure;
    
    private final static Image
        generateOriginal = Icons.BIG_GENERATE.load(),
        optionsOriginal  = Icons.BIG_CONFIGURE.load(),
        generateHover    = Icons.BIG_GENERATE_HOVER.load(),
        optionsHover     = Icons.BIG_CONFIGURE_HOVER.load();
    
    public ActionChoiceController(Pane parent, Runnable onGenerate, Runnable onConfigure) {
        this.parent      = parent;
        this.onGenerate  = onGenerate;
        this.onConfigure = onConfigure;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createHoverEffect(imgGenerate, labelGenerate, generateOriginal, 
            generateHover, onGenerate);
        
        createHoverEffect(imgOptions, labelOptions, optionsOriginal, 
            optionsHover, onConfigure);
    }
    
    private void createHoverEffect(ImageView view, Label label, Image original, Image hover, Runnable onClick) {
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
    
    public static void showActionChoice(Pane parent, Runnable onGenerate, Runnable onConfigure) {
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