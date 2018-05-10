/**
 *
 * Copyright (c) 2006-2018, Speedment, Inc. All Rights Reserved.
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
import com.speedment.generator.core.component.EventComponent;
import com.speedment.generator.core.event.AfterGenerate;
import com.speedment.generator.core.event.BeforeGenerate;
import com.speedment.generator.core.event.FileGenerated;
import com.speedment.tool.core.brand.Brand;
import com.speedment.tool.core.component.UserInterfaceComponent;
import com.speedment.tool.core.resource.FontAwesome;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;
import static javafx.application.Platform.runLater;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 *
 * @author Emil Forslund
 */
public final class ToolbarController implements Initializable {

    @Inject private UserInterfaceComponent ui;
    @Inject private Brand uiBrand;
    @Inject private EventComponent eventComponent;
    
    private @FXML Button buttonReload;
    private @FXML Button buttonGenerate;
    private @FXML ImageView brand;
    private @FXML HBox hBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonReload.setOnAction(ev -> ui.reload());
        buttonGenerate.setOnAction(ev -> ui.generate());
        
        buttonReload.setGraphic(FontAwesome.REFRESH.view());
        buttonGenerate.setGraphic(FontAwesome.PLAY_CIRCLE.view());
        
        buttonReload.setTooltip(new Tooltip("Reload the metadata from the database and merge any changes with the existing configuration."));
        buttonGenerate.setTooltip(new Tooltip("Generate code using the current configuration. Automatically save the configuration before generation."));

        
        final Label progressLabel = new Label("");
        hBox.getChildren().add(2, progressLabel);
               
        eventComponent.on(BeforeGenerate.class, bg -> runLater(() -> progressLabel.setText("Generating...")));
        eventComponent.on(FileGenerated.class, fg -> runLater(() -> progressLabel.setText(fg.meta().getModel().getName())));
        eventComponent.on(AfterGenerate.class, ag -> runLater(() -> progressLabel.setText("")));
        
        uiBrand.logoLarge()
            .map(Image::new)
            .ifPresent(brand::setImage);
    }
}