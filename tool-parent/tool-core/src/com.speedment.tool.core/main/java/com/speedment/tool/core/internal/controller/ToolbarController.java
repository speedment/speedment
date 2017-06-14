/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

/**
 *
 * @author Emil Forslund
 */
public final class ToolbarController implements Initializable {

    private @Inject UserInterfaceComponent ui;
    private @Inject Brand uiBrand;
    
    private @FXML Button buttonReload;
    private @FXML Button buttonGenerate;
    private @FXML ImageView brand;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonReload.setOnAction(ev -> ui.reload());
        buttonGenerate.setOnAction(ev -> ui.generate());
        
        buttonReload.setGraphic(FontAwesome.REFRESH.view());
        buttonGenerate.setGraphic(FontAwesome.PLAY_CIRCLE.view());

        uiBrand.logoLarge()
            .map(Image::new)
            .ifPresent(brand::setImage);
    }
}